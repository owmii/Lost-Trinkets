package owmii.losttrinkets.handler;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import owmii.lib.util.Ticker;
import owmii.losttrinkets.api.trinket.ITrinket;
import owmii.losttrinkets.config.Configs;
import owmii.losttrinkets.impl.LostTrinketsAPIImpl;

import java.util.*;

@Mod.EventBusSubscriber
public class UnlockHandler {
    private static final Map<UUID, Type> MAP = new HashMap<>();
    private static final Ticker DELAY = new Ticker(10);
    private static boolean flag;

    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side == LogicalSide.SERVER) {
            PlayerEntity player = event.player;

            List<ITrinket> trinkets = LostTrinketsAPIImpl.UNLOCK_QUEUE.get(player.getUniqueID());
            if (trinkets != null) {
                trinkets.forEach(trinket -> UnlockManager.unlock(player, trinket, false));
            }
            LostTrinketsAPIImpl.UNLOCK_QUEUE.remove(player.getUniqueID());
            Iterator<UUID> itr = LostTrinketsAPIImpl.WEIGHTED_UNLOCK_QUEUE.iterator();
            while (itr.hasNext()) {
                if (itr.next().equals(player.getUniqueID())) {
                    UnlockManager.unlock(player, false);
                    itr.remove();
                }
            }
            checkUnlocks(player);
        }
    }

    private static void checkUnlocks(PlayerEntity player) {
        if (Configs.GENERAL.unlockEnabled.get()) {
            UUID id = player.getUniqueID();
            if (DELAY.isEmpty() && MAP.containsKey(id)) {
                if (player.world.rand.nextInt(MAP.get(id).getRandom()) == 0) {
                    UnlockManager.unlock(player, true);
                }
                flag = true;
            }
            if (flag) {
                DELAY.onward();
                MAP.remove(id);
                if (DELAY.ended()) {
                    DELAY.reset();
                    flag = false;
                }
            }
        }
    }

    private static void queueUnlock(PlayerEntity player, Type type) {
        if (!player.world.isRemote && !(player instanceof FakePlayer)) {
            MAP.put(player.getUniqueID(), type);
        }
    }

    public static void trade(PlayerEntity player) {
        if (Configs.GENERAL.unlockEnabled.get() && Configs.GENERAL.tradingUnlockEnabled.get()) {
            if (!player.world.isRemote) {
                queueUnlock(player, Type.TRADING);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void kill(LivingDeathEvent event) {
        if (Configs.GENERAL.unlockEnabled.get()) {
            Entity entity = event.getSource().getTrueSource();
            LivingEntity target = event.getEntityLiving();
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                if (!player.world.isRemote) {
                    if (target.isNonBoss()) {
                        if (Configs.GENERAL.killingUnlockEnabled.get()) {
                            queueUnlock(player, Type.KILL);
                        }
                    } else if (Configs.GENERAL.bossKillingUnlockEnabled.get()) {
                        queueUnlock(player, Type.BOSS_KILL);
                    }
                }
            }
        }
    }

    public static void checkBlockHarvest(PlayerEntity player, World world, BlockPos pos, BlockState state) {
        if (Configs.GENERAL.unlockEnabled.get()) {
            if (!player.world.isRemote) {
                if (Tags.Blocks.ORES.contains(state.getBlock())) {
                    if (Configs.GENERAL.oresMiningUnlockEnabled.get()) {
                        queueUnlock(player, Type.ORE_MINE);
                    }
                } else if (BlockTags.CROPS.contains(state.getBlock())) {
                    if (Configs.GENERAL.farmingUnlockEnabled.get()) {
                        queueUnlock(player, Type.FARM_HARVEST);
                    }
                } else if (BlockTags.LOGS.contains(state.getBlock())) {
                    if (Configs.GENERAL.woodCuttingUnlockEnabled.get()) {
                        queueUnlock(player, Type.WOOD_CUTTING);
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void useHoe(BlockEvent.BlockToolInteractEvent event) {
        if (Configs.GENERAL.unlockEnabled.get() && Configs.GENERAL.farmingUnlockEnabled.get()) {
            PlayerEntity player = event.getPlayer();
            if (!player.world.isRemote && event.getToolType() == ToolType.HOE) {
                BlockState originalState = event.getState();
                BlockState finalState = event.getFinalState();
                if (finalState == originalState) {
                    finalState = HoeItem.getHoeTillingState(originalState);
                }
                if (finalState != null && finalState != originalState) {
                    queueUnlock(player, Type.FARM_HARVEST);
                }
            }
        }
    }

    @SubscribeEvent
    public static void bonemeal(BonemealEvent event) {
        if (Configs.GENERAL.unlockEnabled.get() && Configs.GENERAL.farmingUnlockEnabled.get()) {
            PlayerEntity player = event.getPlayer();
            if (!player.world.isRemote) {
                queueUnlock(player, Type.FARM_HARVEST);
            }
        }
    }

    enum Type {
        KILL, BOSS_KILL, ORE_MINE, WOOD_CUTTING, FARM_HARVEST, TRADING;

        public int getRandom() {
            if (this == KILL) {
                return Configs.GENERAL.killing.get();
            } else if (this == BOSS_KILL) {
                return Configs.GENERAL.bossKilling.get();
            } else if (this == ORE_MINE) {
                return Configs.GENERAL.oresMining.get();
            } else if (this == TRADING) {
                return Configs.GENERAL.trading.get();
            } else if (this == FARM_HARVEST) {
                return Configs.GENERAL.farming.get();
            } else if (this == WOOD_CUTTING) {
                return Configs.GENERAL.woodCutting.get();
            }
            return Integer.MAX_VALUE;
        }
    }
}
