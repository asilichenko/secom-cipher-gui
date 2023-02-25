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
