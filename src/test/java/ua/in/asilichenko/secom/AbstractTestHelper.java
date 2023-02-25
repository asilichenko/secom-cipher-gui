package ua.in.asilichenko.secom;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTestHelper {

    protected List<Integer> stringToIntegerList(String string) {
        final List<Integer> retval = new ArrayList<>();
        for (char digit : string.toCharArray()) retval.add(digit - '0');
        return retval;
    }

    protected int[] stringToIntArray(String string) {
        final int[] retval = new int[string.length()];
        for (int i = 0; i < retval.length; i++) retval[i] = string.charAt(i) - '0';
        return retval;
    }
}
