package com.starfish_studios.hamsters;

import eu.midnightdust.lib.config.MidnightConfig;

public class HamstersConfig extends MidnightConfig {
    // @Entry(category = "text") public static boolean enableScientificSpeciesNames = false;
    @Entry(category = "server") public static boolean hamstersSquish = true;
    @Entry(category = "server") public static boolean jumpHurtsHamsters = false;
    @Entry(category = "server") public static boolean hamstersBurst = true;
    @Entry(category = "server") public static BurstStyleEnum hamsterBurstStyle = BurstStyleEnum.CONFETTI;
    public enum BurstStyleEnum {
        CONFETTI, EXPLOSION
    }
}