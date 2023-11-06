package year15;

import org.junit.jupiter.api.Test;
import util.InputResolver;

import java.io.IOException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {

    private static final char UP = '(';
    private static final char DOWN = ')';

    @Test
    void partOne() throws IOException {
        var input = InputResolver.toCharStream(15, 1);
        var map = input.collect(Collectors.groupingBy(c -> c));
        int result = map.get(UP).size() - map.get(DOWN).size();

        assertEquals(280, result);
    }

    @Test
    void partTwo() throws IOException {
        int floor = 0;
        int position = 0;
        var input = InputResolver.toString(15, 1);

        for (; position < input.length(); position++) {
            if (floor < 0) break;
            if (input.charAt(position) == UP)  ++floor;
            else --floor;
        }

        assertEquals(1797, position);
    }

}
