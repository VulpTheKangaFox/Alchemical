package com.vulp.alchemical.item;

import com.mojang.blaze3d.platform.InputConstants;
import com.vulp.alchemical.block.CaptureBlock;
import com.vulp.alchemical.block.entity.CaptureBlockEntity;
import com.vulp.alchemical.entity.AbstractElemental;
import com.vulp.alchemical.entity.ElementalTier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.List;

public class CaptureItem extends BlockItem {

    protected static final List<String> IGNORED_TAGS = Arrays.asList("Air", "ArmorDropChances", "ArmorItems", "Brain", "CanPickUpLoot", "DeathTime", "FallDistance", "FallFlying", "Fire", "HandDropChances", "HandItems", "HurtByTimestamp", "HurtTime", "LeftHanded", "Motion", "OnGround", "PortalCooldown", "Pos", "Rotation", "Passengers", "Leash", "UUID");
    private final ElementalTier maxTier;

    public CaptureItem(ElementalTier maxTier, Block block, Properties properties) {
        super(block, properties);
        this.maxTier = maxTier;
    }

    @Override
    public Object getRenderPropertiesInternal() {
        return super.getRenderPropertiesInternal();
    }

    public ElementalTier getMaxTier() {
        return this.maxTier;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (target.isAlive() && target instanceof AbstractElemental) {
            if (((AbstractElemental) target).getTier().getValue() <= getMaxTier().getValue()) {
                CompoundTag itemInfo = stack.getOrCreateTagElement("held_elemental");
                if (itemInfo.isEmpty()) {
                    CompoundTag targetInfo = target.serializeNBT().copy();
                    if (!player.level.isClientSide()) {
                        target.discard();
                        cleanTags(targetInfo);
                        itemInfo.put("elemental_info", targetInfo);
                        itemInfo.putString("elemental_name", target.getDisplayName().getString());
                        itemInfo.putInt("elemental_color", ((AbstractElemental)target).decimalColor());
                        return InteractionResult.SUCCESS;
                    }
                }
            } else {
                player.displayClientMessage(Component.translatable("block.alchemical.capture_block.wrong_tier"), true);
            }
        }
        return super.interactLivingEntity(stack, player, target, hand);
    }

    // TODO: Transfer an elemental from a held container to a placed container and vice-versa.
    // NOTE: Right-Click to place, Shift+Right-Click to let elemental out.
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (player != null) {
            if (player.isCrouching()) {
                return super.useOn(context);
            }
            ItemStack stack = context.getItemInHand();
            CompoundTag targetInfo = stack.getOrCreateTagElement("held_elemental");
            boolean flag = targetInfo.isEmpty();

            BlockPos pos = context.getClickedPos();
            BlockState state = level.getBlockState(pos);

            boolean isClient = !(level instanceof ServerLevel);
            if (state.getBlock() instanceof CaptureBlock && level.getBlockEntity(pos) instanceof CaptureBlockEntity blockEntity) {

                CompoundTag newInfo = blockEntity.putElemental(targetInfo);

                if (isClient && targetInfo != newInfo) {
                    Minecraft mc = Minecraft.getInstance();
                    mc.gui.toolHighlightTimer = (int)(40.0D * mc.options.notificationDisplayTime().get());
                }

                stack.removeTagKey("held_elemental");
                if (!newInfo.isEmpty()) {
                    stack.getOrCreateTagElement("held_elemental").merge(newInfo);
                }
                return InteractionResult.SUCCESS;
            }

            if (!flag) {
                if (isClient) {
                    return InteractionResult.SUCCESS;
                } else {
                    Direction dir = context.getClickedFace();
                    BlockPos finalPos = state.getCollisionShape(level, pos).isEmpty() ? pos : pos.relative(dir);

                    Entity e = EntityType.loadEntityRecursive(targetInfo.getCompound("elemental_info"), level, func -> func);
                    if (e != null) {
                        e.setPos(finalPos.getCenter().add(0.0D, -0.3D, 0.0D));
                        level.addFreshEntity(e);
                        stack.removeTagKey("held_elemental");
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.CONSUME;
    }

    static void cleanTags(CompoundTag targetInfo) {
        for(String s : IGNORED_TAGS) {
            targetInfo.remove(s);
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        MutableComponent text = super.getName(stack).copy();
        if (stack.hasTag()) {
            CompoundTag targetInfo = stack.getOrCreateTagElement("held_elemental");
            if (!targetInfo.isEmpty()) {
                text.append(Component.literal(" (").withStyle(ChatFormatting.ITALIC));
                String name = targetInfo.getString("elemental_name");
                if (name.isEmpty()) {
                    text.append(Component.literal("???").withStyle(ChatFormatting.BLACK));
                } else {
                    text.append(Component.translatable(name).withStyle(Style.EMPTY.withColor(targetInfo.getInt("elemental_color")).withItalic(true)));
                }
                text.append(Component.literal(")").withStyle(ChatFormatting.ITALIC));
            }
        }
        return text;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        Options options = Minecraft.getInstance().options;
        InputConstants.Key crouchKey = options.keyShift.getKey();
        Component crouchName = crouchKey.getDisplayName();
        Component placeName = options.keyUse.getKey().getDisplayName();
        if (!InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), crouchKey.getValue())) {
            tooltip.add(Component.translatable("block.alchemical.capture_block.info_minimized", Component.literal("[").append(crouchName).append("]").withStyle(ChatFormatting.BOLD)).withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY));
        } else {
            tooltip.add(Component.translatable("block.alchemical.capture_block.info_maximized", Component.literal("[").append(crouchName).append(" + ").append(placeName).append("]").withStyle(ChatFormatting.BOLD)).withStyle(ChatFormatting.GRAY));
        }
    }

    // Simplifies the NBT for the info that is needed on the client.
    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        CompoundTag tag = super.getShareTag(stack);
        if (tag == null) {
            return null;
        }
        CompoundTag targetInfo = tag.getCompound("held_elemental");
        if (targetInfo.isEmpty()) {
            targetInfo.remove("held_elemental");
            return super.getShareTag(stack);
        }
        targetInfo = targetInfo.copy();
        targetInfo.remove("elemental_info");
        return targetInfo;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.hasTag() && !stack.getOrCreateTagElement("held_elemental").getCompound("elemental_info").isEmpty();
    }

}
