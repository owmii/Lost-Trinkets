package owmii.losttrinkets.item.trinkets;

import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;

public class IceShardTrinket extends Trinket<IceShardTrinket> {
    public IceShardTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void frostWalk(LivingEntity entity, BlockPos pos) {
        if (entity instanceof PlayerEntity) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets((PlayerEntity) entity);
            if (trinkets.isActive(Itms.ICE_SHARD)) {
                FrostWalkerEnchantment.freezeNearby(entity, entity.world, pos, 1);
            }
        }
    }
}
