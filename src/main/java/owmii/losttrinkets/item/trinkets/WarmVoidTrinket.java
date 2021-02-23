package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import owmii.losttrinkets.api.trinket.ITickableTrinket;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;

import java.util.Optional;

public class WarmVoidTrinket extends Trinket<WarmVoidTrinket> implements ITickableTrinket {
    public WarmVoidTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void tick(World world, BlockPos pos, PlayerEntity player) {
        if (player.getPosY() <= -64) {
            if (world instanceof ServerWorld && !player.isPassenger() && !player.isBeingRidden()) {
                if (world.getDimensionKey() != World.OVERWORLD) {
                    ServerWorld serverworld = ((ServerWorld) world).getServer().getWorld(World.OVERWORLD);
                    if (serverworld != null) {
                        player.changeDimension(serverworld);
                    }
                } else {
                    Optional<BlockPos> position = player.getBedPosition();
                    if (position.isPresent()) {
                        BlockPos bedPos = position.get();
                        player.setPositionAndUpdate(bedPos.getX(), bedPos.getY(), bedPos.getZ());
                    } else {
                        BlockPos spawnPos = ((ServerWorld) world).getSpawnPoint();
                        player.setPositionAndUpdate(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
                    }
                }
            }
        }
    }
}
