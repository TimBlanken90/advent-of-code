package year15;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import common.Day;
import util.InputResolver;

public class Day12Test implements Day {

    @Override
    @Test
    public void partOne() throws IOException {
        var input = InputResolver.toString(15, 12);

        var sum = Arrays.stream(input.split("[^\\d-]"))
            .filter(str -> !str.equals(""))
            .map(Integer::parseInt)
            .reduce(0, Integer::sum);

        assertEquals(156366, sum);
    }

    @Override
    @Test
    public void partTwo() throws IOException {

    }
}
