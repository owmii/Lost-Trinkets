package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;

public class GoldenHorseshoeTrinket extends Trinket<GoldenHorseshoeTrinket> {
    public GoldenHorseshoeTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onLooting(LootingLevelEvent event) {
        DamageSource source = event.getDamageSource();
        if (source.getTrueSource() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) source.getTrueSource();
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (trinkets.isActive(Itms.GOLDEN_HORSESHOE)) {
                event.setLootingLevel(event.getLootingLevel() + 1);
            }
        }
    }
}
