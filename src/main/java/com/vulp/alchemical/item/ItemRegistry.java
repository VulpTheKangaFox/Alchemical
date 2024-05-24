package com.vulp.alchemical.item;

import com.vulp.alchemical.Alchemical;
import com.vulp.alchemical.block.BlockRegistry;
import com.vulp.alchemical.entity.ElementalTier;
import com.vulp.alchemical.entity.EntityRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Alchemical.MOD_ID);

    // TOOLS
    public static final RegistryObject<Item> ELEMENTALISTS_HAMMER = ITEMS.register("elementalists_hammer", () -> new ElementalistsHammerItem(new Item.Properties().stacksTo(1)));

    // METALS
    public static final RegistryObject<Item> RAW_TIN = ITEMS.register("raw_tin", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIN_NUGGET = ITEMS.register("tin_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_LEAD = ITEMS.register("raw_lead", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LEAD_NUGGET = ITEMS.register("lead_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_SILVER = ITEMS.register("raw_silver", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", () -> new Item(new Item.Properties()));

    // ESSENCES
    public static final RegistryObject<Item> FIRE_ESSENCE = ITEMS.register("fire_essence", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WATER_ESSENCE = ITEMS.register("water_essence", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EARTH_ESSENCE = ITEMS.register("earth_essence", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AIR_ESSENCE = ITEMS.register("air_essence", () -> new Item(new Item.Properties()));

    // ELEMENTAL CAPTURE ITEMS
    public static final RegistryObject<Item> ELEMENTAL_CONTAINER = ITEMS.register("elemental_container", () -> new CaptureContainerItem(ElementalTier.ELEMENTAL, BlockRegistry.ELEMENTAL_CONTAINER.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FUSION_CONTAINER = ITEMS.register("fusion_container", () -> new CaptureContainerItem(ElementalTier.FUSION, BlockRegistry.FUSION_CONTAINER.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PRIMAL_CONTAINER = ITEMS.register("primal_container", () -> new CaptureContainerItem(ElementalTier.PRIMAL, BlockRegistry.PRIMAL_CONTAINER.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ARCANE_CONTAINER = ITEMS.register("arcane_container", () -> new CaptureContainerItem(ElementalTier.ARCANE, BlockRegistry.ARCANE_CONTAINER.get(), new Item.Properties().stacksTo(1)));

    // SPAWN EGGS
    public static final RegistryObject<Item> FIRE_ELEMENTAL_SPAWN_EGG = ITEMS.register("fire_elemental_spawn_egg", () -> new ForgeSpawnEggItem(EntityRegistry.FIRE_ELEMENTAL, 16743446, 16768521, new Item.Properties()));
    public static final RegistryObject<Item> WATER_ELEMENTAL_SPAWN_EGG = ITEMS.register("water_elemental_spawn_egg", () -> new ForgeSpawnEggItem(EntityRegistry.WATER_ELEMENTAL, 4349106, 4456447, new Item.Properties()));
    public static final RegistryObject<Item> EARTH_ELEMENTAL_SPAWN_EGG = ITEMS.register("earth_elemental_spawn_egg", () -> new ForgeSpawnEggItem(EntityRegistry.EARTH_ELEMENTAL, 6052672, 14414519, new Item.Properties()));
    public static final RegistryObject<Item> AIR_ELEMENTAL_SPAWN_EGG = ITEMS.register("air_elemental_spawn_egg", () -> new ForgeSpawnEggItem(EntityRegistry.AIR_ELEMENTAL, 10213042, 15794128, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
