package com.vulp.alchemical.block;

import com.vulp.alchemical.block.entity.BlockEntityRegistry;
import com.vulp.alchemical.block.entity.MainPairedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class TestMainBlock extends FurnaceBlock implements IPairedBlock {

    public static final DirectionProperty NEIGHBOR = AlchemicalBlockStateProperties.NEIGHBOR;

    public TestMainBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(NEIGHBOR, Direction.UP));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) { // IMPORTANT!
        return new MainPairedBlockEntity(pPos, pState);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, BlockEntityRegistry.TEST.get(), MainPairedBlockEntity::serverTick);
    }

    @Override
    protected void openContainer(Level pLevel, BlockPos pPos, Player pPlayer) {
        tryContainer(pLevel, pPos, pPlayer);
    }

    @Override
    public void giveStat(Player player, int increment) {
        player.awardStat(Stats.INTERACT_WITH_FURNACE);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        // TODO: Determine particles.
        if (pState.getValue(LIT)) {
            double d0 = (double)pPos.getX() + 0.5D;
            double d1 = (double)pPos.getY();
            double d2 = (double)pPos.getZ() + 0.5D;
            if (pRandom.nextDouble() < 0.1D) {
                pLevel.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = pState.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d3 = 0.52D;
            double d4 = pRandom.nextDouble() * 0.6D - 0.3D;
            double d5 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : d4;
            double d6 = pRandom.nextDouble() * 6.0D / 16.0D;
            double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : d4;
            pLevel.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
            pLevel.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return getOutline(pState);
    }

    @Override
    public boolean isMainPart() {
        return true;
    }

    @Override
    public Block revertBlock(LevelAccessor level, BlockPos pos) {
        return Blocks.FURNACE;
    }

    @Override
    public Property<?>[] getCarriableBlockProperties() {
        return new Property[]{ HorizontalDirectionalBlock.FACING };
    }

    @Override
    public @NotNull BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return onDeconstruction(pState, pLevel, pCurrentPos);
    }

    @Override
    public BlockState handleBlockEntityChange(LevelAccessor level, BlockPos pos, BlockState newState) { // NOTE: Refer to this when setting up other multiblock transfers.
        /*// Grab old stuff.
        BlockEntity oldBlockEntity = level.getBlockEntity(pos);
        ItemStack oldFuel = null;
        if (oldBlockEntity instanceof TestPairedBlockEntity) {
            oldFuel = ((TestPairedBlockEntity) oldBlockEntity).getItem(1);
        }
        // Set new block early.
        level.setBlock(pos, newState, 3);
        // Insert old stuff into new block entity.
        BlockEntity newBlockEntity = level.getBlockEntity(pos);
        if (newBlockEntity instanceof FurnaceBlockEntity && oldFuel != null) {
            ((FurnaceBlockEntity) newBlockEntity).setItem(1, oldFuel);
        }*/
        return newState;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NEIGHBOR, FACING, LIT);
    }

}
