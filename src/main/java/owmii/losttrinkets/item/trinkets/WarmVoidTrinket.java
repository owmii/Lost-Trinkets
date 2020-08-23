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
                if (world.func_234923_W_() != World.field_234918_g_) {
                    ServerWorld serverworld = ((ServerWorld) world).getServer().getWorld(World.field_234918_g_);
                    if (serverworld != null) {
                        player.changeDimension(serverworld);
                    }
                } else {
                    Optional<BlockPos> position = player.getBedPosition();
                    if (position.isPresent()) {
                        BlockPos bedPos = position.get();
                        player.setPositionAndUpdate(bedPos.getX(), bedPos.getY(), bedPos.getZ());
                    } else {
                        BlockPos spawnPos = ((ServerWorld) world).func_241135_u_();
                        player.setPositionAndUpdate(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
                    }
                }
            }
        }
    }
}
