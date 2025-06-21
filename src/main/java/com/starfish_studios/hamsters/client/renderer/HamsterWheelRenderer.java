package com.starfish_studios.hamsters.client.renderer;

import com.starfish_studios.hamsters.block.entity.HamsterWheelBlockEntity;
import com.starfish_studios.hamsters.client.model.HamsterWheelModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class HamsterWheelRenderer extends GeoBlockRenderer<HamsterWheelBlockEntity> {
    public HamsterWheelRenderer() {
        super(new HamsterWheelModel());
    }
}
