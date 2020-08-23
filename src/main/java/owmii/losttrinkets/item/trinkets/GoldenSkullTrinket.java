package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.item.Itms;

public class GoldenSkullTrinket extends Trinket<GoldenSkullTrinket> {
    public GoldenSkullTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onDrops(LivingDropsEvent event) {
        DamageSource source = event.getSource();
        if (source.getTrueSource() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) source.getTrueSource();
            if (LostTrinketsAPI.getTrinkets(player).isActive(Itms.GOLDEN_SKULL)) {
                LivingEntity target = event.getEntityLiving();
                if (target instanceof MonsterEntity) {
                    if (target.world.rand.nextInt(20) == 0) {
                        event.getDrops().add(new ItemEntity(target.world, target.getPosX(), target.getPosY(), target.getPosZ(), new ItemStack(Itms.TREASURE_BAG)));
                    }
                }
            }
        }
    }
}
