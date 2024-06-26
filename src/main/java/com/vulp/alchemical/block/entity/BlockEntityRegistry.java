package com.vulp.alchemical.block.entity;

import com.vulp.alchemical.Alchemical;
import com.vulp.alchemical.block.BlockRegistry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Alchemical.MOD_ID);

    public static final RegistryObject<BlockEntityType<CaptureContainerBlockEntity>> CAPTURE_BLOCK = BLOCK_ENTITIES.register("capture_block",
            () -> BlockEntityType.Builder.of(CaptureContainerBlockEntity::new,
                    BlockRegistry.ELEMENTAL_CONTAINER.get(),
                    BlockRegistry.FUSION_CONTAINER.get(),
                    BlockRegistry.PRIMAL_CONTAINER.get(),
                    BlockRegistry.ARCANE_CONTAINER.get()
            ).build(null));

    public static final RegistryObject<BlockEntityType<MainPairedBlockEntity>> TEST = BLOCK_ENTITIES.register("test_main",
            () -> BlockEntityType.Builder.of(MainPairedBlockEntity::new, BlockRegistry.TEST_MAIN.get()).build(null));
    public static final RegistryObject<BlockEntityType<DummyPairedBlockEntity>> PAIRED_BLOCK_DUMMY = BLOCK_ENTITIES.register("test_dummy",
            () -> BlockEntityType.Builder.of(DummyPairedBlockEntity::new, BlockRegistry.TEST_DUMMY.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
