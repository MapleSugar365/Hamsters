package com.starfish_studios.hamsters.entity.util;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class SleepGoal<E extends PathfinderMob & SleepingAnimal> extends Goal {

    public final E mob;

    public SleepGoal(E mob) {
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
        this.mob = mob;
    }

    @Override
    public void start() {
        // After the goal is activated, makes the mob stop jumping, start sleeping, and
        // stop moving.
        this.mob.setJumping(false);
        this.mob.setSleeping(true);
        this.mob.getNavigation().stop();
        this.mob.getMoveControl().setWantedPosition(this.mob.getX(), this.mob.getY(), this.mob.getZ(), 0.0D);
    }

    @Override
    public boolean canUse() {
        // If the mob is not moving and can sleep or is already sleeping, return true
        if (this.mob.xxa == 0.0F && this.mob.yya == 0.0F && this.mob.zza == 0.0F)
            return this.mob.canSleep() || this.mob.isSleeping();
        else
            return false;
    }

    @Override
    public boolean canContinueToUse() {
        // Allows the mob to continue sleeping if it can sleep (i.e., if it is not interrupted)
        return this.mob.canSleep();
    }

    @Override
    public void stop() {
        this.mob.setSleeping(false);
        this.mob.getNavigation().stop();
        super.stop();
    }
}