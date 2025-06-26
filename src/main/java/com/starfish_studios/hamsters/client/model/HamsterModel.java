package com.starfish_studios.hamsters.client.model;

import com.starfish_studios.hamsters.Hamsters;
import com.starfish_studios.hamsters.entity.Hamster;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

import static com.starfish_studios.hamsters.HamstersConfig.*;

@OnlyIn(Dist.CLIENT)
public class HamsterModel extends DefaultedEntityGeoModel<Hamster> {

    public HamsterModel() {
        super(ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, "hamster"), true);
    }

    @Override
    public ResourceLocation getModelResource(Hamster hamster) {
        // return hamster.isBaby() ? ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, "geo/entity/pinkie.geo.json") : ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, "geo/entity/hamster.geo.json");
        return ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, "geo/entity/hamster.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Hamster hamster) {

        // if (hamster.isBaby()) return ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, "textures/entity/hamster/pinkie.png");

        return switch (hamster.getVariant()) {
            case 0 -> ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, "textures/entity/hamster/white.png");
            case 1 -> ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, "textures/entity/hamster/cream.png");
            case 2 -> ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, "textures/entity/hamster/champagne.png");
            case 3 -> ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, "textures/entity/hamster/silver_dove.png");
            case 4 -> ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, "textures/entity/hamster/dove.png");
            case 5 -> ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, "textures/entity/hamster/chocolate.png");
            case 6 -> ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, "textures/entity/hamster/black.png");
            default -> throw new IllegalStateException("Unexpected value: " + hamster.getVariant());
        };
    }

    @Override
    public ResourceLocation getAnimationResource(Hamster hamster) {
        return ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, "animations/hamster.rp_anim.json");
    }

    @Override
    public void setCustomAnimations(Hamster hamster, long instanceId, AnimationState<Hamster> animationState) {

        super.setCustomAnimations(hamster, instanceId, animationState);
        if (animationState == null) return;

        GeoBone root = this.getAnimationProcessor().getBone("root");
        GeoBone head = this.getAnimationProcessor().getBone("head");
        GeoBone sleep = this.getAnimationProcessor().getBone("sleep");
        GeoBone cheeks = this.getAnimationProcessor().getBone("cheeks");
        GeoBone leftCheek = this.getAnimationProcessor().getBone("left_cheek");
        GeoBone rightCheek = this.getAnimationProcessor().getBone("right_cheek");

        cheeks.setHidden(hamster.getMainHandItem().isEmpty());
        float cheekDefaultScale = 1.0F;

        if (hamster.getCheekLevel() > 0) {

            cheeks.setHidden(false);
            float cheekScale = cheekDefaultScale + (hamster.getCheekLevel() * 0.2F);

            leftCheek.setScaleX(cheekScale);
            leftCheek.setScaleY(cheekScale);
            leftCheek.setScaleZ(cheekScale);

            rightCheek.setScaleX(cheekScale);
            rightCheek.setScaleY(cheekScale);
            rightCheek.setScaleZ(cheekScale);

        } else {
            cheeks.setScaleX(cheekDefaultScale);
            cheeks.setScaleY(cheekDefaultScale);
            cheeks.setScaleZ(cheekDefaultScale);
        }

        if (hamstersBurst && hamster.getCheekLevel() > 1) root.setRotZ((float) Math.sin(System.currentTimeMillis() * 0.05D) * 0.1F * (hamster.getCheekLevel() * 0.05F));

        // Ensures there are no strange eye glitches when the hamster is sleeping or awake.

        if (!hamster.isBaby()) sleep.setHidden(!hamster.isSleeping());

        float headScale = hamster.isBaby() ? 1.4F : 1.0F;
        if (hamster.isBaby()) head.setPosY(0.0F);

        head.setScaleX(headScale);
        head.setScaleY(headScale);
        head.setScaleZ(headScale);
    }
}