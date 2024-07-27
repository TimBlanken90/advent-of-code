package year15;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import common.Day;
import util.InputResolver;

public class Day16Test implements Day {

    @Override
    @Test
    public void partOne() throws IOException {
       BiPredicate<Sue, Map<String, Integer>> suePredicate = (sue, knownProps) -> {
           for (var key: sue.props.keySet()) {
               var requestedValue = knownProps.get(key);
               if (requestedValue != null & !Objects.equals(sue.props.get(key), requestedValue)) {
                   return false;
               }
           }
           return true;
       };

        assertEquals(40, solve(suePredicate));
    }

    @Override
    @Test
    public void partTwo() throws IOException {
        BiPredicate<Sue, Map<String, Integer>> suePredicate = (sue, knownProps) -> {
            for (var key: sue.props.keySet()) {
                var requestedValue = knownProps.get(key);
                if (requestedValue != null & !partTwoHelper(key, sue.props.get(key), requestedValue)) {
                    return false;
                }
            }
            return true;
        };


        assertEquals(241, solve(suePredicate));
    }

    private int solve(BiPredicate<Sue, Map<String, Integer>> suePredicate) throws IOException {
        return InputResolver.toList(15, 16).stream()
            .map(this::parseSue)
            .filter(s -> suePredicate.test(s, KNOWN_PROPS)).findFirst()
            .orElseThrow(NullPointerException::new)
            .number();
    }

    private boolean partTwoHelper(String key, int propValue, int requestedValue) {
        if (key.equals("cats") || key.equals("trees")) {
            return propValue > requestedValue;
        }

        if (key.equals("pomeranians") || key.equals("goldfish")) {
            return propValue < requestedValue;
        }

        return Objects.equals(propValue, requestedValue);
    }

    private Sue parseSue(String input) {
        var matcher = SUE_PATTERN.matcher(input);
        if (!matcher.matches()) throw new IllegalArgumentException(input);

        int number = Integer.parseInt(matcher.group(1));
        var propsArray = matcher.group(2).split(", ");
        var props = new HashMap<String, Integer>();

        for (var prop : propsArray) {
            var splitted = prop.split(": ");
            props.put(splitted[0], Integer.parseInt(splitted[1]));
        }

        return new Sue(number, props);
    }

    private record Sue(int number, Map<String, Integer> props) {}

    private static final Pattern SUE_PATTERN = Pattern.compile("Sue (\\d+): (.*)");

    private final Map<String, Integer> KNOWN_PROPS = Map.of(
        "children", 3,
        "cats", 7,
        "samoyeds", 2,
        "pomeranians", 3,
        "akitas", 0,
        "vizslas", 0,
        "goldfish", 5,
        "trees", 3,
        "cars", 2,
        "perfumes", 1
    );
}
