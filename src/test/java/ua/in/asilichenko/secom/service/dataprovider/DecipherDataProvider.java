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
