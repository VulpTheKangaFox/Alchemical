package com.vulp.alchemical.entity.client;

import com.vulp.alchemical.Alchemical;
import com.vulp.alchemical.entity.FireElemental;
import com.vulp.alchemical.entity.client.model.FireElementalModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class FireElementalRenderer<T extends FireElemental> extends MobRenderer<T, FireElementalModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Alchemical.MOD_ID, "textures/entity/fire_elemental.png");

    public FireElementalRenderer(EntityRendererProvider.Context context) {
        super(context, new FireElementalModel<>(context.bakeLayer(FireElementalModel.LAYER)), 0.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(FireElemental entity) {
        return TEXTURE;
    }

    @Nullable
    @Override
    protected RenderType getRenderType(T pLivingEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing) {
        return RenderType.entityTranslucent(getTextureLocation(pLivingEntity));
    }

    @Override
    protected int getBlockLightLevel(FireElemental entity, BlockPos pos) {
        return 15;
    }

}
