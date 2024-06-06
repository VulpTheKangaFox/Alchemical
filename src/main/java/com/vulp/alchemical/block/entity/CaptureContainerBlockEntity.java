package com.vulp.alchemical.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CaptureContainerBlockEntity extends BlockEntity {

    @Nullable private CompoundTag elemental = new CompoundTag();

    public CaptureContainerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.CAPTURE_BLOCK.get(), pos, state);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.elemental != null) {
            tag.put("held_elemental", this.elemental);
        }
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        CompoundTag elementalInfo = tag.getCompound("held_elemental");
        if (!elementalInfo.isEmpty()) {
            this.elemental = elementalInfo;
        }
    }

    @Nullable
    public CompoundTag getElementalTags() {
        return this.elemental;
    }

    public void setElementalTags(@Nullable CompoundTag elementalInfo) {
        this.elemental = elementalInfo;
    }

    // Recommended method to use where possible. Attempts to return the old elemental in cases in where we trade elementals.
    public CompoundTag switchElementalTags(@Nullable CompoundTag elementalInfo) {
        CompoundTag currentElemental = new CompoundTag();
        CompoundTag elementalTags = getElementalTags();
        if (elementalTags != null && !elementalTags.isEmpty()) {
            currentElemental = getElementalTags().copy();
        }
        setElementalTags(elementalInfo);
        return currentElemental;
    }

    public Optional<EntityType<?>> getEntityFromTags() {
        CompoundTag elementalTags = getElementalTags();
        return elementalTags == null ? Optional.empty() : EntityType.by(elementalTags.getCompound("elemental_info"));
    }

}
