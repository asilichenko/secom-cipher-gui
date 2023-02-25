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
import static ua.in.asilichenko.secom.utils.TextUtil.*;

public class TextUtilTest {

    @DataProvider
    public Object[][] digitsDataProvider() {
        return new Object[][]{
                {"12345 67890"},
                {"12345~67890"},
                {"12345!67890"},
                {"12345@67890"},
                {"12345#67890"},
                {"12345$67890"},
                {"12345%67890"},
                {"12345^67890"},
                {"12345&67890"},
                {"12345*67890"},
                {"12345(67890"},
                {"12345)67890"},
                {"12345-67890"},
                {"12345_67890"},
                {"12345+67890"},
                {"12345=67890"},
                {"12345D67890"},
                {"12345d67890"},
                {"12345[67890"},
                {"12345]67890"},
                {"12345}67890"},
                {"12345{67890"},
                {"12345\n67890"},
                {"12345\t67890"},
                {"12345\b67890"},
                {"12345\\67890"},
        };
    }

    @Test(dataProvider = "digitsDataProvider")
    public void textShouldBeClearedOfNonNumericCharacters(String input) {
        final String actual = prepareCipherText(input);
        assertThat(actual, is("1234567890"));
    }

    @DataProvider
    public Object[][] cipherTextWarnDataProvider() {
        return new Object[][]{
                {"", false},
                {"1234567890", false},
                {"12345 67890", false},
                {"12345 67890\n", false},
                {"O", true},
                {"o", true},
                {"I23456789", true},
                {"Text with digits: 12345", true},
                {"12345\n", false},
                {"12345!", true},
                {"~12345", true},
                {"@12345", true},
                {"#12345", true},
                {"$12345", true},
                {"12345%", true},
                {"12345^", true},
                {"12345&", true},
                {"12345*", true},
                {"12345)", true},
                {"(12345", true},
                {"(12345)", true},
                {"-12345", true},
                {"+12345", true},
                {"=12345", true},
                {"123*45", true},
                {"123\\45", true},
                {"123/45", true},
                {"123 / 45", true},
        };
    }

    @Test(dataProvider = "cipherTextWarnDataProvider")
    public void isCipherTextWarningTest(String input, boolean expected) {
        final boolean actual = isCipherTextWarning(input);
        assertThat(actual, is(expected));
    }

    @DataProvider
    public Object[][] cipherTextErrorDataProvider() {
        return new Object[][]{
                {"", true},
                {"0", false},
                {"1", false},
                {"5", false},
                {"6", false},
                {"no DIGITS", true},
                {"0ne digit", false},
                {"1234567890", false},
                {"12345 67890", false},
                {"Valid cipher-text with comment 12345 67890", false},
                {"Valid cipher-text with comment and line break 12345\n67890", false},
                {"Valid cipher-text with comment and colon: 12345 67890", false},
                {"Valid cipher-text with comment and semicolon 12345 67890;", false},
                {"Invalid cipher-text contains no digits.", true},
        };
    }

    @Test(dataProvider = "cipherTextErrorDataProvider")
    public void cipherTextShouldContainAtLeastOneDigit(String input, boolean expected) {
        final boolean actual = isCipherTextError(input);
        assertThat(actual, is(expected));
    }

    @DataProvider
    public Object[][] plainTextDataProvider() {
        return new Object[][]{
                {"REPLACE LINE BREAK\nWITH SPACE", "REPLACE LINE BREAK WITH SPACE"},
                {"MaKe TeXt UppEr CAse", "MAKE TEXT UPPER CASE"},
                //
                {"REPLACE SPECIAL~ CHARACTERS", "REPLACE SPECIAL CHARACTERS"},
                {"REPLACE SPECIAL! CHARACTERS", "REPLACE SPECIAL CHARACTERS"},
                {"REPLACE SPECIAL@ CHARACTERS", "REPLACE SPECIAL CHARACTERS"},
                {"REPLACE SPECIAL# CHARACTERS", "REPLACE SPECIAL CHARACTERS"},
                {"REPLACE SPECIAL$ CHARACTERS", "REPLACE SPECIAL CHARACTERS"},
                {"REPLACE SPECIAL% CHARACTERS", "REPLACE SPECIAL CHARACTERS"},
                {"REPLACE SPECIAL^ CHARACTERS", "REPLACE SPECIAL CHARACTERS"},
                {"REPLACE SPECIAL& CHARACTERS", "REPLACE SPECIAL CHARACTERS"},
                {"REPLACE SPECIAL* CHARACTERS", "REPLACE SPECIAL CHARACTERS"},
                {"REPLACE SPECIAL( CHARACTERS", "REPLACE SPECIAL CHARACTERS"},
                {"REPLACE SPECIAL) CHARACTERS", "REPLACE SPECIAL CHARACTERS"},
                {"REPLACE SPECIAL- CHARACTERS", "REPLACE SPECIAL CHARACTERS"},
                {"REPLACE SPECIAL_ CHARACTERS", "REPLACE SPECIAL CHARACTERS"},
                {"REPLACE SPECIAL= CHARACTERS", "REPLACE SPECIAL CHARACTERS"},
                {"REPLACE SPECIAL+ CHARACTERS", "REPLACE SPECIAL CHARACTERS"},
                {"REPLACE SPECIAL/ CHARACTERS", "REPLACE SPECIAL CHARACTERS"},
                {"REPLACE SPECIAL\\ CHARACTERS", "REPLACE SPECIAL CHARACTERS"},
                {"REPLACE SPECIAL: CHARACTERS", "REPLACE SPECIAL CHARACTERS"},
                {"REPLACE SPECIAL\" CHARACTERS", "REPLACE SPECIAL CHARACTERS"},
        };
    }

    @Test(dataProvider = "plainTextDataProvider")
    public void plainTextShouldBePreparedToEnciphering(String input, String expected) {
        final String actual = preparePlainText(input);
        assertThat(actual, is(expected));
    }

    @DataProvider
    public Object[][] groupsDataProvider() {
        return new Object[][]{
                {"", ""},
                {"1", "1"},
                {"12", "12"},
                {"123", "123"},
                {"1234", "1234"},
                {"12345", "12345"},
                {"123456", "12345 6"},
                {"1234567", "12345 67"},
                {"1234567890", "12345 67890"},
                {"1234567890156464365143615464641", "12345 67890 15646 43651 43615 46464 1"},
                {
                        "123451234512345123451234512345123451234512345123451234512345",
                        """
                                12345 12345 12345 12345 12345 12345 12345 12345 12345 12345
                                12345 12345"""
                },
        };
    }

    @Test(dataProvider = "groupsDataProvider")
    public void textShouldBeBrokeIntoGroupsOfCharacters(String input, String expected) {
        final String actual = makeGroups(input);
        assertThat(actual, is(expected));
    }

    @DataProvider
    public Object[][] stringDigitsDataProvider() {
        return new Object[][]{
                {"0", new int[]{0}},
                {"1", new int[]{1}},
                {"2", new int[]{2}},
                {"9", new int[]{9}},
                {"10", new int[]{1, 0}},
                {"15", new int[]{1, 5}},
                {"45", new int[]{4, 5}},
                {"123", new int[]{1, 2, 3}},
                {"12345", new int[]{1, 2, 3, 4, 5}},
                {
                        "777193862200032042396003829683146080607178016736060606463536069686740369681890014021906662606660863160549",
                        new int[]{7, 7, 7, 1, 9, 3, 8, 6, 2, 2, 0, 0, 0, 3, 2, 0, 4, 2, 3, 9, 6, 0, 0, 3, 8, 2, 9, 6, 8,
                                3, 1, 4, 6, 0, 8, 0, 6, 0, 7, 1, 7, 8, 0, 1, 6, 7, 3, 6, 0, 6, 0, 6, 0, 6, 4, 6, 3, 5,
                                3, 6, 0, 6, 9, 6, 8, 6, 7, 4, 0, 3, 6, 9, 6, 8, 1, 8, 9, 0, 0, 1, 4, 0, 2, 1, 9, 0, 6,
                                6, 6, 2, 6, 0, 6, 6, 6, 0, 8, 6, 3, 1, 6, 0, 5, 4, 9}
                },
        };
    }

    @Test(dataProvider = "stringDigitsDataProvider")
    public void stringOfDigitsShouldBeConvertedIntoArrayOfThatDigits(String input, int[] expected) {
        final int[] actual = readDigits(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void keyPhraseSpacesShouldBeRemoved() {
        final String input = "LOREM IPSUM DOLOR SIT AMET CONSECTETUR ADIPISCING ELIT";
        final String expected = "LOREMIPSUMDOLORSITAMETCONSECTETURADIPISCINGELIT";

        final String actual = prepareKey(input);
        assertThat(actual, is(expected));
    }
}