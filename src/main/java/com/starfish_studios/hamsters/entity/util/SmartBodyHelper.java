package com.starfish_studios.hamsters.entity.util;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class SmartBodyHelper extends BodyRotationControl {

    // Credit to Mowzie's Mobs

    private static final float MAX_ROTATE = 75;
    private static final int HISTORY_SIZE = 10;
    private final Mob entity;
    private int rotateTime;
    private float targetYawHead;
    private final double[] histPosX = new double[HISTORY_SIZE];
    private final double[] histPosZ = new double[HISTORY_SIZE];

    public SmartBodyHelper(Mob entity) {
        super(entity);
        this.entity = entity;
    }

    @Override
    public void clientTick() {

        for (int i = this.histPosX.length - 1; i > 0; i--) {
            this.histPosX[i] = this.histPosX[i - 1];
            this.histPosZ[i] = this.histPosZ[i - 1];
        }

        this.histPosX[0] = this.entity.getX();
        this.histPosZ[0] = this.entity.getZ();

        double dx = delta(this.histPosX);
        double dz = delta(this.histPosZ);
        double distSq = dx * dx + dz * dz;

        if (distSq > 2.5e-7) {

            double moveAngle = (float) Mth.atan2(dz, dx) * (180 / (float) Math.PI) - 90;
            this.entity.yBodyRot += (float) (Mth.wrapDegrees(moveAngle - this.entity.yBodyRot) * 0.6F);
            this.targetYawHead = this.entity.yHeadRot;
            this.rotateTime = 0;

        } else if (this.entity.getPassengers().isEmpty() || !(this.entity.getPassengers().get(0) instanceof Mob)) {

            float limit = MAX_ROTATE;

            if (Math.abs(this.entity.yHeadRot - this.targetYawHead) > 15) {
                this.rotateTime = 0;
                this.targetYawHead = entity.yHeadRot;
            } else {
                this.rotateTime++;
                final int speed = 10;
                if (this.rotateTime > speed) limit = Math.max(1 - (this.rotateTime - speed) / (float) speed, 0) * MAX_ROTATE;
            }

            this.entity.yBodyRot = approach(this.entity.yHeadRot, this.entity.yBodyRot, limit);
        }
    }

    private double delta(double[] arr) {
        return mean(arr, 0) - mean(arr, HISTORY_SIZE / 2);
    }

    private double mean(double[] arr, int start) {
        double mean = 0;
        for (int i = 0; i < HISTORY_SIZE / 2; i++) mean += arr[i + start];
        return mean / arr.length;
    }

    public static float approach(float target, float current, float limit) {
        float delta = Mth.wrapDegrees(current - target);
        if (delta < -limit) delta = -limit;
        else if (delta >= limit) delta = limit;
        return target + delta * 0.55F;
    }
}