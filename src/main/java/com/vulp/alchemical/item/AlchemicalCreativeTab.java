package com.vulp.alchemical.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AlchemicalCreativeTab {

    public static final CreativeModeTab TAB = new CreativeModeTab("alchemical_tab") {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemRegistry.LEAD_INGOT.get());
        }

    };

}
