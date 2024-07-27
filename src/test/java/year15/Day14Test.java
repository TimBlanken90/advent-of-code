package year15;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import common.Day;
import lombok.Data;
import util.InputResolver;

public class Day14Test implements Day {

    @Override
    @Test
    public void partOne() throws IOException {
        var maxDistance = InputResolver.toList(15, 14).stream()
            .map(str -> new Reindeer(str.split(" ")))
            .map(r -> r.maxDistanceInSeconds(2503))
            .mapToInt(i -> i)
            .max().orElseThrow(NullPointerException::new);

        assertEquals(2660, maxDistance);
    }

    @Override
    @Test
    public void partTwo() throws IOException {
        var reindeers = InputResolver.toList(15, 14).stream()
            .map(str -> new Reindeer(str.split(" ")))
            .toList();

        for (int second = 1; second <= 2503; second++) {
            var furthestDistance = new AtomicInteger(0);
            for (Reindeer reindeer : reindeers) {
                int distance = reindeer.getDistance();
                if (distance > furthestDistance.get()) {
                    furthestDistance.set(distance);
                }
            }
            reindeers.stream().filter(r -> r.distance == furthestDistance.get()).forEach(Reindeer::addPoint);
        }

        var winningPoints = reindeers.stream().mapToInt(Reindeer::getPoints).max().orElseThrow(NullPointerException::new);
        assertEquals(1256, winningPoints);
    }

    @Data
    private static class Reindeer {

        private final String name;
        private final int speed;
        private final int duration;
        private final int restTime;
        private int points = 0;
        private int distance = 0;
        private int time = 0;
        private boolean isResting = false;

        public Reindeer(String[] parts) {
            this.name = parts[0];
            this.speed = Integer.parseInt(parts[3]);
            this.duration = Integer.parseInt(parts[6]);
            this.restTime = Integer.parseInt(parts[13]);
        }

        public int maxDistanceInSeconds(int seconds) {
            int totalDistance;
            int rounds = seconds / (duration + restTime);
            int remainder = seconds % (duration + restTime);
            totalDistance = rounds * (speed * duration);
            if (remainder >= duration) {
                totalDistance += (speed * duration);
            }
            return totalDistance;
        }

        public int getDistance() {
            time++;
            if (isResting) {
                if (time == restTime) {
                    isResting = false;
                    time = 0;
                }
            } else {
                distance += speed;
                if (time == duration) {
                    isResting = true;
                    time = 0;
                }
            }

            return distance;
        }

        public void addPoint() {
            this.points++;
        }
    }
}
