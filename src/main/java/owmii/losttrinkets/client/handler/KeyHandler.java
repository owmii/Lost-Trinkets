package owmii.losttrinkets.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import owmii.lib.client.util.MC;
import owmii.losttrinkets.client.screen.TrinketsScreen;
import owmii.losttrinkets.item.trinkets.MagnetoTrinket;

import static net.minecraft.client.util.InputMappings.INPUT_INVALID;
import static net.minecraft.client.util.InputMappings.Type.KEYSYM;
import static net.minecraftforge.client.settings.KeyConflictContext.IN_GAME;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class KeyHandler {
    public static final String TRINKET_CATEGORY = "key.categories.losttrinkets";
    public static final KeyBinding TRINKET_GUI = new KeyBinding("key.losttrinkets.trinket", IN_GAME, KEYSYM, GLFW_KEY_R, TRINKET_CATEGORY);
    public static final KeyBinding MAGNETO = new KeyBinding("key.losttrinkets.magneto", IN_GAME, INPUT_INVALID, TRINKET_CATEGORY);

    public static void register() {
        ClientRegistry.registerKeyBinding(TRINKET_GUI);
        ClientRegistry.registerKeyBinding(MAGNETO);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (TRINKET_GUI.isPressed()) {
            Minecraft.getInstance().displayGuiScreen(new TrinketsScreen());
        }
        if (MAGNETO.isPressed()) {
            MC.player().ifPresent(MagnetoTrinket::trySendCollect);
        }
    }
}
