package com.vulp.alchemical.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CaptureContainerBlockEntity extends BlockEntity implements IElementalCaptureBlockEntity {

    private CompoundTag elemental = new CompoundTag();

    public CaptureContainerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.CAPTURE_BLOCK.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.elemental != null) {
            tag.put("held_elemental", this.elemental);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        CompoundTag elementalInfo = tag.getCompound("held_elemental");
        if (!elementalInfo.isEmpty()) {
            this.elemental = elementalInfo;
        }
    }

    @Override
    public CompoundTag getElementalTags() {
        return this.elemental;
    }

    @Override
    public void setElementalTags(CompoundTag elementalInfo) {
        this.elemental = elementalInfo;
    }

}
