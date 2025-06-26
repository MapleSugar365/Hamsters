package com.starfish_studios.hamsters.entity.util;

import com.starfish_studios.hamsters.block.HamsterWheelBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.phys.Vec3;

public interface RideableHamsterEntity {

    default Vec3 findDismountLocation(Entity self, Entity ridingEntity, Vec3 defaultReturnValue) {

        BlockPos blockPos = self.blockPosition();
        Vec3 safeVec;

        if (self.level().getBlockState(blockPos).getBlock() instanceof HamsterWheelBlock) {
            safeVec = DismountHelper.findSafeDismountLocation(ridingEntity.getType(), self.level(), blockPos, false);
            if (safeVec != null) return safeVec.add(0, 0.25, 0);
        }

        Direction original = self.getDirection();
        Direction[] offsets = {original, original.getClockWise(), original.getCounterClockWise(), original.getOpposite()};

        for (Direction direction : offsets) {
            safeVec = DismountHelper.findSafeDismountLocation(ridingEntity.getType(), self.level(), blockPos.relative(direction), false);
            if (safeVec != null) return safeVec.add(0, 0.25, 0);
        }

        return defaultReturnValue;
    }
}