package com.starfish_studios.hamsters.client.renderer;

import com.starfish_studios.hamsters.block.entity.HamsterWheelBlockEntity;
import com.starfish_studios.hamsters.client.model.HamsterWheelModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

@OnlyIn(Dist.CLIENT)
public class HamsterWheelRenderer extends GeoBlockRenderer<HamsterWheelBlockEntity> {
    public HamsterWheelRenderer() {
        super(new HamsterWheelModel());
    }
}
