package owmii.losttrinkets.client.handler.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.StringUtils;
import owmii.lib.util.Ticker;
import owmii.losttrinkets.client.screen.Textures;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(Dist.CLIENT)
public class HudHandler {
    private static final List<Toast> TOASTS = new ArrayList<>();
    private static Ticker ticker = new Ticker(60);

    @Nullable
    private static Toast toast;

    @SubscribeEvent
    public static void tick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.world == null && !TOASTS.isEmpty()) {
                TOASTS.clear();
                toast = null;
            }
            Iterator<Toast> itr = TOASTS.iterator();
            while (itr.hasNext()) {
                Toast b = itr.next();
                if (!b.getTicker().ended()) {
                    toast = b;
                    if (ticker.ended()) {
                        b.getTicker().onward();
                    }
                    ticker.add(5);
                } else {
                    ticker.back(5);
                    if (ticker.getTicks() <= 0) {
                        toast = null;
                        itr.remove();
                    }
                }
                if (toast != null)
                    break;
            }
            if (TOASTS.isEmpty()) {
                toast = null;
            }
        }
    }

    @SubscribeEvent
    public static void renderHud(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && mc.currentScreen == null) {
            render(event.getMatrixStack(), mc, event.getWindow().getScaledWidth(), event.getWindow().getScaledHeight());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void gui(GuiScreenEvent.DrawScreenEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        render(event.getMatrixStack(), mc, event.getGui().width, event.getGui().height);
    }

    static void render(MatrixStack matrix, Minecraft mc, int width, int height) {
        if (toast != null) {
            RenderSystem.pushMatrix();
            RenderSystem.translated(width / 2.0F - Textures.TOAST.getWidth() / 2.0F, 4 - 60.0F + ticker.getTicks(), 0.0F);
            Textures.TOAST.draw(matrix, 0, 0);
            RenderSystem.pushMatrix();
            RenderSystem.translated(41.0F, 5.0F, 0.0F);
            mc.fontRenderer.drawString(matrix, I18n.format("gui.losttrinkets.trinket.unlocked"), 0, 5, new Color(0xFFBA6F).getRGB());
            String s = I18n.format(toast.getTrinket().getItem().getTranslationKey());
            s = StringUtils.abbreviate(s, 20);
            mc.fontRenderer.drawString(matrix, s, 0, 18, 0xF0C6E5);
            RenderSystem.popMatrix();
            RenderSystem.pushMatrix();
            RenderSystem.translated(5.0F, 5.0F, 0.0F);
            RenderSystem.scaled(2.0F, 2.0F, 2.0F);
            mc.getItemRenderer().renderItemAndEffectIntoGUI(new ItemStack(toast.getTrinket()), 0, 0);
            RenderSystem.popMatrix();
            RenderSystem.popMatrix();
        }
    }

    public static void add(Toast toast) {
        TOASTS.add(toast);
    }
}
