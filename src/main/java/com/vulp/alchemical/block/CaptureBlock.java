package com.vulp.alchemical.block;

import com.vulp.alchemical.block.entity.BlockEntityRegistry;
import com.vulp.alchemical.block.entity.CaptureContainerBlockEntity;
import com.vulp.alchemical.entity.ElementalTier;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class CaptureBlock extends BaseEntityBlock implements ICaptureBlock{

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty MULTIBLOCK = BooleanProperty.create("multiblock");
    private final ElementalTier maxTier;

    public CaptureBlock(ElementalTier maxTier, Properties pProperties) {
        super(pProperties);
        this.maxTier = maxTier;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(MULTIBLOCK, false));
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
            level.getBlockEntity(pos, BlockEntityRegistry.CAPTURE_BLOCK.get()).ifPresent((blockEntity) -> {
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
        return updateDrops(super.getDrops(state, builder), builder);
    }

    @Override
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
        pBuilder.add(FACING, MULTIBLOCK);
    }

}
