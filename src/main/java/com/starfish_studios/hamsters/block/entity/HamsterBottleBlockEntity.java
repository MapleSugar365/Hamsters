package com.starfish_studios.hamsters.block.entity;

import com.starfish_studios.hamsters.registry.HamstersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HamsterBottleBlockEntity extends BlockEntity{
    public HamsterBottleBlockEntity(BlockPos pos, BlockState state) {
        super(HamstersBlockEntities.HAMSTER_BOTTLE.get(), pos, state);
    }
    
}
