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

package ua.in.asilichenko.secom.utils;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.in.asilichenko.secom.utils.KeyUtil.*;

public class WhenArrayIsBeingNumberedTest {

    @DataProvider
    public Object[][] zeroCountedAsTenDataProvider() {
        return new Object[][]{
                {new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}, new int[]{10, 10, 10, 10, 10, 10, 10, 10, 10}},
                {new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0}, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}},
                {new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[]{10, 1, 2, 3, 4, 5, 6, 7, 8, 9}},
                {new int[]{0, 9, 8, 7, 6, 5, 4, 3, 2, 1}, new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1}},
                {new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0}, new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 10}},
        };
    }

    @Test(dataProvider = "zeroCountedAsTenDataProvider")
    public void zeroShouldBeConsideredTen(int[] input, int[] expected) {
        final int[] actual = withZeroAsTen(input);
        assertThat(actual, is(expected));
    }

    @DataProvider
    public Object[][] numberingDataProvider() {
        return new Object[][]{
                {
                        new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                        new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
                },
                {
                        new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16},
                        new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16}
                },
                {
                        new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1},
                        new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1}
                },
                {
                        new int[]{16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1},
                        new int[]{16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1}
                },
                {
                        new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
                },
                {
                        new int[]{10, 1, 2, 3},
                        new int[]{4, 1, 2, 3}
                },
                {
                        new int[]{1, 5, 9},
                        new int[]{1, 2, 3}
                },
                {
                        new int[]{9, 8, 5, 2},
                        new int[]{4, 3, 2, 1}
                },
                {
                        new int[]{2, 5, 8, 9},
                        new int[]{1, 2, 3, 4}
                },
                {
                        new int[]{1, 1, 1, 1},
                        new int[]{1, 2, 3, 4}
                },
                {
                        new int[]{5, 1, 5, 1, 2, 3, 6, 6, 4, 8},
                        new int[]{6, 1, 7, 2, 3, 4, 8, 9, 5, 10}
                },
                {
                        new int[]{11, 22, 33, 44, 55, 66, 77, 88, 88, 100},
                        new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
                },
                {
                        new int[]{10, 10, 22, 22, 38, 45, 58, 38, 45, 57},
                        new int[]{1, 2, 3, 4, 5, 7, 10, 6, 8, 9}
                },
        };
    }

    @Test(dataProvider = "numberingDataProvider", dependsOnMethods = "zeroShouldBeConsideredTen")
    public void numbersShouldBeNumberedInAscendingOrderConsideringSameNumbers(int[] input, int[] expected) {
        final int[] actual = numbering(input);
        assertThat(actual, is(expected));
    }

    @DataProvider
    public Object[][] digitsDataProvider() {
        return new Object[][]{
                {
                        new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0},
                        new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0}
                },
                {
                        new int[]{0, 9, 8, 7, 6, 5, 4, 3, 2, 1},
                        new int[]{0, 9, 8, 7, 6, 5, 4, 3, 2, 1}
                },
                {
                        new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0}
                },
                {
                        new int[]{1, 1, 2, 2, 3, 4, 5, 3, 4, 5},
                        new int[]{1, 2, 3, 4, 5, 7, 9, 6, 8, 0}
                },
                {
                        new int[]{7, 9, 9, 2, 1, 9, 0, 1, 6, 6},
                        new int[]{6, 7, 8, 3, 1, 9, 0, 2, 4, 5}
                },
        };
    }

    @Test(dataProvider = "digitsDataProvider",
            dependsOnMethods = {
                    "testModulo",
                    "zeroShouldBeConsideredTen",
                    "numbersShouldBeNumberedInAscendingOrderConsideringSameNumbers"
            })
    public void digitsShouldBeNumberedAndTakenModuloTen(int[] in, int[] expected) {
        final int[] actual = numberDigits(in);
        assertThat(actual, is(expected));
    }

    @DataProvider
    public Object[][] illegalDigitsDataProvider() {
        return new Object[][]{
                {new int[]{10}},
                {new int[]{11}},
                {new int[]{55}},
                {new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}},
                {new int[]{0, 1, 2, 3, 14, 5, 6, 7, 8, 9}},
        };
    }

    @Test(dataProvider = "illegalDigitsDataProvider", expectedExceptions = IllegalArgumentException.class)
    public void validateDigitsArrayShouldDenyNumbersGeTen(int[] input) {
        validateDigitsArray(input);
    }

    @DataProvider
    public Object[][] indexSequenceOfOrdinalNumbersDataProvider() {
        return new Object[][]{
                { //      index:  0  1  2  3  4  5  6  7  8  9
                        new int[]{8, 2, 3, 8, 9, 6, 5, 3, 2, 7},
                        new int[]{1, 8, 2, 7, 6, 5, 9, 0, 3, 4}
                },
                { //      index:  0  1  2  3  4  5  6  7  8  9
                        new int[]{1, 8, 5, 7, 1, 6, 4, 0, 6, 2},
                        new int[]{0, 4, 9, 6, 2, 5, 8, 3, 1, 7}
                },
                { //      index:  0  1  2  3  4  5  6  7  8  9  10 11
                        new int[]{8, 4, 8, 9, 8, 2, 4, 5, 8, 9, 8, 2},
                        new int[]{5, 11, 1, 6, 7, 0, 2, 4, 8, 10, 3, 9}
                },
                { //      index:  0  1  2  3  4  5  6  7  8  9  10
                        new int[]{0, 9, 7, 9, 2, 8, 5, 5, 8, 7, 8},
                        new int[]{4, 6, 7, 2, 9, 5, 8, 10, 1, 3, 0}
                },
        };
    }

    @Test(dataProvider = "indexSequenceOfOrdinalNumbersDataProvider")
    public void shouldReturnIndexSequenceOfAscendingDigitsConsideringSameNumbers(int[] input, int[] expected) {
        final int[] actual = ordinal(input);
        assertThat(actual, is(expected));
    }

    @DataProvider
    public Object[][] lettersDataProvider() {
        return new Object[][]{
                {
                        "ABCDEFGHIJ",
                        new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0}
                },
                {
                        "JIHGFEDCBA",
                        new int[]{0, 9, 8, 7, 6, 5, 4, 3, 2, 1}
                },
                {
                        "AAAAAAAAAA",
                        new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0}
                },
                {
                        "AABBCDECDE",
                        new int[]{1, 2, 3, 4, 5, 7, 9, 6, 8, 0}
                },
                {
                        "TESTME",
                        new int[]{5, 1, 4, 6, 3, 2}
                },
                {
                        "HELLOWORLD",
                        new int[]{3, 2, 4, 5, 7, 0, 8, 9, 6, 1}
                },
        };
    }

    @Test(dataProvider = "lettersDataProvider",
            dependsOnMethods = {
                    "testModulo",
                    "zeroShouldBeConsideredTen",
                    "numbersShouldBeNumberedInAscendingOrderConsideringSameNumbers",
                    "digitsShouldBeNumberedAndTakenModuloTen"
            })
    public void lettersShouldBeNumberedInAlphabeticalOrder(String input, int[] expected) {
        final int[] actual = numberLetters(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void testModulo() {
        final int[] input = new int[]{10, 11, 23, 45, 99, 78, 56};
        final int[] expected = new int[]{0, 1, 3, 5, 9, 8, 6};

        final int[] actual = modulo(input);
        assertThat(actual, is(expected));
    }
}
