package com.vulp.alchemical.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Test extends BlockEntity implements IElementalCaptureBlockEntity, IFluidHandler {

    private CompoundTag elemental = new CompoundTag();

    public Test(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.CAPTURE_BLOCK.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        if (this.elemental != null) {
            tag.put("held_elemental", this.elemental);
        }
        tag = TANK.writeToNBT(tag);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        CompoundTag elementalInfo = tag.getCompound("held_elemental");
        if (!elementalInfo.isEmpty()) {
            this.elemental = elementalInfo;
        }
        TANK.readFromNBT(tag);
    }

    @Override
    public CompoundTag getElementalTags() {
        return this.elemental;
    }

    @Override
    public void setElementalTags(CompoundTag elementalInfo) {
        this.elemental = elementalInfo;
    }



    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    private final FluidTank TANK = new FluidTank(64000) {
        @Override
        protected void onContentsChanged() {
            setChanged();

        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == Fluids.LAVA;
        }

    };

    public void setFluid(FluidStack fluid) {
        this.TANK.setFluid(fluid);
    }

    FluidStack getFluidStack() {
        return this.TANK.getFluid();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyFluidHandler = LazyOptional.of(() -> TANK);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyFluidHandler.invalidate();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }
        return super.getCapability(cap, side);
    }
}
