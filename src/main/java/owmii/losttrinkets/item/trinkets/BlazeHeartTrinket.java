package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.item.Itms;

public class BlazeHeartTrinket extends Trinket<BlazeHeartTrinket> {
    public BlazeHeartTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static boolean isImmuneToFire(LivingEntity target, DamageSource source) {
        if (target instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) target;
            if (LostTrinketsAPI.getTrinkets(player).isActive(Itms.BLAZE_HEART)) {
                if (source.isFireDamage()) {
                    player.extinguish();
                    return true;
                }
            }
        }
        return false;
    }
}
