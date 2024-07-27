package year15;

import static java.util.Optional.ofNullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import common.Day;

public class Day21Test implements Day {

    private static final String WEAPONS = "Weapons";
    private static final String ARMOR = "Armor";
    private static final String RINGS = "Rings";

    private static final int BOSS_HP = 103;
    private static final int BOSS_DAMAGE = 9;
    private static final int BOSS_ARMOR = 2;
    private static final int PLAYER_HP = 100;

    private static final Map<String, List<Item>> SHOP = Map.of(
        "Weapons", List.of(
            new Item("Dagger", 8, 4, 0),
            new Item("Shortsword", 10, 5, 0),
            new Item("Warhammer", 25, 6, 0),
            new Item("Longsword", 40, 7, 0),
            new Item("Greataxe", 74, 8, 0)
        ),
        "Armor", List.of(
            new Item("Leather", 13, 0, 1),
            new Item("Chainmail", 31, 0, 2),
            new Item("Splintmail", 53, 0, 3),
            new Item("Bandedmail", 75, 0, 4),
            new Item("Platemail", 102, 0, 5)
        ),
        "Rings", List.of(
            new Item("Damage +1", 25, 1, 0),
            new Item("Damage +2", 50, 2, 0),
            new Item("Damage +3", 100, 3, 0),
            new Item("Defense +1", 20, 0, 1),
            new Item("Defense +2", 40, 0, 2),
            new Item("Defense +3", 80, 0, 3)
        )
    );


    @Override
    @Test
    public void partOne() throws IOException {
        solve();
    }

    @Override
    public void partTwo() throws IOException {

    }

    private int solve() {
        var items = new HashMap<String, List<Item>>();

        var weaponIdx = 0;
        var armorIdx = 0;

        var weapon = SHOP.get(WEAPONS).get(weaponIdx);
        var armor = SHOP.get(ARMOR).get(armorIdx);

        items.put(WEAPONS, List.of(weapon));
        items.put(ARMOR, List.of(armor));

        if (fight(items)) {
            return 0;
        }

        int cost = 1;

        return 1;
    }

    // return true if wins, otherwise false
    private boolean fight(Map<String, List<Item>> items) {
        int damage = items.get(WEAPONS).get(0).damage;
        int armor = ofNullable(items.get(ARMOR)).orElse(List.of()).stream().findFirst().map(i -> i.armor).orElse(0);

        int additionalDamage = ofNullable(items.get(RINGS)).orElse(List.of()).stream().mapToInt(i -> i.damage).sum();
        int additionalArmor = ofNullable(items.get(RINGS)).orElse(List.of()).stream().mapToInt(i -> i.armor).sum();

        int playerArmor = armor + additionalArmor;
        int playerDamage = damage + additionalDamage;
        int inflictedDamage = playerDamage - BOSS_ARMOR;
        int bossInlictedDamage = BOSS_DAMAGE - playerArmor;

        int playerHP = PLAYER_HP;
        int bossHp = BOSS_HP;

        do {
            bossHp -= inflictedDamage;
            System.out.printf("The player deals %d-%d = %d damage; the boss goes down to %d hit points.%n", playerDamage, BOSS_ARMOR, inflictedDamage, bossHp);

            playerHP -= bossInlictedDamage;
            System.out.printf("The boss deals %d-%d = %d damage; the player goes down to %d hit points.%n", BOSS_DAMAGE, playerArmor, bossInlictedDamage, playerHP);

        } while (playerHP > 0 && bossHp > 0);

        return bossHp < playerHP;
    }

    private record Item(String name, int cost, int damage, int armor) {}
}
