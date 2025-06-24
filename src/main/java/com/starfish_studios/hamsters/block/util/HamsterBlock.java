package com.starfish_studios.hamsters.block.util;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public interface HamsterBlock {
    BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    static boolean isWaterlogged(BlockState blockState) {
        return blockState.getValue(WATERLOGGED);
    }
}
