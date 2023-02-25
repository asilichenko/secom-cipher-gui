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

import static javax.swing.JOptionPane.*;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static ua.in.asilichenko.secom.utils.TextUtil.isCipherTextError;
import static ua.in.asilichenko.secom.utils.TextUtil.isCipherTextWarning;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 */
public class CipherTextValidator extends AbstractValidator {

    private static final String CIPHER_TEXT_VALIDATION_MSG = "The cipher text must consist of numbers.";
    private static final String CIPHER_TEXT_WARN_MSG = "The text contains characters other than numbers, spaces or line breaks.\nWould you like to proceed?";

    public CipherTextValidator(Component parentComponent) {
        super(parentComponent);
    }

    @Override
    public boolean isValidationFail(String cipherText) {
        boolean retval = false;
        if (isCipherTextError(cipherText)) {
            showValidationError(CIPHER_TEXT_VALIDATION_MSG);
            retval = true;
        } else if (isCipherTextWarning(cipherText)) {
            retval = NO_OPTION == showConfirmDialog(parentComponent,
                    CIPHER_TEXT_WARN_MSG, WARNING_TITLE,
                    YES_NO_OPTION, WARNING_MESSAGE);
        }
        return retval;
    }
}
