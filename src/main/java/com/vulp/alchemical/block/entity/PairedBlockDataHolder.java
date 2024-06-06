package com.vulp.alchemical.block.entity;

import com.vulp.alchemical.entity.AbstractElemental;
import com.vulp.alchemical.entity.ElementalTier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PairedBlockDataHolder {

    private final ElementalTier containerTier;
    private final EntityType<? extends AbstractElemental> requiredElemental;
    private CompoundTag currentElemental;
    private final FluidTank tank;
    private final int drainRate;
    private final Set<Fluid> acceptedFluids;
    private LazyOptional<IFluidHandler> lazyFluidHandler;
    private final BlockEntity blockEntity;
    private BlockPos dummyPos = null;
    private boolean empowered = false;

    public PairedBlockDataHolder() {
        this(null, ElementalTier.ELEMENTAL, 0, null);
    }

    // TODO: This does not get saved and loaded. Needs to be. Start with a blank constructor and fill the load/save methods with all the data needed to reconstruct properly.
    public PairedBlockDataHolder(BlockEntity blockEntity, ElementalTier tier, int drainRate, @Nullable EntityType<? extends AbstractElemental> requiredElemental, Fluid... acceptedFluids) {
        this.containerTier = tier;
        this.requiredElemental = requiredElemental;
        this.currentElemental = new CompoundTag();
        this.tank = new FluidTank(calcTankAmount()) {
            @Override
            protected void onContentsChanged() {
                onHolderChanged();
            }
        };
        this.drainRate = drainRate;
        this.acceptedFluids = new HashSet<>(Set.of(acceptedFluids));
        this.lazyFluidHandler = LazyOptional.empty();
        this.blockEntity = blockEntity;
    }

    public ElementalTier getTier() {
        return this.containerTier;
    }

    public CompoundTag getElementalTags() {
        return this.currentElemental;
    }

    public void setElementalTags(CompoundTag elementalInfo) {
        this.currentElemental = elementalInfo;
        this.onHolderChanged();
    }

    // Recommended method to use where possible. Attempts to return the old elemental in cases in where we trade elementals.
    public CompoundTag switchElementalTags(CompoundTag elementalInfo) {
        CompoundTag currentElemental = new CompoundTag();
        if (!getElementalTags().isEmpty()) {
            currentElemental = getElementalTags().copy();
        }
        setElementalTags(elementalInfo);
        this.onHolderChanged();
        return currentElemental;
    }

    public Optional<EntityType<?>> getCurrentElemental() {
        return EntityType.by(getElementalTags().getCompound("elemental_info"));
    }

    public EntityType<? extends AbstractElemental> getRequiredElemental() {
        return requiredElemental;
    }

    public void onHolderChanged() {
        this.blockEntity.setChanged();
    }

    public FluidTank getTank() {
        return this.tank;
    }

    public boolean isFluidCorrect() {
        Fluid currentFluid = getFluidStack().getFluid();
        return acceptedFluids.contains(currentFluid);
    }

    public void setEmpowered(boolean empower) {
        this.empowered = empower;
    }

    public boolean isEmpowered() {
        return this.empowered;
    }

    public FluidStack getFluidStack() {
        return this.tank.getFluid();
    }

    public int getFluidAmount() {
        return this.tank.getFluidAmount();
    }

    public void setFluidStack(FluidStack fluidStack) {
        this.tank.setFluid(fluidStack);
    }

    // Can be positive or negative
    public void modifyFluidAmount(int amount) {
        this.tank.getFluid().setAmount(this.tank.getFluidAmount() + amount);
    }

    private int calcTankAmount() {
        switch (this.getTier()) {
            case FUSION -> {
                return  4000;
            }
            case PRIMAL -> {
                return  8000;
            }
            case ARCANE -> {
                return  16000;
            }
            default -> {
                return 2000;
            }
        }
    }

    public int getDrainRate() {
        return this.drainRate;
    }

    // Attempts to drain the fluid. If it drains, returns true. Great to gate fluid-based functionality and handle fluid-reduction all in one.
    public boolean tryDrainFluid(boolean isBlockActive) {
        FluidStack fluid = getFluidStack();
        int drainRate = getDrainRate();
        if (isFluidCorrect() && fluid.getAmount() > drainRate && isBlockActive) {
            this.tank.drain(drainRate, IFluidHandler.FluidAction.EXECUTE);
            return true;
        }
        return false;
    }

    public LazyOptional<IFluidHandler> getLazyFluidHandler() {
        return this.lazyFluidHandler;
    }

    public void setLazyFluidHandler(LazyOptional<IFluidHandler> lazyFluidHandler) {
        this.lazyFluidHandler = lazyFluidHandler;
    }

    // NOTE: Needs to be inserted into BlockEntity.invalidateCaps()
    public void invalidate() {
        this.lazyFluidHandler.invalidate();
    }

    // NOTE: Needs to be inserted into BlockEntity.getCapability()
    public @NotNull <T> LazyOptional<T> handleCapabilities(@NotNull Capability<T> cap, @Nullable Direction side, @NotNull LazyOptional<T> superMethod) {
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return this.lazyFluidHandler.cast();
        }
        return superMethod;
    }

    // NOTE: Needs to be inserted into BlockEntity.load()
    public void readFromNBT(CompoundTag tag) {
        CompoundTag elementalInfo = tag.getCompound("held_elemental");
        if (!elementalInfo.isEmpty()) {
            this.currentElemental = elementalInfo;
        }
        tank.readFromNBT(tag);
        this.dummyPos = BlockPos.of(tag.getLong("dummyPos"));
    }

    // NOTE: Needs to be inserted into BlockEntity.saveAdditional()
    public CompoundTag writeToNBT(CompoundTag tag) {
        if (this.currentElemental != null) {
            tag.put("held_elemental", this.currentElemental);
        }
        tag = tank.writeToNBT(tag);
        tag.putLong("dummyPos", dummyPos.asLong());
        return tag;
    }

    @Nullable
    public BlockPos getDummyPos() {
        return this.dummyPos;
    }

    public void initNeighborPos(BlockPos pos) {
        this.dummyPos = pos;
    }

    @Nullable
    public BlockEntity getNeighborBlockEntity(boolean isMain) {
        if (!isMain) {
            return this.blockEntity;
        }
        Level level = this.blockEntity.getLevel();
        BlockPos dummyPos = getDummyPos();
        BlockEntity blockEntity = (level != null && dummyPos != null) ? level.getBlockEntity(dummyPos) : null;
        return (blockEntity instanceof IPairedBlockEntity) ? blockEntity : null;
    }

}
