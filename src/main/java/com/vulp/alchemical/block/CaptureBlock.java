package com.vulp.alchemical.block;

import com.vulp.alchemical.block.entity.BlockEntityRegistry;
import com.vulp.alchemical.block.entity.CaptureBlockEntity;
import com.vulp.alchemical.item.CaptureItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// TODO: For some reason the outlines rendered on a translucent block are also translucent, which I don't want since it lets you see through the block.
public abstract class CaptureBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty MULTIBLOCK = BooleanProperty.create("multiblock");

    public CaptureBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(MULTIBLOCK, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CaptureBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
            level.getBlockEntity(pos, BlockEntityRegistry.HOLDER_BLOCK.get()).ifPresent((blockEntity) -> {
                if (stack.hasTag()) {
                    CompoundTag targetInfo = stack.getOrCreateTagElement("held_elemental");
                    if (!targetInfo.isEmpty()) {
                        blockEntity.putElemental(targetInfo);
                    }
                }
            });
        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> drops = super.getDrops(state, builder);
        drops.forEach(stack -> {
            if (stack.getItem() instanceof CaptureItem) {
                if (builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY) instanceof CaptureBlockEntity blockEntity) {
                    if (blockEntity.getElemental() != null) {
                        stack.addTagElement("held_elemental", blockEntity.getElemental());
                    }
                }
            }
        });
        return drops;
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

}
