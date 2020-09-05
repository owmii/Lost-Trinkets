package owmii.losttrinkets.client;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import owmii.lib.api.IClient;
import owmii.losttrinkets.client.handler.KeyHandler;
import owmii.losttrinkets.client.render.entity.EntityRenderer;
import owmii.losttrinkets.client.render.tile.TileRenderer;
import owmii.losttrinkets.client.screen.Screens;

public enum Client implements IClient {
    INSTANCE;

    @Override
    public void client(FMLClientSetupEvent event) {
        TileRenderer.register();
        Screens.register();
        KeyHandler.register();
        EntityRenderer.register();
    }
}
