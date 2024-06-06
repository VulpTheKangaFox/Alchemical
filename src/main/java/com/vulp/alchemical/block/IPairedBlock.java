package com.vulp.alchemical.block;

import com.vulp.alchemical.block.entity.IPairedBlockEntity;
import com.vulp.alchemical.block.entity.PairedBlockDataHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface IPairedBlock {

    boolean isMainPart();

    default BlockPos getNeighborPos(BlockState currentState, BlockPos currentPos) {
        return currentPos.relative(currentState.getValue(AlchemicalBlockStateProperties.NEIGHBOR));
    }

    default BlockState getNeighborState(LevelAccessor level, BlockState currentState, BlockPos currentPos) {
        return level.getBlockState(currentPos.relative(currentState.getValue(AlchemicalBlockStateProperties.NEIGHBOR)));
    }

    default VoxelShape getOutline(BlockState state) {
        Direction dir = state.getValue(AlchemicalBlockStateProperties.NEIGHBOR);
        return Shapes.or(Shapes.block(), Shapes.block().move(dir.getStepX(), dir.getStepY(), dir.getStepZ()));
    }

    Block revertBlock(LevelAccessor level, BlockPos pos);

    Property<?>[] getCarriableBlockProperties();

    default void tryContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof IPairedBlockEntity pairedBlockEntity) {
            if (pairedBlockEntity.isMainPart()) {
                player.openMenu((MenuProvider) blockentity);
                giveStat(player, 1);
            } else {
                player.openMenu((MenuProvider) pairedBlockEntity.getHolder().getNeighborBlockEntity(isMainPart()));
                ((IPairedBlock)getNeighborState(level, level.getBlockState(pos), pos).getBlock()).giveStat(player, 1);
            }
        }
    }

    default boolean checkNeighborValid(LevelAccessor level, BlockState currentState, BlockPos currentPos) {
        Direction dir = currentState.getValue(AlchemicalBlockStateProperties.NEIGHBOR);
        BlockState neighborState = level.getBlockState(currentPos.relative(dir));
        return neighborState.getBlock() instanceof IPairedBlock && neighborState.getValue(AlchemicalBlockStateProperties.NEIGHBOR) == dir.getOpposite();
    }

    void giveStat(Player player, int increment);

    default BlockState onDeconstruction(BlockState pState, LevelAccessor pLevel, BlockPos pCurrentPos) {
        if (!checkNeighborValid(pLevel, pState, pCurrentPos)) {
            BlockState newState = carryBlockProperties(pState, revertBlock(pLevel, pCurrentPos).defaultBlockState());
            return handleBlockEntityChange(pLevel, pCurrentPos, newState); // This is done here to make sure the data is carried to a different block entity.
        }
        return pState;
    }

    @SuppressWarnings("unchecked")
    default <S extends Comparable<S>> BlockState carryBlockProperties(BlockState oldState, BlockState newState) {
        Property<?>[] rawProperties = getCarriableBlockProperties();
        for (Property<?> rawProperty : rawProperties) {
            Property<S> property = (Property<S>) rawProperty;
            if (oldState.getValues().containsKey(property) && newState.getValues().containsKey(property)) {
                S value = oldState.getValue(property);
                newState = newState.setValue(property, value);
            }
        }
        return newState;
    }

    BlockState handleBlockEntityChange(LevelAccessor level, BlockPos pos, BlockState newState);

    default boolean isPowered(LevelAccessor level, BlockState currentState, BlockPos currentPos) {
        BlockEntity blockEntity = level.getBlockEntity(currentPos);
        if (blockEntity instanceof IPairedBlockEntity) {
            PairedBlockDataHolder holder = ((IPairedBlockEntity) blockEntity).getHolder();
            return holder.getCurrentElemental().filter(e -> e.equals(holder.getRequiredElemental())).isPresent();
        }
        return false;
    }

}
