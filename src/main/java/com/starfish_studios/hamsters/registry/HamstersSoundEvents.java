package com.starfish_studios.hamsters.registry;

import com.starfish_studios.hamsters.Hamsters;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HamstersSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister
            .create(BuiltInRegistries.SOUND_EVENT, Hamsters.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> HAMSTER_AMBIENT = register("entity.hamster.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMSTER_HURT = register("entity.hamster.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMSTER_DEATH = register("entity.hamster.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMSTER_BEG = register("entity.hamster.beg");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMSTER_SLEEP = register("entity.hamster.sleep");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String id) {
        return SOUND_EVENTS.register(id,
                () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, id)));
    }
}
