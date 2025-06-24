package com.starfish_studios.hamsters.registry;

import com.starfish_studios.hamsters.Hamsters;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class HamstersTags {

        // region Item Tags

        public static final TagKey<Item> CAGE_PANEL_ITEMS = registerItemTag("cage_panels"),
                        HAMSTER_BLOCK_ITEMS = registerItemTag("hamster_blocks"),
                        HAMSTER_BOTTLE_ITEMS = registerItemTag("hamster_bottles"),
                        HAMSTER_BOWL_ITEMS = registerItemTag("hamster_bowls"),
                        HAMSTER_WHEEL_ITEMS = registerItemTag("hamster_wheels"),

                        HAMSTER_FOOD = registerItemTag("hamster_food"),
                        HAMSTER_BREEDING_FOOD = registerItemTag("hamster_breeding_food"),
                        HAMSTER_BOWL_FOOD = registerItemTag("hamster_bowl_food");

        // endregion

        // region Block Tags

        public static final TagKey<Block> CAGE_PANELS = registerBlockTag("cage_panels"),
                        HAMSTER_BLOCKS = registerBlockTag("hamster_blocks"),
                        HAMSTER_BOTTLES = registerBlockTag("hamster_bottles"),
                        HAMSTER_BOWLS = registerBlockTag("hamster_bowls"),
                        HAMSTER_WHEELS = registerBlockTag("hamster_wheels"),

                        HAMSTERS_SPAWNABLE_ON = registerBlockTag("hamsters_spawnable_on");

        // endregion

        // region Entity Type Tags

        public static final TagKey<EntityType<?>> HAMSTER_AVOIDED = registerEntityTag("hamster_avoided");

        // endregion

        // region Damage Type Tags

        public static final TagKey<DamageType> SQUISHES_HAMSTERS = registerDamageTypeTag("squishes_hamsters");

        // endregion

        // region Biome Tags

        public static final TagKey<Biome> HAS_HAMSTER = registerBiomeTag("has_hamster");

        // endregion

        // region Registry

        private static TagKey<Item> registerItemTag(String name) {
                return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, name));
        }

        private static TagKey<Block> registerBlockTag(String name) {
                return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, name));
        }

        private static TagKey<EntityType<?>> registerEntityTag(String name) {
                return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, name));
        }

        private static TagKey<DamageType> registerDamageTypeTag(String name) {
                return TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, name));
        }

        private static TagKey<Biome> registerBiomeTag(String name) {
                return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Hamsters.MOD_ID, name));
        }

        // endregion
}