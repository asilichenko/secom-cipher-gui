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
 * Code of character encoded with checkerboard.
 * Can be one-digit or two-digits number.
 *
 * @param row row in a checkerboard grid, null for the most common characters: ESTONIA
 * @param col column in a checkerboard grid
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 */
public record CheckerBoardCode(Integer row, int col) {
}
