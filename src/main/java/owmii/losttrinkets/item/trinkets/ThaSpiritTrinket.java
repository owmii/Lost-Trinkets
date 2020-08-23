package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import owmii.losttrinkets.api.trinket.ITickableTrinket;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;

public class ThaSpiritTrinket extends Trinket<ThaSpiritTrinket> implements ITickableTrinket {
    public ThaSpiritTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void tick(World world, BlockPos pos, PlayerEntity player) {
        if (!world.isRemote && player.getHealth() <= 2.0F && player.ticksExisted % 90 == 0) {
            player.addPotionEffect(new EffectInstance(Effects.INVISIBILITY, 300, 1, false, false));
        }
    }
}
