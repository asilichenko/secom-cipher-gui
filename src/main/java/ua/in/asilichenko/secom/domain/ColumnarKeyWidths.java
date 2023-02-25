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

package ua.in.asilichenko.secom.domain;

/**
 * Container for columnar keys width values.
 *
 * @param width1 width of the first columnar key
 * @param width2 width of the second columnar key
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 */
public record ColumnarKeyWidths(int width1, int width2) {
}
