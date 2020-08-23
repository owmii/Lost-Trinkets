package owmii.losttrinkets.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import owmii.losttrinkets.client.screen.TrinketsScreen;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class KeyHandler {
    public static final KeyBinding TRINKET_GUI = new KeyBinding("key.losttrinkets.trinket", 82, "itemGroup.losttrinkets");

    public static void register() {
        ClientRegistry.registerKeyBinding(TRINKET_GUI);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.CLIENT && event.phase == TickEvent.Phase.START) {
            PlayerEntity player = event.player;
            if (TRINKET_GUI.isPressed()) {
                Minecraft.getInstance().displayGuiScreen(new TrinketsScreen());
            }
        }
    }
}
