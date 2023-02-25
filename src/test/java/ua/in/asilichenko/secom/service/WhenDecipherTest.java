package ua.in.asilichenko.secom.service;

import org.testng.annotations.Test;
import ua.in.asilichenko.secom.AbstractTestHelper;
import ua.in.asilichenko.secom.domain.CheckerBoardCode;
import ua.in.asilichenko.secom.domain.DisruptMask;
import ua.in.asilichenko.secom.domain.KeyHalves;
import ua.in.asilichenko.secom.function.DisruptConsumer;
import ua.in.asilichenko.secom.service.dataprovider.DecipherDataProvider;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static ua.in.asilichenko.secom.utils.KeyUtil.createKeyHalves;

public class WhenDecipherTest extends AbstractTestHelper {

    @Test(dataProvider = "reverseColumnarDataProvider", dataProviderClass = DecipherDataProvider.class)
    public void testReverseColumnar(String inputString, int[] key, int[][] expected) {
        final int[] input = stringToIntArray(inputString);

        final DecipherService sut = mock(DecipherService.class);
        when(sut.reverseColumnar(any(int[].class), any(int[].class))).thenCallRealMethod();

        final int[][] actual = sut.reverseColumnar(input, key);
        assertThat(actual, is(expected));
    }

    @Test(dataProvider = "reverseDisruptDataProvider", dataProviderClass = DecipherDataProvider.class)
    public void testReverseDisrupt(int[][] input, DisruptMask mask, String expectedString) {
        final int[] expected = stringToIntArray(expectedString.replace(" ", ""));

        final int size = expected.length;
        final DecipherService sut = mock(DecipherService.class);
        when(sut.reverseDisrupt(input, size, mask)).thenCallRealMethod();
        doCallRealMethod().when(sut).disrupt(eq(mask), eq(size), any(DisruptConsumer.class));

        final int[] actual = sut.reverseDisrupt(input, size, mask);
        assertThat(actual, is(expected));
    }

    @Test(dataProvider = "checkerBoardDataProvider", dataProviderClass = DecipherDataProvider.class)
    public void testCheckerBoardDecode(int[][] input, Map<CheckerBoardCode, Character> checkerBoard, char[] expected) {
        final DecipherService sut = mock(DecipherService.class);
        when(sut.checkerBoardDecode(input, checkerBoard)).thenCallRealMethod();

        final char[] actual = sut.checkerBoardDecode(input, checkerBoard);
        assertThat(actual, is(expected));
    }

    @Test(dataProvider = "decodeDataProvider", dataProviderClass = DecipherDataProvider.class,
            dependsOnMethods = {"testReverseDisrupt", "testReverseColumnar", "testCheckerBoardDecode"})
    public void testDecode(String secret, String keyPhrase, String expected) {
        final DecipherService sut = new DecipherService();

        final int[] digits = stringToIntArray(secret);
        final KeyHalves keyHalves = createKeyHalves(keyPhrase.replace(" ", ""));

        final char[] actual = sut.decipher(digits, keyHalves);
        assertThat(actual, is(expected.toCharArray()));
    }
}
