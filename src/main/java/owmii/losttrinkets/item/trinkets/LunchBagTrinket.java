package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;

public class LunchBagTrinket extends Trinket<LunchBagTrinket> {
    public LunchBagTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onUseFinish(LivingEntityUseItemEvent.Finish event) {
        LivingEntity entity = event.getEntityLiving();
        World world = entity.getEntityWorld();
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (event.getItem().isFood()) {
                Food food = event.getItem().getItem().getFood();
                if (food != null && food.getEffects().isEmpty()) {
                    if (trinkets.isActive(Itms.LUNCH_BAG) && world.rand.nextInt(10) == 0) {
                        player.addPotionEffect(new EffectInstance(Effects.SATURATION, world.rand.nextInt(200) + 100, 1, false, false));
                    }
                }
            }
        }
    }
}
