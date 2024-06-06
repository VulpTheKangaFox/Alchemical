package com.vulp.alchemical.item;

import com.vulp.alchemical.Alchemical;
import com.vulp.alchemical.block.BlockRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Alchemical.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AlchemicalCreativeModeTabs {

    public static CreativeModeTab ALCHEMICAL_TAB;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        ALCHEMICAL_TAB = event.registerCreativeModeTab(new ResourceLocation(Alchemical.MOD_ID, "alchemical_tab"),
                builder -> builder.icon(() -> new ItemStack(ItemRegistry.LEAD_INGOT.get()))
                        .title(Component.translatable("creativemodetab.alchemical_tab")));
    }

    public static void addAlchemicalCreativeTabs(CreativeModeTabEvent.BuildContents event) {
        if(event.getTab() == AlchemicalCreativeModeTabs.ALCHEMICAL_TAB) {
            event.accept(ItemRegistry.DEBUGGER);
            event.accept(ItemRegistry.ELEMENTALISTS_HAMMER);
            // TIN ----------------------------------------------------
            event.accept(BlockRegistry.TIN_ORE);
            event.accept(BlockRegistry.DEEPSLATE_TIN_ORE);
            event.accept(BlockRegistry.RAW_TIN_BLOCK);
            event.accept(BlockRegistry.TIN_BLOCK);
            event.accept(ItemRegistry.RAW_TIN);
            event.accept(ItemRegistry.TIN_INGOT);
            event.accept(ItemRegistry.TIN_NUGGET);
            /*event.accept(ItemRegistry.TIN_SWORD);
            event.accept(ItemRegistry.TIN_SHOVEL);
            event.accept(ItemRegistry.TIN_PICKAXE);
            event.accept(ItemRegistry.TIN_AXE);
            event.accept(ItemRegistry.TIN_HOE);
            event.accept(ItemRegistry.TIN_HELMET);
            event.accept(ItemRegistry.TIN_CHESTPLATE);
            event.accept(ItemRegistry.TIN_LEGGINGS);
            event.accept(ItemRegistry.TIN_BOOTS);*/
            // LEAD ---------------------------------------------------
            event.accept(BlockRegistry.LEAD_ORE);
            event.accept(BlockRegistry.DEEPSLATE_LEAD_ORE);
            event.accept(BlockRegistry.RAW_LEAD_BLOCK);
            event.accept(BlockRegistry.LEAD_BLOCK);
            event.accept(ItemRegistry.RAW_LEAD);
            event.accept(ItemRegistry.LEAD_INGOT);
            event.accept(ItemRegistry.LEAD_NUGGET);
            /*event.accept(ItemRegistry.LEAD_SWORD);
            event.accept(ItemRegistry.LEAD_SHOVEL);
            event.accept(ItemRegistry.LEAD_PICKAXE);
            event.accept(ItemRegistry.LEAD_AXE);
            event.accept(ItemRegistry.LEAD_HOE);
            event.accept(ItemRegistry.LEAD_HELMET);
            event.accept(ItemRegistry.LEAD_CHESTPLATE);
            event.accept(ItemRegistry.LEAD_LEGGINGS);
            event.accept(ItemRegistry.LEAD_BOOTS);*/
            // SILVER -------------------------------------------------
            event.accept(BlockRegistry.SILVER_ORE);
            event.accept(BlockRegistry.DEEPSLATE_SILVER_ORE);
            event.accept(BlockRegistry.RAW_SILVER_BLOCK);
            event.accept(BlockRegistry.SILVER_BLOCK);
            event.accept(ItemRegistry.RAW_SILVER);
            event.accept(ItemRegistry.SILVER_INGOT);
            event.accept(ItemRegistry.SILVER_NUGGET);
            /*event.accept(ItemRegistry.TIN_SWORD);
            event.accept(ItemRegistry.TIN_SHOVEL);
            event.accept(ItemRegistry.TIN_PICKAXE);
            event.accept(ItemRegistry.TIN_AXE);
            event.accept(ItemRegistry.TIN_HOE);
            event.accept(ItemRegistry.TIN_HELMET);
            event.accept(ItemRegistry.TIN_CHESTPLATE);
            event.accept(ItemRegistry.TIN_LEGGINGS);
            event.accept(ItemRegistry.TIN_BOOTS);*/
            // SPAWN EGGS ---------------------------------------------
            event.accept(BlockRegistry.ELEMENTAL_CONTAINER);
            event.accept(BlockRegistry.FUSION_CONTAINER);
            event.accept(BlockRegistry.PRIMAL_CONTAINER);
            event.accept(BlockRegistry.ARCANE_CONTAINER);
            event.accept(ItemRegistry.FIRE_ELEMENTAL_SPAWN_EGG);
            event.accept(ItemRegistry.WATER_ELEMENTAL_SPAWN_EGG);
            event.accept(ItemRegistry.EARTH_ELEMENTAL_SPAWN_EGG);
            event.accept(ItemRegistry.AIR_ELEMENTAL_SPAWN_EGG);
        }
    }

}
