package com.vulp.alchemical.entity.client;

import com.vulp.alchemical.Alchemical;
import com.vulp.alchemical.entity.AirElemental;
import com.vulp.alchemical.entity.client.model.AirElementalModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class AirElementalRenderer<T extends AirElemental> extends MobRenderer<T, AirElementalModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Alchemical.MOD_ID, "textures/entity/air_elemental.png");

    public AirElementalRenderer(EntityRendererProvider.Context context) {
        super(context, new AirElementalModel<>(context.bakeLayer(AirElementalModel.LAYER)), 0.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(AirElemental entity) {
        return TEXTURE;
    }

    @Nullable
    @Override
    protected RenderType getRenderType(T pLivingEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing) {
        return RenderType.entityTranslucent(getTextureLocation(pLivingEntity));
    }

}
