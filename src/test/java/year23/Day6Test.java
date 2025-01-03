package year23;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import common.Day;
import common.MyGatherers;
import util.InputResolver;

public class Day6Test implements Day {


    @Override
    @Test
    public void partOne() throws IOException {
        var input = InputResolver.toList(23, 6).stream()
            .gather(MyGatherers.collectNumbers)
            .toList();

        var races = parseRaces(input);
        long score = getScore(races);
        assertEquals(220320, score);
    }

    @Override
    @Test
    public void partTwo() throws IOException {
        var input = InputResolver.toList(23, 6).stream()
            .gather(MyGatherers.collectNumbers)
            .map(numbers -> {
                var sb = new StringBuilder();
                for (long i : numbers) {
                    sb.append(i);
                }
                return List.of(Long.parseLong(sb.toString()));
            })
            .toList();

        var races = parseRaces(input);
        long score = getScore(races);
        assertEquals(34454850, score);
    }

    private List<Race> parseRaces(List<List<Long>> input) {
        var time = input.getFirst();
        var distance = input.getLast();
        var races = new ArrayList<Race>(time.size());

        for (int i = 0; i < time.size(); i++) {
            races.add(new Race(time.get(i), distance.get(i)));
        }

        return races;
    }

    private long getScore(List<Race> races) {
        return races.stream().map(Race::calculateWaysToWin).reduce(1L, (a, b) -> a * b);
    }

    private record Race(long time, long recordDistance) {
        long calculateWaysToWin() {
            long count = 0;
            for (long holdTime = 0; holdTime <= time; holdTime++) {
                long distance = holdTime * (time - holdTime);
                if (distance > recordDistance) {
                    count++;
                }
            }

            return count;
        }
    }

}
