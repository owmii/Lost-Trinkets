package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.Event;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;

public class CoffeeBeanTrinket extends Trinket<CoffeeBeanTrinket> {
    public CoffeeBeanTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onPotion(PotionEvent.PotionApplicableEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity instanceof PlayerEntity) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets((PlayerEntity) entity);
            if (trinkets.isActive(Itms.COFFEE_BEAN)) {
                Effect effect = event.getPotionEffect().getPotion();
                if (effect.equals(Effects.NAUSEA) || effect.equals(Effects.MINING_FATIGUE) || effect.equals(Effects.SLOWNESS)) {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }
}
