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

package ua.in.asilichenko.swing.text.html;

import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.html.ParagraphView;

/**
 * Adds possibility to wrap long words on a html page in the JEditorPane
 */
public class WrappingTextParagraphView extends ParagraphView {

    public WrappingTextParagraphView(Element elem) {
        super(elem);
    }

    @Override
    public float getMinimumSpan(int axis) {
        return View.X_AXIS == axis ? 0 : super.getMinimumSpan(axis);
    }
}
