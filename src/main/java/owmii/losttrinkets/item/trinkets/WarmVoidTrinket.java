package owmii.losttrinkets.item.trinkets;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PortalInfo;
import net.minecraft.command.impl.SpawnPointCommand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.network.play.client.CClientStatusPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.concurrent.TickDelayedTask;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;
import owmii.losttrinkets.api.trinket.ITickableTrinket;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class WarmVoidTrinket extends Trinket<WarmVoidTrinket> implements ITickableTrinket {
    public WarmVoidTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void tick(World world, BlockPos pos, PlayerEntity player) {
        // TODO 1.17: Update -64
        if (player instanceof ServerPlayerEntity && player.getPosY() + Math.min(0, player.getMotion().getY()) <= -64) {
            if (!player.isPassenger() && !player.isBeingRidden()) {
                MinecraftServer server = ((ServerPlayerEntity) player).getServerWorld().getServer();
                server.enqueue(new TickDelayedTask(server.getTickCounter(), () -> {
                        teleportToSpawnPoint((ServerPlayerEntity) player);
                }));
            }
        }
    }

    /**
     * Follow code in {@link SpawnPointCommand} to find the real spawn point information.
     */
    private static void teleportToSpawnPoint(ServerPlayerEntity player) {
        player.stopRiding();
        SpawnPointInfo info = getSpawnPointInfo(player);
        player.setMotion(Vector3d.ZERO);
        player.fallDistance = 0;
        if (player.getServerWorld() != info.spawnWorld) {
            player.changeDimension(info.spawnWorld, new WarmVoidTeleporter(info));
        } else {
            player.connection.setPlayerLocation(info.spawnPos.getX(), info.spawnPos.getY(), info.spawnPos.getZ(), info.spawnAngle, 0);
            info.repositionEntity.accept(player);
        }
    }

    /**
     * Follow code in {@link ServerPlayNetHandler#processClientStatus(CClientStatusPacket)}.
     * Based on {@link PlayerList#func_232644_a_(ServerPlayerEntity, boolean)}.
     */
    private static SpawnPointInfo getSpawnPointInfo(ServerPlayerEntity player) {
        MinecraftServer server = player.getServerWorld().getServer();
        BlockPos spawnPosRaw = player.func_241140_K_();
        float spawnAngle = player.func_242109_L();
        boolean spawnForced = player.func_241142_M_();
        ServerWorld spawnWorldRaw = server.getWorld(player.func_241141_L_());
        Optional<Vector3d> spawnPos;
        if (spawnWorldRaw != null && spawnPosRaw != null) {
            spawnPos = PlayerEntity.func_242374_a(spawnWorldRaw, spawnPosRaw, spawnAngle, spawnForced, true);
        } else {
            spawnPos = Optional.empty();
        }

        ServerWorld spawnWorld = spawnWorldRaw != null && spawnPos.isPresent() ? spawnWorldRaw : server.func_241755_D_();

        Consumer<ServerPlayerEntity> repositionEntity = callbackPlayer -> {
            if (spawnPos.isPresent()) {
                BlockState blockstate = spawnWorld.getBlockState(spawnPosRaw);
                boolean isRespawnAnchor = blockstate.isIn(Blocks.RESPAWN_ANCHOR);
                Vector3d spawnPosResolved = spawnPos.get();
                float spawnAngleResolved;
                if (!blockstate.isIn(BlockTags.BEDS) && !isRespawnAnchor) {
                    spawnAngleResolved = spawnAngle;
                } else {
                    Vector3d vector3d1 = Vector3d.copyCenteredHorizontally(spawnPosRaw).subtract(spawnPosResolved).normalize();
                    spawnAngleResolved = (float) MathHelper.wrapDegrees(MathHelper.atan2(vector3d1.z, vector3d1.x) * (double) (180F / (float) Math.PI) - 90.0D);
                }

                callbackPlayer.setLocationAndAngles(spawnPosResolved.x, spawnPosResolved.y, spawnPosResolved.z, spawnAngleResolved, 0.0F);
                callbackPlayer.func_242111_a(spawnWorld.getDimensionKey(), spawnPosRaw, spawnAngle, spawnForced, false);
            } else if (spawnPosRaw != null) {
                callbackPlayer.connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241764_a_, 0.0F));
            }

            while (!spawnWorld.hasNoCollisions(callbackPlayer) && callbackPlayer.getPosY() < 256.0D) {
                callbackPlayer.setPosition(callbackPlayer.getPosX(), callbackPlayer.getPosY() + 1.0D, callbackPlayer.getPosZ());
            }
        };
        return new SpawnPointInfo(
                spawnWorld,
                spawnPos.orElseGet(() -> Vector3d.copyCenteredHorizontally(spawnWorld.getSpawnPoint())),
                spawnAngle,
                repositionEntity
        );
    }

    private static class WarmVoidTeleporter implements ITeleporter {
        private final SpawnPointInfo info;

        public WarmVoidTeleporter(SpawnPointInfo info) {
            this.info = info;
        }

        @Override
        public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
            entity = repositionEntity.apply(false);
            if (entity instanceof ServerPlayerEntity) {
                info.repositionEntity.accept((ServerPlayerEntity) entity);
            }
            entity.setPositionAndUpdate(entity.getPosX(), entity.getPosY(), entity.getPosZ());
            return entity;
        }

        @Nullable
        @Override
        public PortalInfo getPortalInfo(Entity entity, ServerWorld destWorld, Function<ServerWorld, PortalInfo> defaultPortalInfo) {
            return new PortalInfo(info.spawnPos, Vector3d.ZERO, info.spawnAngle, 0);
        }
    }

    private static class SpawnPointInfo {
        public final ServerWorld spawnWorld;
        public final Vector3d spawnPos;
        public final float spawnAngle;
        public final Consumer<ServerPlayerEntity> repositionEntity;

        public SpawnPointInfo(ServerWorld spawnWorld, Vector3d spawnPos, float spawnAngle, Consumer<ServerPlayerEntity> repositionEntity) {
            this.spawnWorld = spawnWorld;
            this.spawnPos = spawnPos;
            this.spawnAngle = spawnAngle;
            this.repositionEntity = repositionEntity;
        }
    }
}
