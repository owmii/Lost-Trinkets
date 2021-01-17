package owmii.losttrinkets.item.trinkets;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;

import java.util.Set;

public class OctopickTrinket extends Trinket<OctopickTrinket> {
    private static final ThreadLocal<ServerPlayerEntity> octoMiningPlayer = new ThreadLocal<>();

    public OctopickTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onBreak(BlockEvent.BreakEvent event) {
        if (octoMiningPlayer.get() != null) return;
        try {
            PlayerEntity player = event.getPlayer();
            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                octoMiningPlayer.set(serverPlayer);
                if (OctopickTrinket.mine(serverPlayer, serverPlayer.getServerWorld(), event.getPos(), event.getState())) {
                    event.setCanceled(true);
                }
            }
        } finally {
            octoMiningPlayer.set(null);
        }
    }

    private static boolean mine(ServerPlayerEntity player, ServerWorld world, BlockPos pos, BlockState state) {
        if (ForgeHooks.canHarvestBlock(state, player, world, pos)) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (trinkets.isActive(Itms.OCTOPICK)) {
                Set<BlockPos> toBreak = Sets.newLinkedHashSet();
                if (Tags.Blocks.ORES.contains(state.getBlock()) || state.getBlock() == Blocks.OBSIDIAN) {
                    toBreak.add(pos);
                    for (BlockPos pos1 : BlockPos.getAllInBoxMutable(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
                        if (toBreak.contains(pos1)) continue;
                        BlockState state1 = world.getBlockState(pos1);
                        if (state.getBlock() == state1.getBlock()) {
                            toBreak.add(pos1.toImmutable());
                            for (BlockPos pos2 : BlockPos.getAllInBoxMutable(pos1.add(-1, -1, -1), pos1.add(1, 1, 1))) {
                                if (toBreak.contains(pos2)) continue;
                                BlockState state2 = world.getBlockState(pos2);
                                if (state.getBlock() == state2.getBlock()) {
                                    toBreak.add(pos2.toImmutable());
                                }
                            }
                        }
                    }
                }
                if (toBreak.size() > 1) {
                    toBreak.forEach(breakPos -> {
                        BlockState breakState = world.getBlockState(breakPos);
                        if (breakState.canHarvestBlock(world, breakPos, player)) {
                            if (player.interactionManager.tryHarvestBlock(breakPos)) {
                                world.playEvent(Constants.WorldEvents.BREAK_BLOCK_EFFECTS, breakPos, Block.getStateId(breakState));
                            }
                        }
                    });
                    return true;
                }
            }
        }
        return false;
    }

    public static void collectDrops(EntityJoinWorldEvent event) {
        ServerPlayerEntity player = octoMiningPlayer.get();
        if (player != null) {
            Entity entity = event.getEntity();
            if (entity.isAlive() && entity.world == player.world) {
                boolean valid = true;
                if (entity instanceof ItemEntity) {
                    ((ItemEntity) entity).setNoPickupDelay();
                } else if (entity instanceof ExperienceOrbEntity) {
                    ((ExperienceOrbEntity) entity).delayBeforeCanPickup = 0;
                    player.xpCooldown = 0;
                } else {
                    valid = false;
                }
                if (valid) {
                    Vector3d pos = player.getPositionVec();
                    entity.setPosition(pos.x, pos.y, pos.z);
                    entity.onCollideWithPlayer(player);
                    if (!entity.isAlive()) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
