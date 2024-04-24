package com.vulp.alchemical;

import com.mojang.logging.LogUtils;
import com.vulp.alchemical.block.BlockRegistry;
import com.vulp.alchemical.block.entity.BlockEntityRegistry;
import com.vulp.alchemical.entity.EntityRegistry;
import com.vulp.alchemical.item.AlchemicalCreativeModeTabs;
import com.vulp.alchemical.item.ItemRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Alchemical.MOD_ID)
public class Alchemical {
    public static final String MOD_ID = "alchemical";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Alchemical() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemRegistry.register(modEventBus);
        BlockRegistry.register(modEventBus);

        EntityRegistry.register(modEventBus);
        BlockEntityRegistry.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        AlchemicalCreativeModeTabs.addAlchemicalCreativeTabs(event);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }

    }

}
