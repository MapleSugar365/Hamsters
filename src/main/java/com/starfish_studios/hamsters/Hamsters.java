package com.starfish_studios.hamsters;

import com.starfish_studios.hamsters.registry.*;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(Hamsters.MOD_ID)
public class Hamsters {
	public static final String MOD_ID = "hamsters";

	public Hamsters(IEventBus modEventBus, ModContainer modContainer) {
		HamstersBlocks.BLOCKS.register(modEventBus);
		HamstersBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
		HamstersCreativeModeTab.CREATIVE_MODE_TABS.register(modEventBus);
		HamstersItems.ITEMS.register(modEventBus);
		HamstersEntityType.ENTITY_TYPES.register(modEventBus);
		HamstersSoundEvents.SOUND_EVENTS.register(modEventBus);
		modEventBus.addListener(this::commonSetup);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		event.enqueueWork(HamstersVanillaIntegration::configInit);
	}

}