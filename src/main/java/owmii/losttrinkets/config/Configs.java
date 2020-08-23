package owmii.losttrinkets.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import owmii.lib.config.Config;
import owmii.losttrinkets.LostTrinkets;

public class Configs {
    public static final GeneralConfig GENERAL;
    private static final ForgeConfigSpec GENERAL_SPEC;

    public static void register() {
        final String path = Config.createConfigDir(LostTrinkets.MOD_ID);
        Config.registerCommon(GENERAL_SPEC, LostTrinkets.MOD_ID + "/general_common.toml");
    }

    static {
        final Pair<GeneralConfig, ForgeConfigSpec> generalPair = Config.get(GeneralConfig::new);
        GENERAL = generalPair.getLeft();
        GENERAL_SPEC = generalPair.getRight();
    }
}
