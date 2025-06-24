package com.starfish_studios.hamsters.block.util;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum CageType implements StringRepresentable {
    TOP("top"),
    MIDDLE("middle"),
    BOTTOM("bottom"),
    NONE("none");

    private final String type;

    CageType(String type) {
        this.type = type;
    }

    public @NotNull String getSerializedName() {
        return this.type;
    }
}