package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;

public class ThaWizardTrinket extends Trinket<ThaWizardTrinket> {
    public ThaWizardTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onSetAttackTarget(LivingSetAttackTargetEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity instanceof WitchEntity && event.getTarget() instanceof PlayerEntity) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets((PlayerEntity) event.getTarget());
            if (trinkets.isActive(Itms.THA_WIZARD)) {
                ((WitchEntity) entity).setAttackTarget(null);
            }
        }
    }
}
