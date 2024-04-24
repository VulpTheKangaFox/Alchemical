package com.vulp.alchemical.world.feature;

import com.vulp.alchemical.Alchemical;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class PlacedFeatureRegistry {

    public static final ResourceKey<PlacedFeature> ORE_TIN_PLACED = createKey("ore_tin_placed");
    public static final ResourceKey<PlacedFeature> ORE_TIN_SMALL_PLACED = createKey("ore_tin_small_placed");
    public static final ResourceKey<PlacedFeature> ORE_LEAD_PLACED = createKey("ore_lead_placed");
    public static final ResourceKey<PlacedFeature> ORE_LEAD_LOWER_PLACED = createKey("ore_lead_lower_placed");
    public static final ResourceKey<PlacedFeature> ORE_SILVER_PLACED = createKey("ore_silver_placed");
    public static final ResourceKey<PlacedFeature> ORE_SILVER_BURIED_PLACED = createKey("ore_silver_buried_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        // TODO: Configs for the below.
        register(context, ORE_TIN_PLACED, configuredFeatures.getOrThrow(ConfiguredFeatureRegistry.ORE_TIN),
                commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(88))));
        register(context, ORE_TIN_SMALL_PLACED, configuredFeatures.getOrThrow(ConfiguredFeatureRegistry.ORE_TIN_SMALL),
                commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(112))));
        register(context, ORE_LEAD_PLACED, configuredFeatures.getOrThrow(ConfiguredFeatureRegistry.ORE_LEAD),
                commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-44), VerticalAnchor.absolute(48))));
        register(context, ORE_LEAD_LOWER_PLACED, configuredFeatures.getOrThrow(ConfiguredFeatureRegistry.ORE_LEAD_LOWER),
                commonOrePlacement(5, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(8))));
        register(context, ORE_SILVER_PLACED, configuredFeatures.getOrThrow(ConfiguredFeatureRegistry.ORE_SILVER),
                commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-56), VerticalAnchor.absolute(16))));
        register(context, ORE_SILVER_BURIED_PLACED, configuredFeatures.getOrThrow(ConfiguredFeatureRegistry.ORE_SILVER_BURIED),
                orePlacement(CountPlacement.of(UniformInt.of(0, 2)), HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-40))));

    }

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Alchemical.MOD_ID, name));
    }

    // REGISTRATION:
    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }

    // ORE PLACEMENT TYPES:
    private static List<PlacementModifier> orePlacement(PlacementModifier placement1, PlacementModifier placement2) {
        return List.of(placement1, InSquarePlacement.spread(), placement2, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int pCount, PlacementModifier heightRange) {
        return orePlacement(CountPlacement.of(pCount), heightRange);
    }

    private static List<PlacementModifier> rareOrePlacement(int pChance, PlacementModifier heightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(pChance), heightRange);
    }

}
