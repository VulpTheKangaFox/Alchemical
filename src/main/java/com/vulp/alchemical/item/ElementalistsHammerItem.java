package com.vulp.alchemical.item;

import com.vulp.alchemical.block.BlockRegistry;
import com.vulp.alchemical.block.ElementalContainerBlock;
import com.vulp.alchemical.block.IPairedBlock;
import com.vulp.alchemical.block.entity.CaptureContainerBlockEntity;
import com.vulp.alchemical.block.entity.DummyPairedBlockEntity;
import com.vulp.alchemical.block.entity.IPairedBlockEntity;
import com.vulp.alchemical.block.entity.PairedBlockDataHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class ElementalistsHammerItem extends Item {

    public static BlockPos RENDER_OVERLAY_POS = null;

    public ElementalistsHammerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos clickedPos = pContext.getClickedPos();

        BlockState clickedState = level.getBlockState(clickedPos);
        if (clickedState.getBlock() instanceof ElementalContainerBlock && level.getBlockEntity(clickedPos) instanceof CaptureContainerBlockEntity captureBlockEntity) {
        /*if (level.isClientSide()) {
            RENDER_OVERLAY_POS = clickedPos;
        }*/
            BlockPos belowPos = clickedPos.below();
            BlockState belowState = level.getBlockState(belowPos);
            if (belowState.getBlock() instanceof FurnaceBlock) {
                CompoundTag elementalData = captureBlockEntity.getElementalTags();
                BlockState dummyState = BlockRegistry.TEST_DUMMY.get().defaultBlockState();
                BlockState mainState = BlockRegistry.TEST_MAIN.get().defaultBlockState();
                dummyState = ((IPairedBlock) dummyState.getBlock()).carryBlockProperties(clickedState, dummyState);
                mainState = ((IPairedBlock) mainState.getBlock()).carryBlockProperties(belowState, mainState);
                level.setBlock(clickedPos, dummyState, 3);
                level.setBlock(belowPos, mainState, 3);
                level.markAndNotifyBlock(belowPos, level.getChunkAt(belowPos), level.getBlockState(belowPos), mainState, 3, 512);
                marryPairedBlocks(level, belowPos, clickedPos, elementalData);
                level.gameEvent(pContext.getPlayer(), GameEvent.BLOCK_CHANGE, clickedPos);
                level.gameEvent(pContext.getPlayer(), GameEvent.BLOCK_CHANGE, belowPos);
                return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(pContext);
    }

    // TODO: Make sure to use this to register paired block positions.
    private void marryPairedBlocks(Level level, BlockPos mainPos, BlockPos dummyPos, CompoundTag elementalData) {
        if (level.getBlockEntity(mainPos) instanceof IPairedBlockEntity mainBlockEntity && level.getBlockEntity(dummyPos) instanceof DummyPairedBlockEntity otherBlockEntity) {
            PairedBlockDataHolder holder = mainBlockEntity.getHolder();
            holder.initNeighborPos(dummyPos);
            otherBlockEntity.setHolder(holder);
            otherBlockEntity.setNeighborPos(mainPos);
            holder.setElementalTags(elementalData);
        }
    }

    @Override
    public boolean useOnRelease(ItemStack pStack) {
        RENDER_OVERLAY_POS = null;
        return super.useOnRelease(pStack);
    }

}
