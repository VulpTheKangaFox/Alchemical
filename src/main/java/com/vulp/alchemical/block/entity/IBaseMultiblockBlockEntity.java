package com.vulp.alchemical.block.entity;

import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public interface IBaseMultiblockBlockEntity extends IElementalCaptureBlockEntity {

    IBaseMultiblockBlockEntity oppositePart();

    IBaseMultiblockBlockEntity getCapturePart();

    IBaseMultiblockBlockEntity getLogicPart();

    LazyOptional<?> getCaptureFluidHandler();

    LazyOptional<?>[] getLogicHandlers();

    default boolean attemptFluidDrain(boolean isBlockRunning) {
        if (fluidTank().getCapacity() > fluidDrainRate() && isBlockRunning) {
            fluidTank().drain(fluidDrainRate(), IFluidHandler.FluidAction.EXECUTE);
            return true;
        }
        return false;
    }

    // TODO: Replace with config amounts someday
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

    // TODO: Replace with config amounts someday
    int fluidDrainRate();

    FluidTank fluidTank();

}