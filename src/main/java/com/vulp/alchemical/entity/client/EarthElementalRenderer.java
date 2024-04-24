package com.vulp.alchemical.entity.client;

import com.vulp.alchemical.Alchemical;
import com.vulp.alchemical.entity.EarthElemental;
import com.vulp.alchemical.entity.client.model.EarthElementalModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class EarthElementalRenderer<T extends EarthElemental> extends MobRenderer<T, EarthElementalModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Alchemical.MOD_ID, "textures/entity/earth_elemental.png");

    public EarthElementalRenderer(EntityRendererProvider.Context context) {
        super(context, new EarthElementalModel<>(context.bakeLayer(EarthElementalModel.LAYER)), 0.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(EarthElemental entity) {
        return TEXTURE;
    }

    @Nullable
    @Override
    protected RenderType getRenderType(T pLivingEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing) {
        return RenderType.entityTranslucent(getTextureLocation(pLivingEntity));
    }

}
