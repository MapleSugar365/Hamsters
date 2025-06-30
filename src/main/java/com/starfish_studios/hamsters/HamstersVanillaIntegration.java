package com.starfish_studios.hamsters;

import eu.midnightdust.lib.config.MidnightConfig;

public class HamstersVanillaIntegration {
    public static void configInit() {
        MidnightConfig.init(Hamsters.MOD_ID, HamstersConfig.class);
    }
}
