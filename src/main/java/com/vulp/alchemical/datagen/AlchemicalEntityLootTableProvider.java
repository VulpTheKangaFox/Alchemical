package com.vulp.alchemical.datagen;

import com.vulp.alchemical.entity.EntityRegistry;
import com.vulp.alchemical.item.ItemRegistry;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.Stream;

public class AlchemicalEntityLootTableProvider extends EntityLootSubProvider {

    protected AlchemicalEntityLootTableProvider() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        addSimple(EntityRegistry.FIRE_ELEMENTAL.get(), ItemRegistry.FIRE_ESSENCE.get(), UniformGenerator.between(0.0F, 1.0F), UniformGenerator.between(0.0F, 1.0F), true, 1);
        addSimple(EntityRegistry.WATER_ELEMENTAL.get(), ItemRegistry.WATER_ESSENCE.get(), UniformGenerator.between(0.0F, 1.0F), UniformGenerator.between(0.0F, 1.0F), true, 1);
        addSimple(EntityRegistry.EARTH_ELEMENTAL.get(), ItemRegistry.EARTH_ESSENCE.get(), UniformGenerator.between(0.0F, 1.0F), UniformGenerator.between(0.0F, 1.0F), true, 1);
        addSimple(EntityRegistry.AIR_ELEMENTAL.get(), ItemRegistry.AIR_ESSENCE.get(), UniformGenerator.between(0.0F, 1.0F), UniformGenerator.between(0.0F, 1.0F), true, 1);
    }

    private void addSimple(EntityType<?> entityType, Item loot, NumberProvider amount, NumberProvider lootModifier, boolean playerKillOnly) {
        LootPool.Builder poolBuilder = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(loot)
                        .apply(SetItemCountFunction.setCount(amount))
                        .apply(LootingEnchantFunction.lootingMultiplier(lootModifier)))
                .when(LootItemKilledByPlayerCondition.killedByPlayer());
        this.add(entityType, LootTable.lootTable().withPool(playerKillOnly ? poolBuilder.when(LootItemKilledByPlayerCondition.killedByPlayer()) : poolBuilder));
    }

    private void addSimple(EntityType<?> entityType, Item loot, NumberProvider amount, NumberProvider lootModifier, boolean playerKillOnly, int lootModLimit) {
        LootPool.Builder poolBuilder = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(loot)
                        .apply(SetItemCountFunction.setCount(amount))
                        .apply(LootingEnchantFunction.lootingMultiplier(lootModifier).setLimit(lootModLimit)))
                .when(LootItemKilledByPlayerCondition.killedByPlayer());
        this.add(entityType, LootTable.lootTable().withPool(playerKillOnly ? poolBuilder.when(LootItemKilledByPlayerCondition.killedByPlayer()) : poolBuilder));
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return EntityRegistry.ENTITY_TYPES.getEntries().stream().map(RegistryObject::get);
    }

}
