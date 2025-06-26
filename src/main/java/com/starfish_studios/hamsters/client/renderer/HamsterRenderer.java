package com.starfish_studios.hamsters.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.starfish_studios.hamsters.client.model.HamsterModel;
import com.starfish_studios.hamsters.client.renderer.layers.HamsterCollarLayer;
import com.starfish_studios.hamsters.client.renderer.layers.HamsterMarkingLayer;
import com.starfish_studios.hamsters.entity.Hamster;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class HamsterRenderer extends GeoEntityRenderer<Hamster> {
    public final EntityRendererProvider.Context context;

    public HamsterRenderer(EntityRendererProvider.Context context) {

        super(context, new HamsterModel());
        this.shadowRadius = 0.3F;
        this.context = context;
        this.addRenderLayer(new HamsterMarkingLayer(this));
        this.addRenderLayer(new HamsterCollarLayer(this));
    }

    @Override
    public float getMotionAnimThreshold(Hamster animatable) {
        return 0.001F;
    }

    @Override
    public void render(Hamster hamster, float yaw, float partialTick, @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource bufferSource, int packedLight) {

        float adultScale = 1.0F;
        float babyScale = 0.6F;

        if (hamster.isBaby())
            poseStack.scale(babyScale, babyScale, babyScale);
        else
            poseStack.scale(adultScale, adultScale, adultScale);

        super.render(hamster, yaw, partialTick, poseStack, bufferSource, packedLight);
    }
}