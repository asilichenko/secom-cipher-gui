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
