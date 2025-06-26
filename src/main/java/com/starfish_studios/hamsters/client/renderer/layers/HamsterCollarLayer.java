package com.starfish_studios.hamsters.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.starfish_studios.hamsters.Hamsters;
import com.starfish_studios.hamsters.entity.Hamster;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@OnlyIn(Dist.CLIENT)
public class HamsterCollarLayer extends GeoRenderLayer<Hamster> {

        public HamsterCollarLayer(GeoRenderer<Hamster> geoRenderer) {
                super(geoRenderer);
        }

        @Override
        public void render(PoseStack poseStack, Hamster hamster, BakedGeoModel bakedModel, RenderType renderType,
                        MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight,
                        int packedOverlay) {

                if (hamster.isTame() && !hamster.isInvisible()) {

                        RenderType collarRenderer = RenderType.entityCutout(
                                        ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID,
                                                        "textures/entity/hamster/collar.png"));
                        RenderType collarTagRenderer = RenderType.entityCutout(
                                        ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID,
                                                        "textures/entity/hamster/collar_tag.png"));
                        int color = hamster.getCollarColor().getTextureDiffuseColor();

                        this.getRenderer().reRender(this.getDefaultBakedModel(hamster), poseStack, bufferSource,
                                        hamster,
                                        collarRenderer,
                                        bufferSource.getBuffer(collarRenderer), partialTick, packedLight, packedOverlay,
                                        color);

                        this.getRenderer().reRender(this.getDefaultBakedModel(hamster), poseStack, bufferSource,
                                        hamster,
                                        collarTagRenderer,
                                        bufferSource.getBuffer(collarTagRenderer), partialTick, packedLight,
                                        packedOverlay,
                                        0xFFFFFFFF);
                }

                super.render(poseStack, hamster, bakedModel, renderType, bufferSource, buffer, partialTick, packedLight,
                                packedOverlay);
        }
}