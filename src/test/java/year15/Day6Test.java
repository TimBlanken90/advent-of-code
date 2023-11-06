package year15;

import common.Day;
import org.junit.jupiter.api.Test;
import util.InputResolver;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day6Test implements Day {

    private static final String TURN_ON = "turn on";
    private static final String TOGGLE = "toggle";

    @Override
    @Test
    public void partOne() throws IOException {
        var input = InputResolver.toList(15, 6);
        var booleanGrid = new BooleanGrid(new Boolean[1000][1000], false);

        input.forEach(booleanGrid::traverse);

        assertEquals(400410, booleanGrid.getLightsOn());
    }

    @Override
    @Test
    public void partTwo() throws IOException {
        var input = InputResolver.toList(15, 6);
        var integerGrid = new IntegerGrid(new Integer[1000][1000], 0);

        input.forEach(integerGrid::traverse);

        assertEquals(15343601, integerGrid.getTotalBrightness());
    }

    private static abstract class Grid<T> {

        T[][] grid;
        T defaultValue;

        public Grid(T[][] grid, T defaultValue) {
            this.grid = grid;
            this.defaultValue = defaultValue;
            init();
        }

        protected void init() {
            for (T[] row : grid) {
                Arrays.fill(row, defaultValue);
            }
        }

        protected void traverse(String command) {
            var numbers = parseNumbers(command);
            int startRow = numbers[0];
            int endRow = numbers[2];
            int startCol = numbers[1];
            int endCol = numbers[3];

            for (int row = startRow; row <= endRow; row++) {
                for (int col = startCol; col <= endCol; col++) {
                  handleInput(command, row, col);
                }
            }
        }

        protected void handleInput(String command, int row, int col) {
            if (command.contains(TURN_ON)) {
                handleTurnOn(row, col);
            } else if (command.contains(TOGGLE)) {
                handleToggle(row, col);
            } else {
                handleTurnOff(row, col);
            }
        }

        protected Integer[] parseNumbers(String input) {
            return Arrays.stream(input.replaceAll("[^0-9]+", " ")
                            .trim()
                            .split(" "))
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);
        }

        protected abstract void handleTurnOn(int row, int col);

        protected abstract void handleToggle(int row, int col);

        protected abstract void handleTurnOff(int row, int col);
    }

    private static class BooleanGrid extends Grid<Boolean> {

        public BooleanGrid(Boolean[][] grid, Boolean defaultValue) {
            super(grid, defaultValue);
        }

        @Override
        protected void handleTurnOn(int row, int col) {
            grid[row][col] = true;
        }

        @Override
        protected void handleToggle(int row, int col) {
            grid[row][col] = !grid[row][col];
        }

        @Override
        protected void handleTurnOff(int row, int col) {
            grid[row][col] = false;
        }

        protected int getLightsOn() {
            int lightsOn = 0;
            for (Boolean[] rows : grid) {
                for (Boolean col : rows) {
                    if (col) lightsOn++;
                }
            }
            return lightsOn;
        }
    }

    private static class IntegerGrid extends Grid<Integer> {

        public IntegerGrid(Integer[][] grid, Integer defaultValue) {
            super(grid, defaultValue);
        }

        @Override
        protected void handleTurnOn(int row, int col) {
            grid[row][col]++;
        }

        @Override
        protected void handleToggle(int row, int col) {
            grid[row][col] = grid[row][col] + 2;
        }

        @Override
        protected void handleTurnOff(int row, int col) {
            int brightness = grid[row][col];
            if (brightness > 0) grid[row][col]--;
        }

        protected int getTotalBrightness() {
            int brightness = 0;
            for (Integer[] rows : grid) {
                for (Integer col : rows) {
                    brightness = brightness + col;
                }
            }
            return brightness;
        }
    }
}
