package year15;

import org.junit.jupiter.api.Test;
import util.InputResolver;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {

    @Test
    void partOne() throws IOException {
        var input = InputResolver.toList(15, 2);
        long result = input.stream()
                .map(Dimensions::new)
                .map(Dimensions::getArea)
                .reduce(0, Integer::sum);

        assertEquals(1598415, result);
    }

    @Test
    void partTwo() throws IOException {
        var input = InputResolver.toList(15, 2);
        long result = input.stream()
                .map(Dimensions::new)
                .map(Dimensions::getRibbonLength)
                .reduce(0, Integer::sum);

        assertEquals(3812909, result);
    }


    private static class Dimensions {
        private final int l, w, h;
        public Dimensions(String input) {
            var dimensions = input.split("x");
            this.l = Integer.parseInt(dimensions[0]);
            this.w = Integer.parseInt(dimensions[1]);
            this.h = Integer.parseInt(dimensions[2]);
        }

        public int getSmallest() {
            var areas = new int[]{l * w, w * h, h * l};
            Arrays.sort(areas);
            return areas[0];
        }

        public int getArea() {
            return ((2 * l * w) + (2 * w * h) + (2 * h * l) + getSmallest());
        }

        public int getRibbonLength() {
            var lengths = new int[]{l, w, h};
            Arrays.sort(lengths);
            return (lengths[0] + lengths[0]) + (lengths[1] + lengths[1]) + (l * w  * h);
        }
    }

}
