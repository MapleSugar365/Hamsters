package com.starfish_studios.hamsters.registry;

import com.starfish_studios.hamsters.Hamsters;
import com.starfish_studios.hamsters.block.entity.HamsterBottleBlockEntity;
import com.starfish_studios.hamsters.block.entity.HamsterWheelBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class HamstersBlockEntities {
        public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister
                        .create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Hamsters.MOD_ID);

        public static final Supplier<BlockEntityType<HamsterWheelBlockEntity>> HAMSTER_WHEEL = BLOCK_ENTITY_TYPES
                        .register(
                                        "hamster_wheel",
                                        () -> BlockEntityType.Builder
                                                        .of(HamsterWheelBlockEntity::new,
                                                                        HamstersBlocks.HAMSTER_WHEEL.get())
                                                        .build(null));

        public static final Supplier<BlockEntityType<HamsterBottleBlockEntity>> HAMSTER_BOTTLE = BLOCK_ENTITY_TYPES
                        .register(
                                        "hamster_bottle",
                                        () -> {
                                                Block[] bottles = HamstersBlocks.HAMSTER_BOTTLES.stream()
                                                                .map(Supplier::get)
                                                                .toArray(Block[]::new);
                                                return BlockEntityType.Builder
                                                                .of(HamsterBottleBlockEntity::new, bottles)
                                                                .build(null);
                                        });

}