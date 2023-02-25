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
