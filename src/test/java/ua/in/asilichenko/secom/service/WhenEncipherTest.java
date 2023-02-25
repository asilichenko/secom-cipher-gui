package ua.in.asilichenko.secom.service;

import org.testng.annotations.Test;
import ua.in.asilichenko.secom.AbstractTestHelper;
import ua.in.asilichenko.secom.domain.CheckerBoardCode;
import ua.in.asilichenko.secom.domain.DisruptMask;
import ua.in.asilichenko.secom.domain.KeyHalves;
import ua.in.asilichenko.secom.function.DisruptConsumer;
import ua.in.asilichenko.secom.service.dataprovider.EncipherDataProvider;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static ua.in.asilichenko.secom.utils.KeyUtil.createKeyHalves;

public class WhenEncipherTest extends AbstractTestHelper {

    @Test(dataProvider = "checkerBoardEncodeDataProvider", dataProviderClass = EncipherDataProvider.class)
    public void testCheckerBoardEncode(String plain, Map<Character, CheckerBoardCode> checkerBoard, String expectedString) {
        final List<Integer> expected = stringToIntegerList(expectedString);

        final EncipherService sut = mock(EncipherService.class);
        when(sut.checkerBoardEncipher(any(char[].class), eq(checkerBoard))).thenCallRealMethod();

        final List<Integer> actual = sut.checkerBoardEncipher(plain.toCharArray(), checkerBoard);
        assertThat(actual, is(expected));
    }

    @Test(dataProvider = "transposition1DataProvider", dataProviderClass = EncipherDataProvider.class)
    public void testTransposition1(int[] key, String inputString, String expectedString) {
        final List<Integer> input = stringToIntegerList(inputString);
        final int[] expected = stringToIntArray(expectedString);

        final EncipherService sut = mock(EncipherService.class);
        when(sut.transposition1(any(int[].class), anyList())).thenCallRealMethod();

        final int[] actual = sut.transposition1(key, input);
        assertThat(actual, is(expected));
    }

    /**
     * {@link AbstractCipherServiceTest#testMakeDisruptMask}
     */
    @Test(dataProvider = "disruptDataProvider", dataProviderClass = EncipherDataProvider.class)
    public void testDisrupt(String inputString, DisruptMask disruptMask, int[][] expected) {
        final int[] input = stringToIntArray(inputString);

        final EncipherService sut = mock(EncipherService.class);
        when(sut.disrupt(eq(input), eq(disruptMask))).thenCallRealMethod();
        doCallRealMethod().when(sut).disrupt(eq(disruptMask), eq(input.length), any(DisruptConsumer.class));

        final int[][] actual = sut.disrupt(input, disruptMask);
        assertThat(actual, is(expected));
    }

    /**
     * {@link AbstractCipherServiceTest#testMakeKeys}
     */
    @Test(dataProvider = "transposition2DataProvider", dataProviderClass = EncipherDataProvider.class)
    public void testTransposition2(int[] key, int[][] input, String expected) {
        final EncipherService sut = mock(EncipherService.class);
        when(sut.transposition2(eq(key), eq(input), eq(expected.length()))).thenCallRealMethod();

        final String actual = sut.transposition2(key, input, expected.length());
        assertThat(actual, is(expected));
    }

    @Test(dataProvider = "encodeDataProvider", dataProviderClass = EncipherDataProvider.class,
            dependsOnMethods = {"testCheckerBoardEncode", "testTransposition1", "testDisrupt", "testTransposition2"})
    public void testEncode(String plain, String key, String expected) {
        final KeyHalves keyHalves = createKeyHalves(key.replace(" ", ""));

        final EncipherService sut = spy(new EncipherService());

        final String actual = sut.encipher(plain.toCharArray(), keyHalves);
        assertThat(actual, is(expected));
    }
}