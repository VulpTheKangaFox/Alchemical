package com.vulp.alchemical.item;

import com.vulp.alchemical.block.ElementalContainerBlock;
import com.vulp.alchemical.block.entity.CaptureContainerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

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
            if (level.isClientSide()) {
                RENDER_OVERLAY_POS = clickedPos;
            }
        }
        return super.useOn(pContext);
    }

    @Override
    public boolean useOnRelease(ItemStack pStack) {
        RENDER_OVERLAY_POS = null;
        return super.useOnRelease(pStack);
    }

}
