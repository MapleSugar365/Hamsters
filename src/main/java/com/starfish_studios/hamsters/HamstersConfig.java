package com.starfish_studios.hamsters;

import eu.midnightdust.lib.config.MidnightConfig;

public class HamstersConfig extends MidnightConfig {
    @Entry(category = "server") public static int hamstersInWheelTime = 60;
    @Entry(category = "server") public static int hamstersRestTime = 120;
    @Entry(category = "server") public static int hamstersFeedingInterval = 300;
    @Entry(category = "server") public static boolean hamstersSleepDuringTheDay = true;
    @Entry(category = "server") public static boolean hamstersSleepAtNight = false;
    @Entry(category = "server") public static boolean hamstersSquish = true;
    @Entry(category = "server") public static boolean jumpHurtsHamsters = false;
    @Entry(category = "server") public static boolean hamstersBurst = true;
    @Entry(category = "server") public static BurstStyleEnum hamstersBurstStyle = BurstStyleEnum.CONFETTI;
    public enum BurstStyleEnum {
        CONFETTI, EXPLOSION
    }
}