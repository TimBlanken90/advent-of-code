package year23;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Gatherers;

import org.junit.jupiter.api.Test;

import common.Day;
import util.InputResolver;

class Year5Test implements Day {
    private static final Pattern DIGIT = Pattern.compile("-?\\d+(\\.\\d+)?");

    @Override
    @Test
    public void partOne() throws IOException {
        var input = InputResolver.toString(23, 5);
        var config = createFertilizerConfig(input);

        long solution = Collections.min(calculateLocations(config));
        assertEquals(177942185L, solution);
    }

    @Override
    @Test
    public void partTwo() throws IOException {

    }

    private FertilizerConfig createFertilizerConfig(String input) {
        var parsed = Arrays.stream(input.split("\\n")).filter(s -> !s.isEmpty()).toArray(String[]::new);

        // seeds are always at first index
        var seeds = Arrays.stream(parsed[0].split(" ")).filter(s -> DIGIT.matcher(s).matches()).map(Long::valueOf).toList();

        var map = new LinkedHashMap<String, List<List<Long>>>();
        String currentKey = null;

        // iterate over the rest
        for (int i = 1; i < parsed.length; i++) {
            // it is a header, which will be the key of the map
            if (Character.isLetter(parsed[i].charAt(0))) {
                var parts = parsed[i].split(" ")[0].split("-");
                currentKey = parts[parts.length - 1];
                map.put(currentKey, new ArrayList<>());
            }
            // integers, which will represent the values of the map
            else {
                var numbersPartitionedByThree = Arrays.stream(parsed[i].split(" "))
                    .filter(s -> DIGIT.matcher(s).matches())
                    .map(Long::valueOf)
                    .gather(Gatherers.windowFixed(3))
                    .toList();

                map.get(currentKey).addAll(numbersPartitionedByThree);
            }
        }

        return new FertilizerConfig(seeds, map);
    }

    private List<Long> calculateLocations(FertilizerConfig config) {
        var locations = new ArrayList<Long>(config.seeds.size());

        // process each seed to calculate its final location
        for (long seed: config.seeds()) {
            long target = seed;
            for (var entry: config.maps.entrySet()) {
                // after each iteration pass the (tmp) target as input
                target = findLocation(target, entry.getValue());
            }
            locations.add(target);
        }

       return locations;
    }

    // recursively find the final location based on the given maps
    private long findLocation(long seed, List<List<Long>> numbers) {
        if (numbers.isEmpty()) {
            return seed;
        }

        var rowOfNumbers = numbers.getFirst();
        long range = rowOfNumbers.getLast();
        long destinationRange = rowOfNumbers.getFirst();
        long sourceRangeStart = rowOfNumbers.get(1);
        long sourceRangeEnd = sourceRangeStart + range - 1;

        if (seed >= sourceRangeStart && seed <= sourceRangeEnd) {
            return destinationRange + (seed - sourceRangeStart);
        }

        return findLocation(seed, numbers.subList(1, numbers.size()));
    }

    record FertilizerConfig(List<Long> seeds, Map<String, List<List<Long>>> maps) {
    }

}
