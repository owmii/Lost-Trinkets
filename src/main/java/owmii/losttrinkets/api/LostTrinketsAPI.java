package owmii.losttrinkets.api;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.PlayerEntity;
import owmii.losttrinkets.api.player.PlayerData;
import owmii.losttrinkets.api.trinket.ITrinket;
import owmii.losttrinkets.api.trinket.Trinkets;

import java.util.*;

public class LostTrinketsAPI {
    public static final Map<UUID, List<ITrinket>> UNLOCK_QUEUE = new HashMap<>();
    public static final List<UUID> WEIGHTED_UNLOCK_QUEUE = new ArrayList<>();

    public static boolean unlock(PlayerEntity player, ITrinket trinket) {
        if (!player.world.isRemote && !getTrinkets(player).has(trinket)) {
            List<ITrinket> trinketList = UNLOCK_QUEUE.get(player.getUniqueID());
            if (trinketList != null) {
                trinketList.add(trinket);
            } else trinketList = Lists.newArrayList(trinket);
            UNLOCK_QUEUE.put(player.getUniqueID(), trinketList);
            return true;
        }
        return false;
    }

    public static void unlock(PlayerEntity player) {
        if (!player.world.isRemote) {
            WEIGHTED_UNLOCK_QUEUE.add(player.getUniqueID());
        }
    }

    public static Trinkets getTrinkets(PlayerEntity player) {
        return getData(player).getTrinkets();
    }

    public static PlayerData getData(PlayerEntity player) {
        return player.getCapability(PlayerData.CAP).orElse(new PlayerData());
    }
}
