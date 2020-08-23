package owmii.losttrinkets.api.trinket;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITickableTrinket extends ITrinket {
    void tick(World world, BlockPos pos, PlayerEntity player);
}
