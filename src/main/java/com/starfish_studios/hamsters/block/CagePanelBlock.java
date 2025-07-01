package com.starfish_studios.hamsters.block;

import com.starfish_studios.hamsters.block.util.CageType;
import com.starfish_studios.hamsters.block.util.HamsterBlock;
import com.starfish_studios.hamsters.registry.HamstersTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CagePanelBlock extends Block implements HamsterBlock, SimpleWaterloggedBlock {

    private static final EnumProperty<CageType> TYPE = EnumProperty.create("type", CageType.class);
    private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public CagePanelBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(TYPE, CageType.NONE)
                .setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    // region Block State Initialization

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, FACING, WATERLOGGED);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, @NotNull BlockGetter blockGetter,
            @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
        return switch (blockState.getValue(FACING)) {
            case SOUTH -> Block.box(0, 0, 0, 16, 16, 1);
            case EAST -> Block.box(0, 0, 0, 1, 16, 16);
            case WEST -> Block.box(15, 0, 0, 16, 16, 16);
            default -> Block.box(0, 0, 15, 16, 16, 16);
        };
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState blockState, @NotNull BlockGetter blockGetter,
            @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
        return switch (blockState.getValue(FACING)) {
            case SOUTH -> Block.box(0, 0, 0, 16, 24, 1);
            case EAST -> Block.box(0, 0, 0, 1, 24, 16);
            case WEST -> Block.box(15, 0, 0, 16, 24, 16);
            default -> Block.box(0, 0, 15, 16, 24, 16);
        };
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState blockState, @NotNull Direction direction,
            @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos,
            @NotNull BlockPos neighborPos) {
        if (HamsterBlock.isWaterlogged(blockState))
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        return super.updateShape(blockState, direction, neighborState, level, currentPos, neighborPos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();

        BlockState blockState = this.defaultBlockState().setValue(FACING,
                context.getHorizontalDirection().getOpposite());
        blockState = blockState.setValue(TYPE, this.getType(blockState, this.getRelativeTop(level, blockPos),
                this.getRelativeBottom(level, blockPos)));

        return blockState.setValue(WATERLOGGED,
                context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    public @NotNull FluidState getFluidState(@NotNull BlockState blockState) {
        if (HamsterBlock.isWaterlogged(blockState))
            return Fluids.WATER.getSource(false);
        return super.getFluidState(blockState);
    }

    @Override
    public void neighborChanged(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos,
            @NotNull Block block, @NotNull BlockPos fromPos, boolean isMoving) {
        if (level.isClientSide())
            return;
        CageType cageType = this.getType(blockState, this.getRelativeTop(level, blockPos),
                this.getRelativeBottom(level, blockPos));
        if (blockState.getValue(TYPE) == cageType)
            return;
        blockState = blockState.setValue(TYPE, cageType);
        level.setBlockAndUpdate(blockPos, blockState);
    }

    // endregion

    // region Interaction

    @SuppressWarnings("deprecation")
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockPos abovePos = pos.above();
        if (stack.is(HamstersTags.CAGE_PANEL_ITEMS) && level.getBlockState(abovePos).is(BlockTags.REPLACEABLE)) {
            if (!(stack.getItem() instanceof BlockItem blockItem)) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
            BlockState newState = blockItem.getBlock().defaultBlockState()
                    .setValue(FACING, state.getValue(FACING))
                    .setValue(WATERLOGGED, level.getFluidState(abovePos).is(FluidTags.WATER));
            level.setBlockAndUpdate(abovePos, newState);
            SoundType soundType = newState.getSoundType();
            level.playSound(null, pos, soundType.getPlaceSound(), SoundSource.BLOCKS,
                    (soundType.getVolume() + 1.0F) / 2.0F,
                    soundType.getPitch() * 0.8F);
            level.gameEvent(player, GameEvent.BLOCK_PLACE, abovePos);
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    // endregion

    // region Miscellaneous

    @Override
    public boolean skipRendering(@NotNull BlockState blockState, BlockState neighborState,
            @NotNull Direction direction) {
        return neighborState.getBlock() instanceof CagePanelBlock
                && neighborState.getValue(FACING) == blockState.getValue(FACING);
    }

    @Override
    public float getShadeBrightness(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter,
            @NotNull BlockPos blockPos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter,
            @NotNull BlockPos blockPos) {
        return true;
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    private BlockState getRelativeTop(Level level, BlockPos blockPos) {
        return level.getBlockState(blockPos.above());
    }

    private BlockState getRelativeBottom(Level level, BlockPos blockPos) {
        return level.getBlockState(blockPos.below());
    }

    private CageType getType(BlockState blockState, BlockState aboveState, BlockState belowState) {

        boolean shape_above_same = aboveState.getBlock() instanceof CagePanelBlock
                && blockState.getValue(FACING) == aboveState.getValue(FACING);
        boolean shape_below_same = belowState.getBlock() instanceof CagePanelBlock
                && blockState.getValue(FACING) == belowState.getValue(FACING);

        if (shape_above_same && !shape_below_same)
            return CageType.BOTTOM;
        else if (!shape_above_same && shape_below_same)
            return CageType.TOP;
        else if (shape_above_same)
            return CageType.MIDDLE;
        return CageType.NONE;
    }

    // endregion
}