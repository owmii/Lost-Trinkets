package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;

public class GoldenSwatterTrinket extends Trinket<GoldenSwatterTrinket> {
    public GoldenSwatterTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onHurt(LivingHurtEvent event) {
        LivingEntity living = event.getEntityLiving();
        if (living instanceof SilverfishEntity || living instanceof EndermiteEntity) {
            Entity entity = event.getSource().getImmediateSource();
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
                if (trinkets.isActive(Itms.GOLDEN_SWATTER)) {
                    if (living.isNonBoss()) {
                        living.setHealth(0.5F);
                    }
                }
            }
        }
    }
}
