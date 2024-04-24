package com.vulp.alchemical.event;

import com.vulp.alchemical.Alchemical;
import com.vulp.alchemical.entity.*;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Alchemical.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AlchemicalEvents {

    // NOTE: REGISTER ENTITY ATTRIBUTES HERE!
    @SubscribeEvent
    public static void entityAttributeCreationEvent(EntityAttributeCreationEvent event) {
        event.put(EntityRegistry.FIRE_ELEMENTAL.get(), FireElemental.setAttributes());
        event.put(EntityRegistry.WATER_ELEMENTAL.get(), WaterElemental.setAttributes());
        event.put(EntityRegistry.EARTH_ELEMENTAL.get(), EarthElemental.setAttributes());
        event.put(EntityRegistry.AIR_ELEMENTAL.get(), AirElemental.setAttributes());
    }

}
