package com.vulp.alchemical.datagen;

import com.vulp.alchemical.Alchemical;
import com.vulp.alchemical.item.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class AlchemicalItemModelProvider extends ItemModelProvider {

    public AlchemicalItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Alchemical.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ItemRegistry.RAW_TIN);
        simpleItem(ItemRegistry.TIN_INGOT);
        simpleItem(ItemRegistry.TIN_NUGGET);
        simpleItem(ItemRegistry.RAW_LEAD);
        simpleItem(ItemRegistry.LEAD_INGOT);
        simpleItem(ItemRegistry.LEAD_NUGGET);
        simpleItem(ItemRegistry.RAW_SILVER);
        simpleItem(ItemRegistry.SILVER_INGOT);
        simpleItem(ItemRegistry.SILVER_NUGGET);

        simpleItem(ItemRegistry.FIRE_ESSENCE);
        simpleItem(ItemRegistry.WATER_ESSENCE);
        simpleItem(ItemRegistry.EARTH_ESSENCE);
        simpleItem(ItemRegistry.AIR_ESSENCE);

        // SPAWN EGGS
        createSpawnEggs(mcLoc("item/template_spawn_egg"),
                ItemRegistry.FIRE_ELEMENTAL_SPAWN_EGG.getId().getPath(),
                ItemRegistry.WATER_ELEMENTAL_SPAWN_EGG.getId().getPath(),
                ItemRegistry.EARTH_ELEMENTAL_SPAWN_EGG.getId().getPath(),
                ItemRegistry.AIR_ELEMENTAL_SPAWN_EGG.getId().getPath()
        );
    }

    private void createSpawnEggs(ResourceLocation template, String... paths) {
        for (String path : paths) {
            withExistingParent(path, template);
        }
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Alchemical.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(Alchemical.MOD_ID,"item/" + item.getId().getPath()));
    }

}
