package com.vulp.alchemical.item;

import com.vulp.alchemical.block.entity.CaptureContainerBlockEntity;
import com.vulp.alchemical.block.entity.IPairedBlockEntity;
import com.vulp.alchemical.block.entity.PairedBlockDataHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class DebuggerItem extends Item {

    public DebuggerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockEntity blockEntity = level.getBlockEntity(pContext.getClickedPos());
        if (blockEntity instanceof IPairedBlockEntity pairedBlockEntity && !level.isClientSide()) {
            PairedBlockDataHolder holder = pairedBlockEntity.getHolder();
            pContext.getPlayer().displayClientMessage(Component.literal(blockEntity.toString()), false);
        }
        if (blockEntity instanceof CaptureContainerBlockEntity captureContainerBlockEntity) {
            pContext.getPlayer().displayClientMessage(Component.literal(captureContainerBlockEntity.getEntityFromTags().toString()), false);
        }
        return super.useOn(pContext);
    }
}
