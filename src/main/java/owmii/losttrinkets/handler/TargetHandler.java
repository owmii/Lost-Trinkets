package owmii.losttrinkets.handler;

import net.minecraft.entity.IAngerable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import owmii.losttrinkets.api.LostTrinketsAPI;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

@Mod.EventBusSubscriber
public class TargetHandler {
    public static boolean preventTargeting(LivingEntity attacker, @Nullable LivingEntity target) {
        if (attacker instanceof MobEntity) {
            MobEntity mob = (MobEntity) attacker;
            if (target instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) target;

                // Always allow targeting for boss mobs
                if (!mob.isNonBoss()) {
                    return false;
                }

                // Not recently attacked by the player
                boolean notAttacked = !player.equals(mob.getRevengeTarget()) && !player.equals(mob.getCombatTracker().getBestAttacker());

                return LostTrinketsAPI.getTrinkets(player).getTargeting().stream()
                        .anyMatch(trinket -> trinket.preventTargeting(mob, player, notAttacked));
            }
        }
        return false;
    }

    public static <T> Optional<T> getBrainMemorySafe(Brain<?> brain, MemoryModuleType<T> type) {
        return brain.hasMemory(type) ? brain.getMemory(type) : Optional.empty();
    }

    @SubscribeEvent
    public static void setTarget(LivingSetAttackTargetEvent event) {
        LivingEntity living = event.getEntityLiving();
        if (living instanceof MobEntity && preventTargeting(living, event.getTarget())) {
            MobEntity mob = (MobEntity) living;
            mob.setAttackTarget(null);
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity living = event.getEntityLiving();
        if (living instanceof MobEntity) {
            MobEntity mob = (MobEntity) living;
            // Remove anger target (mainly for sounds)
            if (mob instanceof IAngerable) {
                IAngerable angerable = (IAngerable) mob;
                UUID targetUUID = angerable.getAngerTarget();
                if (targetUUID != null && preventTargeting(mob, mob.world.getPlayerByUuid(targetUUID))) {
                    // Resets anger timer and target
                    angerable.func_241356_K__();
                }
            }
            // Remove attack target
            if (preventTargeting(mob, mob.getAttackTarget())) {
                mob.setAttackTarget(null);
            }
            // Remove attack target memory from brain
            Brain<?> brain = mob.getBrain();
            getBrainMemorySafe(brain, MemoryModuleType.ATTACK_TARGET).ifPresent(target -> {
                if (preventTargeting(mob, target)) {
                    brain.removeMemory(MemoryModuleType.ATTACK_TARGET);
                }
            });
        }
    }
}
