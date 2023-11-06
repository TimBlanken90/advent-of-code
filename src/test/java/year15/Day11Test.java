package year15;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Set;

import org.junit.jupiter.api.Test;

import common.Day;
import util.InputResolver;

public class Day11Test implements Day {

    private static final Set<Character> FORBIDDEN_CHARS = Set.of('i', 'o', 'l');

    @Override
    @Test
    public void partOne() throws IOException {
        var password = InputResolver.toString(15, 11);
        boolean match = false;

        do {
            if (isValidPassword(password)) {
                match = true;
            } else {
                password = increment(password.toCharArray());
            }
        } while (!match);

        assertEquals("hxbxxyzz", password);
    }

    @Override
    @Test
    public void partTwo() throws IOException {
        var password = increment("hxbxxyzz".toCharArray());
        boolean match = false;

        do {
            if (isValidPassword(password)) {
                match = true;
            } else {
                password = increment(password.toCharArray());
            }
        } while (!match);

        assertEquals("hxcaabcc", password);
    }

    private String increment(char[] chars) {
        int i = chars.length - 1;
        while (i >= 0) {
            char currentChar = chars[i];
            if (currentChar == 'z') {
                chars[i] = 'a';
                i--;
            } else {
                chars[i] = (char) (currentChar + 1);
                break;
            }
        }

        return new String(chars);
    }

    private boolean isValidPassword(String password) {
        return !hasIllegalChar(password.toCharArray()) &&
            hasStraight(password.toCharArray()) &&
            hasTwoNonOverlappingPairs(password.toCharArray());
    }

    private boolean hasIllegalChar(char[] chars) {
        for (char c : chars) {
            if (FORBIDDEN_CHARS.contains(c)) return true;
        }
        return false;
    }

    private boolean hasStraight(char[] chars) {
        for (int i = 0; i < chars.length -2; i++) {
            char left = chars[i];
            char middle = chars[i + 1];
            char right = chars[i + 2];
            if (++left == middle && ++middle == right) return true;
        }
        return false;
    }

    private boolean hasTwoNonOverlappingPairs(char[] chars) {
        int pairs = 0;
        for (int i = 0; i < chars.length - 1;) {
            if (chars[i] == chars[i + 1]) {
                pairs++;
                i += 2;
            } else {
                i++;
            } if (pairs == 2) {
                return true;
            }
        }
        return false;
    }
}
