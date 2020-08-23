package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;

public class FireMindTrinket extends Trinket<FireMindTrinket> {
    public FireMindTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity instanceof MobEntity) {
            MobEntity mob = (MobEntity) entity;
            LivingEntity target = mob.getAttackTarget();
            if (target instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) target;
                Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
                if (trinkets.isActive(Itms.FIRE_MIND) && !mob.isImmuneToFire()) {
                    mob.setFire(3);
                }
            }
        }
    }
}
