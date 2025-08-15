package com.starfish_studios.hamsters.block;

import com.mojang.serialization.MapCodec;
import com.starfish_studios.hamsters.block.util.HamsterBlock;
import com.starfish_studios.hamsters.entity.Hamster;
import com.starfish_studios.hamsters.entity.SeatEntity;
import com.starfish_studios.hamsters.registry.HamstersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.Optional;

public class HamsterWheelBlock extends BaseEntityBlock implements HamsterBlock {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public HamsterWheelBlock(Properties properties) {
        super(properties);
        registerDefaultState(
                this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    // region Block State Initialization

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos,
            @NotNull CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case SOUTH -> Block.box(1, 0, 0, 15, 16, 13);
            case EAST -> Block.box(0, 0, 1, 13, 16, 15);
            case WEST -> Block.box(3, 0, 1, 16, 16, 15);
            default -> Block.box(1, 0, 3, 15, 16, 16);
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

    // region Interaction

    public static boolean isOccupied(Level level, BlockPos blockPos) {
        return !level.getEntitiesOfClass(SeatEntity.class, new AABB(blockPos)).isEmpty();
    }

    public float setRiderRotation(BlockState state) {
        Direction facing = state.getValue(FACING);
        return facing.getCounterClockWise().toYRot();
    }

    public static Optional<Entity> getLeashed(Player player) {
        List<Entity> nearbyEntities = player.level().getEntitiesOfClass(Entity.class,
                player.getBoundingBox().inflate(10), EntitySelector.LIVING_ENTITY_STILL_ALIVE);
        for (Entity entities : nearbyEntities)
            if (entities instanceof Mob mob && mob.getLeashHolder() == player && canBePickedUp(entities))
                return Optional.of(mob);
        return Optional.empty();
    }

    public static boolean canBePickedUp(Entity passenger) {
        if (passenger instanceof Player)
            return false;
        return passenger instanceof LivingEntity;
    }

    public static boolean ejectSeatedExceptPlayer(Level level, SeatEntity seatEntity) {
        if (seatEntity.getPassengers().isEmpty())
            return false;
        if (!level.isClientSide())
            seatEntity.ejectPassengers();
        return true;
    }

    public static void sitDown(Level level, BlockPos blockPos, Entity entity) {
        if (level.isClientSide() || entity == null)
            return;
        List<SeatEntity> existingSeats = level.getEntitiesOfClass(SeatEntity.class, new AABB(blockPos));
        if (!existingSeats.isEmpty()) {
            SeatEntity seat = existingSeats.get(0);
            if (seat.isVehicle()) {
                return;
            }
        }
        SeatEntity seatEntity = new SeatEntity(level, blockPos);
        level.addFreshEntity(seatEntity);
        entity.startRiding(seatEntity);
        level.updateNeighbourForOutputSignal(blockPos, level.getBlockState(blockPos).getBlock());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player,
            BlockHitResult hitResult) {
        InteractionResult original = super.useWithoutItem(state, level, pos, player, hitResult);
        if (level.mayInteract(player, pos) && player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() &&
                !player.isShiftKeyDown() && !player.isPassenger()) {
            if (isOccupied(level, pos)) {
                List<SeatEntity> seatEntities = level.getEntitiesOfClass(SeatEntity.class, new AABB(pos));
                if (!seatEntities.isEmpty() && ejectSeatedExceptPlayer(level, seatEntities.get(0))) {
                    return InteractionResult.SUCCESS;
                }
                return original;
            }
            if (getLeashed(player).isPresent() && getLeashed(player).get() instanceof Hamster hamster) {
                sitDown(level, pos, hamster);
                return InteractionResult.SUCCESS;
            }
        }
        return original;
    }

    // endregion

    // region Miscellaneous

    @Override
    public boolean hasAnalogOutputSignal(@NotNull BlockState blockState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos) {
        return isOccupied(level, blockPos) ? 15 : 0;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return HamstersBlockEntities.HAMSTER_WHEEL.get().create(blockPos, blockState);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState blockState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(HamsterWheelBlock::new);
    }

    // endregion
}