package com.starfish_studios.hamsters.registry;

import java.util.function.Supplier;
import com.starfish_studios.hamsters.Hamsters;
import com.starfish_studios.hamsters.entity.Hamster;
import com.starfish_studios.hamsters.entity.SeatEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HamstersEntityType {

        public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
                        .create(BuiltInRegistries.ENTITY_TYPE, Hamsters.MOD_ID);

        public static final Supplier<EntityType<Hamster>> HAMSTER = ENTITY_TYPES.register(
                        "hamster", () -> EntityType.Builder.of(Hamster::new, MobCategory.CREATURE)
                                        .sized(0.5F, 0.5F)
                                        .clientTrackingRange(10)
                                        .build(ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, "hamster")
                                                        .toString()));

        public static final Supplier<EntityType<SeatEntity>> SEAT = ENTITY_TYPES.register(
                        "seat", () -> EntityType.Builder.<SeatEntity>of(SeatEntity::new, MobCategory.MISC)
                                        .sized(0.0F, 0.0F)
                                        .build(ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, "seat")
                                                        .toString()));

}
