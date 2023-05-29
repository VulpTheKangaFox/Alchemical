package com.vulp.alchemical.item;

import com.vulp.alchemical.Alchemical;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Alchemical.MOD_ID);

    public static final RegistryObject<Item> RAW_TIN = ITEMS.register("raw_tin", () -> new Item(new Item.Properties().tab(AlchemicalCreativeTab.TAB)));
    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot", () -> new Item(new Item.Properties().tab(AlchemicalCreativeTab.TAB)));
    public static final RegistryObject<Item> RAW_LEAD = ITEMS.register("raw_lead", () -> new Item(new Item.Properties().tab(AlchemicalCreativeTab.TAB)));
    public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot", () -> new Item(new Item.Properties().tab(AlchemicalCreativeTab.TAB)));
    public static final RegistryObject<Item> RAW_SILVER = ITEMS.register("raw_silver", () -> new Item(new Item.Properties().tab(AlchemicalCreativeTab.TAB)));
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties().tab(AlchemicalCreativeTab.TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
