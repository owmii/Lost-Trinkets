package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import owmii.losttrinkets.LostTrinkets;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;
import owmii.losttrinkets.network.packet.MagnetoPacket;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class MagnetoTrinket extends Trinket<MagnetoTrinket> {
    public MagnetoTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @SubscribeEvent
    public static void collect(PlayerInteractEvent.RightClickEmpty event) {
        PlayerEntity player = event.getPlayer();
        Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
        if (event.getHand() == Hand.MAIN_HAND && trinkets.isActive(Itms.MAGNETO)) {
            LostTrinkets.NET.toServer(new MagnetoPacket());
            event.setCancellationResult(ActionResultType.SUCCESS);
        }
    }
}
