package com.starfish_studios.hamsters.registry;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import com.starfish_studios.hamsters.Hamsters;
import com.starfish_studios.hamsters.block.HamsterWheelBlock;
import com.starfish_studios.hamsters.block.HamsterBottleBlock;
import com.starfish_studios.hamsters.block.HamsterBowlBlock;
import com.starfish_studios.hamsters.block.CagePanelBlock;
import com.starfish_studios.hamsters.block.HamsterBedBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HamstersBlocks {
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(Hamsters.MOD_ID);

        public static Supplier<Block> registerBlockItem(String name, Supplier<Block> block) {
                Supplier<Block> toReturn = BLOCKS.register(name, block);
                HamstersItems.ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties()));
                return toReturn;
        }

        public static Block hamsterBottleBlock(DyeColor dyeColor) {
                return new HamsterBottleBlock(BlockBehaviour.Properties.of().strength(0.6F).noOcclusion()
                                .isSuffocating((state, world, pos) -> false).mapColor(dyeColor)
                                .pushReaction(PushReaction.DESTROY));
        }

        public static Block hamsterBowlBlock(DyeColor dyeColor) {
                return new HamsterBowlBlock(BlockBehaviour.Properties.of().strength(0.6F).noOcclusion()
                                .isSuffocating((state, world, pos) -> false).mapColor(dyeColor)
                                .pushReaction(PushReaction.DESTROY));
        }

        public static Block cagePanelBlock(DyeColor dyeColor) {
                return new CagePanelBlock(BlockBehaviour.Properties.of().strength(0.3F).noOcclusion()
                                .isSuffocating((state, world, pos) -> false).mapColor(dyeColor)
                                .requiresCorrectToolForDrops());
        }

        // hamster wheel
        public static final Supplier<Block> HAMSTER_WHEEL = BLOCKS.register("hamster_wheel",
                        () -> new HamsterWheelBlock(BlockBehaviour.Properties.of().strength(0.6F).noOcclusion()
                                        .isSuffocating((state, world, pos) -> false).pushReaction(PushReaction.IGNORE)
                                        .requiresCorrectToolForDrops()));

        // hamster bottles
        public static final Supplier<Block> WHITE_HAMSTER_BOTTLE = registerBlockItem("white_hamster_bottle",
                        () -> hamsterBottleBlock(DyeColor.WHITE));
        public static final Supplier<Block> LIGHT_GRAY_HAMSTER_BOTTLE = registerBlockItem("light_gray_hamster_bottle",
                        () -> hamsterBottleBlock(DyeColor.LIGHT_GRAY));
        public static final Supplier<Block> GRAY_HAMSTER_BOTTLE = registerBlockItem("gray_hamster_bottle",
                        () -> hamsterBottleBlock(DyeColor.GRAY));
        public static final Supplier<Block> BLACK_HAMSTER_BOTTLE = registerBlockItem("black_hamster_bottle",
                        () -> hamsterBottleBlock(DyeColor.BLACK));
        public static final Supplier<Block> BROWN_HAMSTER_BOTTLE = registerBlockItem("brown_hamster_bottle",
                        () -> hamsterBottleBlock(DyeColor.BROWN));
        public static final Supplier<Block> RED_HAMSTER_BOTTLE = registerBlockItem("red_hamster_bottle",
                        () -> hamsterBottleBlock(DyeColor.RED));
        public static final Supplier<Block> ORANGE_HAMSTER_BOTTLE = registerBlockItem("orange_hamster_bottle",
                        () -> hamsterBottleBlock(DyeColor.ORANGE));
        public static final Supplier<Block> YELLOW_HAMSTER_BOTTLE = registerBlockItem("yellow_hamster_bottle",
                        () -> hamsterBottleBlock(DyeColor.YELLOW));
        public static final Supplier<Block> LIME_HAMSTER_BOTTLE = registerBlockItem("lime_hamster_bottle",
                        () -> hamsterBottleBlock(DyeColor.LIME));
        public static final Supplier<Block> GREEN_HAMSTER_BOTTLE = registerBlockItem("green_hamster_bottle",
                        () -> hamsterBottleBlock(DyeColor.GREEN));
        public static final Supplier<Block> CYAN_HAMSTER_BOTTLE = registerBlockItem("cyan_hamster_bottle",
                        () -> hamsterBottleBlock(DyeColor.CYAN));
        public static final Supplier<Block> LIGHT_BLUE_HAMSTER_BOTTLE = registerBlockItem("light_blue_hamster_bottle",
                        () -> hamsterBottleBlock(DyeColor.LIGHT_BLUE));
        public static final Supplier<Block> BLUE_HAMSTER_BOTTLE = registerBlockItem("blue_hamster_bottle",
                        () -> hamsterBottleBlock(DyeColor.BLUE));
        public static final Supplier<Block> PURPLE_HAMSTER_BOTTLE = registerBlockItem("purple_hamster_bottle",
                        () -> hamsterBottleBlock(DyeColor.PURPLE));
        public static final Supplier<Block> MAGENTA_HAMSTER_BOTTLE = registerBlockItem("magenta_hamster_bottle",
                        () -> hamsterBottleBlock(DyeColor.MAGENTA));
        public static final Supplier<Block> PINK_HAMSTER_BOTTLE = registerBlockItem("pink_hamster_bottle",
                        () -> hamsterBottleBlock(DyeColor.PINK));
        public static final List<Supplier<Block>> HAMSTER_BOTTLES = Arrays.asList(
                        WHITE_HAMSTER_BOTTLE, LIGHT_GRAY_HAMSTER_BOTTLE, GRAY_HAMSTER_BOTTLE, BLACK_HAMSTER_BOTTLE,
                        BROWN_HAMSTER_BOTTLE, RED_HAMSTER_BOTTLE, ORANGE_HAMSTER_BOTTLE, YELLOW_HAMSTER_BOTTLE,
                        LIME_HAMSTER_BOTTLE, GREEN_HAMSTER_BOTTLE, CYAN_HAMSTER_BOTTLE, LIGHT_BLUE_HAMSTER_BOTTLE,
                        BLUE_HAMSTER_BOTTLE, PURPLE_HAMSTER_BOTTLE, MAGENTA_HAMSTER_BOTTLE, PINK_HAMSTER_BOTTLE);

        // hamster bowls
        public static final Supplier<Block> WHITE_HAMSTER_BOWL = registerBlockItem("white_hamster_bowl",
                        () -> hamsterBowlBlock(DyeColor.WHITE));
        public static final Supplier<Block> LIGHT_GRAY_HAMSTER_BOWL = registerBlockItem("light_gray_hamster_bowl",
                        () -> hamsterBowlBlock(DyeColor.LIGHT_GRAY));
        public static final Supplier<Block> GRAY_HAMSTER_BOWL = registerBlockItem("gray_hamster_bowl",
                        () -> hamsterBowlBlock(DyeColor.GRAY));
        public static final Supplier<Block> BLACK_HAMSTER_BOWL = registerBlockItem("black_hamster_bowl",
                        () -> hamsterBowlBlock(DyeColor.BLACK));
        public static final Supplier<Block> BROWN_HAMSTER_BOWL = registerBlockItem("brown_hamster_bowl",
                        () -> hamsterBowlBlock(DyeColor.BROWN));
        public static final Supplier<Block> RED_HAMSTER_BOWL = registerBlockItem("red_hamster_bowl",
                        () -> hamsterBowlBlock(DyeColor.RED));
        public static final Supplier<Block> ORANGE_HAMSTER_BOWL = registerBlockItem("orange_hamster_bowl",
                        () -> hamsterBowlBlock(DyeColor.ORANGE));
        public static final Supplier<Block> YELLOW_HAMSTER_BOWL = registerBlockItem("yellow_hamster_bowl",
                        () -> hamsterBowlBlock(DyeColor.YELLOW));
        public static final Supplier<Block> LIME_HAMSTER_BOWL = registerBlockItem("lime_hamster_bowl",
                        () -> hamsterBowlBlock(DyeColor.LIME));
        public static final Supplier<Block> GREEN_HAMSTER_BOWL = registerBlockItem("green_hamster_bowl",
                        () -> hamsterBowlBlock(DyeColor.GREEN));
        public static final Supplier<Block> CYAN_HAMSTER_BOWL = registerBlockItem("cyan_hamster_bowl",
                        () -> hamsterBowlBlock(DyeColor.CYAN));
        public static final Supplier<Block> LIGHT_BLUE_HAMSTER_BOWL = registerBlockItem("light_blue_hamster_bowl",
                        () -> hamsterBowlBlock(DyeColor.LIGHT_BLUE));
        public static final Supplier<Block> BLUE_HAMSTER_BOWL = registerBlockItem("blue_hamster_bowl",
                        () -> hamsterBowlBlock(DyeColor.BLUE));
        public static final Supplier<Block> PURPLE_HAMSTER_BOWL = registerBlockItem("purple_hamster_bowl",
                        () -> hamsterBowlBlock(DyeColor.PURPLE));
        public static final Supplier<Block> MAGENTA_HAMSTER_BOWL = registerBlockItem("magenta_hamster_bowl",
                        () -> hamsterBowlBlock(DyeColor.MAGENTA));
        public static final Supplier<Block> PINK_HAMSTER_BOWL = registerBlockItem("pink_hamster_bowl",
                        () -> hamsterBowlBlock(DyeColor.PINK));
        public static final List<Supplier<Block>> HAMSTER_BOWLS = Arrays.asList(
                        WHITE_HAMSTER_BOWL, LIGHT_GRAY_HAMSTER_BOWL, GRAY_HAMSTER_BOWL, BLACK_HAMSTER_BOWL,
                        BROWN_HAMSTER_BOWL, RED_HAMSTER_BOWL, ORANGE_HAMSTER_BOWL, YELLOW_HAMSTER_BOWL,
                        LIME_HAMSTER_BOWL, GREEN_HAMSTER_BOWL, CYAN_HAMSTER_BOWL, LIGHT_BLUE_HAMSTER_BOWL,
                        BLUE_HAMSTER_BOWL, PURPLE_HAMSTER_BOWL, MAGENTA_HAMSTER_BOWL, PINK_HAMSTER_BOWL);

        // cage panels
        public static final Supplier<Block> CAGE_PANEL = registerBlockItem("cage_panel",
                        () -> cagePanelBlock(DyeColor.WHITE));
        public static final Supplier<Block> WHITE_CAGE_PANEL = registerBlockItem("white_cage_panel",
                        () -> cagePanelBlock(DyeColor.WHITE));
        public static final Supplier<Block> LIGHT_GRAY_CAGE_PANEL = registerBlockItem("light_gray_cage_panel",
                        () -> cagePanelBlock(DyeColor.LIGHT_GRAY));
        public static final Supplier<Block> GRAY_CAGE_PANEL = registerBlockItem("gray_cage_panel",
                        () -> cagePanelBlock(DyeColor.GRAY));
        public static final Supplier<Block> BLACK_CAGE_PANEL = registerBlockItem("black_cage_panel",
                        () -> cagePanelBlock(DyeColor.BLACK));
        public static final Supplier<Block> BROWN_CAGE_PANEL = registerBlockItem("brown_cage_panel",
                        () -> cagePanelBlock(DyeColor.BROWN));
        public static final Supplier<Block> RED_CAGE_PANEL = registerBlockItem("red_cage_panel",
                        () -> cagePanelBlock(DyeColor.RED));
        public static final Supplier<Block> ORANGE_CAGE_PANEL = registerBlockItem("orange_cage_panel",
                        () -> cagePanelBlock(DyeColor.ORANGE));
        public static final Supplier<Block> YELLOW_CAGE_PANEL = registerBlockItem("yellow_cage_panel",
                        () -> cagePanelBlock(DyeColor.YELLOW));
        public static final Supplier<Block> LIME_CAGE_PANEL = registerBlockItem("lime_cage_panel",
                        () -> cagePanelBlock(DyeColor.LIME));
        public static final Supplier<Block> GREEN_CAGE_PANEL = registerBlockItem("green_cage_panel",
                        () -> cagePanelBlock(DyeColor.GREEN));
        public static final Supplier<Block> CYAN_CAGE_PANEL = registerBlockItem("cyan_cage_panel",
                        () -> cagePanelBlock(DyeColor.CYAN));
        public static final Supplier<Block> LIGHT_BLUE_CAGE_PANEL = registerBlockItem("light_blue_cage_panel",
                        () -> cagePanelBlock(DyeColor.LIGHT_BLUE));
        public static final Supplier<Block> BLUE_CAGE_PANEL = registerBlockItem("blue_cage_panel",
                        () -> cagePanelBlock(DyeColor.BLUE));
        public static final Supplier<Block> PURPLE_CAGE_PANEL = registerBlockItem("purple_cage_panel",
                        () -> cagePanelBlock(DyeColor.PURPLE));
        public static final Supplier<Block> MAGENTA_CAGE_PANEL = registerBlockItem("magenta_cage_panel",
                        () -> cagePanelBlock(DyeColor.MAGENTA));
        public static final Supplier<Block> PINK_CAGE_PANEL = registerBlockItem("pink_cage_panel",
                        () -> cagePanelBlock(DyeColor.PINK));
        public static final List<Supplier<Block>> CAGE_PANELS = Arrays.asList(CAGE_PANEL, WHITE_CAGE_PANEL,
                        LIGHT_GRAY_CAGE_PANEL, GRAY_CAGE_PANEL, BLACK_CAGE_PANEL, BROWN_CAGE_PANEL, RED_CAGE_PANEL,
                        ORANGE_CAGE_PANEL, YELLOW_CAGE_PANEL, LIME_CAGE_PANEL, GREEN_CAGE_PANEL, CYAN_CAGE_PANEL,
                        LIGHT_BLUE_CAGE_PANEL, BLUE_CAGE_PANEL, PURPLE_CAGE_PANEL, MAGENTA_CAGE_PANEL, PINK_CAGE_PANEL);

        public static final Supplier<Block> HAMSTER_BED = BLOCKS.register("hamster_bed",
                        () -> new HamsterBedBlock(BlockBehaviour.Properties.of().strength(0.2F).noOcclusion()));

        // public static final Block TUNNEL = register("tunnel", new
        // TunnelBlock(FabricBlockSettings.copyOf(Blocks.GREEN_STAINED_GLASS)));

}
