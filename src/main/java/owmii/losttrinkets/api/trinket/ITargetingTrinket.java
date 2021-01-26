package owmii.losttrinkets.api.trinket;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

public interface ITargetingTrinket extends ITrinket {
    /**
     * Checks if the given mob is prevented from targeting the player.
     * Boss mobs are not prevented.
     *
     * @param mob         Mob that is trying to target a player
     * @param player      Player that has this trinket activated
     * @param notAttacked If the mob has not been attacked by the player
     */
    boolean preventTargeting(MobEntity mob, PlayerEntity player, boolean notAttacked);
}
