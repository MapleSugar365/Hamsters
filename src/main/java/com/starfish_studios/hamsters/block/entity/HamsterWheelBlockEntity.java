package com.starfish_studios.hamsters.block.entity;

import com.starfish_studios.hamsters.block.HamsterWheelBlock;
import com.starfish_studios.hamsters.registry.HamstersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class HamsterWheelBlockEntity extends BlockEntity implements GeoBlockEntity {

    private static final RawAnimation SPIN = RawAnimation.begin().thenLoop("animation.sf_hba.hamster_wheel.spin");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public HamsterWheelBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(HamstersBlockEntities.HAMSTER_WHEEL.get(), blockPos, blockState);
    }

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