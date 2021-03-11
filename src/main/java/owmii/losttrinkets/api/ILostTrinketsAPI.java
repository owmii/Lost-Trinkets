package owmii.losttrinkets.api;

import net.minecraft.entity.player.PlayerEntity;
import owmii.losttrinkets.api.player.PlayerData;
import owmii.losttrinkets.api.trinket.ITrinket;
import owmii.losttrinkets.api.trinket.Trinkets;

import java.util.Set;

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

    /**
     * Get all enabled Trinkets.
     */
    Set<ITrinket> getTrinkets();

    /**
     * @return true iff Trinket is enabled.
     */
    default boolean isEnabled(ITrinket trinket) {
        return getTrinkets().contains(trinket);
    }

    /**
     * @return true iff Trinket is disabled.
     */
    default boolean isDisabled(ITrinket trinket) {
        return !isEnabled(trinket);
    }

    /**
     * Gets trinkets that can be obtained randomly.
     * Will not contain trinkets that are not enabled.
     */
    Set<ITrinket> getRandomTrinkets();

    /**
     * @return true iff Trinket can be obtained randomly.
     */
    default boolean isRandom(ITrinket trinket) {
        return getRandomTrinkets().contains(trinket);
    }

    /**
     * @return true iff Trinket cannot be obtained randomly.
     */
    default boolean isNonRandom(ITrinket trinket) {
        return !isRandom(trinket);
    }
}
