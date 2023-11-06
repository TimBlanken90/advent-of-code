package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class InputResolver {

    private InputResolver() {}

    public static String toString(int year, int day) throws IOException {
        var path = resolvePath(year, day);
        return Files.readString(path);
    }

    public static Stream<Character> toCharStream(int year, int day) throws IOException {
        var path = resolvePath(year, day);
        return Files.readString(path).chars().mapToObj(c -> (char) c);
    }

    public static List<String> toList(int year, int day) throws IOException {
        var path = resolvePath(year, day);
        return Files.readAllLines(path);
    }

    public static List<Integer> toIntegerList(int year, int day) throws IOException {
        return toCharStream(year, day).map(Character::getNumericValue).toList();
    }

    private static Path resolvePath(int year, int day) {
        return Paths.get("src/test/java/year%s/input/%s.txt".formatted(year, day));
    }
}
