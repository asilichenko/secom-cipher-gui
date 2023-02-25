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

package ua.in.asilichenko.secom.function;

/**
 * Is used to make disruption method generic.
 *
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 */
@FunctionalInterface
public interface DisruptConsumer {

    /**
     * Maps the digits sequence index to the disrupt matrix coordinates.
     *
     * @param index index of the digits sequence
     * @param i     matrix coordinate
     * @param j     matrix coordinate
     */
    void consume(int index, int i, int j);
}
