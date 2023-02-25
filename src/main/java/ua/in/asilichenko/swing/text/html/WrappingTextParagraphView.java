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
