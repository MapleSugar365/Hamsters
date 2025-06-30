package com.starfish_studios.hamsters.event;

import com.starfish_studios.hamsters.client.renderer.HamsterRenderer;
import com.starfish_studios.hamsters.client.renderer.HamsterWheelRenderer;
import com.starfish_studios.hamsters.client.renderer.SeatRenderer;
import com.starfish_studios.hamsters.registry.HamstersBlockEntities;
import com.starfish_studios.hamsters.registry.HamstersEntityType;
import com.starfish_studios.hamsters.registry.HamstersItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        ResourceLocation variantId = ResourceLocation.parse("variant");

        ItemProperties.register(
                HamstersItems.HAMSTER.get(),
                variantId,
                (stack, world, entity, num) -> {
                    CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
                    if (customData != null) {
                        CompoundTag tag = customData.copyTag();
                        if (tag.contains("Variant")) {
                            return tag.getInt("Variant") / 7.0F;
                        }
                    }
                    return 0.333F;
                });
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(HamstersBlockEntities.HAMSTER_WHEEL.get(),
                context -> new HamsterWheelRenderer());
        event.registerEntityRenderer(HamstersEntityType.HAMSTER.get(), HamsterRenderer::new);
        event.registerEntityRenderer(HamstersEntityType.SEAT.get(), SeatRenderer::new);
    }

}
