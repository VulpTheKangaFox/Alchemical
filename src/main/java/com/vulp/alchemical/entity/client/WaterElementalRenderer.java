package com.vulp.alchemical.entity.client;

import com.vulp.alchemical.Alchemical;
import com.vulp.alchemical.entity.WaterElemental;
import com.vulp.alchemical.entity.client.model.WaterElementalModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class WaterElementalRenderer<T extends WaterElemental> extends MobRenderer<T, WaterElementalModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Alchemical.MOD_ID, "textures/entity/water_elemental.png");

    public WaterElementalRenderer(EntityRendererProvider.Context context) {
        super(context, new WaterElementalModel<>(context.bakeLayer(WaterElementalModel.LAYER)), 0.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(WaterElemental entity) {
        return TEXTURE;
    }

    @Nullable
    @Override
    protected RenderType getRenderType(T pLivingEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing) {
        return RenderType.entityTranslucent(getTextureLocation(pLivingEntity));
    }

}
