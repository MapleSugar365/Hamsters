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

    public static final DeferredHolder<SoundEvent, SoundEvent> HAMSTER_AMBIENT = registerSoundEvent("entity.hamster.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMSTER_HURT = registerSoundEvent("entity.hamster.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMSTER_DEATH = registerSoundEvent("entity.hamster.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMSTER_BEG = registerSoundEvent("entity.hamster.beg");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMSTER_SLEEP = registerSoundEvent("entity.hamster.sleep");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMSTER_EAT = registerSoundEvent("entity.hamster.eat");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMSTER_EXPLODE = registerSoundEvent("entity.hamster.explode");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMSTER_SQUISH = registerSoundEvent("entity.hamster.squish");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMSTER_UNSQUISH = registerSoundEvent("entity.hamster.unsquish");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMSTER_PICK_UP = registerSoundEvent("entity.hamster.pick_up");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMSTER_PLACE = registerSoundEvent("entity.hamster.place");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String id) {
        return SOUND_EVENTS.register(id,
                () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, id)));
    }
}
