package owmii.losttrinkets.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.player.PlayerData;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;
import owmii.losttrinkets.item.trinkets.*;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (event.phase == TickEvent.Phase.END) {
            PlayerData data = LostTrinketsAPI.getData(player);
            if (data.unlockDelay > 0) {
                data.unlockDelay--;
            }
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            trinkets.getTickable().forEach(trinket -> trinket.tick(player.world, player.getPosition(), player));
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        FireMindTrinket.onLivingUpdate(event);
    }

    @SubscribeEvent
    public static void onExplosionStart(ExplosionEvent.Start event) {
        Entity entity = event.getExplosion().getExploder();
        if (entity instanceof CreeperEntity) {
            CreeperEntity creeper = ((CreeperEntity) entity);
            LivingEntity target = creeper.getAttackTarget();
            if (target instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) target;
                Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
                if (trinkets.isActive(Itms.CREEPO)) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void joinWorld(EntityJoinWorldEvent event) {
        BigFootTrinket.joinWorld(event.getEntity());
    }

    @SubscribeEvent
    public static void setTarget(LivingSetAttackTargetEvent event) {
        BigFootTrinket.setTarget(event.getEntityLiving(), event.getTarget());
        ThaGhostTrinket.onSetAttackTarget(event);
        ThaWizardTrinket.onSetAttackTarget(event);
    }

    @SubscribeEvent
    public static void onAttack(LivingAttackEvent event) {
        DamageSource source = event.getSource();
        if (source == null) return;
        if (BlazeHeartTrinket.isImmuneToFire(event.getEntityLiving(), event.getSource())) {
            event.setCanceled(true);
        }
        MadAuraTrinket.onAttack(event);
        //OctopusLegTrinket.onAttack(event); TODO check
    }

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        if (source == null) return;

        DarkDaggerTrinket.onHurt(event);
        DarkEggTrinket.onHurt(event);
        DropSpindleTrinket.onHurt(event);
        EmberTrinket.onHurt(event);
        GoldenSwatterTrinket.onHurt(event);
        MadPiggyTrinket.onHurt(event);
        MirrorShardTrinket.onHurt(event);
        SerpentToothTrinket.onHurt(event);
        StarfishTrinket.onHurt(event);
        SlingshotTrinket.onHurt(event);
        WitherNailTrinket.onHurt(event);

        if (source.getTrueSource() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) source.getTrueSource();
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            float amount = event.getAmount();
            if (trinkets.isActive(Itms.SILVER_NAIL)) {
                amount *= 1.1F;
            }
            if (trinkets.isActive(Itms.GLORY_SHARDS)) {
                amount *= 1.2F;
            }
            event.setAmount(amount);
        }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        DamageSource source = event.getSource();
        if (source == null) return;
        RubyHeartTrinket.onDeath(event);
    }

    @SubscribeEvent
    public static void onDrops(LivingDropsEvent event) {
        ButchersCleaverTrinket.dropExtra(event);
        TreasureRingTrinket.onDrops(event);
        GoldenSkullTrinket.onDrops(event);
    }

    @SubscribeEvent
    public static void onPotion(PotionEvent.PotionApplicableEvent event) {
        CoffeeBeanTrinket.onPotion(event);
        MagicalHerbsTrinket.onPotion(event);
        OxalisTrinket.onPotion(event);
        TeaLeafTrinket.onPotion(event);
    }

    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        CreepoTrinket.resetExplosion(event);
    }

    @SubscribeEvent
    public static void onLooting(LootingLevelEvent event) {
        DamageSource source = event.getDamageSource();
        if (source == null) return;
        if (source.getTrueSource() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) source.getTrueSource();
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            int looting = event.getLootingLevel();
            if (trinkets.isActive(Itms.GOLDEN_HORSESHOE)) {
                looting++;
            }
            if (trinkets.isActive(Itms.GOLDEN_TOOTH)) {
                looting++;
            }
            event.setLootingLevel(looting);
        }
    }

    @SubscribeEvent
    public static void onUseFinish(LivingEntityUseItemEvent.Finish event) {
        GoldenMelonTrinket.onUseFinish(event);
        LunchBagTrinket.onUseFinish(event);
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        MinersPickTrinket.onBreakSpeed(event);
    }

    @SubscribeEvent
    public static void onBreak(BlockEvent.BreakEvent event) {
        PlayerEntity player = event.getPlayer();
        OctopickTrinket.mine(player, player.world, event.getPos(), event.getState());
    }
}
