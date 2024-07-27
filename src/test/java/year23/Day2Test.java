package year23;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import common.Day;
import util.InputResolver;

public class Day2Test  implements Day {

    record Game(int round, List<Set> sets) {}

    record Set(List<Cube> cubes) {}

    record Cube(int amount, Color color) {}

    enum Color {
        BLUE, RED, GREEN
    }

    @Override
    @Test
    public void partOne() throws IOException {
        var solution = initGameSolver().part1();
        assertEquals(2101, solution);
    }

    @Override
    @Test
    public void partTwo() throws IOException {
        var solution = initGameSolver().part2();
        assertEquals(58269, solution);
    }

    private GameSolver initGameSolver() throws IOException {
        var input = InputResolver.toList(23, 2);
        var games = input.stream().map(GameParser::parse).toList();
        return new GameSolver(games, Map.of(Color.BLUE, 14, Color.RED, 12, Color.GREEN, 13));
    }

    static class GameParser {
        static Pattern digitPattern = Pattern.compile("\\d+");
        static Game parse(String input) {
            var splitted = input.split(":");
            var digits = digitPattern.matcher(splitted[0]);
            int idx = digits.find() ? Integer.parseInt(digits.group()) : -1;
            return new Game(idx, Arrays.stream(splitted[1].split(";")).map(set -> {
                var cubes = Arrays.stream(set.split(","))
                    .map(String::trim)
                    .map(s -> s.split(" "))
                    .map(s -> new Cube(Integer.parseInt(s[0]), Color.valueOf(s[1].toUpperCase()))).toList();
                return new Set(cubes);
            }).toList());
        }
    }

    static class GameSolver {
        private final List<Game> games;
        private final Map<Color, Integer> bag;

        private GameSolver(List<Game> games, Map<Color, Integer> bag) {
            this.games = games;
            this.bag = bag;
        }

        private int part1() {
            return this.games.stream()
                .filter(game -> isPossible().test(game))
                .mapToInt(Game::round).sum();
        }

        private int part2() {
            return this.games.stream()
                .map(this::maxPerColor)
                .map(map -> map.values().stream().reduce(1, (a, b) -> a * b))
                .mapToInt(Integer::intValue).sum();
        }

        private Predicate<Game> isPossible() {
            return (game) -> {
                var maxPerColor = maxPerColor(game);
                return Stream.of(Color.values())
                    .allMatch(color -> maxPerColor.getOrDefault(color, 0) <= this.bag.getOrDefault(color, 0));
            };
        }

        private Map<Color, Integer> maxPerColor(Game game) {
            return game.sets.stream()
                .flatMap(sets -> sets.cubes.stream())
                .collect(Collectors.toMap(Cube::color, Cube::amount, Integer::max));
        }
    }
}
