package owmii.losttrinkets.api;

import net.minecraft.entity.player.PlayerEntity;
import owmii.losttrinkets.api.player.PlayerData;
import owmii.losttrinkets.api.trinket.Trinkets;

import javax.annotation.Nullable;
import java.util.Objects;

public class LostTrinketsAPI {
    @Nullable
    private static ILostTrinketsAPI instance = null;

    /**
     * Internal use only.
     */
    public static void init(ILostTrinketsAPI impl) {
        LostTrinketsAPI.instance = impl;
    }

    public static ILostTrinketsAPI get() {
        return Objects.requireNonNull(instance);
    }

    public static Trinkets getTrinkets(PlayerEntity player) {
        return get().getTrinkets(player);
    }

    public static PlayerData getData(PlayerEntity player) {
        return get().getData(player);
    }
}
