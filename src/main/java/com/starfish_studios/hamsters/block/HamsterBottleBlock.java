package com.starfish_studios.hamsters.block;

import com.mojang.serialization.MapCodec;
import com.starfish_studios.hamsters.block.util.HamsterBlock;
import com.starfish_studios.hamsters.registry.HamstersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HamsterBottleBlock extends BaseEntityBlock implements HamsterBlock, SimpleWaterloggedBlock {

    private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public HamsterBottleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    // region Block State Initialization

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, @NotNull BlockGetter blockGetter,
            @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
        return switch (blockState.getValue(FACING)) {
            case SOUTH -> Block.box(5, 2, 0, 11, 16, 8);
            case EAST -> Block.box(0, 2, 5, 8, 16, 11);
            case WEST -> Block.box(8, 2, 5, 16, 16, 11);
            default -> Block.box(5, 2, 8, 11, 16, 16);
        };
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState blockState, @NotNull Direction direction,
            @NotNull BlockState neighborState, @NotNull LevelAccessor levelAccessor, @NotNull BlockPos currentPos,
            @NotNull BlockPos neighborPos) {
        if (HamsterBlock.isWaterlogged(blockState))
            levelAccessor.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        return super.updateShape(blockState, direction, neighborState, levelAccessor, currentPos, neighborPos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.getStateDefinition().any()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED,
                        context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    public @NotNull FluidState getFluidState(@NotNull BlockState blockState) {
        if (HamsterBlock.isWaterlogged(blockState))
            return Fluids.WATER.getSource(false);
        return super.getFluidState(blockState);
    }

    // endregion

    // region Ticking

    @Override
    public void animateTick(@NotNull BlockState blockState, Level level, BlockPos blockPos,
            @NotNull RandomSource randomSource) {

        if (level.getBlockState(blockPos.below()).is(BlockTags.REPLACEABLE)) {
            for (int particleAmount = 0; particleAmount < 1; ++particleAmount) {
                double x = (double) blockPos.getX() + 0.6 + (level.getRandom().nextDouble() * 0.25D - 0.25D);
                double y = (double) blockPos.getY() + (level.getRandom().nextDouble() * 0.2D - 0.1D);
                double z = (double) blockPos.getZ() + 0.6 + (level.getRandom().nextDouble() * 0.25D - 0.25D);
                level.addParticle(ParticleTypes.FALLING_WATER, x, y, z, 0.0D, 0.0D, 0.0D);
            }
        }

        super.animateTick(blockState, level, blockPos, randomSource);
    }

    // endregion

    // region Miscellaneous

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean propagatesSkylightDown(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter,
            @NotNull BlockPos blockPos) {
        return true;
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState blockState, @NotNull PathComputationType pathComputationType) {
        return false;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return HamstersBlockEntities.HAMSTER_BOTTLE.get().create(blockPos, blockState);
    }

    // endregion

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(HamsterBottleBlock::new);
    }
}