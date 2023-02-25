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

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 */
public abstract class AbstractValidator {

    static final String WARNING_TITLE = "Warning";
    static final String VALIDATION_ERROR_TITLE = "Validation error";

    final Component parentComponent;

    AbstractValidator(Component parentComponent) {
        this.parentComponent = parentComponent;
    }

    public abstract boolean isValidationFail(String string);

    void showValidationError(String msg) {
        showMessageDialog(parentComponent, msg, VALIDATION_ERROR_TITLE, ERROR_MESSAGE);
    }
}
