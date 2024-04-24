package com.vulp.alchemical.datagen;

import com.vulp.alchemical.Alchemical;
import com.vulp.alchemical.AlchemicalTags;
import com.vulp.alchemical.block.BlockRegistry;
import com.vulp.alchemical.item.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static net.minecraftforge.common.Tags.Items.*;

public class AlchemicalItemTagProvider extends ItemTagsProvider {


    public AlchemicalItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, Alchemical.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // ORES
        tag(ORES)
                .addTag(AlchemicalTags.Items.ORES_TIN)
                .addTag(AlchemicalTags.Items.ORES_LEAD)
                .addTag(AlchemicalTags.Items.ORES_SILVER);
        tag(ORE_RATES_SINGULAR)
                .addTag(AlchemicalTags.Items.ORES_TIN)
                .addTag(AlchemicalTags.Items.ORES_LEAD)
                .addTag(AlchemicalTags.Items.ORES_SILVER);
        tag(AlchemicalTags.Items.ORES_TIN)
                .add(BlockRegistry.TIN_ORE.get().asItem(), BlockRegistry.DEEPSLATE_TIN_ORE.get().asItem());
        tag(AlchemicalTags.Items.ORES_LEAD)
                .add(BlockRegistry.LEAD_ORE.get().asItem(), BlockRegistry.DEEPSLATE_LEAD_ORE.get().asItem());
        tag(AlchemicalTags.Items.ORES_SILVER)
                .add(BlockRegistry.SILVER_ORE.get().asItem(), BlockRegistry.DEEPSLATE_SILVER_ORE.get().asItem());

        // STORAGE BLOCKS
        tag(STORAGE_BLOCKS)
                .addTag(AlchemicalTags.Items.STORAGE_BLOCKS_TIN)
                .addTag(AlchemicalTags.Items.STORAGE_BLOCKS_LEAD)
                .addTag(AlchemicalTags.Items.STORAGE_BLOCKS_SILVER)
                .addTag(AlchemicalTags.Items.STORAGE_BLOCKS_RAW_TIN)
                .addTag(AlchemicalTags.Items.STORAGE_BLOCKS_RAW_LEAD)
                .addTag(AlchemicalTags.Items.STORAGE_BLOCKS_RAW_SILVER);
        tag(AlchemicalTags.Items.STORAGE_BLOCKS_TIN)
                .add(BlockRegistry.TIN_BLOCK.get().asItem());
        tag(AlchemicalTags.Items.STORAGE_BLOCKS_LEAD)
                .add(BlockRegistry.LEAD_BLOCK.get().asItem());
        tag(AlchemicalTags.Items.STORAGE_BLOCKS_SILVER)
                .add(BlockRegistry.SILVER_BLOCK.get().asItem());
        tag(AlchemicalTags.Items.STORAGE_BLOCKS_RAW_TIN)
                .add(BlockRegistry.RAW_TIN_BLOCK.get().asItem());
        tag(AlchemicalTags.Items.STORAGE_BLOCKS_RAW_LEAD)
                .add(BlockRegistry.RAW_LEAD_BLOCK.get().asItem());
        tag(AlchemicalTags.Items.STORAGE_BLOCKS_RAW_SILVER)
                .add(BlockRegistry.RAW_SILVER_BLOCK.get().asItem());

        // INGOTS
        tag(INGOTS)
                .addTag(AlchemicalTags.Items.INGOTS_TIN)
                .addTag(AlchemicalTags.Items.INGOTS_LEAD)
                .addTag(AlchemicalTags.Items.INGOTS_SILVER);
        tag(AlchemicalTags.Items.INGOTS_TIN)
                .add(ItemRegistry.TIN_INGOT.get());
        tag(AlchemicalTags.Items.INGOTS_LEAD)
                .add(ItemRegistry.LEAD_INGOT.get());
        tag(AlchemicalTags.Items.INGOTS_SILVER)
                .add(ItemRegistry.SILVER_INGOT.get());
        
        // NUGGETS
        tag(NUGGETS)
                .addTag(AlchemicalTags.Items.NUGGETS_TIN)
                .addTag(AlchemicalTags.Items.NUGGETS_LEAD)
                .addTag(AlchemicalTags.Items.NUGGETS_SILVER);
        tag(AlchemicalTags.Items.NUGGETS_TIN)
                .add(ItemRegistry.TIN_NUGGET.get());
        tag(AlchemicalTags.Items.NUGGETS_LEAD)
                .add(ItemRegistry.LEAD_NUGGET.get());
        tag(AlchemicalTags.Items.NUGGETS_SILVER)
                .add(ItemRegistry.SILVER_NUGGET.get());
    }
}
