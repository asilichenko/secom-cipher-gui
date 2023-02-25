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

package ua.in.asilichenko.secom.utils;

import java.nio.IntBuffer;

import static ua.in.asilichenko.secom.service.EncipherService.*;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 */
public class TextUtil {

    /**
     * Neither letter, nor space, nor digit
     */
    public static final String PLAIN_TEXT_FILTER = "[^A-Z \\d]";
    /**
     * Not a digit
     */
    public static final String CIPHER_TEXT_FILTER = "\\D";

    /**
     * Remove all non-numbering characters
     *
     * @param cipherText string of numbers, could be separated with spaces
     * @return string of numbers
     */
    public static String prepareCipherText(String cipherText) {
        return cipherText.replaceAll(CIPHER_TEXT_FILTER, "");
    }

    /**
     * Check if non-digit, non-space and non-line break characters present
     *
     * @param cipherText cipher text
     * @return true if text consists only of digits, spaces and line breaks
     */
    public static boolean isCipherTextWarning(String cipherText) {
        return cipherText.chars().anyMatch(ch -> !(isDigit(ch) || ' ' == ch || '\n' == ch));
    }

    private static boolean isDigit(int ch) {
        return '0' <= ch && ch <= '9';
    }

    /**
     * Check if text doesn't contain any digits
     *
     * @param cipherText cipher text
     * @return true if no digits found
     */
    public static boolean isCipherTextError(String cipherText) {
        return cipherText.chars().noneMatch(TextUtil::isDigit);
    }

    /**
     * Remove spaces
     *
     * @param keyPhrase row phrase, may contain spaces
     * @return string of row letters
     */
    public static String prepareKey(String keyPhrase) {
        return keyPhrase.replace(" ", "").toUpperCase();
    }

    /**
     * Replace line breaks with spaces, make upper case, remove special characters.
     *
     * @param plainText plain text, may contain several lines or numbers
     * @return single line plain text consists of letters and numbers only
     */
    public static String preparePlainText(String plainText) {
        return plainText
                .replace("\n", " ").toUpperCase()
                .replaceAll(PLAIN_TEXT_FILTER, "");
    }

    /**
     * Split cipher text in groups of GROUP_SIZE numbers and DIGITS_PER_LINE groups per line
     *
     * @param digits cipher text, numbers
     * @return formatted cipher text
     */
    public static String makeGroups(String digits) {
        final StringBuilder retval = new StringBuilder();
        for (int i = 0; i < digits.length(); i++) {
            if (i > 0) {
                if (0 == i % DIGITS_PER_LINE) retval.append("\n");
                else if (0 == i % GROUP_SIZE) retval.append(" ");
            }
            retval.append(digits.charAt(i));
        }
        return retval.toString();
    }

    /**
     * Read digits from string and put them into array
     *
     * @param secret cipher text
     * @return array of digits in the secret
     */
    public static int[] readDigits(String secret) {
        final IntBuffer digits = IntBuffer.allocate(secret.length());
        for (char digit : secret.toCharArray()) digits.put(digit - '0');
        return digits.array();
    }
}
