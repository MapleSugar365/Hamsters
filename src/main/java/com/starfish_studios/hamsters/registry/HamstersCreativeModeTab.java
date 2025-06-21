package com.starfish_studios.hamsters.registry;

import com.starfish_studios.hamsters.Hamsters;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.starfish_studios.hamsters.registry.HamstersItems.*;

public class HamstersCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, Hamsters.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ITEM_GROUP = CREATIVE_MODE_TABS
            .register("item_group",
                    () -> CreativeModeTab.builder().icon(HAMSTER_SPAWN_EGG.get()::getDefaultInstance)
                            .title(Component.translatable("itemGroup.hamsters.tab"))
                            .displayItems((featureFlagSet, output) -> {

                                // output.accept(TUNNEL);
                                output.accept(HAMSTER_WHEEL.get());
                                output.accept(HAMSTER_BED.get());
                                output.accept(HAMSTER_SPAWN_EGG.get());

                                for (int i = 0; i <= 6; i++) {
                                    ItemStack stack = new ItemStack(HAMSTER.get());
                                    CompoundTag variantTag = new CompoundTag();
                                    variantTag.putInt("Variant", i);
                                    output.accept(stack);
                                }

                            }).build());

    @SuppressWarnings("unused")
    private static CreativeModeTab register(String id, CreativeModeTab tab) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
                ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, id), tab);
    }
}
