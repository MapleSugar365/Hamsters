package com.starfish_studios.hamsters.entity;

import com.starfish_studios.hamsters.block.HamsterWheelBlock;
import com.starfish_studios.hamsters.entity.util.RideableHamsterEntity;
import com.starfish_studios.hamsters.registry.HamstersEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SeatEntity extends Entity implements RideableHamsterEntity {

    // region Initialization

    public SeatEntity(Level level) {
        super(HamstersEntityType.SEAT.get(), level);
        this.noPhysics = true;
    }

    public SeatEntity(Level level, BlockPos blockPos) {
        this(level);
        this.setPos(blockPos.getX() + 0.5D, blockPos.getY() + 0.01D, blockPos.getZ() + 0.5D);
    }

    public SeatEntity(EntityType<SeatEntity> entityType, Level level) {
        super(entityType, level);
    }

    // endregion

    // region Entity Data

    @Override
    protected void defineSynchedData(@NotNull SynchedEntityData.@NotNull Builder builder) {
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
    }

    // endregion

    // region Ticking

    @Override
    public void tick() {
        if (this.level().isClientSide())
            return;
        if (this.isVehicle() && this.level().getBlockState(this.blockPosition()).getBlock() instanceof HamsterWheelBlock)
            return;
        this.discard();
        this.level().updateNeighbourForOutputSignal(this.blockPosition(),
                this.level().getBlockState(this.blockPosition()).getBlock());
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float partialTick) {
        return new Vec3(0, 0.15, 0);
    }

    @Override
    public @NotNull Vec3 getDismountLocationForPassenger(@NotNull LivingEntity ridingEntity) {
        return this.findDismountLocation(this, ridingEntity, super.getDismountLocationForPassenger(ridingEntity));
    }

    @Override
    protected void addPassenger(@NotNull Entity passenger) {
        super.addPassenger(passenger);
    }

    // endregion

    // region Miscellaneous

    @Override
    protected boolean canRide(@NotNull Entity entity) {
        return true;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
        return new ClientboundAddEntityPacket(this, entity);
    }

    // endregion
}