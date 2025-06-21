package com.starfish_studios.hamsters.registry;

import java.util.function.Supplier;

import com.starfish_studios.hamsters.Hamsters;
import com.starfish_studios.hamsters.block.HamsterWheelBlock;
import com.starfish_studios.hamsters.block.HamsterBedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HamstersBlocks {

        // public static final Block TUNNEL = register("tunnel", new
        // TunnelBlock(FabricBlockSettings.copyOf(Blocks.GREEN_STAINED_GLASS)));
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(Hamsters.MOD_ID);

        public static final Supplier<Block> HAMSTER_WHEEL = BLOCKS.register("hamster_wheel",
                        () -> new HamsterWheelBlock(BlockBehaviour.Properties.of().strength(0.6F).noOcclusion()
                                        .isSuffocating((state, world, pos) -> false)));

        public static final Supplier<Block> HAMSTER_BED = BLOCKS.register("hamster_bed",
                        () -> new HamsterBedBlock(BlockBehaviour.Properties.of().strength(0.2F).noOcclusion()));

}
