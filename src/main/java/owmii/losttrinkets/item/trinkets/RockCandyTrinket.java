package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import owmii.losttrinkets.api.trinket.ITickableTrinket;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;

public class RockCandyTrinket extends Trinket<RockCandyTrinket> implements ITickableTrinket {
    public RockCandyTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void tick(World world, BlockPos pos, PlayerEntity player) {
        if (!player.isSneaking() && player.moveForward > 0F) {
            player.moveRelative(player.isSprinting() ? 0.22F : 0.15F, new Vector3d(0.0D, 0.0D, 1.0D));
        }
    }
}
