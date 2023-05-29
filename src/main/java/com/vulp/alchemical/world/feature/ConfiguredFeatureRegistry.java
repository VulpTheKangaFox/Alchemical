package com.vulp.alchemical.world.feature;

import com.vulp.alchemical.Alchemical;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class ConfiguredFeatureRegistry {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Alchemical.MOD_ID);

    public static void register(IEventBus eventBus) {
        CONFIGURED_FEATURES.register(eventBus);
    }

}
