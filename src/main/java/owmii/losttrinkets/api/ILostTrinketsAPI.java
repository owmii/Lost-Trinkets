package owmii.losttrinkets.api;

import net.minecraft.entity.player.PlayerEntity;
import owmii.losttrinkets.api.player.PlayerData;
import owmii.losttrinkets.api.trinket.ITrinket;
import owmii.losttrinkets.api.trinket.Trinkets;

public interface ILostTrinketsAPI {
    /**
     * Unlock a specific Trinket for a Player.
     *
     * @return true iff the trinket was successfully unlocked.
     */
    boolean unlock(PlayerEntity player, ITrinket trinket);

    /**
     * Unlock a random Trinket for a Player.
     */
    void unlock(PlayerEntity player);

    /**
     * Get the {@link Trinkets} data for a player.
     */
    Trinkets getTrinkets(PlayerEntity player);

    /**
     * Get the {@link PlayerData} for a player.
     */
    PlayerData getData(PlayerEntity player);
}
