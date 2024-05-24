package com.vulp.alchemical.block.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Optional;

public interface ILogicMultiblockBlockEntity extends IBaseMultiblockBlockEntity {

    @Override
    default IBaseMultiblockBlockEntity getCapturePart() {
        return oppositePart();
    }

    @Override
    default IBaseMultiblockBlockEntity getLogicPart() {
        return this;
    }

    @Override
    default CompoundTag getElementalTags() {
        return getCapturePart().getElementalTags();
    }

    @Override
    default void setElementalTags(CompoundTag elementalInfo) {
        getCapturePart().setElementalTags(elementalInfo);
    }

    @Override
    default Optional<EntityType<?>> getEntityFromTags() {
        return getCapturePart().getEntityFromTags();
    }

    @Override
    default CompoundTag switchElementalTags(CompoundTag elementalInfo) {
        return getCapturePart().switchElementalTags(elementalInfo);
    }

    @Override
    default LazyOptional<?> getCaptureFluidHandler() {
        return getCapturePart().getCaptureFluidHandler();
    }

    // Probs won't need here. Instead put on the capture-half of the multiblock.
    /*@Override
    default boolean attemptFluidDrain(boolean isBlockRunning) {
        if (fluidTank().getCapacity() > fluidDrainRate() && isBlockRunning) {
            fluidTank().drain(fluidDrainRate(), IFluidHandler.FluidAction.EXECUTE);
            return true;
        }
        return false;
    }

    @Override
    default int fluidTankAmount() {
        switch (getTier()) {
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

    @Override
    int fluidDrainRate();

    @Override
    FluidTank fluidTank();*/
}
