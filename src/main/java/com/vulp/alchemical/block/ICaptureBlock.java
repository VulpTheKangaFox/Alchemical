package com.vulp.alchemical.block;

import com.vulp.alchemical.block.entity.IElementalCaptureBlockEntity;
import com.vulp.alchemical.entity.ElementalTier;
import com.vulp.alchemical.item.CaptureContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;

public interface ICaptureBlock {

    ElementalTier getMaxTier();

    default List<ItemStack> updateDrops(List<ItemStack> oldDrops, LootContext.Builder builder) {
        oldDrops.forEach(stack -> {
            if (stack.getItem() instanceof CaptureContainerItem) {
                if (builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY) instanceof IElementalCaptureBlockEntity blockEntity) {
                    if (blockEntity.getElementalInfo() != null) {
                        stack.addTagElement("held_elemental", blockEntity.getElementalInfo());
                    }
                }
            }
        });
        return oldDrops;
    }
}
