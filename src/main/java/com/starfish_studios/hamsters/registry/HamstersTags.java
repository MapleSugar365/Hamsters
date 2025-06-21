package com.starfish_studios.hamsters.registry;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import com.starfish_studios.hamsters.Hamsters;

public interface HamstersTags {

        TagKey<Item> HAMSTER_FOOD = TagKey.create(Registries.ITEM,
                        ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, "hamster_food"));

        TagKey<Biome> HAS_HAMSTER = TagKey.create(Registries.BIOME,
                        ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, "has_hamster"));
}
