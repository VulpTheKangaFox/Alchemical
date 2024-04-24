package com.vulp.alchemical.entity.client;

import com.vulp.alchemical.Alchemical;
import com.vulp.alchemical.entity.EntityRegistry;
import com.vulp.alchemical.entity.client.model.AirElementalModel;
import com.vulp.alchemical.entity.client.model.EarthElementalModel;
import com.vulp.alchemical.entity.client.model.FireElementalModel;
import com.vulp.alchemical.entity.client.model.WaterElementalModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Alchemical.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientHandler {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(EntityRegistry.FIRE_ELEMENTAL.get(), FireElementalRenderer::new);
        event.registerEntityRenderer(EntityRegistry.WATER_ELEMENTAL.get(), WaterElementalRenderer::new);
        event.registerEntityRenderer(EntityRegistry.EARTH_ELEMENTAL.get(), EarthElementalRenderer::new);
        event.registerEntityRenderer(EntityRegistry.AIR_ELEMENTAL.get(), AirElementalRenderer::new);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(FireElementalModel.LAYER, FireElementalModel::createBodyLayer);
        event.registerLayerDefinition(WaterElementalModel.LAYER, WaterElementalModel::createBodyLayer);
        event.registerLayerDefinition(EarthElementalModel.LAYER, EarthElementalModel::createBodyLayer);
        event.registerLayerDefinition(AirElementalModel.LAYER, AirElementalModel::createBodyLayer);
    }

}
