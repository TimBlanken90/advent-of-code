package year15;

import common.Day;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import util.InputResolver;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test implements Day {

    @Test
    @Override
    public void partOne() throws IOException {
        var input = InputResolver.toString(15, 4);
        int result = findLeadingZeroOccurences(input, 5);

        assertEquals(282749, result);
    }

    @Test
    @Override
    public void partTwo() throws IOException {
        var input = InputResolver.toString(15, 4);
        int result = findLeadingZeroOccurences(input, 6);

        assertEquals(9962624, result);
    }

    @SneakyThrows
    private String hash(String input) {
        var md = MessageDigest.getInstance("MD5");
        var digest = md.digest(input.getBytes());
        var hexString = new StringBuilder();
        for (byte b : digest) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1)
                hexString.append('0');

            hexString.append(hex);
        }
        return hexString.toString();
    }

    private int findLeadingZeroOccurences(String input, int numberOfLeadingZeros) {
        var zerosString = IntStream.range(0, numberOfLeadingZeros).mapToObj(i -> "0").collect(Collectors.joining(""));
        int i = 0;
        for (; i < Integer.MAX_VALUE; i++) {
            var hash = hash(input + i);
            if (hash.startsWith(zerosString)) {
                break;
            }
        }
        return i;
    }
}
