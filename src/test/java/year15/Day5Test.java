package year15;

import common.Day;
import org.junit.jupiter.api.Test;
import util.InputResolver;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day5Test implements Day {

    private static final List<Character> VOWELS = List.of('a', 'e', 'i', 'o', 'u');
    private static final List<String> STRINGS = List.of("ab", "cd", "pq", "xy");
    private static final Pattern PAIR = Pattern.compile("(\\w{2}).*?(\\1)");

    @Override
    @Test
    public void partOne() throws IOException {
        var input = InputResolver.toList(15, 5);

        long result = input.stream()
                .filter(str ->
                        hasThreeVowels(str) &&
                        hasCharThatAppearsTwiceInARow(str) &&
                        !containsString(str))
                .count();

        assertEquals(238, result);
    }

    @Override
    @Test
    public void partTwo() throws IOException {
        var input = InputResolver.toList(15, 5);
        long result = input.stream()
                .filter(str ->
                        containsPairTwiceWithoutOverlap(str) &&
                        containsAtLeastOneLetterWhichRepeatsWith1LetterBetweenThem(str))
                .count();

        assertEquals(69, result);
    }

    private boolean hasThreeVowels(String input) {
        int vowels = 0;
        for (int i = 0; i < input.length(); i++) {
            if (VOWELS.contains(input.charAt(i))) vowels++;
        }
        return vowels >= 3;
    }

    private boolean hasCharThatAppearsTwiceInARow(String input) {
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i - 1) == input.charAt(i)) return true;
        }
        return false;
    }

    private boolean containsString(String input) {
        return STRINGS.stream().anyMatch(input::contains);
    }

    private boolean containsPairTwiceWithoutOverlap(String input) {
        return PAIR.matcher(input).find();
    }

    private boolean containsAtLeastOneLetterWhichRepeatsWith1LetterBetweenThem(String input) {
        for (int i = 0; i < input.length() - 2; i++) {
            if (input.charAt(i) == input.charAt(i + 2)) return true;
        }
        return false;
    }
}
