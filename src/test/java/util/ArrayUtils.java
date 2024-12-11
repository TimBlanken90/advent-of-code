package util;

import java.util.List;

public class ArrayUtils {

    public static char[][] to2dCharArray(List<String> input) {
        int size = input.size();
        char[][] chars = new char[size][size];
        for (int i = 0; i < size; i++) {
            chars[i] = input.get(i).toCharArray();
        }
        return chars;
    }

}
