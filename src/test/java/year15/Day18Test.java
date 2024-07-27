package year15;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import common.Day;
import util.InputResolver;

public class Day18Test implements Day {

    private static final char ON = '#';
    private static final char OFF = '.';

    @Override
    @Test
    public void partOne() throws IOException {
        var input = InputResolver.toList(15, 18);
        var initialState = getInitialState(input);
        boolean[][] finalState = calculateFinalState(initialState, 100, false);

        assertEquals(821, countLightsOn(finalState));
    }

    @Override
    @Test
    public void partTwo() throws IOException {
        var input = InputResolver.toList(15, 18);
        var initialState = getInitialState(input);
        initialState[99][0] = true;
        boolean[][] finalState = calculateFinalState(initialState, 100, true);

        assertEquals(886, countLightsOn(finalState));
    }

    private boolean[][] getInitialState(List<String> input) {
        int size = input.size();
        boolean[][] grid = new boolean[size][size];
        for (int i = 0; i < input.size(); i++) {
            var str = input.get(i);
            for (int j = 0; j < input.size(); j++) {
                grid[i][j] = str.charAt(j) == ON;
            }
        }
        return grid;
    }

    private boolean[][] calculateFinalState(boolean[][] grid, int steps, boolean cornersAreStuck) {
        if (steps < 1) {
            return grid;
        }
        var nextState = getNextState(grid, cornersAreStuck);
        return calculateFinalState(nextState, --steps, cornersAreStuck);
    }

    private boolean[][] getNextState(boolean[][] grid, boolean cornersAreStuck) {
        boolean[][] nextGridState = new boolean[grid.length][grid.length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                var neighborsThatAreOn = countNeighborsThatAreOn(grid, i, j);
                if (grid[i][j] && (neighborsThatAreOn < 2 || neighborsThatAreOn > 3)) {
                    nextGridState[i][j] = false;
                } else {
                    if (neighborsThatAreOn == 3) {
                        nextGridState[i][j] = true;
                    } else {
                        nextGridState[i][j] = grid[i][j];
                    }
                }
            }
            if (cornersAreStuck) {
                stuckCorners(nextGridState);
            }
        }
        return nextGridState;
    }

    private int countNeighborsThatAreOn(boolean[][] grid, int x, int y) {
        int neighborsThatAreOn = 0;
        for (int i = x > 0 ? -1 : 0; i < (x < grid.length - 1 ? 2 : 1); i++) {
            for (int j = y > 0 ? -1 : 0; j < (y < grid.length - 1 ? 2 : 1); j++) {
                if (!(i == 0 && j == 0) && grid[x + i][y + j]) {
                    neighborsThatAreOn++;
                }
            }
        }
        return neighborsThatAreOn;
    }

    private void stuckCorners(boolean[][] grid) {
        grid[0][0] = true;
        grid[0][grid.length - 1] = true;
        grid[grid.length - 1][grid.length - 1] = true;
        grid[grid.length - 1][0] = true;
    }

    private int countLightsOn(boolean[][] grid) {
        int lightsOn = 0;
        for (boolean[] row : grid) {
            for (boolean isOn : row)
                if (isOn) ++lightsOn;
        }
        return lightsOn;
    }

}
