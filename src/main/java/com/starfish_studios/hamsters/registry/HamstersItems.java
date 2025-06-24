package com.starfish_studios.hamsters.registry;

import java.util.function.Supplier;

import com.starfish_studios.hamsters.Hamsters;
import com.starfish_studios.hamsters.item.HamsterItem;
import com.starfish_studios.hamsters.item.HamsterWheelItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HamstersItems {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(Hamsters.MOD_ID);

        public static final Supplier<Item> HAMSTER_SPAWN_EGG = ITEMS.register("hamster_spawn_egg",
                        () -> new DeferredSpawnEggItem(HamstersEntityType.HAMSTER, 16747824, 16775119,
                                        new Item.Properties()));

        public static final Supplier<Item> HAMSTER = ITEMS.register("hamster",
                        () -> new HamsterItem(new Item.Properties().stacksTo(1)));

        public static final Supplier<Item> HAMSTER_WHEEL = ITEMS.register("hamster_wheel",
                        () -> new HamsterWheelItem(HamstersBlocks.HAMSTER_WHEEL.get(), new Item.Properties()));

        public static final Supplier<Item> HAMSTER_BED = ITEMS.register("hamster_bed",
                        () -> new BlockItem(HamstersBlocks.HAMSTER_BED.get(), new Item.Properties()));

        // public static final DeferredItem<Item> TUNNEL = ITEMS.register("tunnel", new
        // BlockItem () ->(HamstersBlocks.TUNNEL, new FabricItemSettings()));
        // public static Supplier<Item> registerCaughtMobItem(String name, EntityType
        // entitySupplier, Supplier<? extends Fluid> fluidSupplier, SoundEvent
        // soundSupplier, int variantAmount) {
        // return registerItem(name, () -> new HamsterItem(entitySupplier,
        // fluidSupplier.get(), soundSupplier, variantAmount, new
        // Item.Properties().stacksTo(1)));
        // }
}
