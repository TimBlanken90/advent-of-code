package year23;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import common.Day;
import util.InputResolver;

public class Day1Test implements Day {

    @Override
    @Test
    public void partOne() throws IOException {
        var input = InputResolver.toList(23, 1);
        int sum = 0;

        for (var str : input) {
            var chars = str.toCharArray();
            var numbers = new int[chars.length];
            int idx = 0;
            for (char c : chars) {
                if (Character.isDigit(c)) {
                    numbers[idx++] = Character.getNumericValue(c);
                }
            }

            int first = numbers[0];
            int last = numbers[idx - 1];
            var intVal = Integer.parseInt("" + first + last);
            sum += intVal;
        }

        assertEquals(55607, sum);
    }

    @Override
    @Test
    public void partTwo() throws IOException {
        var words = List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");

        var pattern = Pattern.compile("one|two|three|four|five|six|seven|eight|nine|\\d");
        var input = InputResolver.toList(23, 1);
        int sum = 0;

        for (var str : input) {
            var matcher = pattern.matcher(str);
            var numbers = new int[str.length()];
            int idx = 0;
            while (matcher.find()) {
                var match = matcher.group();
                var intValue = match.length() == 1 && Character.isDigit(match.charAt(0)) ? Character.getNumericValue(match.charAt(0)) : words.indexOf(match) + 1;
                numbers[idx++] = intValue;
            }

            int first = numbers[0];
            int last = numbers[idx > 0 ? idx - 1 : idx];
            var intVal = Integer.parseInt("" + first + last);
            sum += intVal;
        }

        assertEquals(55309, sum);
    }

}
