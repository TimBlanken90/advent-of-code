package year15;

import static java.util.Collections.swap;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import common.Day;
import util.InputResolver;

public class Day13Test implements Day {

    Map<String, Map<String, Integer>> table = new HashMap<>();

    @Override
    @Test
    public void partOne() throws IOException {
        var persons = InputResolver.toList(15, 13).stream()
            .map(line -> line.substring(0, line.length() -1))
            .map(line -> line.split(" "))
            .map(strings -> new Person(strings[0], strings[2], Integer.parseInt(strings[3]), strings[10]))
            .collect(Collectors.toList());

        initTable(persons);

        int result = solve();
        assertEquals(664, result);
    }

    @Override
    @Test
    public void partTwo() throws IOException {

    }

    private void initTable(List<Person> persons) {
        if (!persons.isEmpty()) {
            var person = persons.get(0);
            if (table.get(person.name) == null) {
                var map = new HashMap<String, Integer>();
                map.put(person.other, person.hapiness());
                table.put(person.name, map);
            } else {
                table.get(person.name).put(person.other, person.hapiness());
            }
            persons.remove(person);
            initTable(persons);
        }
    }

    private int solve() {
        var guests = new ArrayList<>(table.keySet());
        var permutations = generatePermutations(guests);

        int maxHappiness = 0;
        for (var permutation : permutations) {
            int happiness = computeHappiness(permutation);
            maxHappiness = Math.max(maxHappiness, happiness);
        }
        return maxHappiness;
    }

    private List<List<String>> generatePermutations(List<String> guests) {
        List<List<String>> result = new ArrayList<>();
        generatePermutations(guests, 0, result);
        return result;
    }

    private void generatePermutations(List<String> guests, int start, List<List<String>> result) {
        if (start == guests.size() - 1) {
            result.add(new ArrayList<>(guests));
            return;
        }

        for (int i = start; i < guests.size(); i++) {
            swap(guests, start, i);
            generatePermutations(guests, start + 1, result);
            swap(guests, start, i);
        }
    }

    private int computeHappiness(List<String> l) {
        int happiness = 0;
        for (int i = 0; i < (l.size() - 1); i++) {
            happiness += findHappiness(l.get(i), l.get(i + 1));
            happiness += findHappiness(l.get(i + 1), l.get(i));
        }
        happiness += findHappiness(l.get(l.size() - 1), l.get(0));
        happiness += findHappiness(l.get(0), l.get(l.size() - 1));
        return happiness;
    }

    private int findHappiness(String s1, String s2) {
        return table.get(s1).get(s2);
    }

    private record Person(String name, String op, Integer hapiness, String other) {
        @Override
        public Integer hapiness() {
            return op.equals("gain") ? hapiness : hapiness * -1;
        }
    }
}

