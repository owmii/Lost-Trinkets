package owmii.losttrinkets.client.handler;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.item.Itms;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(Dist.CLIENT)
public class EventHandler {
    @SubscribeEvent
    @SuppressWarnings("unchecked")
    public static void onBreakSpeed(RenderLivingEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && mc.world != null) {
            LivingEntity living = event.getEntity();
            if (living.isInvisible() && LostTrinketsAPI.getTrinkets(mc.player).isActive(Itms.MINDS_EYE)) {
                LivingRenderer renderer = event.getRenderer();
                EntityModel model = renderer.getEntityModel();
                MatrixStack matrix = event.getMatrixStack();
                matrix.push();
                boolean shouldSit = living.isPassenger() && (living.getRidingEntity() != null && living.getRidingEntity().shouldRiderSit());
                model.isSitting = shouldSit;
                model.isChild = living.isChild();
                float partialTicks = event.getPartialRenderTick();
                float f6 = MathHelper.lerp(partialTicks, living.prevRotationPitch, living.rotationPitch);
                if (living.getPose() == Pose.SLEEPING) {
                    Direction direction = living.getBedDirection();
                    if (direction != null) {
                        float f4 = living.getEyeHeight(Pose.STANDING) - 0.1F;
                        matrix.translate((double) ((float) (-direction.getXOffset()) * f4), 0.0D, (double) ((float) (-direction.getZOffset()) * f4));
                    }
                }
                float f = MathHelper.interpolateAngle(partialTicks, living.prevRenderYawOffset, living.renderYawOffset);
                float f1 = MathHelper.interpolateAngle(partialTicks, living.prevRotationYawHead, living.rotationYawHead);
                float f2 = f1 - f;
                float f7 = living.ticksExisted + partialTicks;
                Pose pose = living.getPose();
                if (pose != Pose.SLEEPING) {
                    matrix.rotate(Vector3f.YP.rotationDegrees(180.0F - f));
                }
                if (living.deathTime > 0) {
                    float ff = ((float) living.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
                    ff = MathHelper.sqrt(ff);
                    if (ff > 1.0F) {
                        ff = 1.0F;
                    }
                    matrix.rotate(Vector3f.ZP.rotationDegrees(ff * 90.0F));
                } else if (living.isSpinAttacking()) {
                    matrix.rotate(Vector3f.XP.rotationDegrees(-90.0F - living.rotationPitch));
                    matrix.rotate(Vector3f.YP.rotationDegrees(((float) living.ticksExisted + partialTicks) * -75.0F));
                } else if (pose == Pose.SLEEPING) {
                    Direction direction = living.getBedDirection();
                    float ff1 = direction != null ? getFacingAngle(direction) : f;
                    matrix.rotate(Vector3f.YP.rotationDegrees(ff1));
                    matrix.rotate(Vector3f.ZP.rotationDegrees(90.0F));
                    matrix.rotate(Vector3f.YP.rotationDegrees(270.0F));
                }
                matrix.scale(-1.0F, -1.0F, 1.0F);
                matrix.translate(0.0D, (double) -1.501F, 0.0D);
                float f8 = 0.0F;
                float f5 = 0.0F;
                if (!shouldSit && living.isAlive()) {
                    f8 = MathHelper.lerp(partialTicks, living.prevLimbSwingAmount, living.limbSwingAmount);
                    f5 = living.limbSwing - living.limbSwingAmount * (1.0F - partialTicks);
                    if (living.isChild()) {
                        f5 *= 3.0F;
                    }

                    if (f8 > 1.0F) {
                        f8 = 1.0F;
                    }
                }
                model.setLivingAnimations(living, f5, f8, partialTicks);
                model.setRotationAngles(living, f5, f8, f7, f2, f6);
                ResourceLocation texture = renderer.getEntityTexture(living);
                RenderType rendertype = RenderType.getEntityTranslucent(texture);
                IVertexBuilder ivertexbuilder = event.getBuffers().getBuffer(rendertype);
                int ii = OverlayTexture.getPackedUV(OverlayTexture.getU(0.0F), OverlayTexture.getV(living.hurtTime > 0 || living.deathTime > 0));
                model.render(matrix, ivertexbuilder, mc.getRenderManager().getPackedLight(living, partialTicks), ii, 1.0F, 1.0F, 1.0F, 0.2F);
                matrix.pop();
            }
            if (living instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) living;
                if (player.isPotionActive(Effects.INVISIBILITY) && LostTrinketsAPI.getTrinkets(player).isActive(Itms.THA_GHOST)) {
                    event.setCanceled(true);
                }
            }
        }
    }

    private static float getFacingAngle(Direction facingIn) {
        switch (facingIn) {
            case SOUTH:
                return 90.0F;
            case NORTH:
                return 270.0F;
            case EAST:
                return 180.0F;
            default:
                return 0.0F;
        }
    }
}
