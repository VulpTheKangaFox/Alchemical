package com.vulp.alchemical.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CaptureBlockEntity extends BlockEntity {

    private CompoundTag elemental = new CompoundTag();

    public CaptureBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.HOLDER_BLOCK.get(), pos, state);
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.elemental != null) {
            tag.put("held_elemental", this.elemental);
        }
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        CompoundTag elementalInfo = tag.getCompound("held_elemental");
        if (!elementalInfo.isEmpty()) {
            this.elemental = elementalInfo;
        }
    }

    // Returns back the current elemental information in the event of swapping what's in the container with the container in hand.
    public CompoundTag putElemental(CompoundTag elementalData) {
        CompoundTag currentElemental = new CompoundTag();
        if (!this.elemental.isEmpty()) {
            currentElemental = this.elemental.copy();
        }
        this.elemental = elementalData;
        return currentElemental;
    }

    public CompoundTag getElemental() {
        return this.elemental;
    }

}
