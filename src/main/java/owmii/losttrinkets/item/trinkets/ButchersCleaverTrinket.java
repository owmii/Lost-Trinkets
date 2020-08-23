package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.item.Itms;

public class ButchersCleaverTrinket extends Trinket<ButchersCleaverTrinket> {
    public ButchersCleaverTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void dropExtra(LivingDropsEvent event) {
        DamageSource source = event.getSource();
        if (source.getTrueSource() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) source.getTrueSource();
            if (LostTrinketsAPI.getTrinkets(player).isActive(Itms.BUTCHERS_CLEAVER)) {
                LivingEntity target = event.getEntityLiving();
                if (target instanceof AnimalEntity) {
                    if (target.world.rand.nextInt(10) == 0) {
                        ItemStack stack = new ItemStack(Items.BONE, target.world.rand.nextInt(2) + 1);
                        event.getDrops().add(new ItemEntity(target.world, target.getPosX(), target.getPosY(), target.getPosZ(), stack));
                    }
                }
            }
        }
    }
}
