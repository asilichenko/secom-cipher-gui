/*
 * Copyright (c) 2023 Oleksii Sylichenko.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ua.in.asilichenko.secom.service;

import ua.in.asilichenko.secom.domain.CheckerBoardCode;
import ua.in.asilichenko.secom.domain.KeyHalves;
import ua.in.asilichenko.secom.domain.Keys;
import ua.in.asilichenko.secom.domain.DisruptMask;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Map;

import static ua.in.asilichenko.secom.utils.KeyUtil.ordinal;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 */
public class DecipherService extends AbstractCipherService {

    /**
     * Unknown character. <br/>
     * Failed to be decoded.
     */
    private static final char UNK = '?';

    public char[] decipher(int[] digits, KeyHalves keyHalves) {
        final int size = digits.length;

        final Keys keys = makeKeys(keyHalves);
        final Map<CheckerBoardCode, Character> checkerBoard = keys.checkerBoard().inverse();
        final int[] columnarKey1 = keys.columnarKey1();
        final int[] columnarKey2 = keys.columnarKey2();

        final int[][] reverseColumnar2 = reverseColumnar(digits, columnarKey2);

        final DisruptMask disruptMask = makeDisruptMask(columnarKey2, size);
        final int[] reverseDisrupt = reverseDisrupt(reverseColumnar2, size, disruptMask);

        final int[][] reverseColumnar1 = reverseColumnar(reverseDisrupt, columnarKey1);

        return checkerBoardDecode(reverseColumnar1, checkerBoard);
    }

    /**
     * Decode the Straddling Checkerboard
     *
     * @param digits       digits to decode in a transposition matrix
     * @param checkerBoard checkerboard
     * @return deciphered text
     */
    char[] checkerBoardDecode(int[][] digits, Map<CheckerBoardCode, Character> checkerBoard) {
        final CharBuffer retval = CharBuffer.allocate(digits.length * digits[0].length);
        Integer buffer = null;
        for (int[] line : digits) {
            for (int col = 0; col < digits[0].length; col++) {
                final int digit = line[col];

                if (-1 == digit) {
                    if (null != buffer && 0 != buffer) retval.append(UNK);
                    break;
                }

                final CheckerBoardCode code = new CheckerBoardCode(buffer, digit);

                final char ch;
                if (null != buffer) {
                    ch = checkerBoard.getOrDefault(code, UNK);
                    buffer = null;
                } else if (!checkerBoard.containsKey(code)) {
                    buffer = digit;
                    continue;
                } else {
                    ch = checkerBoard.get(code);
                }
                retval.append(ch);
            }
        }
        return Arrays.copyOf(retval.array(), retval.position());
    }

    /**
     * Reverse Columnar Transposition
     *
     * @param digits digits sequence to process
     * @param key    transposition key
     * @return original transposition matrix
     */
    int[][] reverseColumnar(int[] digits, int[] key) {
        final int size = digits.length;
        final int keySize = key.length;

        final int fullRows = size / keySize;
        final int partialRowSize = size % keySize;
        final boolean isLastRowPartial = 0 != partialRowSize;
        final int[][] retval = new int[fullRows + (isLastRowPartial ? 1 : 0)][keySize];

        int counter = 0;
        for (int col : ordinal(key)) {
            for (int row = 0; row < retval.length; row++) {
                final boolean isTextOverflow = isLastRowPartial && retval.length - 1 == row && col >= partialRowSize;
                retval[row][col] = isTextOverflow ? -1 : digits[counter++];
            }
        }
        return retval;
    }

    /**
     * Reverse disruption process.
     * Read out digits from the matrix according to disruption mask.
     *
     * @param matrix      transposition matrix
     * @param size        length of the cipher text
     * @param disruptMask disruption mask
     * @return digits sequence
     */
    int[] reverseDisrupt(int[][] matrix, int size, DisruptMask disruptMask) {
        final int[] retval = new int[size];
        disrupt(disruptMask, size, (index, i, j) -> retval[index] = matrix[i][j]);
        return retval;
    }
}
