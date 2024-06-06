package com.vulp.alchemical.block;

import com.vulp.alchemical.block.entity.CaptureContainerBlockEntity;
import com.vulp.alchemical.block.entity.DummyPairedBlockEntity;
import com.vulp.alchemical.block.entity.IPairedBlockEntity;
import com.vulp.alchemical.block.entity.PairedBlockDataHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class TestDummyBlock extends BaseEntityBlock implements IPairedBlock {

    public static final DirectionProperty NEIGHBOR = AlchemicalBlockStateProperties.NEIGHBOR;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public TestDummyBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(NEIGHBOR, Direction.DOWN).setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new DummyPairedBlockEntity(pPos, pState);
    }

    @Override
    public @NotNull InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            tryContainer(pLevel, pPos, pPlayer);
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return getOutline(pState);
    }

    @Override
    public boolean isMainPart() {
        return false;
    }

    @Override
    public Block revertBlock(LevelAccessor level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof IPairedBlockEntity) {
            PairedBlockDataHolder holder = ((IPairedBlockEntity) blockEntity).getHolder();
            if (holder != null) {
                return switch (holder.getTier()) {
                    case FUSION -> BlockRegistry.FUSION_CONTAINER.get();
                    case PRIMAL -> BlockRegistry.PRIMAL_CONTAINER.get();
                    case ARCANE -> BlockRegistry.ARCANE_CONTAINER.get();
                    default -> BlockRegistry.ELEMENTAL_CONTAINER.get();
                };
            }
        }
        return Blocks.AIR;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(LIT)) {
            Random rand = new Random();
            double x = (double)pPos.getX() + 0.5D + rand.nextDouble(0.4D) - 0.2D;
            double y = (double)pPos.getY() + 1.1D + rand.nextDouble(0.4D) - 0.2D;
            double z = (double)pPos.getZ() + 0.5D + rand.nextDouble(0.4D) - 0.2D;
            pLevel.addParticle(ParticleTypes.LARGE_SMOKE, x, y, z, 0.0D, 0.05D, 0.0D);
        }
    }

    @Override
    public Property<?>[] getCarriableBlockProperties() {
        return new Property[]{ HorizontalDirectionalBlock.FACING };
    }

    @Override
    public void giveStat(Player player, int increment) {
        // Empty because we don't need it (I think)
    }

    @Override
    public @NotNull BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return onDeconstruction(pState, pLevel, pCurrentPos);
    }

    @Override
    public BlockState handleBlockEntityChange(LevelAccessor level, BlockPos pos, BlockState newState) { // NOTE: Refer to this when setting up other multiblock transfers.
        // Grab old stuff.
        BlockEntity oldBlockEntity = level.getBlockEntity(pos);
        CompoundTag oldElemental = null;
        if (oldBlockEntity instanceof DummyPairedBlockEntity) {
            PairedBlockDataHolder holder = ((DummyPairedBlockEntity) oldBlockEntity).getHolder();
            if (holder != null) {
                oldElemental = holder.getElementalTags();
            }
        }
        // Set new block early.
        level.setBlock(pos, newState, 3);
        // Insert old stuff into new block entity.
        BlockEntity newBlockEntity = level.getBlockEntity(pos);
        if (newBlockEntity instanceof CaptureContainerBlockEntity && oldElemental != null) {
            ((CaptureContainerBlockEntity) newBlockEntity).setElementalTags(oldElemental);
        }
        return newState;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NEIGHBOR, FACING, LIT);
    }

}
