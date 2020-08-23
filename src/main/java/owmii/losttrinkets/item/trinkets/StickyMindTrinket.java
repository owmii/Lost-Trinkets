package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.item.Itms;

import java.util.List;

@Mod.EventBusSubscriber
public class StickyMindTrinket extends Trinket<StickyMindTrinket> {
    public StickyMindTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @SubscribeEvent
    public static void onEnderTeleport(EnderTeleportEvent event) {
        LivingEntity entity = event.getEntityLiving();
        AxisAlignedBB bb = new AxisAlignedBB(entity.getPosition()).grow(16);
        List<PlayerEntity> players = entity.world.getEntitiesWithinAABB(PlayerEntity.class, bb);
        for (PlayerEntity player : players) {
            if (LostTrinketsAPI.getTrinkets(player).isActive(Itms.STICKY_MIND)) {
                event.setCanceled(true);
                break;
            }
        }
    }
}
