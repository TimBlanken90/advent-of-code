package year15;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import common.Day;
import util.InputResolver;

public class Day15Test implements Day {

    @Override
    @Test
    public void partOne() throws IOException {
        var ingredients = InputResolver.toList(15, 15).stream()
            .map(str -> str.replaceAll(",", ""))
            .map(str -> str.split(" "))
            .map(this::createIngredient)
            .toList();

        var quantities = new int[ingredients.size()];
        var best = 0;
        var temp = 0;

        while (incrementQuantities(quantities)) {
            int quantityApplied = Arrays.stream(quantities, 0, quantities.length - 1).sum();

            if (quantityApplied > 100) {
                continue;
            }

            quantities[ingredients.size() - 1] = 100 - quantityApplied;

            int capacity = 0, durability = 0, flavor = 0, texture = 0, calories = 0;

            for (int i = 0; i < ingredients.size(); i++) {
                var ingredient = ingredients.get(i);
                capacity += quantities[i] * ingredient.capacity();
                durability += quantities[i] * ingredient.durability();
                flavor += quantities[i] * ingredient.flavor();
                texture += quantities[i] * ingredient.texture();
                calories += quantities[i] * ingredient.calories();
            }

            int total = Math.max(0, capacity) * Math.max(0, durability) * Math.max(0, flavor) * Math.max(0, texture);

            if (total > best) {
                best = total;
            }
            if (calories == 500 && total > temp) {
                temp = total;
            }
        }

        assertEquals(13882464, best);
    }

    @Override
    @Test
    public void partTwo() throws IOException {

    }

    private boolean incrementQuantities(int[] quantities) {
        for (int i = 0; i < quantities.length - 1; i++) {
            quantities[i]++;
            if (quantities[i] <= 100) {
                return true;
            }
            quantities[i] = 0;
        }
        return false;
    }

    private record Ingredient(String name, int capacity, int durability, int flavor, int texture, int calories) {

    }

    private Ingredient createIngredient(String[] parts) {
        return new Ingredient(parts[0], toInt(parts[2]), toInt(parts[4]), toInt(parts[6]), toInt(parts[8]), toInt(parts[10]));
    }

    private int toInt(String str) {
        return Integer.parseInt(str);
    }

}
