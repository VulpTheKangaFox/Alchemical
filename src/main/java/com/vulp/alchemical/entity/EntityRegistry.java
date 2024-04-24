package com.vulp.alchemical.entity;

import com.vulp.alchemical.Alchemical;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Alchemical.MOD_ID);

    public static final RegistryObject<EntityType<FireElemental>> FIRE_ELEMENTAL = ENTITY_TYPES.register("fire_elemental", () -> EntityType.Builder.of(FireElemental::new, MobCategory.CREATURE)
                    .sized(0.55F, 0.55F)
                    .build(new ResourceLocation(Alchemical.MOD_ID, "fire_elemental").toString()));
    public static final RegistryObject<EntityType<WaterElemental>> WATER_ELEMENTAL = ENTITY_TYPES.register("water_elemental", () -> EntityType.Builder.of(WaterElemental::new, MobCategory.CREATURE)
            .sized(0.55F, 0.55F)
            .build(new ResourceLocation(Alchemical.MOD_ID, "water_elemental").toString()));
    public static final RegistryObject<EntityType<EarthElemental>> EARTH_ELEMENTAL = ENTITY_TYPES.register("earth_elemental", () -> EntityType.Builder.of(EarthElemental::new, MobCategory.CREATURE)
            .sized(0.55F, 0.55F)
            .build(new ResourceLocation(Alchemical.MOD_ID, "earth_elemental").toString()));
    public static final RegistryObject<EntityType<AirElemental>> AIR_ELEMENTAL = ENTITY_TYPES.register("air_elemental", () -> EntityType.Builder.of(AirElemental::new, MobCategory.CREATURE)
            .sized(0.55F, 0.55F)
            .build(new ResourceLocation(Alchemical.MOD_ID, "air_elemental").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}
