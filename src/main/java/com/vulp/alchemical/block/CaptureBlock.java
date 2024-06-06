package com.vulp.alchemical.block;

import com.vulp.alchemical.block.entity.BlockEntityRegistry;
import com.vulp.alchemical.block.entity.CaptureContainerBlockEntity;
import com.vulp.alchemical.entity.ElementalTier;
import com.vulp.alchemical.item.CaptureContainerItem;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class CaptureBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private final ElementalTier maxTier;

    public CaptureBlock(ElementalTier maxTier, Properties pProperties) {
        super(pProperties);
        this.maxTier = maxTier;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CaptureContainerBlockEntity(pos, state);
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
        super.setPlacedBy(level, pos, state, placer, stack);
        level.getBlockEntity(pos, BlockEntityRegistry.CAPTURE_BLOCK.get()).ifPresent((blockEntity) -> {
            if (stack.hasTag()) {
                CompoundTag targetInfo = stack.getTagElement("held_elemental");
                if (targetInfo != null && !targetInfo.isEmpty()) {
                    blockEntity.setElementalTags(targetInfo);
                }
            }
        });
    }

    // TODO: Make sure this still writes tags to drops as intended.
    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> drops = super.getDrops(state, builder);
        drops.forEach(stack -> {
            if (stack.getItem() instanceof CaptureContainerItem) {
                if (builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY) instanceof CaptureContainerBlockEntity blockEntity) {
                    CompoundTag elementalTags = blockEntity.getElementalTags();
                    if (elementalTags != null && !elementalTags.isEmpty()) {
                        stack.getOrCreateTagElement("held_elemental").merge(elementalTags);
                    }
                }
            }
        });
        return drops;
    }

    public ElementalTier getMaxTier() {
        return maxTier;
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
