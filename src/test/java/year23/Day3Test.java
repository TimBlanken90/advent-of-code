package year23;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import common.Day;
import util.ArrayUtils;
import util.InputResolver;

public class Day3Test implements Day {

    @Override
    @Test
    public void partOne() throws IOException {
        var input = InputResolver.toList(23, 3);
        var grid = ArrayUtils.to2dCharArray(input);
        int sum = 0;

        for (int i = 0; i < grid.length; i++) {
            colIterator: for (int j = 0; j < grid[i].length; j++) {
                char current = grid[i][j];
                if (Character.isDigit(current)) {
                    // parse the whole number
                    int number = parseNumber(grid[i], j);
                    int numberLength = (int) (Math.log10(number)) + 1;
                    int[] indexes = getIndexesOfNumber(numberLength, j);
                    // iterate over the indexes and get all the neighbours
                    for (int index : indexes) {
                        var neighbours = getNeighbours(grid, i, index);
                        var hasAdjacentSymbol = neighbours.stream().anyMatch(this::isSymbol);
                        // found a part number, increment sum and col iterator to continue search
                        if (hasAdjacentSymbol) {
                            sum += number;
                            j += numberLength;
                            continue colIterator;
                        }
                    }
                    // no part number found, increment col iterator and continue search
                    j += numberLength;
                }
            }
        }

        assertEquals(528819, sum);
    }

    @Override
    @Test
    public void partTwo() throws IOException {

    }

    private int parseNumber(char[] row, int idx) {
        int start = idx;
        while (start > 0 && Character.isDigit(row[start - 1])) {
            start--;
        }

        int end = idx;
        while (end < row.length - 1 && Character.isDigit(row[end + 1])) {
            end++;
        }

        var numberString = new StringBuilder();
        for (int i = start; i <= end; i++) {
            numberString.append(row[i]);
        }

        return Integer.parseInt(numberString.toString());
    }

    private int[] getIndexesOfNumber(int size, int startIdx) {
        int[] indexes = new int[size];
        Arrays.setAll(indexes, i -> startIdx + i);
        return indexes;
    }

    private List<Character> getNeighbours(char[][] grid, int row, int col) {
        var chars = new ArrayList<Character>();
        final int length = grid.length;
        final int width = grid[0].length;
        if (isWithinBounds(row, col, length, width)) {
            for (int i = Math.max(0, row - 1); i < Math.min(length, row + 2); ++i) {
                for (int j = Math.max(0, col - 1); j < Math.min(width, col + 2); ++j) {
                    if (i != row || j != col) {
                        chars.add(grid[i][j]);
                    }
                }
            }
        }
        return chars;
    }

    private boolean isSymbol(char c) {
        return !(Character.isDigit(c) || Character.isLetter(c) || c == '.');
    }

    private boolean isWithinBounds(int x, int y, int length, int width) {
        return x >= 0 && y >= 0 && x < length && y < width;
    }

}
