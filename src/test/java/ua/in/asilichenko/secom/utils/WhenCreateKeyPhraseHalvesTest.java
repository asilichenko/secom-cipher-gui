package ua.in.asilichenko.secom.utils;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.in.asilichenko.secom.domain.KeyHalves;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.in.asilichenko.secom.utils.KeyUtil.createKeyHalves;

public class WhenCreateKeyPhraseHalvesTest {

    @DataProvider
    public Object[][] keyPhraseDataProvider() {
        return new Object[][]{
                {
                        "MAKE NEW FRIENDS BUT KEEP THE OLD",
                        new int[]{7, 1, 6, 2, 8, 3, 0, 4, 9, 5}, new int[]{3, 7, 2, 8, 1, 0, 9, 6, 4, 5}
                },
                {
                        "SECURE COMMUNICATION KEY",
                        new int[]{9, 3, 1, 0, 8, 4, 2, 7, 5, 6}, new int[]{0, 6, 3, 2, 1, 9, 4, 8, 7, 5}
                },
        };
    }

    @Test(dataProvider = "keyPhraseDataProvider")
    public void keyHalvesShouldBeCreatedFromKeyPhrase(String keyPhrase, int[] expectedFirst, int[] expectedSecond) {
        final KeyHalves actual = createKeyHalves(keyPhrase.replace(" ", ""));
        assertThat(actual.first(), is(expectedFirst));
        assertThat(actual.second(), is(expectedSecond));
    }

    @DataProvider
    public Object[][] invalidKeyPhraseDataProvider() {
        return new Object[][]{
                {"INVALID KEY"},
                {"INVALID\nKEY"},
                {"INVALID:KEY"},
                {"INVALID-KEY"},
                {"1NVAL1DKEY"},
        };
    }

    @Test(dataProvider = "invalidKeyPhraseDataProvider", expectedExceptions = IllegalArgumentException.class)
    public void createKeyHalvesFromInvalidKeyPhraseShouldThrowException(String keyPhrase) {
        createKeyHalves(keyPhrase);
    }
}
