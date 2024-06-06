package com.vulp.alchemical.block.entity;

import com.vulp.alchemical.entity.ElementalTier;
import com.vulp.alchemical.entity.EntityRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.LazyOptional;


abstract class AbstractMainPairedBlockEntity extends BaseContainerBlockEntity {

    private final PairedBlockDataHolder dataHolder;

    public AbstractMainPairedBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.TEST.get(), pos, state);
        this.dataHolder = setupDataHolder();
    }

    abstract PairedBlockDataHolder setupDataHolder();

    public PairedBlockDataHolder getHolder() {
        return this.dataHolder;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag = getHolder().writeToNBT(tag);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        getHolder().readFromNBT(tag);
    }

    BlockState getRevertState();

    void dummyClientTick(ClientLevel level, BlockPos dummyPos);

    @Override
    public void onLoad() {
        super.onLoad();
        getHolder().setLazyFluidHandler(LazyOptional.of(() -> getHolder().getTank()));
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        getHolder().getLazyFluidHandler().invalidate();
    }
}
