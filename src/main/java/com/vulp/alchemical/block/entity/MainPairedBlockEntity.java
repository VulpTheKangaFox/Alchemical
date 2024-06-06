package com.vulp.alchemical.block.entity;

import com.vulp.alchemical.entity.ElementalTier;
import com.vulp.alchemical.entity.EntityRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MainPairedBlockEntity extends AbstractFurnaceBlockEntity implements IPairedBlockEntity {

    private final PairedBlockDataHolder dataHolder;

    public MainPairedBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.TEST.get(), pos, state, RecipeType.SMELTING);
        this.dataHolder = new PairedBlockDataHolder(this, ElementalTier.ELEMENTAL, 5, EntityRegistry.FIRE_ELEMENTAL.get(), Fluids.LAVA);
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.furnace"); // TODO: Change.
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int pId, Inventory pPlayer) {
        return new FurnaceMenu(pId, pPlayer, this, this.dataAccess); // TODO: Change.
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MainPairedBlockEntity blockEntity) {
        PairedBlockDataHolder holder = blockEntity.getHolder();
        Optional<EntityType<?>> currentElemental = holder.getCurrentElemental();
        if (currentElemental.isPresent() && currentElemental.get() == holder.getRequiredElemental()) {
            blockEntity.dataAccess.set(0, 200);
        } else {
            blockEntity.dataAccess.set(0, 0);
        }






        boolean flag1 = false;
        if (blockEntity.isLit()) {
            --blockEntity.litTime;
        }

        ItemStack fuelStack = blockEntity.items.get(1); // Will not exist once we sort out fluids. Need a smart way to dispose of this.
        boolean flag2 = !blockEntity.items.get(0).isEmpty();
        boolean flag3 = !fuelStack.isEmpty();
        if (blockEntity.isLit() || flag3 && flag2) {
            Recipe<?> recipe;
            if (flag2) {
                recipe = blockEntity.quickCheck.getRecipeFor(blockEntity, level).orElse(null);
            } else {
                recipe = null;
            }

            int i = blockEntity.getMaxStackSize();
            if (!blockEntity.isLit() && blockEntity.canBurn(level.registryAccess(), recipe, blockEntity.items, i)) {
                blockEntity.litTime = blockEntity.getBurnDuration(fuelStack);
                blockEntity.litDuration = blockEntity.litTime;
                if (blockEntity.isLit()) {
                    flag1 = true;
                    if (fuelStack.hasCraftingRemainingItem())
                        blockEntity.items.set(1, fuelStack.getCraftingRemainingItem());
                    else
                    if (flag3) {
                        Item item = fuelStack.getItem();
                        fuelStack.shrink(1);
                        if (fuelStack.isEmpty()) {
                            blockEntity.items.set(1, fuelStack.getCraftingRemainingItem());
                        }
                    }
                }
            }

            if (blockEntity.isLit() && blockEntity.canBurn(level.registryAccess(), recipe, blockEntity.items, i)) {
                ++blockEntity.cookingProgress;
                if (blockEntity.cookingProgress == blockEntity.cookingTotalTime) {
                    blockEntity.cookingProgress = 0;
                    blockEntity.cookingTotalTime = getTotalCookTime(level, blockEntity);
                    if (blockEntity.burn(level.registryAccess(), recipe, blockEntity.items, i)) {
                        blockEntity.setRecipeUsed(recipe);
                    }

                    flag1 = true;
                }
            } else {
                blockEntity.cookingProgress = 0;
            }
        } else if (!blockEntity.isLit() && blockEntity.cookingProgress > 0) {
            blockEntity.cookingProgress = Mth.clamp(blockEntity.cookingProgress - 2, 0, blockEntity.cookingTotalTime);
        }

        if (flag != blockEntity.isLit()) {
            flag1 = true;
            state = state.setValue(AbstractFurnaceBlock.LIT, Boolean.valueOf(blockEntity.isLit()));
            level.setBlock(pos, state, 3);
        }

        if (flag1) {
            setChanged(level, pos, state);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag = this.dataHolder.writeToNBT(tag);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.dataHolder.readFromNBT(tag);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.dataHolder.onLoad();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.dataHolder.invalidate();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return this.dataHolder.handleCapabilities(cap, side, super.getCapability(cap, side));
    }

    @Override
    public boolean isMainPart() {
        return true;
    }

    @Override
    public PairedBlockDataHolder getHolder() {
        return this.dataHolder;
    }

    @Override
    public BlockState getRevertState() {
        return Blocks.FURNACE.defaultBlockState();
    }

    @Override
    public void dummyClientTick(ClientLevel level, BlockPos dummyPos) {

    }

}
