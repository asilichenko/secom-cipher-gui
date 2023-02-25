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
