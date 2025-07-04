package com.starfish_studios.hamsters.block.entity;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.starfish_studios.hamsters.block.HamsterWheelBlock;
import com.starfish_studios.hamsters.registry.HamstersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class HamsterWheelBlockEntity extends GeneratingKineticBlockEntity implements GeoBlockEntity {

    private static final RawAnimation SPIN = RawAnimation.begin().thenLoop("animation.sf_hba.hamster_wheel.spin");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public HamsterWheelBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(HamstersBlockEntities.HAMSTER_WHEEL.get(), blockPos, blockState);
    }

    // region Ticking

    @Override
    public void tick() {
        super.tick();
        if (this.level == null)
            return;
        if (HamsterWheelBlock.isOccupied(this.level, this.getBlockPos())) {
            this.updateGeneratedRotation();
        } else if (this.getGeneratedSpeed() == 0) {
            this.updateGeneratedRotation();
        }
    }

    @Override
    public float getGeneratedSpeed() {
        if (this.level != null && this.getBlockState().getBlock() instanceof HamsterWheelBlock
                && HamsterWheelBlock.isOccupied(this.level, this.getBlockPos())) {
            Direction facing = this.getBlockState().getValue(HamsterWheelBlock.FACING);
            if (facing == Direction.SOUTH || facing == Direction.EAST)
                return -32;
            return 32;
        }
        return 0;
    }

    @Override
    public float calculateAddedStressCapacity() {
        return getGeneratedSpeed() != 0 ? 8.0f : 0;
    }

    // endregion

    // region GeckoLib

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::controller));
    }

    private <E extends HamsterWheelBlockEntity> PlayState controller(final AnimationState<E> event) {
        if (this.level != null && HamsterWheelBlock.isOccupied(this.level, this.getBlockPos())) {
            event.getController().setAnimation(SPIN);
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    // endregion
}