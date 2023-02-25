/*
 * Copyright (c) 2023 Oleksii Sylichenko.
 *
 * Licensed under the LGPL, Version 3.0 or later (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.gnu.org/licenses/lgpl-3.0.en.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ua.in.asilichenko.secom.service;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import ua.in.asilichenko.secom.domain.*;
import ua.in.asilichenko.secom.function.DisruptConsumer;

import java.util.Arrays;

import static ua.in.asilichenko.secom.utils.KeyUtil.numberDigits;
import static ua.in.asilichenko.secom.utils.KeyUtil.ordinal;

/**
 * Base class for enciphering and deciphering.
 *
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 */
public abstract class AbstractCipherService {

    /**
     * Number of generated digits by LFG.
     * {@link #generateBaseSequence(int[])}
     */
    private static final int LFG_SIZE = 50;

    /**
     * 1 2 3 4 5 6 7 8 9 10 - index <br/>
     * -------------------- <br/>
     * E S • T O • N I • A - the most frequently letters <br/>
     * B C D F G H J K L M <br/>
     * P Q R U V W X Y Z * <br/>
     * 1 2 3 4 5 6 7 8 9 0 - digits <br/>
     */
    static final char[][] ALPHABET = new char[][]{
            "ES•TO•NI•A".toCharArray(),
            "BCDFGHJKLM".toCharArray(),
            "PQRUVWXYZ ".toCharArray(),
            "1234567890".toCharArray()
    };

    /**
     * Indicator for blanks in the first {@link #ALPHABET} row.
     */
    static final char BLANK = '•';

    /**
     * Error message for invalid LFG-sequence.
     */
    private static final String BASE_SEQ_VALIDATION_MSG = "Cannot encipher with such keys: pseudo-random sequence is not valid.";

    /**
     * Make the Checkerboard, the first and the second Columnar Transposition keys.
     */
    Keys makeKeys(KeyHalves keyHalves) {
        final int[] sumMasterKey = sum(keyHalves.first(), keyHalves.second());
        final int[] baseSequence = generateBaseSequence(sumMasterKey);

        final int[] last10Lfg = new int[10];
        System.arraycopy(baseSequence, baseSequence.length - last10Lfg.length, last10Lfg, 0, last10Lfg.length);

        final int[] checkerBoardKey = numberDigits(last10Lfg);
        final BiMap<Character, CheckerBoardCode> checkerBoard = makeCheckerBoard(checkerBoardKey);

        final ColumnarKeyWidths columnarKeyWidths = obtainColumnarWidths(baseSequence);
        final int[] columnarKey1 = new int[columnarKeyWidths.width1()];
        final int[] columnarKey2 = new int[columnarKeyWidths.width2()];

        final int[] sumKey0Key2 = sum(checkerBoardKey, keyHalves.second());
        final int[] order = ordinal(sumKey0Key2);
        final int totalRows = baseSequence.length / order.length;

        for (int i = 0; i < columnarKey1.length + columnarKey2.length; i++) {
            final int col = order[i / totalRows];
            final int row = i % totalRows;
            final int digit = baseSequence[row * order.length + col];
            if (i < columnarKey1.length) columnarKey1[i] = digit;
            else columnarKey2[i - columnarKey1.length] = digit;
        }

        return new Keys(checkerBoard, columnarKey1, columnarKey2);
    }

    /**
     * The first row as is, other are shifted according to the key.
     *
     * @param key the Straddling Checkerboard key
     * @return double-sided map of the {@link #ALPHABET} characters and their cipher-codes
     */
    BiMap<Character, CheckerBoardCode> makeCheckerBoard(int[] key) {
        final BiMap<Character, CheckerBoardCode> retval = HashBiMap.create();
        final int[] blanks = new int[3];
        for (int row = 0, blankCounter = 0; row < ALPHABET.length; row++) {
            final char[] alphabetLine = ALPHABET[row];
            for (int alphabetIndex = 0; alphabetIndex < alphabetLine.length; alphabetIndex++) {
                //
                final char ch = alphabetLine[alphabetIndex];
                final CheckerBoardCode code;
                if (0 == row) {
                    final int col = key[alphabetIndex];
                    code = new CheckerBoardCode(null, col);
                    if (BLANK == ch) {
                        blanks[blankCounter++] = col;
                        continue;
                    }
                } else {
                    final int blank = blanks[row - 1];
                    final int index = (alphabetIndex + (blank - 1) + key.length) % key.length;
                    code = new CheckerBoardCode(blank, key[index]);
                }
                retval.put(ch, code);
                //
            }
        }
        return retval;
    }

    /**
     * To determine the number of columns for the two transpositions,
     * take the unique digits one by one, starting from the end of the LFG‑sequence,
     * moving from left to right, add the digits until the result is greater than 9.
     *
     * @param sequence LFG-sequence
     * @return widths of the columnar keys
     */
    ColumnarKeyWidths obtainColumnarWidths(int[] sequence) {
        int index = sequence.length;
        int width1 = 0, width2 = 0;

        for (int i = 0; i < 2; i++) {
            int sumOfUniques = 0;
            boolean[] usedDigits = new boolean[10];
            while (sumOfUniques <= 9) {
                if (0 == index) throw new IllegalStateException(BASE_SEQ_VALIDATION_MSG);
                final int v = sequence[--index];
                if (!usedDigits[v]) {
                    usedDigits[v] = true;
                    sumOfUniques += v;
                }
            }
            if (0 == width1) width1 = sumOfUniques;
            else width2 = sumOfUniques;
        }

        return new ColumnarKeyWidths(width1, width2);
    }

    /**
     * Make mask for disruption.
     *
     * @return matrix holding triangular areas
     */
    DisruptMask makeDisruptMask(int[] key, int totalDigits) {
        final int lastIndex = totalDigits % key.length;
        final boolean isLastRowPartial = 0 != lastIndex;

        final int[][] mask = new int[totalDigits / key.length + (isLastRowPartial ? 1 : 0)][key.length];
        if (isLastRowPartial) Arrays.fill(mask[mask.length - 1], lastIndex, mask[0].length, -1);

        int row = 0, maskStartIndex = totalDigits;
        for (int keyCol : ordinal(key)) { // triangle start col
            for (int startCol = keyCol; startCol < key.length && row < mask.length; startCol++, row++) { // start col for the row
                final boolean isLastRow = mask.length - 1 == row;
                for (int col = startCol; col < key.length; col++) { // curr col of the row to fill in
                    //
                    if (isLastRow && lastIndex > 0 && lastIndex <= col) break;
                    mask[row][col] = 1;
                    maskStartIndex--;
                    //
                }
            }
            row++;
        }
        return new DisruptMask(mask, maskStartIndex);
    }

    /**
     * Lagged Fibonacci generator (LFG)
     * <p>
     * Put the seed to the result.
     * <p>
     * Add the first two digits of the result,
     * take only the last digit and append it
     * to the end of the result.
     *
     * @return 50 generated digits
     * @see <a href="https://en.wikipedia.org/wiki/Lagged_Fibonacci_generator">Lagged Fibonacci generator</a>
     */
    int[] generateBaseSequence(int[] seed) {
        final int[] retval = new int[seed.length + LFG_SIZE];
        System.arraycopy(seed, 0, retval, 0, seed.length);
        for (int i = seed.length; i < retval.length; i++) {
            retval[i] = sum(retval[i - seed.length], retval[i - seed.length + 1]);
        }
        return Arrays.copyOfRange(retval, seed.length, retval.length);
    }

    /**
     * Sum up two values and get only the last digit.
     *
     * @param a the first term
     * @param b the second term
     * @return sum of the terms and modulo of the result
     */
    int sum(int a, int b) {
        return (a + b) % 10;
    }

    /**
     * Sup up two arrays of integers and return array of the modulus of the sum.
     *
     * @param a the first term
     * @param b the second term
     * @return array of the modulus of the sum values.
     */
    int[] sum(int[] a, int[] b) {
        final int[] retval = new int[a.length];
        for (int i = 0; i < retval.length; i++) retval[i] = sum(a[i], b[i]);
        return retval;
    }

    /**
     * Process disrupt the digits sequence with the matrix by the mask:
     * for enciphering - put digits into disrupted matrix,
     * for deciphering - get from.
     *
     * @param disruptMask disrupt areas mask
     * @param size        size of the digits sequence
     * @param consumer    the function that puts a digit into the matrix or the gets from
     */
    void disrupt(DisruptMask disruptMask, int size, DisruptConsumer consumer) {
        int firstPartIndex = 0, secondPartIndex = disruptMask.secondPartStartIndex();
        for (int i = 0; i < disruptMask.mask().length; i++) {
            for (int j = 0; j < disruptMask.mask()[0].length; j++) {
                final int index;
                if (disruptMask.isSecondPart(i, j)) {
                    if (secondPartIndex == size) continue;
                    index = secondPartIndex++;
                } else {
                    if (firstPartIndex == disruptMask.secondPartStartIndex()) continue;
                    index = firstPartIndex++;
                }
                consumer.consume(index, i, j);
            }
        }
    }
}
