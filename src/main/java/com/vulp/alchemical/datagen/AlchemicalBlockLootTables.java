package com.vulp.alchemical.datagen;

import com.vulp.alchemical.block.BlockRegistry;
import com.vulp.alchemical.item.ItemRegistry;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class AlchemicalBlockLootTables extends BlockLootSubProvider {

    public AlchemicalBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        // ORES
        add(BlockRegistry.TIN_ORE.get(), (block -> createOreDrop(BlockRegistry.TIN_ORE.get(), ItemRegistry.RAW_TIN.get())));
        add(BlockRegistry.DEEPSLATE_TIN_ORE.get(), (block -> createOreDrop(BlockRegistry.DEEPSLATE_TIN_ORE.get(), ItemRegistry.RAW_TIN.get())));
        add(BlockRegistry.LEAD_ORE.get(), (block -> createOreDrop(BlockRegistry.LEAD_ORE.get(), ItemRegistry.RAW_LEAD.get())));
        add(BlockRegistry.DEEPSLATE_LEAD_ORE.get(), (block -> createOreDrop(BlockRegistry.DEEPSLATE_LEAD_ORE.get(), ItemRegistry.RAW_LEAD.get())));
        add(BlockRegistry.SILVER_ORE.get(), (block -> createOreDrop(BlockRegistry.SILVER_ORE.get(), ItemRegistry.RAW_SILVER.get())));
        add(BlockRegistry.DEEPSLATE_SILVER_ORE.get(), (block -> createOreDrop(BlockRegistry.DEEPSLATE_SILVER_ORE.get(), ItemRegistry.RAW_SILVER.get())));

        // REGULAR
        dropSelf(BlockRegistry.RAW_TIN_BLOCK.get());
        dropSelf(BlockRegistry.TIN_BLOCK.get());
        dropSelf(BlockRegistry.RAW_LEAD_BLOCK.get());
        dropSelf(BlockRegistry.LEAD_BLOCK.get());
        dropSelf(BlockRegistry.RAW_SILVER_BLOCK.get());
        dropSelf(BlockRegistry.SILVER_BLOCK.get());

        // TODO: Will likely need some custom code to retain contained elemental.
        add(BlockRegistry.ELEMENTAL_CONTAINER.get(), this.createSingleItemTable(ItemRegistry.ELEMENTAL_CONTAINER.get()));
        add(BlockRegistry.FUSION_CONTAINER.get(), this.createSingleItemTable(ItemRegistry.FUSION_CONTAINER.get()));
        add(BlockRegistry.PRIMAL_CONTAINER.get(), this.createSingleItemTable(ItemRegistry.PRIMAL_CONTAINER.get()));
        add(BlockRegistry.ARCANE_CONTAINER.get(), this.createSingleItemTable(ItemRegistry.ARCANE_CONTAINER.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlockRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
