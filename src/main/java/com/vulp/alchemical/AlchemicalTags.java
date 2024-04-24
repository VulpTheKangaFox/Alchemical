package com.vulp.alchemical;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class AlchemicalTags {

    public static class Items {

        // True Items
        public static final TagKey<Item> INGOTS_TIN = tagForge("ingots/tin");
        public static final TagKey<Item> INGOTS_LEAD = tagForge("ingots/lead");
        public static final TagKey<Item> INGOTS_SILVER = tagForge("ingots/silver");

        public static final TagKey<Item> NUGGETS_TIN = tagForge("nuggets/tin");
        public static final TagKey<Item> NUGGETS_LEAD = tagForge("nuggets/lead");
        public static final TagKey<Item> NUGGETS_SILVER = tagForge("nuggets/silver");

        // Block Items
        public static final TagKey<Item> ORES_TIN = tagForge("ores/tin");
        public static final TagKey<Item> ORES_LEAD = tagForge("ores/lead");
        public static final TagKey<Item> ORES_SILVER = tagForge("ores/silver");
        public static final TagKey<Item> STORAGE_BLOCKS_TIN = tagForge("storage_blocks/tin");
        public static final TagKey<Item> STORAGE_BLOCKS_LEAD = tagForge("storage_blocks/lead");
        public static final TagKey<Item> STORAGE_BLOCKS_SILVER = tagForge("storage_blocks/silver");

        public static final TagKey<Item> STORAGE_BLOCKS_RAW_TIN = tagForge("storage_blocks/raw_tin");
        public static final TagKey<Item> STORAGE_BLOCKS_RAW_LEAD = tagForge("storage_blocks/raw_lead");
        public static final TagKey<Item> STORAGE_BLOCKS_RAW_SILVER = tagForge("storage_blocks/raw_silver");

        private static TagKey<Item> tagCustom(String name) {
            return ItemTags.create(new ResourceLocation(Alchemical.MOD_ID, name));
        }

        private static TagKey<Item> tagForge(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> ORES_TIN = tagForge("ores/tin");
        public static final TagKey<Block> ORES_LEAD = tagForge("ores/lead");
        public static final TagKey<Block> ORES_SILVER = tagForge("ores/silver");

        public static final TagKey<Block> STORAGE_BLOCKS_TIN = tagForge("storage_blocks/tin");
        public static final TagKey<Block> STORAGE_BLOCKS_LEAD = tagForge("storage_blocks/lead");
        public static final TagKey<Block> STORAGE_BLOCKS_SILVER = tagForge("storage_blocks/silver");

        public static final TagKey<Block> STORAGE_BLOCKS_RAW_TIN = tagForge("storage_blocks/raw_tin");
        public static final TagKey<Block> STORAGE_BLOCKS_RAW_LEAD = tagForge("storage_blocks/raw_lead");
        public static final TagKey<Block> STORAGE_BLOCKS_RAW_SILVER = tagForge("storage_blocks/raw_silver");

        private static TagKey<Block> tagCustom(String name) {
            return BlockTags.create(new ResourceLocation(Alchemical.MOD_ID, name));
        }

        private static TagKey<Block> tagForge(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }

    }

}
