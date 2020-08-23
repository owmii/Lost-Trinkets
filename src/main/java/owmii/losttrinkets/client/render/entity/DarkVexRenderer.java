package owmii.losttrinkets.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import owmii.losttrinkets.client.model.DarkVexModel;
import owmii.losttrinkets.entity.DarkVexEntity;

@OnlyIn(Dist.CLIENT)
public class DarkVexRenderer extends BipedRenderer<DarkVexEntity, DarkVexModel> {
    private static final ResourceLocation VEX_TEXTURE = new ResourceLocation("textures/entity/illager/vex.png");
    private static final ResourceLocation VEX_CHARGING_TEXTURE = new ResourceLocation("textures/entity/illager/vex_charging.png");

    public DarkVexRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new DarkVexModel(), 0.3F);
    }

    @Override
    protected int getBlockLight(DarkVexEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

    @Override
    public ResourceLocation getEntityTexture(DarkVexEntity entity) {
        return entity.isCharging() ? VEX_CHARGING_TEXTURE : VEX_TEXTURE;
    }

    @Override
    protected void preRenderCallback(DarkVexEntity vexEntity, MatrixStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(0.4F, 0.4F, 0.4F);
    }
}
