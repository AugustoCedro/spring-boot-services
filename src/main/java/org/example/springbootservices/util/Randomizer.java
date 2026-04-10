package org.example.springbootservices.util;

import org.example.springbootservices.model.aventura.enums.DangerLevel;
import org.example.springbootservices.model.aventura.enums.MissionStatus;
import org.example.springbootservices.model.aventura.enums.Role;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {

    private static final Random random = new Random();

    public static DangerLevel generateRandomDangerLevel(){
        DangerLevel[] values = DangerLevel.values();
        return values[random.nextInt(values.length)];
    }

    public static MissionStatus generateRandomMissionStatus(){
        MissionStatus[] values = MissionStatus.values();
        return values[random.nextInt(values.length)];
    }

    public static Role generateRandomMissionRole(){
        Role[] values = Role.values();
        return values[random.nextInt(values.length)];
    }

    public static Boolean generateRandomMvp() {
        return Math.random() < 0.5;
    }

    public static Integer generateRandomReward(){
        return random.nextInt(2500) + 1;
    }

    public static LocalDateTime randomDateTime() {

        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2026, 2, 28, 23, 59, 59);

        long startEpoch = start.atZone(ZoneId.systemDefault()).toEpochSecond();
        long endEpoch = end.atZone(ZoneId.systemDefault()).toEpochSecond();

        long randomEpoch = ThreadLocalRandom.current()
                .nextLong(startEpoch, endEpoch + 1);

        return LocalDateTime.ofEpochSecond(randomEpoch, 0, ZoneId.systemDefault().getRules().getOffset(start.atZone(ZoneId.systemDefault()).toInstant()));
    }
}
