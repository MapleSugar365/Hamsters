package com.starfish_studios.hamsters.registry;

import java.util.function.Supplier;
import net.minecraft.world.level.block.Block;
import com.starfish_studios.hamsters.Hamsters;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.starfish_studios.hamsters.registry.HamstersItems.*;
import static com.starfish_studios.hamsters.registry.HamstersBlocks.*;

public class HamstersCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, Hamsters.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ITEM_GROUP = CREATIVE_MODE_TABS
            .register("item_group",
                    () -> CreativeModeTab.builder().icon(HamstersItems.HAMSTER.get()::getDefaultInstance)
                            .title(Component.translatable("itemGroup.hamsters.tab"))
                            .displayItems((featureFlagSet, output) -> {
                                // other items
                                output.accept(HamstersItems.HAMSTER_WHEEL.get());
                                output.accept(HAMSTER_SPAWN_EGG.get());
                                // output.accept(HamstersItems.HAMSTER_BED.get());
                                // hamster bottles
                                for (Supplier<Block> hamsterBottle : HAMSTER_BOTTLES) {
                                    output.accept(hamsterBottle.get());
                                }
                                // hamster bowls
                                for (Supplier<Block> hamsterBowl : HAMSTER_BOWLS) {
                                    output.accept(hamsterBowl.get());
                                }
                                // cage panels
                                for (Supplier<Block> cagePanel : CAGE_PANELS) {
                                    output.accept(cagePanel.get());
                                }
                                // for (int i = 0; i <= 6; i++) {
                                // ItemStack stack = new ItemStack(HAMSTER.get());
                                // CompoundTag variantTag = new CompoundTag();
                                // variantTag.putInt("Variant", i);
                                // output.accept(stack);
                                // }

                            }).build());

}
