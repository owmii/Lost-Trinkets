package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import owmii.losttrinkets.api.trinket.ITickableTrinket;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;

public class MossyRingTrinket extends Trinket<MossyRingTrinket> implements ITickableTrinket {
    public MossyRingTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void tick(World world, BlockPos pos, PlayerEntity player) {
        // Don't repair item if player is currently swinging (workaround for MinecraftForge#7606 and MC-176559)
        if (world.getGameTime() % 40 == 0 && !player.isSwingInProgress) {
            for (Hand hand : Hand.values()) {
                ItemStack stack = player.getHeldItem(hand);
                if (!stack.isEmpty() && stack.isDamaged()) {
                    stack.setDamage(stack.getDamage() - 1);
                    break;
                }
            }
        }
    }
}
