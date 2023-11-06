package year15;

import common.Day;
import org.junit.jupiter.api.Test;
import util.InputResolver;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Day8Test implements Day {

    @Override
    @Test
    public void partOne() throws IOException {
        var input = InputResolver.toList(15, 8);

        int result = input.stream().mapToInt(str -> str.length() - getnumberOfChars(str)).sum();
        assertEquals(1371, result);
    }

    @Override
    @Test
    public void partTwo() throws IOException {
        var input = InputResolver.toList(15, 8);

        int result = input.stream().mapToInt(str -> getEscapedLength(str) - str.length()).sum();
        assertEquals(2117, result);
    }

    private int getnumberOfChars(String str) {
        int characterCount = 0;
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            char currentCharacter = str.charAt(i);
            if (currentCharacter == '\\' && i < strLen - 1) {
                char nextCharacter = str.charAt(i + 1);
                if (nextCharacter == '\\' || nextCharacter == '\"') {
                    i++;
                } else if (nextCharacter == 'x' && i < strLen - 3) {
                    i += 3;
                }
            }
            characterCount++;
        }
        return characterCount - 2;
    }

    public int getEscapedLength(String str) {
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .length() + 2;
    }
}
