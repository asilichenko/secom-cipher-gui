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

import javax.swing.text.*;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

import static javax.swing.text.html.HTML.Tag.IMPLIED;

/**
 * This class is used to proper render the html page on a JEditorPane
 */
public class CustomHTMLEditorKit extends HTMLEditorKit {

    private final ViewFactory viewFactory = new HTMLFactory() {
        @Override
        public View create(Element elem) {
            AttributeSet attrs = elem.getAttributes();
            Object elementName = attrs.getAttribute(AbstractDocument.ElementNameAttribute);
            Object o = (elementName != null) ? null : attrs.getAttribute(StyleConstants.NameAttribute);
            if (o instanceof HTML.Tag kind) {
                if (IMPLIED == kind) return new WrappingTextParagraphView(elem); // <pre>
            }
            return super.create(elem);
        }
    };

    @Override
    public ViewFactory getViewFactory() {
        return this.viewFactory;
    }
}
