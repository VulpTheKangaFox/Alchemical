package com.vulp.alchemical.datagen;

import com.vulp.alchemical.Alchemical;
import com.vulp.alchemical.AlchemicalTags;
import com.vulp.alchemical.block.BlockRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.minecraftforge.common.Tags.Blocks.*;

public class AlchemicalBlockTagProvider extends BlockTagsProvider {

    private static final Material[] AXE_HARVESTABLE = new Material[]{Material.WOOD, Material.NETHER_WOOD, Material.BAMBOO, Material.VEGETABLE, Material.PLANT};
    private static final Material[] PICKAXE_HARVESTABLE = new Material[]{Material.STONE, Material.METAL, Material.HEAVY_METAL, Material.AMETHYST, Material.ICE, Material.ICE_SOLID, Material.PISTON, Material.SHULKER_SHELL};
    private static final Material[] SHOVEL_HARVESTABLE = new Material[]{Material.CLAY, Material.DIRT, Material.GRASS, Material.SNOW, Material.TOP_SNOW};
    private static final Material[] HOE_HARVESTABLE = new Material[]{Material.LEAVES, Material.MOSS, Material.SCULK, Material.SPONGE};

    public AlchemicalBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Alchemical.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // A blacklist that stops block's harvest tools from being set up through the automatic handling. Add to the list just below it.
        List<Object> autoBlacklist = new ArrayList<>(Collections.emptyList());


        // Automatically tag blocks their assumed harvest tools based on their material.
        IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> axe = this.tag(BlockTags.MINEABLE_WITH_AXE);
        IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> pickaxe = this.tag(BlockTags.MINEABLE_WITH_PICKAXE);
        IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> shovel = this.tag(BlockTags.MINEABLE_WITH_SHOVEL);
        IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> hoe = this.tag(BlockTags.MINEABLE_WITH_HOE);
        BlockRegistry.BLOCKS.getEntries().forEach(blockRegistryObject -> {
            if (autoBlacklist.contains(blockRegistryObject)) {
                return;
            }
            Block block = blockRegistryObject.get();
            Material material = block.defaultBlockState().getMaterial();
            if (Arrays.stream(AXE_HARVESTABLE).anyMatch(mapMaterial -> material == mapMaterial)) {
                axe.add(block);
            } else if (Arrays.stream(PICKAXE_HARVESTABLE).anyMatch(mapMaterial -> material == mapMaterial)) {
                pickaxe.add(block);
            } else if (Arrays.stream(SHOVEL_HARVESTABLE).anyMatch(mapMaterial -> material == mapMaterial)) {
                shovel.add(block);
            } else if (Arrays.stream(HOE_HARVESTABLE).anyMatch(mapMaterial -> material == mapMaterial)) {
                hoe.add(block);
            }
        });

        // ORES
        tag(ORES)
                .addTag(AlchemicalTags.Blocks.ORES_TIN)
                .addTag(AlchemicalTags.Blocks.ORES_LEAD)
                .addTag(AlchemicalTags.Blocks.ORES_SILVER);
        tag(ORE_RATES_SINGULAR)
                .addTag(AlchemicalTags.Blocks.ORES_TIN)
                .addTag(AlchemicalTags.Blocks.ORES_LEAD)
                .addTag(AlchemicalTags.Blocks.ORES_SILVER);
        tag(AlchemicalTags.Blocks.ORES_TIN)
                .add(BlockRegistry.TIN_ORE.get(), BlockRegistry.DEEPSLATE_TIN_ORE.get());
        tag(AlchemicalTags.Blocks.ORES_LEAD)
                .add(BlockRegistry.LEAD_ORE.get(), BlockRegistry.DEEPSLATE_LEAD_ORE.get());
        tag(AlchemicalTags.Blocks.ORES_SILVER)
                .add(BlockRegistry.SILVER_ORE.get(), BlockRegistry.DEEPSLATE_SILVER_ORE.get());

        // STORAGE BLOCKS
        tag(STORAGE_BLOCKS)
                .addTag(AlchemicalTags.Blocks.STORAGE_BLOCKS_TIN)
                .addTag(AlchemicalTags.Blocks.STORAGE_BLOCKS_LEAD)
                .addTag(AlchemicalTags.Blocks.STORAGE_BLOCKS_SILVER)
                .addTag(AlchemicalTags.Blocks.STORAGE_BLOCKS_RAW_TIN)
                .addTag(AlchemicalTags.Blocks.STORAGE_BLOCKS_RAW_LEAD)
                .addTag(AlchemicalTags.Blocks.STORAGE_BLOCKS_RAW_SILVER);
        tag(AlchemicalTags.Blocks.STORAGE_BLOCKS_TIN)
                .add(BlockRegistry.TIN_BLOCK.get());
        tag(AlchemicalTags.Blocks.STORAGE_BLOCKS_LEAD)
                .add(BlockRegistry.LEAD_BLOCK.get());
        tag(AlchemicalTags.Blocks.STORAGE_BLOCKS_SILVER)
                .add(BlockRegistry.SILVER_BLOCK.get());
        tag(AlchemicalTags.Blocks.STORAGE_BLOCKS_RAW_TIN)
                .add(BlockRegistry.RAW_TIN_BLOCK.get());
        tag(AlchemicalTags.Blocks.STORAGE_BLOCKS_RAW_LEAD)
                .add(BlockRegistry.RAW_LEAD_BLOCK.get());
        tag(AlchemicalTags.Blocks.STORAGE_BLOCKS_RAW_SILVER)
                .add(BlockRegistry.RAW_SILVER_BLOCK.get());

    }

}
