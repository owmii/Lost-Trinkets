package owmii.losttrinkets.client.screen;

import net.minecraft.client.Minecraft;

public class Screens {
    public static void register() {

    }

    public static void checkScreenRefresh() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.currentScreen instanceof AbstractLTScreen) {
            ((AbstractLTScreen) mc.currentScreen).refresh();
        }
    }
}
