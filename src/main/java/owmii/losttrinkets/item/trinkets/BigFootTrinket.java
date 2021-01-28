package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import owmii.losttrinkets.api.trinket.ITargetingTrinket;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.entity.ai.BigFootGoal;

public class BigFootTrinket extends Trinket<BigFootTrinket> implements ITargetingTrinket {
    public BigFootTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void addAvoidGoal(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof CreatureEntity) {
            CreatureEntity mob = (CreatureEntity) entity;
            mob.goalSelector.addGoal(-1, new BigFootGoal(mob));
        }
    }

    public boolean preventTargeting(MobEntity mob, PlayerEntity player, boolean notAttacked) {
        return mob.isChild();
    }
}
