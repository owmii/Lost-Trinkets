package owmii.losttrinkets;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import owmii.lib.api.IClient;
import owmii.lib.api.IMod;
import owmii.lib.network.Network;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.block.Blcks;
import owmii.losttrinkets.client.Client;
import owmii.losttrinkets.client.Sounds;
import owmii.losttrinkets.config.Configs;
import owmii.losttrinkets.entity.Entities;
import owmii.losttrinkets.handler.DataManager;
import owmii.losttrinkets.impl.LostTrinketsAPIImpl;
import owmii.losttrinkets.item.Itms;
import owmii.losttrinkets.network.Packets;

import javax.annotation.Nullable;

@Mod(LostTrinkets.MOD_ID)
public class LostTrinkets implements IMod {
    public static final String MOD_ID = "losttrinkets";
    public static final Network NET = new Network(MOD_ID);
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public LostTrinkets() {
        Blcks.REG.init();
        Itms.REG.init();
        Entities.REG.init();
        Sounds.REG.init();

        LostTrinketsAPI.init(new LostTrinketsAPIImpl());

        loadListeners();
        Configs.register(this);
    }

    @Override
    public void setup(FMLCommonSetupEvent event) {
        DataManager.register();
        Packets.register();
        Entities.register();
    }

    @Nullable
    @Override
    public IClient getClient() {
        return Client.INSTANCE;
    }
}