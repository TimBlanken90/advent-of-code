package year23;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import common.Day;
import util.InputResolver;

public class Day4Test implements Day {

    @Override
    @Test
    public void partOne() throws IOException {
        var score = InputResolver.toList(23, 4).stream()
            .map(this::parseLine)
            .map(this::countMatches)
            .map(this::countScore)
            .reduce(0L, Long::sum);

        assertEquals(26346, score);
    }

    @Override
    @Test
    public void partTwo() throws IOException {
        var idx = new AtomicInteger(1);
        var map = new HashMap<Integer, AtomicInteger>();

        InputResolver.toList(23, 4).stream()
            .map(this::parseLine)
            .forEach(numbers -> {
                int matches = countMatches(numbers);
                int currentIdx = idx.get();
                if (currentIdx == 1) {
                    countCopies(currentIdx, matches, map);
                } else {
                    map.computeIfAbsent(currentIdx, k -> new AtomicInteger(0)).incrementAndGet();
                    // start with number of cards from previous iterations (copies included)
                    int numberOfCards = map.get(currentIdx).get();
                    for (int i = 0; i < numberOfCards; i++) {
                        countCopies(currentIdx + 1, matches - 1, map);
                    }
                }
                idx.incrementAndGet();
            });

        int numberOfCards = map.values().stream().mapToInt(AtomicInteger::get).sum();
        assertEquals(8467762, numberOfCards);
    }

    private List<List<Integer>> parseLine(String line) {
        // remove "Card x:" prefix and split by '|'
        return Stream.of(line.split(":")[1].split("\\|"))
            .map(String::trim)
            .map(part -> Arrays.stream(part.split("\\s+")).map(Integer::parseInt).toList())
            .toList();
    }

    // Count which of the numbers you have appears in the list of winning numbers.
    private int countMatches(List<List<Integer>> numbers) {
        var winningNumbers = numbers.get(0);
        var myNumbers = numbers.get(1);
        return (int) winningNumbers.stream().filter(myNumbers::contains).count();
    }

    // The first match makes the card worth one point and each match after the first doubles the point value of that card.
    private long countScore(long matches) {
        return matches > 0 ? (long) Math.pow(2, matches - 1) : 0;
    }

    private void countCopies(int idx, int matches, HashMap<Integer, AtomicInteger> map) {
        for (int i = idx; i <= matches + idx; i++) {
            map.computeIfAbsent(i, k -> new AtomicInteger(0)).incrementAndGet();
        }
    }

}
