package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import owmii.losttrinkets.api.trinket.ITickableTrinket;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;

public class LuckCoinTrinket extends Trinket<LuckCoinTrinket> implements ITickableTrinket {
    public LuckCoinTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void tick(World world, BlockPos pos, PlayerEntity player) {
        if (!world.isRemote && player.ticksExisted % 90 == 0) {
            player.addPotionEffect(new EffectInstance(Effects.LUCK, 300, 1, false, false));
        }
    }
}
