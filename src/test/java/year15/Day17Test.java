package year15;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import common.Day;
import util.InputResolver;

public class Day17Test implements Day {

    @Override
    @Test
    public void partOne() throws IOException {
        var input = InputResolver.toList(15, 17).stream().map(Integer::parseInt).toList();
        var possibleCombinations = solve(input).size();
        assertEquals(1638, possibleCombinations);
    }

    @Override
    @Test
    public void partTwo() throws IOException {
        var input = InputResolver.toList(15, 17).stream().map(Integer::parseInt).toList();
        var combinations = solve(input);

        int minimalContainersNeeded = combinations.stream().mapToInt(List::size).min().getAsInt();
        int possibleCombinations = combinations.stream().filter(list -> list.size() == minimalContainersNeeded).toList().size();
        assertEquals(17, possibleCombinations);
    }

    private List<List<Integer>> solve(List<Integer> containers) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> currentCombination = new ArrayList<>();
        findCombinations(containers, 150, 0, currentCombination, result);
        return result;
    }

    private void findCombinations(List<Integer> containers, int totalSizeLeft, int startIndex,
        List<Integer> combinations, List<List<Integer>> result) {
        if (totalSizeLeft == 0) {
            result.add(new ArrayList<>(combinations));
            return;
        }

        for (int i = startIndex; i < containers.size(); i++) {
            int containerSize = containers.get(i);
            if (totalSizeLeft >= containerSize) {
                combinations.add(containerSize);
                findCombinations(containers, totalSizeLeft - containerSize, i + 1, combinations, result);
                combinations.remove(combinations.size() - 1);
            }
        }
    }
}
