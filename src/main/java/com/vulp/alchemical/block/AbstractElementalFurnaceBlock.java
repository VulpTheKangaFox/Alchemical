package com.vulp.alchemical.block;

import com.vulp.alchemical.block.entity.CaptureBlockEntity;
import com.vulp.alchemical.entity.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
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
        BlockEntity blockEntity = level.getBlockEntity(pos.above());
        if (level.getBlockState(pos.above()).getBlock() instanceof ElementalContainerBlock && blockEntity instanceof CaptureBlockEntity && EntityType.by(((CaptureBlockEntity) blockEntity).getElemental()).get().equals(EntityRegistry.FIRE_ELEMENTAL))
            EntityType.loadEntityRecursive(((CaptureBlockEntity) blockEntity).getElemental(), (Level) level, func -> func);
        EntityType.by(((CaptureBlockEntity) blockEntity).getElemental()).equals(EntityRegistry.FIRE_ELEMENTAL);
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }
}
