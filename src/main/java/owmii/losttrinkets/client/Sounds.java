package owmii.losttrinkets.client;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import owmii.lib.registry.Registry;
import owmii.losttrinkets.LostTrinkets;

public class Sounds {
    public static final Registry<SoundEvent> REG = new Registry<>(LostTrinkets.MOD_ID);
    public static final SoundEvent UNLOCK = register("unlock");

    static SoundEvent register(String name) {
        return REG.register(name, new SoundEvent(new ResourceLocation(LostTrinkets.MOD_ID, name)));
    }
}
