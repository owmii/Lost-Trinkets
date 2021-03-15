package owmii.losttrinkets.item.trinkets;

import net.minecraft.command.impl.SpawnPointCommand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;
import owmii.losttrinkets.api.trinket.ITickableTrinket;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;

import java.util.Optional;
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
                teleportToSpawnPoint((ServerPlayerEntity) player);
            }
        }
    }

    /**
     * Follow code in {@link SpawnPointCommand} to find the real spawn point information.
     */
    private static void teleportToSpawnPoint(ServerPlayerEntity player) {
        ServerWorld currentWorld = player.getServerWorld();
        RegistryKey<World> spawnDimensionKey = player.func_241141_L_();
        if (currentWorld.getDimensionKey() != spawnDimensionKey) {
            MinecraftServer server = currentWorld.getServer();
            ServerWorld spawnWorld = server.getWorld(spawnDimensionKey);
            if (spawnWorld != null) {
                player.changeDimension(spawnWorld, new WarmVoidTeleporter());
            }
        } else {
            Vector3d spawnPos = Vector3d.copyCenteredHorizontally(Optional.ofNullable(player.func_241140_K_())
                    .orElseGet(currentWorld::getSpawnPoint));
            float spawnAngle = player.func_242109_L();
            player.connection.setPlayerLocation(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), spawnAngle, 0);
        }
    }

    public static class WarmVoidTeleporter implements ITeleporter {
        @Override
        public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
            Entity maybePlayer = ITeleporter.super.placeEntity(entity, currentWorld, destWorld, yaw, repositionEntity);
            if (maybePlayer instanceof ServerPlayerEntity) {
                ServerPlayerEntity player = (ServerPlayerEntity) maybePlayer;
                // Only try teleport if the destination matches spawn point
                if (destWorld.getDimensionKey() == player.func_241141_L_()) {
                    teleportToSpawnPoint(player);
                }
            }
            return maybePlayer;
        }
    }
}
