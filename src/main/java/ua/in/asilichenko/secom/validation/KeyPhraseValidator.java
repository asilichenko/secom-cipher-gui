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

package ua.in.asilichenko.secom.validation;

import java.awt.*;

import static ua.in.asilichenko.secom.utils.TextUtil.prepareKey;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 */
public class KeyPhraseValidator extends AbstractValidator {

    private static final int KEY_PHRASE_MIN_LEN = 20;
    private static final int KEY_PHRASE_MAX_LEN = 2 * KEY_PHRASE_MIN_LEN;

    private static final String KEY_PHRASE_MIN_MSG = "Please enter a key phrase with at least 20 letters.";
    private static final String KEY_PHRASE_MAX_MSG = String.format("It's not allowed to use a key phrase longer than %d symbols.", KEY_PHRASE_MAX_LEN);

    public KeyPhraseValidator(Component parentComponent) {
        super(parentComponent);
    }

    @Override
    public boolean isValidationFail(String keyPhrase) {
        boolean retval = false;
        keyPhrase = prepareKey(keyPhrase);
        if (KEY_PHRASE_MIN_LEN > keyPhrase.length()) {
            showValidationError(KEY_PHRASE_MIN_MSG);
            retval = true;
        }
        return retval;
    }

    public boolean isLengthValid(int length) {
        boolean retval = true;
        if (KEY_PHRASE_MAX_LEN <= length) {
            showValidationError(KEY_PHRASE_MAX_MSG);
            retval = false;
        }
        return retval;
    }
}
