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

import com.google.common.collect.BiMap;

/**
 * Cipher keys container.
 *
 * @param checkerBoard the Straddling Checkerboard key
 * @param columnarKey1 the first Columnar Transposition key
 * @param columnarKey2 the second Columnar Transposition key
 * @author Oleksii Sylichenko (a.silichenko@gmail.com)
 */
public record Keys(BiMap<Character, CheckerBoardCode> checkerBoard, int[] columnarKey1, int[] columnarKey2) {
}
