package com.starfish_studios.hamsters.item;

import com.starfish_studios.hamsters.entity.Hamster;
import com.starfish_studios.hamsters.registry.HamstersEntityType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class HamsterItem extends Item {

    public HamsterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        BlockPlaceContext blockPlaceContext = new BlockPlaceContext(useOnContext);
        BlockPos pos = blockPlaceContext.getClickedPos();
        ItemStack stack = useOnContext.getItemInHand();

        Hamster hamster = HamstersEntityType.HAMSTER.get().create(useOnContext.getLevel());
        if (stack.has(DataComponents.CUSTOM_NAME))
            hamster.setCustomName(stack.getHoverName());

        if (stack.has(DataComponents.CUSTOM_DATA))
            hamster.load(stack.getComponents().get(DataComponents.CUSTOM_DATA).copyTag());

        hamster.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                Objects.requireNonNull(useOnContext.getPlayer()).getYRot(), 0.0f);

        hamster.playSound(SoundEvents.CHICKEN_EGG);
        useOnContext.getLevel().addFreshEntity(hamster);
        useOnContext.getPlayer().setItemInHand(useOnContext.getHand(), ItemStack.EMPTY);
        return InteractionResult.SUCCESS;
    }

    // @Override
    // public void appendHoverText(ItemStack itemStack, TooltipContext tc,
    // List<Component> list, TooltipFlag tooltipFlag) {
    // CompoundTag compoundTag;

    // if ((compoundTag = itemStack.getTag()) != null &&
    // compoundTag.contains("Variant", 3)) {
    // int i = compoundTag.getInt("Variant");
    // list.add(Component.translatable("tooltip.hamsters." +
    // Hamster.Variant.getTypeById(i).getName())
    // .withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    // }
    // if ((compoundTag = itemStack.getTag()) != null && compoundTag.getInt("Age") <
    // 0) {
    // list.add(Component.translatable("tooltip.hamsters.baby").withStyle(ChatFormatting.ITALIC,
    // ChatFormatting.BLUE));
    // }
    // if ((compoundTag = itemStack.getTag()) != null &&
    // compoundTag.hasUUID("Owner")) {
    // list.add(Component.translatable("tooltip.hamsters.tamed").withStyle(ChatFormatting.ITALIC,
    // ChatFormatting.BLUE));
    // }
    // }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable TooltipContext tooltipContext, List<Component> list,
            TooltipFlag tooltipFlag) {
        CompoundTag customData = itemStack.getComponents().get(DataComponents.CUSTOM_DATA).copyTag();

        if (customData != null) {
            if (customData.contains("Variant", CompoundTag.TAG_INT)) {
                int i = customData.getInt("Variant");
                list.add(Component.translatable("tooltip.hamsters." + Hamster.Variant.getTypeById(i).getName())
                        .withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
            }

            if (customData.contains("Age", CompoundTag.TAG_INT) && customData.getInt("Age") < 0) {
                list.add(Component.translatable("tooltip.hamsters.baby")
                        .withStyle(ChatFormatting.ITALIC, ChatFormatting.BLUE));
            }

            if (customData.hasUUID("Owner")) {
                list.add(Component.translatable("tooltip.hamsters.tamed")
                        .withStyle(ChatFormatting.ITALIC, ChatFormatting.BLUE));
            }
        }
    }
}
