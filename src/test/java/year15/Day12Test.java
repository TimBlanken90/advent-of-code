package year15;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import common.Day;
import util.InputResolver;

public class Day12Test implements Day {

    @Override
    @Test
    public void partOne() throws IOException {
        var input = InputResolver.toString(15, 12);

        var sum = Arrays.stream(input.split("[^\\d-]"))
            .filter(str -> !str.equals(""))
            .map(Integer::parseInt)
            .reduce(0, Integer::sum);

        assertEquals(156366, sum);
    }

    @Override
    @Test
    public void partTwo() throws IOException {
        var input = InputResolver.toString(15, 12);
        assertEquals(96852, solve(new JSONArray(input)));
    }

    private int solve(Object input) {
        if (input instanceof Integer) return (int) input;
        if (input instanceof String) return 0;

        var sum = new AtomicInteger();

        if (input instanceof JSONArray jsonArray) {
            jsonArray.forEach(item -> sum.addAndGet(solve(item)));
            return sum.get();
        }

        if (input instanceof JSONObject jsonObject) {
            JSONArray names = jsonObject.names();
            for (int i = 0; i < names.length(); ++i) {
                String name = (String) names.get(i);
                if (jsonObject.get(name).equals("red")) {
                    return 0;
                } else {
                    sum.addAndGet(solve(jsonObject.get(name)));
                }
            }
            return sum.get();
        }

        return 0;
    }
}
