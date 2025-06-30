package com.starfish_studios.hamsters.registry;

import java.util.function.Supplier;

import com.starfish_studios.hamsters.Hamsters;
import com.starfish_studios.hamsters.item.ChocolateHamsterItem;
import com.starfish_studios.hamsters.item.HamsterItem;
import com.starfish_studios.hamsters.item.HamsterWheelItem;

import net.minecraft.world.food.FoodProperties;
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

        public static final Supplier<Item> CHOCOLATE_HAMSTER = ITEMS.register("chocolate_hamster",
                        () -> new ChocolateHamsterItem(new Item.Properties().stacksTo(1)
                                        .food(new FoodProperties.Builder().nutrition(20).saturationModifier(0.9F).build())));

        // public static final DeferredItem<Item> TUNNEL = ITEMS.register("tunnel", new
        // BlockItem () ->(HamstersBlocks.TUNNEL, new FabricItemSettings()));
}
