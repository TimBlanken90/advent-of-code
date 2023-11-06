package year15;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import common.Day;
import util.InputResolver;

public class Day10Test implements Day {

    @Override
    @Test
    public void partOne() throws IOException {
        var input = InputResolver.toIntegerList(15, 10);
        var result = solve(input, 40);

        assertEquals(329356, result.length());
    }

    @Override
    @Test
    public void partTwo() throws IOException {
        var input = InputResolver.toIntegerList(15, 10);
        var result = solve(input, 50);

        assertEquals(4666278, result.length());
    }

    private String solve(List<Integer> sequence, int n) {
        if (n <= 0) {
            return sequence.stream().map(String::valueOf).collect(Collectors.joining(""));
        }

        var lookAndSay = generateLookAndSay(sequence);
        var input = lookAndSay.chars().map(c -> c-'0').boxed().toList();
        return solve(input, --n);
    }

    private String generateLookAndSay(List<Integer> sequence) {
        int occurrences = 1;
        StringBuilder result = new StringBuilder();
        int size = sequence.size();

        for (int i = 0; i < size; i++) {
            int currentNumber = sequence.get(i);
            if (i == size - 1 || !sequence.get(i).equals(sequence.get(i + 1))) {
                result.append(occurrences);
                result.append(currentNumber);
                occurrences = 1;
            } else {
                occurrences++;
            }
        }

        return result.toString();
    }

}
