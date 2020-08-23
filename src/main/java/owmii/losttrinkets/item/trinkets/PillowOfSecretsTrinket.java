package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.stats.ServerStatisticsManager;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import owmii.losttrinkets.api.trinket.ITickableTrinket;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;

public class PillowOfSecretsTrinket extends Trinket<PillowOfSecretsTrinket> implements ITickableTrinket {
    public PillowOfSecretsTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void tick(World world, BlockPos pos, PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            ServerStatisticsManager stats = ((ServerPlayerEntity) player).getStats();
            int j = MathHelper.clamp(stats.getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
            if (j > 12000) {
                player.takeStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
            }
        }
    }
}
