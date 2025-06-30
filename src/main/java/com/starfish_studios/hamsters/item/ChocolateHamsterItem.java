package com.starfish_studios.hamsters.item;

import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ChocolateHamsterItem extends Item {

    public ChocolateHamsterItem(Properties properties) {
        super(properties);
    }

    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        ItemStack original = super.finishUsingItem(itemStack, level, livingEntity);
        ExperienceOrb experienceOrb = new ExperienceOrb(level, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 160);
        level.addFreshEntity(experienceOrb);
        return original;
    }
}