package com.vulp.alchemical.block.entity;

import com.vulp.alchemical.entity.ElementalTier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;

import java.util.Optional;

public interface IElementalCaptureBlockEntity {

    ElementalTier getTier();

    CompoundTag getElementalTags();

    void setElementalTags(CompoundTag elementalInfo);

    // Recommended method to use where possible. Attempts to return the old elemental in cases in where we trade elementals.
    default CompoundTag switchElementalTags(CompoundTag elementalInfo) {
        CompoundTag currentElemental = new CompoundTag();
        if (!getElementalTags().isEmpty()) {
            currentElemental = getElementalTags().copy();
        }
        setElementalTags(elementalInfo);
        return currentElemental;
    }

    default Optional<EntityType<?>> getEntityFromTags() {
        return EntityType.by(getElementalTags());
    }

}
