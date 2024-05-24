package com.vulp.alchemical.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ElementalItemUtil {

    // Swaps the elemental in the provided container item with the elemental data provided and returns the elemental that was in the item so it can be applied to the source of the elemental data. Null for no elemental.
    // TODO: Needs testing!
    @Nullable
    public static CompoundTag transferElemental(ItemStack stack, @Nullable CompoundTag externalData) {
        CompoundTag heldNewData = null;
        CompoundTag externalNewData = null;
        if (externalData != null && externalData.contains("held_elemental")) {
            CompoundTag externalOldData = externalData.getCompound("held_elemental");
            if (externalOldData.contains("elemental_info")) {
                heldNewData = externalOldData;
            }
        }
        if (stack.getItem() instanceof CaptureContainerItem) {
            CompoundTag heldOldData = stack.getOrCreateTagElement("held_elemental");
            if (heldOldData.contains("elemental_info")) {
                externalNewData = heldOldData;
                stack.removeTagKey("held_elemental");
                if (heldNewData != null) {
                    stack.getOrCreateTagElement("held_elemental").merge(heldNewData);
                }
            }
        }
        return externalNewData;
    }

}
