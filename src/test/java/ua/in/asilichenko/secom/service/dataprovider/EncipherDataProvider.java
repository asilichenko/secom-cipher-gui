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
