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

package ua.in.asilichenko.secom.utils;

import ua.in.asilichenko.secom.domain.KeyHalves;

import java.nio.IntBuffer;
import java.util.Arrays;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 * Creation date: 01.02.2023
 */
public class KeyUtil {

    private static final String LETTERS_ONLY = "[A-Z]+";
    private static final String MASTER_KEY_VALIDATION_MSG = "Key phrase must consists of A-Z letters only.";
    public static final String DIGIT_VALIDATION_MSG = "Array must contains digits only.";

    /**
     * To number letter in alphabetical order considering the same letters.
     * <p>
     * 0 represents 10
     *
     * @param string HELLO
     * @return [2, 1, 3, 4, 5]
     */
    static int[] numberLetters(String string) {
        final IntBuffer buffer = IntBuffer.allocate(string.length());
        string.chars().forEach(buffer::put);

        final int[] numbered = numbering(buffer.array());
        return modulo(numbered);
    }

    /**
     * Parse key phrase and split into two 10-digits parts.
     *
     * @param keyPhrase key phrase, letters only
     * @return two 10-digit arrays
     */
    public static KeyHalves createKeyHalves(String keyPhrase) {
        if (!keyPhrase.matches(LETTERS_ONLY)) throw new IllegalArgumentException(MASTER_KEY_VALIDATION_MSG);

        final String keyString1 = keyPhrase.substring(0, 10);
        final String keyString2 = keyPhrase.substring(10, 20);

        final int[] first = numberLetters(keyString1);
        final int[] second = numberLetters(keyString2);

        return new KeyHalves(first, second);
    }

    /**
     * Number array values in ascending order considering the same values.
     *
     * @param arr array of numbers
     * @return ordinal numbers of the array values
     */
    static int[] numbering(int[] arr) {
        arr = Arrays.copyOf(arr, arr.length);
        final int[] retval = new int[arr.length];

        for (int counter = 1; counter <= arr.length; counter++) {
            int value = 255, index = -1;
            for (int i = 0; i < arr.length; i++) {
                final int keyVal = arr[i];
                if (keyVal < value) {
                    value = keyVal;
                    index = i;
                }
            }
            arr[index] = 255;
            retval[index] = counter;
        }

        return retval;
    }

    /**
     * Number digits in the array considering the same values and take modulo of the order numbers.
     *
     * @param digits array of 0-9 digits
     * @return arrays of ordinal numbers, where 0 represents 10
     */
    public static int[] numberDigits(int[] digits) {
        validateDigitsArray(digits);
        digits = withZeroAsTen(digits);
        final int[] numbered = numbering(digits);
        return modulo(numbered);
    }

    /**
     * Take modulo 10 over all the numbers in the array.
     *
     * @param arr numbers array
     * @return array numbers taken modulo 10
     */
    public static int[] modulo(int[] arr) {
        final int[] retval = new int[arr.length];
        for (int i = 0; i < arr.length; i++) retval[i] = arr[i] % 10;
        return retval;
    }

    /**
     * Number the array values considering the same values and return their positions in ascending order of value.
     *
     * @param digits array of digits 0-9, where 0 = 10
     * @return array of ordinal positions
     */
    public static int[] ordinal(int[] digits) {
        validateDigitsArray(digits);
        digits = withZeroAsTen(digits);
        digits = numbering(digits);

        final int[] retval = new int[digits.length];
        for (int i = 0; i < retval.length; i++) {
            final int v = digits[i] - 1;
            retval[v] = i;
        }
        return retval;
    }

    /**
     * Validate if array contains only one-number digits.
     *
     * @param digits array of numbers
     */
    static void validateDigitsArray(int[] digits) {
        if (Arrays.stream(digits).anyMatch(d -> d >= 10)) throw new IllegalArgumentException(DIGIT_VALIDATION_MSG);
    }

    /**
     * Create copy of the array with 0 replaced by 10.
     *
     * @param arr array
     * @return copy of the arrays with 0 replaced by 10
     */
    static int[] withZeroAsTen(int[] arr) {
        final int[] retval = new int[arr.length];
        for (int i = 0; i < arr.length; i++) retval[i] = 0 == arr[i] ? 10 : arr[i];
        return retval;
    }
}
