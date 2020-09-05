package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.entity.DarkVexEntity;
import owmii.losttrinkets.entity.Entities;
import owmii.losttrinkets.item.Itms;

public class DarkEggTrinket extends Trinket<DarkEggTrinket> {
    public DarkEggTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        World world = entity.getEntityWorld();
        DamageSource source = event.getSource();
        Entity trueSource = source.getTrueSource();
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (trueSource instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) trueSource;
                if (trinkets.isActive(Itms.DARK_EGG)) {
                    int entities = world.getEntitiesWithinAABB(DarkVexEntity.class, new AxisAlignedBB(player.getPosition()).grow(16)).size();
                    if (entities < 6) {
                        for (int i = 0; i < 3; i++) {
                            DarkVexEntity vex = Entities.DARK_VEX.create(world);
                            if (vex != null && world instanceof IServerWorld) {
                                vex.onInitialSpawn((IServerWorld) world, world.getDifficultyForLocation(player.getPosition()), SpawnReason.MOB_SUMMONED, null, null);
                                vex.setAttackTarget(living);
                                vex.setOwner(player);
                                vex.setBoundOrigin(player.getPosition());
                                vex.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
                                world.addEntity(vex);
                            }
                        }
                    }
                }
            }
        }
    }
}
