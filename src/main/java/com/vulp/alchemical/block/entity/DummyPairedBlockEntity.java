package com.vulp.alchemical.block.entity;

import com.vulp.alchemical.block.AlchemicalBlockStateProperties;
import com.vulp.alchemical.block.BlockRegistry;
import com.vulp.alchemical.block.CaptureBlock;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DummyPairedBlockEntity extends BlockEntity implements IPairedBlockEntity {

    private PairedBlockDataHolder dataHolderReference = null;
    private BlockPos neighborPos = null;

    public DummyPairedBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.PAIRED_BLOCK_DUMMY.get(), pos, state);
    }

    public void setHolder(PairedBlockDataHolder holder) {
        this.dataHolderReference = holder;
    }

    public void setNeighborPos(BlockPos pos) {
        this.neighborPos = pos;
    }

    @Override
    public void onLoad() {
        if (getHolder() == null) {
            Level level = getLevel();
            if (level != null) {
                BlockEntity blockEntity = level.getBlockEntity(getBlockPos().relative(getBlockState().getValue(AlchemicalBlockStateProperties.NEIGHBOR)));
                if (blockEntity instanceof IPairedBlockEntity neighbor) {
                    setHolder(neighbor.getHolder());
                }
            }
        }
        super.onLoad();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        setNeighborPos(BlockPos.of(tag.getLong("neighborPos")));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putLong("neighborPos", neighborPos.asLong());
        super.saveAdditional(tag);
    }

    // TODO: Set up client ticking in the dummy block class.
    public void clientTick(ClientLevel level) {
        getNeighborBlockEntity().dummyClientTick(level, getBlockPos());
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
        BlockEntity neighbor = (BlockEntity) getNeighborBlockEntity();
        if (neighbor != null) {
            return neighbor.getCapability(cap, side);
        }
        return super.getCapability(cap, side);
    }

    @Nullable
    public IPairedBlockEntity getNeighborBlockEntity() {
        if (this.level == null) {
            return null;
        }

        BlockEntity blockEntity = this.level.getBlockEntity(getNeighborPos());
        if (blockEntity instanceof IPairedBlockEntity) {
            return (IPairedBlockEntity) blockEntity;
        } else {
            return null;
        }
    }

    @Nullable
    private BlockPos getNeighborPos() {
        return this.neighborPos;
    }

    @Override
    public boolean isMainPart() {
        return false;
    }

    @Override
    @Nullable
    public PairedBlockDataHolder getHolder() {
        return this.dataHolderReference;
    }

    @Override
    public BlockState getRevertState() {
        Block container;
        switch(this.dataHolderReference.getTier()) {
            case FUSION -> container = BlockRegistry.FUSION_CONTAINER.get();
            case PRIMAL -> container = BlockRegistry.PRIMAL_CONTAINER.get();
            case ARCANE -> container = BlockRegistry.ARCANE_CONTAINER.get();
            default -> container = BlockRegistry.ELEMENTAL_CONTAINER.get();
        }
        return container.defaultBlockState().setValue(CaptureBlock.FACING, this.getBlockState().getValue(BlockStateProperties.FACING));
    }

    @Override
    public void dummyClientTick(ClientLevel level, BlockPos dummyPos) {
        // This is never used because we instead use the non-dummy block's instructions.
    }

}
