package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.Event;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;

import java.util.Iterator;

public class MagicalHerbsTrinket extends Trinket<MagicalHerbsTrinket> {
    public MagicalHerbsTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onPotion(PotionEvent.PotionApplicableEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity instanceof PlayerEntity) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets((PlayerEntity) entity);
            if (trinkets.isActive(Itms.MAGICAL_HERBS)) {
                Effect effect = event.getPotionEffect().getPotion();
                if (effect.getEffectType().equals(EffectType.HARMFUL)) {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

    @Override
    public void onActivated(World world, BlockPos pos, PlayerEntity player) {
        if (world.isRemote) return;
        Iterator<EffectInstance> iterator = player.getActivePotionMap().values().iterator();
        while (iterator.hasNext()) {
            EffectInstance effect = iterator.next();
            if (effect.getPotion().getEffectType().equals(EffectType.HARMFUL)) {
                player.onFinishedPotionEffect(effect);
                iterator.remove();
            }
        }
    }
}
