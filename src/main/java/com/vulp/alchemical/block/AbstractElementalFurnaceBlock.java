package com.vulp.alchemical.block;

import com.vulp.alchemical.block.entity.CaptureContainerBlockEntity;
import com.vulp.alchemical.entity.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractElementalFurnaceBlock extends AbstractFurnaceBlock {

    protected AbstractElementalFurnaceBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (level.getBlockState(pos.above()).getBlock() instanceof CaptureBlock block && block.getMaxTier().getValue() > 0) {
            // Validity check 1 passed!
        } else {
            // Break block and replace with normal furnace!
        }
        BlockEntity blockEntity = level.getBlockEntity(pos.above());
        if (blockEntity instanceof CaptureContainerBlockEntity captureContainerBlockEntity && captureContainerBlockEntity.getElemental().stream().anyMatch(e -> e == EntityRegistry.FIRE_ELEMENTAL.get())) {
            // Validity check 2 passed!
            // Furnace powered!
        } else {
            // Furnace unpowered!
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }
}
