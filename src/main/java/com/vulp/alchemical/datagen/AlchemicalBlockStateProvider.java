package com.vulp.alchemical.datagen;

import com.vulp.alchemical.Alchemical;
import com.vulp.alchemical.block.BlockRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

// Handles creation of blockstate jsons AND their block and item models.
public class AlchemicalBlockStateProvider extends BlockStateProvider {

    private static ExistingFileHelper FILE_HELPER = null;

    public AlchemicalBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Alchemical.MOD_ID, exFileHelper);
        FILE_HELPER = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(BlockRegistry.TIN_ORE);
        blockWithItem(BlockRegistry.DEEPSLATE_TIN_ORE);
        blockWithItem(BlockRegistry.RAW_TIN_BLOCK);
        blockWithItem(BlockRegistry.TIN_BLOCK);
        blockWithItem(BlockRegistry.LEAD_ORE);
        blockWithItem(BlockRegistry.DEEPSLATE_LEAD_ORE);
        blockWithItem(BlockRegistry.RAW_LEAD_BLOCK);
        blockWithItem(BlockRegistry.LEAD_BLOCK);
        blockWithItem(BlockRegistry.SILVER_ORE);
        blockWithItem(BlockRegistry.DEEPSLATE_SILVER_ORE);
        blockWithItem(BlockRegistry.RAW_SILVER_BLOCK);
        blockWithItem(BlockRegistry.SILVER_BLOCK);

        horizontalBlockWithItem(BlockRegistry.ELEMENTAL_CONTAINER, new ResourceLocation(Alchemical.MOD_ID, "block/elemental_container"));
        horizontalBlockWithItem(BlockRegistry.FUSION_CONTAINER, new ResourceLocation(Alchemical.MOD_ID, "block/fusion_container"));
        horizontalBlockWithItem(BlockRegistry.PRIMAL_CONTAINER, new ResourceLocation(Alchemical.MOD_ID, "block/primal_container"));
        horizontalBlockWithItem(BlockRegistry.ARCANE_CONTAINER, new ResourceLocation(Alchemical.MOD_ID, "block/arcane_container"));

        horizontalBlockAndBooleanProperty(BlockRegistry.TEST_MAIN, new ResourceLocation(Alchemical.MOD_ID, "block/elemental_furnace_bottom_lit"), new ResourceLocation(Alchemical.MOD_ID, "block/elemental_furnace_bottom"), BlockStateProperties.LIT);
        horizontalBlockAndBooleanProperty(BlockRegistry.TEST_DUMMY, new ResourceLocation(Alchemical.MOD_ID, "block/elemental_furnace_top_lit"), new ResourceLocation(Alchemical.MOD_ID, "block/elemental_furnace_top"), BlockStateProperties.LIT);

    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        Block block = blockRegistryObject.get();
        simpleBlockWithItem(block, cubeAll(block));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject, ResourceLocation existingModel) {
        Block block = blockRegistryObject.get();
        simpleBlockWithItem(block, new ModelFile.ExistingModelFile(existingModel, FILE_HELPER));
    }

    private void horizontalBlockWithItem(RegistryObject<Block> blockRegistryObject) {
        Block block = blockRegistryObject.get();
        horizontalBlock(block, cubeAll(block));
        simpleBlockItem(block, cubeAll(block));
    }

    private void horizontalBlockWithItem(RegistryObject<Block> blockRegistryObject, ResourceLocation existingModel) {
        Block block = blockRegistryObject.get();
        ModelFile.ExistingModelFile existingFile = models().getExistingFile(existingModel);
        simpleBlockItem(block, existingFile);
        horizontalBlock(block, existingFile);
    }

    private void horizontalBlockAndBooleanProperty(RegistryObject<Block> blockRegistryObject, ResourceLocation existingModelFalse, ResourceLocation existingModelTrue, BooleanProperty property) {
        Block block = blockRegistryObject.get();
        ModelFile.ExistingModelFile trueFile = models().getExistingFile(existingModelTrue);
        ModelFile.ExistingModelFile falseFile = models().getExistingFile(existingModelFalse);
        getVariantBuilder(block)
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(state.getValue(property) ? falseFile : trueFile) // Dunno why these need to be backwards but they are and it works.
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                        .build()
                );
    }

    public void horizontalBlockWithItem(Block block, Function<BlockState, ModelFile> modelFunc, int angleOffset) {
        getVariantBuilder(block)
                .forAllStates(state -> new ConfiguredModel[]{ConfiguredModel.builder()
                        .modelFile(modelFunc.apply(state))
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + angleOffset) % 360).buildLast()}
                );
    }

}
