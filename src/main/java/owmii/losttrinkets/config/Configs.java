package owmii.losttrinkets.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import owmii.lib.config.Config;
import owmii.losttrinkets.LostTrinkets;

import static owmii.lib.config.Config.MARKER;
import static owmii.losttrinkets.LostTrinkets.LOGGER;

public class Configs {
    public static final GeneralConfig GENERAL;
    private static final ForgeConfigSpec GENERAL_SPEC;

    public static void register(LostTrinkets lostTrinkets) {
        final String path = Config.createConfigDir(LostTrinkets.MOD_ID);
        Config.registerCommon(GENERAL_SPEC, LostTrinkets.MOD_ID + "/general_common.toml");
        lostTrinkets.addModListener(Configs::refresh);
    }

    public static void refresh(ModConfig.ModConfigEvent event) {
        String type = event instanceof ModConfig.Loading ? "Loading" : "Reloading";
        ModConfig modConfig = event.getConfig();
        LOGGER.info(MARKER, type + " " + modConfig.getFileName());
        ForgeConfigSpec spec = modConfig.getSpec();
        if (spec == GENERAL_SPEC) {
            GENERAL.refresh();
        }
    }

    static {
        final Pair<GeneralConfig, ForgeConfigSpec> generalPair = Config.get(GeneralConfig::new);
        GENERAL = generalPair.getLeft();
        GENERAL_SPEC = generalPair.getRight();
    }
}
