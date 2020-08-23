package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.Event;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;

public class TeaLeafTrinket extends Trinket<TeaLeafTrinket> {
    public TeaLeafTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onPotion(PotionEvent.PotionApplicableEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity instanceof PlayerEntity) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets((PlayerEntity) entity);
            if (trinkets.isActive(Itms.TEA_LEAF)) {
                Effect effect = event.getPotionEffect().getPotion();
                if (effect.equals(Effects.WITHER) || effect.equals(Effects.POISON)) {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

    @Override
    public void onActivated(World world, BlockPos pos, PlayerEntity player) {
        if (world.isRemote) return;
        player.removePotionEffect(Effects.POISON);
        player.removePotionEffect(Effects.WITHER);
    }
}
