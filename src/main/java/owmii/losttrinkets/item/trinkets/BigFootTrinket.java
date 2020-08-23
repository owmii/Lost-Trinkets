package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.entity.ai.BigFootGoal;
import owmii.losttrinkets.item.Itms;

public class BigFootTrinket extends Trinket<BigFootTrinket> {
    public BigFootTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void joinWorld(Entity entity) {
        if (entity instanceof MonsterEntity) {
            MonsterEntity mob = (MonsterEntity) entity;
            if (mob.isChild()) {
                mob.goalSelector.addGoal(1, new BigFootGoal(mob));
            }
        }
    }

    public static void setTarget(LivingEntity entity, LivingEntity target) {
        if (entity instanceof MonsterEntity && entity.isChild() && entity.isNonBoss()) {
            if (target instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) target;
                Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
                if (trinkets.isActive(Itms.BIG_FOOT)) {
                    ((MonsterEntity) entity).setAttackTarget(null);
                }
            }
        }
    }
}
