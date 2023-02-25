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
