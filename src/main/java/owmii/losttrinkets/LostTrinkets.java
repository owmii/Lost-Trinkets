package owmii.losttrinkets;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import owmii.lib.api.IMod;
import owmii.lib.network.Network;
import owmii.lib.util.FML;
import owmii.losttrinkets.block.Blcks;
import owmii.losttrinkets.client.Sounds;
import owmii.losttrinkets.client.handler.KeyHandler;
import owmii.losttrinkets.client.render.entity.EntityRenderer;
import owmii.losttrinkets.client.render.tile.TileRenderer;
import owmii.losttrinkets.client.screen.Screens;
import owmii.losttrinkets.config.Configs;
import owmii.losttrinkets.entity.Entities;
import owmii.losttrinkets.handler.DataManager;
import owmii.losttrinkets.handler.UnlockManager;
import owmii.losttrinkets.item.Itms;
import owmii.losttrinkets.network.Packets;

@Mod(LostTrinkets.MOD_ID)
public class LostTrinkets implements IMod {
    public static final String MOD_ID = "losttrinkets";
    public static final Network NET = new Network(MOD_ID);

    public LostTrinkets() {
        Blcks.REG.init();
        Itms.REG.init();
        Sounds.REG.init();
        Entities.REG.init();

        loadListeners();
        Configs.register();
    }

    @Override
    public void setup(FMLCommonSetupEvent event) {
        DataManager.register();
        Packets.register();
        Entities.register();
    }

    @Override
    public void client(FMLClientSetupEvent event) {
        if (FML.isClient()) {
            TileRenderer.register();
            Screens.register();
            KeyHandler.register();
            EntityRenderer.register();
        }
    }

    @Override
    public void loadComplete(FMLLoadCompleteEvent event) {
        UnlockManager.init();
    }
}