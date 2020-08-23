package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.item.Itms;

public class CreepoTrinket extends Trinket {
    public CreepoTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void resetExplosion(CriticalHitEvent event) {
        if (LostTrinketsAPI.getTrinkets(event.getPlayer()).isActive(Itms.CREEPO)) {
            Entity target = event.getTarget();
            if (target instanceof CreeperEntity) {
                ((CreeperEntity) target).setCreeperState(-1);
                ((CreeperEntity) target).timeSinceIgnited = 0;
            }
        }
    }
}
