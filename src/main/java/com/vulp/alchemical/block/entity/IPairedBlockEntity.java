package com.vulp.alchemical.block.entity;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public interface IPairedBlockEntity {

    boolean isMainPart();

    PairedBlockDataHolder getHolder();

    BlockState getRevertState();

    void dummyClientTick(ClientLevel level, BlockPos dummyPos);

}
