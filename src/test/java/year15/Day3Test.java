package year15;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import util.InputResolver;

class Day3Test {

    @Test
    void partOne() throws IOException {
        var x = new AtomicInteger(1);
        var y = new AtomicInteger(1);

        var input = InputResolver.toCharStream(15, 3);
        var result = input.map(direction -> getCoordinates(direction, x, y)).collect(Collectors.toSet()).size();

        assertEquals(2565, result);
    }

    @Test
    void partTwo() throws IOException {
        var xSanta = new AtomicInteger(1);
        var ySanta = new AtomicInteger(1);
        var xRobot = new AtomicInteger(1);
        var yRobot = new AtomicInteger(1);

        var input = InputResolver.toString(15, 3);
        var result = IntStream.range(0, input.length())
                .mapToObj(i -> {
                    if (i % 2 == 0) {
                        return getCoordinates(input.charAt(i), xSanta, ySanta);
                    }
                    return getCoordinates(input.charAt(i), xRobot, yRobot);
                }).collect(Collectors.toSet()).size();

        assertEquals(2639, result);
    }

    private Coordinates getCoordinates(char direction, AtomicInteger x, AtomicInteger y) {
        return switch (direction) {
            case '^' -> new Coordinates(x.get(), y.incrementAndGet());
            case '>' -> new Coordinates(x.incrementAndGet(), y.get());
            case 'v' -> new Coordinates(x.get(), y.decrementAndGet());
            case '<' -> new Coordinates(x.decrementAndGet(), y.get());
            default -> null;
        };
    }

    private record Coordinates(int x, int y) {}
}
