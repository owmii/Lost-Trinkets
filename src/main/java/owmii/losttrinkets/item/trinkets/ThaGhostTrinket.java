package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;

public class ThaGhostTrinket extends Trinket<ThaGhostTrinket> {
    public ThaGhostTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onSetAttackTarget(LivingSetAttackTargetEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity instanceof MobEntity && entity.isNonBoss()) {
            if (event.getTarget() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) event.getTarget();
                Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
                if (player.isPotionActive(Effects.INVISIBILITY) && trinkets.isActive(Itms.THA_GHOST)) {
                    ((MobEntity) entity).setAttackTarget(null);
                }
            }
        }
    }
}
