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

package ua.in.asilichenko.secom.service.dataprovider;

import org.testng.annotations.DataProvider;
import ua.in.asilichenko.secom.service.dataprovider.data.Data1;
import ua.in.asilichenko.secom.service.dataprovider.data.Data2;

public class DecipherDataProvider {

    @DataProvider
    public static Object[][] reverseColumnarDataProvider() {
        return new Object[][]{
                {Data1.enciphered, Data1.columnarKey2, Data1.disrupt},
                {Data1.transposition1, Data1.columnarKey1, Data1.reverseColumnar1},
                //
                {Data2.enciphered, Data2.columnarKey2, Data2.disrupt},
                {Data2.transposition1, Data2.columnarKey1, Data2.reverseColumnar1},
        };
    }

    @DataProvider
    public static Object[][] reverseDisruptDataProvider() {
        return new Object[][]{
                {Data1.disrupt, Data1.disruptMask, Data1.transposition1},
                {Data2.disrupt, Data2.disruptMask, Data2.transposition1},
        };
    }


    @DataProvider
    public static Object[][] checkerBoardDataProvider() {
        return new Object[][]{
                {Data1.reverseColumnar1, Data1.checkerBoard(), Data1.deciphered.toCharArray()},
                {Data2.reverseColumnar1, Data2.checkerBoard(), Data2.deciphered.toCharArray()},
        };
    }

    @DataProvider
    public static Object[][] decodeDataProvider() {
        return new Object[][]{
                {Data1.enciphered, Data1.keyPhrase, Data1.deciphered},
                {Data2.enciphered, Data2.keyPhrase, Data2.deciphered},
        };
    }
}
