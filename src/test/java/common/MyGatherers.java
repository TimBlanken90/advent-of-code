package common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Gatherer;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MyGatherers {

    private static final Pattern DIGITS_PATTERN = Pattern.compile("\\d+");

    /**
     * A {@link Gatherer} that processes each input string (representing a line of text) and extracts all integers from that line. Each line's integers are collected into a
     * separate {@link List} and pushed downstream. The output is a list of lists, where each inner list corresponds to the integers found in one input line.
     *
     * <p>This gatherer processes input by using a regular expression to find all sequences
     * of digits and converts them into integers, storing them in a new list for each line.</p>
     *
     * <p>For example, if the input contains the following lines:
     * <pre>
     * Time: 7 15 30
     * Distance: 9 40 200
     * </pre>
     * The resulting output will be:
     * <pre>
     * [[7, 15, 30], [9, 40, 200]]
     * </pre>
     *
     * @see Gatherer
     */
    public static Gatherer<String, List<Long>, List<Long>> collectNumbers = Gatherer.ofSequential(
        ArrayList::new,
        (state, element, downstream) -> {
            var matcher = DIGITS_PATTERN.matcher(element);
            var integers = new ArrayList<Long>();
            while (matcher.find()) {
                integers.add(Long.parseLong(matcher.group()));
            }
            downstream.push(integers);
            return true;
        },
        Gatherer.defaultFinisher()
    );

}
