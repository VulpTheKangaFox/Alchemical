package com.vulp.alchemical.event;

import com.vulp.alchemical.Alchemical;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Alchemical.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AlchemicalClientEvents {

    // ???? This is where I would put the cute rendering for the hammer functionality when it's ready.
    public static void onRenderLevelStageEvent(RenderLevelStageEvent event) {
        /*Minecraft mc = Minecraft.getInstance();
        BlockPos blockpos2 = ElementalistsHammerItem.RENDER_OVERLAY_POS;
        Vec3 vec3 = event.getCamera().getPosition();
        double d0 = vec3.x();
        double d1 = vec3.y();
        double d2 = vec3.z();
        double d3 = (double)blockpos2.getX() - d0;
        double d4 = (double)blockpos2.getY() - d1;
        double d5 = (double)blockpos2.getZ() - d2;
        if (!(d3 * d3 + d4 * d4 + d5 * d5 > 1024.0D)) {
            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();
            poseStack.translate((double)blockpos2.getX() - d0, (double)blockpos2.getY() - d1, (double)blockpos2.getZ() - d2);
            PoseStack.Pose posestack$pose1 = poseStack.last();
            VertexConsumer vertexconsumer1 = new SheetedDecalTextureGenerator(mc.renderBuffers.crumblingBufferSource().getBuffer(ModelBakery.DESTROY_TYPES.get(k)), posestack$pose1.pose(), posestack$pose1.normal(), 1.0F);
            net.minecraftforge.client.model.data.ModelData modelData = level.getModelDataManager().getAt(blockpos2);
            mc.getBlockRenderer().getModelRenderer().renderModel(poseStack.last(), event.getBuffer(Sheets.solidBlockSheet()), (BlockState)null, modelmanager.getModel(modelresourcelocation), 1.0F, 1.0F, 1.0F, pPackedLight, OverlayTexture.NO_OVERLAY);
            (mc.level.getBlockState(blockpos2), blockpos2, mc.level, poseStack, vertexconsumer1, modelData == null ? net.minecraftforge.client.model.data.ModelData.EMPTY : modelData);
            poseStack.popPose();
        }*/
    }

}
