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

public class EncipherDataProvider {

    @DataProvider
    public static Object[][] checkerBoardEncodeDataProvider() {
        return new Object[][]{
                {Data1.plain, Data1.checkerBoard().inverse(), Data1.checkerBoardEncoded}, // MAKE NEW FRIENDS BUT KEEP THE OLD
                {Data2.plain, Data2.checkerBoard().inverse(), Data2.checkerBoardEncoded}, // SECURE COMMUNICATION KEY
        };
    }

    @DataProvider
    public static Object[][] transposition1DataProvider() {
        return new Object[][]{
                {Data1.columnarKey1, Data1.checkerBoardEncoded + '0', Data1.transposition1}, // MAKE NEW FRIENDS BUT KEEP THE OLD
                {Data2.columnarKey1, Data2.checkerBoardEncoded, Data2.transposition1}, // SECURE COMMUNICATION KEY
        };
    }

    @DataProvider
    public static Object[][] disruptDataProvider() {
        return new Object[][]{
                {Data1.transposition1, Data1.disruptMask, Data1.disrupt}, // MAKE NEW FRIENDS BUT KEEP THE OLD
                {Data2.transposition1, Data2.disruptMask, Data2.disrupt}, // SECURE COMMUNICATION KEY
        };
    }

    @DataProvider
    public static Object[][] transposition2DataProvider() {
        return new Object[][]{
                {Data1.columnarKey2, Data1.disrupt, Data1.enciphered}, // MAKE NEW FRIENDS BUT KEEP THE OLD
                {Data2.columnarKey2, Data2.disrupt, Data2.enciphered}, // SECURE COMMUNICATION KEY
        };
    }

    @DataProvider
    public static Object[][] encodeDataProvider() {
        return new Object[][]{
                {Data1.plain, Data1.keyPhrase, Data1.enciphered}, // MAKE NEW FRIENDS BUT KEEP THE OLD
                {Data2.plain, Data2.keyPhrase, Data2.enciphered}, // SECURE COMMUNICATION KEY
        };
    }
}
