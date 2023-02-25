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
