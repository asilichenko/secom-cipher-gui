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

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 */
public class PlainTextValidator extends AbstractValidator {

    private final int textLenLimit;

    private static final String EMPTY_TEXT_VALIDATION_MSG = "Please enter a plain text to encipher.";
    private static final String TEXT_LIMIT_VALIDATION_MSG = "Please enter a text with less than %,d characters long.";

    public PlainTextValidator(Component parentComponent, int textLenLimit) {
        super(parentComponent);
        this.textLenLimit = textLenLimit;
    }

    @Override
    public boolean isValidationFail(String plainText) {
        boolean retval = false;
        if (plainText.isEmpty()) {
            showValidationError(EMPTY_TEXT_VALIDATION_MSG);
            retval = true;
        }
        return retval;
    }

    public boolean isNotLengthValid(int textLen) {
        boolean retval = false;
        if (textLenLimit < textLen) {
            showValidationError(String.format(TEXT_LIMIT_VALIDATION_MSG, textLenLimit));
            retval = true;
        }
        return retval;
    }
}
