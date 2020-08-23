package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;

public class ThaSpiderTrinket extends Trinket<ThaSpiderTrinket> {
    public ThaSpiderTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static boolean doClimb(LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets((PlayerEntity) entity);
            if (trinkets.isActive(Itms.THA_SPIDER)) {
                return entity.collidedHorizontally;
            }
        }
        return false;
    }
}
