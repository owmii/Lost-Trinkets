package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import owmii.losttrinkets.LostTrinkets;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.player.PlayerData;
import owmii.losttrinkets.api.trinket.ITickableTrinket;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.network.packet.SyncFlyPacket;

public class MagicalFeathersTrinket extends Trinket<MagicalFeathersTrinket> implements ITickableTrinket {
    public MagicalFeathersTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void tick(World world, BlockPos pos, PlayerEntity player) {
        PlayerData data = LostTrinketsAPI.getData(player);
        player.abilities.allowFlying = true;
        if (data.wasFlying) {
            player.abilities.isFlying = true;
            data.wasFlying = false;
        }
        if (!data.allowFlying) {
            if (!world.isRemote) {
                LostTrinkets.NET.toClient(new SyncFlyPacket(true), player);
            }
            data.allowFlying = true;
        }
    }

    @Override
    public void onDeactivated(World world, BlockPos pos, PlayerEntity player) {
        super.onDeactivated(world, pos, player);
        PlayerData data = LostTrinketsAPI.getData(player);
        if (data.allowFlying) {
            player.abilities.allowFlying = false;
            player.abilities.isFlying = false;
            if (!world.isRemote) {
                LostTrinkets.NET.toClient(new SyncFlyPacket(false), player);
            }
            data.allowFlying = false;
        }
    }
}
