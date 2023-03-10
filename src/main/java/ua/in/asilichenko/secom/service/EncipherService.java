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
import ua.in.asilichenko.secom.domain.DisruptMask;
import ua.in.asilichenko.secom.domain.Keys;
import ua.in.asilichenko.secom.domain.KeyHalves;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static ua.in.asilichenko.secom.utils.KeyUtil.ordinal;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 */
public class EncipherService extends AbstractCipherService {

    public static final int GROUP_SIZE = 5;
    public static final int GROUPS_PER_LINE = 10;
    public static final int DIGITS_PER_LINE = GROUP_SIZE * GROUPS_PER_LINE;

    public String encipher(char[] plain, KeyHalves keyHalves) {
        final Keys keys = makeKeys(keyHalves);
        final Map<Character, CheckerBoardCode> checkerBoard = keys.checkerBoard();
        final int[] columnarKey1 = keys.columnarKey1();
        final int[] columnarKey2 = keys.columnarKey2();

        final List<Integer> checkerBoardEnciphered = checkerBoardEncipher(plain, checkerBoard);
        while (0 != checkerBoardEnciphered.size() % GROUP_SIZE) checkerBoardEnciphered.add(0); // padding
        final int[] columnarEnciphered1 = transposition1(columnarKey1, checkerBoardEnciphered);
        final DisruptMask disruptMask = makeDisruptMask(columnarKey2, checkerBoardEnciphered.size());
        final int[][] disrupted = disrupt(columnarEnciphered1, disruptMask);
        return transposition2(columnarKey2, disrupted, checkerBoardEnciphered.size());
    }

    /**
     * The Straddling Checkerboard
     *
     * @param plain        plain text
     * @param checkerBoard checkerboard
     * @return text encoded with digits
     */
    List<Integer> checkerBoardEncipher(char[] plain, Map<Character, CheckerBoardCode> checkerBoard) {
        final List<Integer> retval = new ArrayList<>();
        for (char ch : plain) {
            final CheckerBoardCode code = checkerBoard.get(ch); // text expected to be cleaned up and code is always must be found
            if (null != code.row()) retval.add(code.row());
            retval.add(code.col());
        }
        return retval;
    }

    /**
     * The first Columnar Transposition
     *
     * @param key    key
     * @param digits digits to encipher
     * @return enciphered sequence
     */
    int[] transposition1(int[] key, List<Integer> digits) {
        final int[] retval = new int[digits.size()];
        int counter = 0;
        for (int col : ordinal(key)) {
            for (int i = col; i < digits.size(); i += key.length) {
                retval[counter++] = digits.get(i);
            }
        }
        return retval;
    }

    /**
     * Put digits sequence into transposition matrix using the disruption mask.
     *
     * @param digits      digits sequence to encipher
     * @param disruptMask disruption mask
     * @return transposition matrix with digits which were placed in disruption order
     */
    int[][] disrupt(int[] digits, DisruptMask disruptMask) {
        final int[][] retval = new int[disruptMask.mask().length][disruptMask.mask()[0].length];
        Arrays.stream(retval).forEach(row -> Arrays.fill(row, -1));
        disrupt(disruptMask, digits.length, (index, i, j) -> retval[i][j] = digits[index]);
        return retval;
    }

    /**
     * The second Columnar Transposition
     *
     * @param key    key
     * @param matrix matrix with digits to encipher
     * @param size   length of the cipher-text
     * @return cipher-text
     */
    String transposition2(int[] key, int[][] matrix, int size) {
        final StringBuilder retval = new StringBuilder();
        int counter = size;
        for (int col : ordinal(key)) {
            for (int[] line : matrix) {
                final int digit = line[col];
                if (digit < 0) continue;
                if (0 == counter--) break;
                retval.append(digit);
            }
        }
        return retval.toString();
    }
}
