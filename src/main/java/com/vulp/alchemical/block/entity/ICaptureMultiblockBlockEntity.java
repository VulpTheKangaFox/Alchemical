package com.vulp.alchemical.block.entity;

import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public interface ICaptureMultiblockBlockEntity extends IBaseMultiblockBlockEntity {

    @Override
    default IBaseMultiblockBlockEntity getCapturePart() {
        return this;
    }

    @Override
    default IBaseMultiblockBlockEntity getLogicPart() {
        return oppositePart();
    }

    @Override
    default LazyOptional<?>[] getLogicHandlers() {
        return getLogicPart().getLogicHandlers();
    }

    @Override
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
    FluidTank fluidTank();
}
