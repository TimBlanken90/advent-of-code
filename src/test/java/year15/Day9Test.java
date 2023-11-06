package year15;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import common.Day;
import lombok.Getter;
import util.InputResolver;

public class Day9Test implements Day {

    private ArrayList<City> cities = new ArrayList<>();
    private Map<String,Integer> routes = new HashMap<>();

    @Override
    @Test
    public void partOne() throws IOException {
        var input = InputResolver.toList(15, 9);

        input.forEach(str -> {
            var splitted = str.split(" ");
            var city = getCityByName(splitted[0]);
            city.addRoute(getCityByName(splitted[2]), Integer.parseInt(splitted[4]));
        });

        cities.forEach(city -> getRoutes(city, new ArrayList<>(), 0));

        int lowestDistance = Collections.min(routes.values().stream().toList());

        System.out.println(lowestDistance);

    }

    @Override
    @Test
    public void partTwo() throws IOException {

    }

    private City getCityByName(String name) {
        return cities.stream().filter(c -> c.name.equals(name)).findFirst().orElseGet(() -> {
            var newCity = new City(name);
            cities.add(newCity);
            return newCity;
        });
    }

    private void getRoutes(City city, ArrayList<String> names, int sum) {
        names.add(city.name);
        for (City c : city.routes.keySet()) {
            if (!names.contains(c.name)) {
                getRoutes(c, new ArrayList<>(names), sum + city.routes.get(c));
            }
           else if(names.size() == cities.size()) {
                routes.put(Arrays.toString(names.toArray()), sum);
            }
        }

    }

    @Getter
    static class City {
        private final String name;
        private final Map<City, Integer> routes = new HashMap<>();

        public City(String name) {
            this.name = name;
        }

        void addRoute(City city, int distance) {
            routes.put(city, distance);
            city.routes.put(this, distance);
        }
    }

}
