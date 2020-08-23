package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;

import java.util.List;

public class MadPiggyTrinket extends Trinket<MadPiggyTrinket> {
    public MadPiggyTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        World world = entity.getEntityWorld();
        Entity trueSource = event.getSource().getTrueSource();
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (trueSource instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) trueSource;
                if (trinkets.isActive(Itms.MAD_PIGGY)) {
                    AxisAlignedBB bb = new AxisAlignedBB(player.getPosition()).grow(24.0D);
                    List<ZombifiedPiglinEntity> entities = world.getEntitiesWithinAABB(ZombifiedPiglinEntity.class, bb);
                    for (ZombifiedPiglinEntity zombifiedPiglin : entities) {
                        zombifiedPiglin.setRevengeTarget(living);
                        zombifiedPiglin.setAttackTarget(living);
                        world.playSound(null, living.getPosX(), living.getPosY(), living.getPosZ(), SoundEvents.ENTITY_ZOMBIFIED_PIGLIN_ANGRY, SoundCategory.HOSTILE, 1.5F, 1.0F);
                    }
                }
            }
        }
    }
}
