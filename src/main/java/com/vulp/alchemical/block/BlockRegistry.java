package com.vulp.alchemical.block;

import com.vulp.alchemical.Alchemical;
import com.vulp.alchemical.item.AlchemicalCreativeTab;
import com.vulp.alchemical.item.ItemRegistry;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Alchemical.MOD_ID);

    public static final RegistryObject<Block> TIN_ORE = registerBlock("tin_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), AlchemicalCreativeTab.TAB);
    public static final RegistryObject<Block> RAW_TIN_BLOCK = registerBlock("raw_tin_block", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F)), AlchemicalCreativeTab.TAB);
    public static final RegistryObject<Block> TIN_BLOCK = registerBlock("tin_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), AlchemicalCreativeTab.TAB);
    public static final RegistryObject<Block> LEAD_ORE = registerBlock("lead_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), AlchemicalCreativeTab.TAB);
    public static final RegistryObject<Block> RAW_LEAD_BLOCK = registerBlock("raw_lead_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F)), AlchemicalCreativeTab.TAB);
    public static final RegistryObject<Block> LEAD_BLOCK = registerBlock("lead_block", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), AlchemicalCreativeTab.TAB);
    public static final RegistryObject<Block> SILVER_ORE = registerBlock("silver_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), AlchemicalCreativeTab.TAB);
    public static final RegistryObject<Block> RAW_SILVER_BLOCK = registerBlock("raw_silver_block", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F)), AlchemicalCreativeTab.TAB);
    public static final RegistryObject<Block> SILVER_BLOCK = registerBlock("silver_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), AlchemicalCreativeTab.TAB);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, Supplier<T> block, CreativeModeTab tab) {
        return ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}