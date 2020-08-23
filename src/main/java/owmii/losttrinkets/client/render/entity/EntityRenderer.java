package owmii.losttrinkets.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import owmii.losttrinkets.entity.Entities;

@OnlyIn(Dist.CLIENT)
public class EntityRenderer {
    public static void register() {
        final Minecraft mc = Minecraft.getInstance();
        RenderingRegistry.registerEntityRenderingHandler(Entities.DARK_VEX, DarkVexRenderer::new);
    }
}
