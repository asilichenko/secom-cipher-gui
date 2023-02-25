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
