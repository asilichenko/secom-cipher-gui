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
 * Divides a matrix of digits into two special-shaped parts.
 *
 * @param mask                 matrix of 0, 1 and -1. <br/>
 *                             0 represents the first part, <br/>
 *                             1 is the second and <br/>
 *                             -1 is out of text.
 * @param secondPartStartIndex index of the input sequence where the second part starts.
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 */
public record DisruptMask(int[][] mask, int secondPartStartIndex) {

    /**
     * Is digit of coordinates located in the second part.
     *
     * @param i coordinate
     * @param j coordinate
     * @return true if the second part, false otherwise.
     */
    public boolean isSecondPart(int i, int j) {
        return 1 == mask[i][j];
    }
}
