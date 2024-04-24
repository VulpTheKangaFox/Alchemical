package com.vulp.alchemical.datagen;

import com.google.common.collect.ImmutableList;
import com.vulp.alchemical.block.BlockRegistry;
import com.vulp.alchemical.item.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class AlchemicalRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public static final ImmutableList<ItemLike> TIN_SMELTABLES = ImmutableList.of(BlockRegistry.TIN_ORE.get(), BlockRegistry.DEEPSLATE_TIN_ORE.get(), ItemRegistry.RAW_TIN.get());
    public static final ImmutableList<ItemLike> LEAD_SMELTABLES = ImmutableList.of(BlockRegistry.LEAD_ORE.get(), BlockRegistry.DEEPSLATE_LEAD_ORE.get(), ItemRegistry.RAW_LEAD.get());
    public static final ImmutableList<ItemLike> SILVER_SMELTABLES = ImmutableList.of(BlockRegistry.SILVER_ORE.get(), BlockRegistry.DEEPSLATE_SILVER_ORE.get(), ItemRegistry.RAW_SILVER.get());

    public AlchemicalRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        // SMELTING
        oreSmelting(writer, TIN_SMELTABLES, RecipeCategory.MISC, ItemRegistry.TIN_INGOT.get(), 0.7F, 200, "tin_ingot");
        oreSmelting(writer, LEAD_SMELTABLES, RecipeCategory.MISC, ItemRegistry.LEAD_INGOT.get(), 0.7F, 200, "lead_ingot");
        oreSmelting(writer, SILVER_SMELTABLES, RecipeCategory.MISC, ItemRegistry.SILVER_INGOT.get(), 0.7F, 200, "silver_ingot");
        // BLASTING
        oreBlasting(writer, TIN_SMELTABLES, RecipeCategory.MISC, ItemRegistry.TIN_INGOT.get(), 0.7F, 100, "tin_ingot");
        oreBlasting(writer, LEAD_SMELTABLES, RecipeCategory.MISC, ItemRegistry.LEAD_INGOT.get(), 0.7F, 100, "lead_ingot");
        oreBlasting(writer, SILVER_SMELTABLES, RecipeCategory.MISC, ItemRegistry.SILVER_INGOT.get(), 0.7F, 100, "silver_ingot");
        // SMOKING


        // CRAFTING
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.MAP).define('#', Items.PAPER).define('X', Items.COMPASS).pattern("###").pattern("#X#").pattern("###").unlockedBy("has_compass", has(Items.COMPASS)).save(writer);

        // NINE BLOCK STORAGE
        nineBlockStorageRecipesRecipesWithCustomUnpacking(writer, RecipeCategory.MISC, ItemRegistry.TIN_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.TIN_BLOCK.get(), "tin_ingot_from_tin_block", "tin_ingot");
        nineBlockStorageRecipesWithCustomPacking(writer, RecipeCategory.MISC, ItemRegistry.TIN_NUGGET.get(), RecipeCategory.MISC, ItemRegistry.TIN_INGOT.get(), "tin_ingot_from_nuggets", "tin_ingot");
        nineBlockStorageRecipesRecipesWithCustomUnpacking(writer, RecipeCategory.MISC, ItemRegistry.LEAD_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.LEAD_BLOCK.get(), "lead_ingot_from_lead_block", "lead_ingot");
        nineBlockStorageRecipesWithCustomPacking(writer, RecipeCategory.MISC, ItemRegistry.LEAD_NUGGET.get(), RecipeCategory.MISC, ItemRegistry.LEAD_INGOT.get(), "lead_ingot_from_nuggets", "lead_ingot");
        nineBlockStorageRecipesRecipesWithCustomUnpacking(writer, RecipeCategory.MISC, ItemRegistry.SILVER_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.SILVER_BLOCK.get(), "silver_ingot_from_silver_block", "silver_ingot");
        nineBlockStorageRecipesWithCustomPacking(writer, RecipeCategory.MISC, ItemRegistry.SILVER_NUGGET.get(), RecipeCategory.MISC, ItemRegistry.SILVER_INGOT.get(), "silver_ingot_from_nuggets", "silver_ingot");
        nineBlockStorageRecipes(writer, RecipeCategory.MISC, ItemRegistry.RAW_TIN.get(), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.RAW_TIN_BLOCK.get());
        nineBlockStorageRecipes(writer, RecipeCategory.MISC, ItemRegistry.RAW_LEAD.get(), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.RAW_LEAD_BLOCK.get());
        nineBlockStorageRecipes(writer, RecipeCategory.MISC, ItemRegistry.RAW_SILVER.get(), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.RAW_SILVER_BLOCK.get());
        // SHAPELESS

    }

}
