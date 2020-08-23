package owmii.losttrinkets.item.trinkets;

import net.minecraftforge.event.entity.player.PlayerEvent;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.item.Itms;

public class MinersPickTrinket extends Trinket<MinersPickTrinket> {
    public MinersPickTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (LostTrinketsAPI.getTrinkets(event.getPlayer()).isActive(Itms.MINERS_PICK)) {
            event.setNewSpeed(event.getOriginalSpeed() + 3.7F);
        }
    }
}
