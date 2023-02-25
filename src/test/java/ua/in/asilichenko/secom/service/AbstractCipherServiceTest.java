package ua.in.asilichenko.secom.service;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.in.asilichenko.secom.domain.*;
import ua.in.asilichenko.secom.service.dataprovider.data.Data1;
import ua.in.asilichenko.secom.service.dataprovider.data.Data2;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AbstractCipherServiceTest {

    @Test
    public void testSum10() {
        final AbstractCipherService sut = mock(AbstractCipherService.class);
        when(sut.sum(anyInt(), anyInt())).thenCallRealMethod();
        when(sut.sum(any(int[].class), any(int[].class))).thenCallRealMethod();

        final int[] actual = sut.sum(
                new int[]{3, 7, 2, 8, 1, 0, 9, 6, 4, 5},
                new int[]{8, 1, 3, 9, 0, 6, 5, 4, 2, 7}
        );
        assertThat(actual, is(
                new int[]{1, 8, 5, 7, 1, 6, 4, 0, 6, 2}
        ));
    }

    @DataProvider
    public Object[][] sumDataProvider() {
        return new Object[][]{
                {1, 1, 2},
                {1, 8, 9},
                {8, 1, 9},
                {1, 9, 0},
                {9, 1, 0},
                {6, 6, 2},
                {8, 3, 1},
                {9, 9, 8},
                {5, 9, 4},
                {7, 5, 2},
                {7, 3, 0},
                {1, 7, 8},
                {6, 2, 8},
                {2, 8, 0},
                {8, 1, 9},
                {3, 0, 3},
                {0, 9, 9},
                {4, 6, 0},
                {9, 4, 3},
                {5, 5, 0},
        };
    }

    @Test(dataProvider = "sumDataProvider")
    public void testSum(int a, int b, int expected) {
        final AbstractCipherService sut = mock(AbstractCipherService.class);
        when(sut.sum(a, b)).thenCallRealMethod();

        final int actual = sut.sum(a, b);
        assertThat(actual, is(expected));
    }

    @DataProvider
    public Object[][] baseSequenceDataProvider() {
        return new Object[][]{
                { // MAKE NEW FRIENDS BUT KEEP THE OLD
                        new int[]{0, 8, 8, 0, 9, 3, 9, 0, 3, 0},
                        new int[]{
                                8, 6, 8, 9, 2, 2, 9, 3, 3, 8,
                                4, 4, 7, 1, 4, 1, 2, 6, 1, 2,
                                8, 1, 8, 5, 5, 3, 8, 7, 3, 0,
                                9, 9, 3, 0, 8, 1, 5, 0, 3, 9,
                                8, 2, 3, 8, 9, 6, 5, 3, 2, 7
                        }
                },
                { // SECURE COMMUNICATION KEY
                        new int[]{9, 9, 4, 2, 9, 3, 6, 5, 2, 1},
                        new int[]{
                                8, 3, 6, 1, 2, 9, 1, 7, 3, 9,
                                1, 9, 7, 3, 1, 0, 8, 0, 2, 0,
                                0, 6, 0, 4, 1, 8, 8, 2, 2, 0,
                                6, 6, 4, 5, 9, 6, 0, 4, 2, 6,
                                2, 0, 9, 4, 5, 6, 4, 6, 8, 8
                        }
                },
        };
    }

    @Test(dataProvider = "baseSequenceDataProvider")
    public void testGenerateBaseSequence(int[] seed, int[] expected) {
        final AbstractCipherService sut = mock(AbstractCipherService.class);
        when(sut.sum(anyInt(), anyInt())).thenCallRealMethod();
        when(sut.sum(any(int[].class), any(int[].class))).thenCallRealMethod();
        when(sut.generateBaseSequence(any(int[].class))).thenCallRealMethod();

        final int[] actual = sut.generateBaseSequence(seed);
        assertThat(actual, is(expected));
    }

    @DataProvider
    public Object[][] checkerBoardMappingDataProvider() {
        return new Object[][]{
                {Data1.checkerBoardKey, Data1.checkerBoard().inverse()}, // MAKE NEW FRIENDS BUT KEEP THE OLD
                {Data2.checkerBoardKey, Data2.checkerBoard().inverse()}, // SECURE COMMUNICATION KEY
        };
    }

    @Test(dataProvider = "checkerBoardMappingDataProvider")
    public void testCheckerBoardMapping(int[] key, Map<Character, CheckerBoardCode> expected) {
        final AbstractCipherService sut = mock(AbstractCipherService.class);
        when(sut.makeCheckerBoard(key)).thenCallRealMethod();

        final Map<Character, CheckerBoardCode> actual = sut.makeCheckerBoard(key);
        assertThat(actual, is(expected));
    }

    @DataProvider
    public Object[][] columnarWidthsDataProvider() {
        return new Object[][]{
                {new int[]{8, 2, 3, 8, 9, 6, 5, 3, 2, 7}, new ColumnarKeyWidths(12, 11)},
                {new int[]{2, 0, 9, 4, 5, 6, 4, 6, 8, 8}, new ColumnarKeyWidths(14, 10)},
                {new int[]{1, 4, 7, 9, 2, 9, 9}, new ColumnarKeyWidths(11, 16)},
        };
    }

    @Test(dataProvider = "columnarWidthsDataProvider")
    public void testObtainColumnarWidths(int[] sequence, ColumnarKeyWidths expected) {
        final AbstractCipherService sut = mock(AbstractCipherService.class);
        when(sut.obtainColumnarWidths(sequence)).thenCallRealMethod();

        final ColumnarKeyWidths actual = sut.obtainColumnarWidths(sequence);
        assertThat(actual, is(expected));
    }

    @DataProvider
    public Object[][] makeKeysDataProvider() {
        return new Object[][]{
                {Data1.KEY_PHRASE_HALVES, Data1.columnarKey1, Data1.columnarKey2}, // MAKE NEW FRIENDS BUT KEEP THE OLD
                {Data2.KEY_PHRASE_HALVES, Data2.columnarKey1, Data2.columnarKey2}, // SECURE COMMUNICATION KEY
        };
    }

    @Test(dataProvider = "makeKeysDataProvider",
            dependsOnMethods = {"testCheckerBoardMapping", "testObtainColumnarWidths"})
    public void testMakeKeys(KeyHalves keyHalves, int[] expectedColumnar1, int[] expectedColumnar2) {
        final AbstractCipherService sut = mock(AbstractCipherService.class);
        when(sut.sum(anyInt(), anyInt())).thenCallRealMethod();
        when(sut.sum(any(int[].class), any(int[].class))).thenCallRealMethod();
        when(sut.generateBaseSequence(any(int[].class))).thenCallRealMethod();
        when(sut.obtainColumnarWidths(any(int[].class))).thenCallRealMethod();
        when(sut.makeKeys(keyHalves)).thenCallRealMethod();

        final Keys actual = sut.makeKeys(keyHalves);
        assertThat(actual.columnarKey1(), is(expectedColumnar1));
        assertThat(actual.columnarKey2(), is(expectedColumnar2));
    }

    @Test(expectedExceptions = IllegalStateException.class,
            dependsOnMethods = {"testObtainColumnarWidths", "testGenerateBaseSequence"})
    public void testIllegalState() {
        final AbstractCipherService sut = mock(AbstractCipherService.class);
        when(sut.generateBaseSequence(any(int[].class))).thenCallRealMethod();
        when(sut.obtainColumnarWidths(any(int[].class))).thenCallRealMethod();

        final int[] sequence = sut.generateBaseSequence(new int[]{5, 0, 5, 5, 0, 5, 0, 5, 5, 0});
        final ColumnarKeyWidths ignored = sut.obtainColumnarWidths(sequence);
    }

    @DataProvider
    public Object[][] disruptMaskDataProvider() {
        return new Object[][]{
                {Data1.columnarKey2, Data1.enciphered.length(), Data1.disruptMask}, // MAKE NEW FRIENDS BUT KEEP THE OLD
                {Data2.columnarKey2, Data2.enciphered.length(), Data2.disruptMask}, // SECURE COMMUNICATION KEY
        };
    }

    @Test(dataProvider = "disruptMaskDataProvider")
    public void testMakeDisruptMask(int[] key, int length, DisruptMask expected) {
        final AbstractCipherService sut = mock(AbstractCipherService.class);
        when(sut.makeDisruptMask(eq(key), eq(length))).thenCallRealMethod();

        final DisruptMask actual = sut.makeDisruptMask(key, length);

        assertThat(actual.mask(), is(expected.mask()));
        assertThat(actual.secondPartStartIndex(), is(expected.secondPartStartIndex()));
    }
}
