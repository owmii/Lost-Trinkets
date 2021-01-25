package owmii.losttrinkets.handler;

import com.google.common.collect.Sets;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.registries.ForgeRegistries;
import owmii.lib.util.Server;
import owmii.losttrinkets.LostTrinkets;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.player.PlayerData;
import owmii.losttrinkets.api.trinket.ITrinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.config.Configs;
import owmii.losttrinkets.network.packet.TrinketUnlockedPacket;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static owmii.lib.config.Config.MARKER;
import static owmii.losttrinkets.LostTrinkets.LOGGER;

public class UnlockManager {
    private static final Set<ITrinket> TRINKETS = Sets.newLinkedHashSet();
    private static final Set<ITrinket> UNLOCKABLE_TRINKETS = Sets.newLinkedHashSet();
    private static final List<WeightedTrinket> WEIGHTED_TRINKETS = new ArrayList<>();

    @Nullable
    public static ITrinket unlock(PlayerEntity player, boolean checkDelay) {
        if (player instanceof ServerPlayerEntity) {
            PlayerData data = LostTrinketsAPI.getData(player);
            if (!checkDelay || data.unlockDelay <= 0) {
                Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
                WEIGHTED_TRINKETS.clear();
                WEIGHTED_TRINKETS.addAll(UNLOCKABLE_TRINKETS.stream()
                        .filter(trinket -> !trinkets.has(trinket))
                        .map(WeightedTrinket::new)
                        .collect(Collectors.toList()));
                if (!WEIGHTED_TRINKETS.isEmpty()) {
                    WeightedTrinket item = WeightedRandom.getRandomItem(player.world.rand, WEIGHTED_TRINKETS);
                    unlock(player, item.trinket, checkDelay);
                }
            }
        }
        return null;
    }

    public static boolean unlock(PlayerEntity player, ITrinket trinket, boolean checkDelay) {
        return unlock(player, trinket, checkDelay, true);
    }

    public static boolean unlock(PlayerEntity player, ITrinket trinket, boolean checkDelay, boolean doNotification) {
        PlayerData data = LostTrinketsAPI.getData(player);
        if (!checkDelay || data.unlockDelay <= 0) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (trinkets.give(trinket)) {
                if (checkDelay) {
                    data.unlockDelay = Configs.GENERAL.unlockCooldown.get();
                }
                if (doNotification) {
                    LostTrinkets.NET.toClient(new TrinketUnlockedPacket(Objects.requireNonNull(trinket.getItem().getRegistryName()).toString()), player);
                    ItemStack stack = new ItemStack(trinket);
                    ITextComponent trinketName = stack.getDisplayName().deepCopy().modifyStyle(style -> {
                        return style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemHover(stack)));
                    });
                    Server.get().getPlayerList().func_232641_a_(new TranslationTextComponent("chat.losttrinkets.unlocked.trinket", player.getDisplayName(), trinketName).mergeStyle(TextFormatting.DARK_AQUA), ChatType.SYSTEM, Util.DUMMY_UUID);
                }
                return true;
            }
        }
        return false;
    }


    static class WeightedTrinket extends WeightedRandom.Item {
        private final ITrinket trinket;

        public WeightedTrinket(ITrinket trinket) {
            super(trinket.getRarity().getWeight());
            this.trinket = trinket;
        }

        public ITrinket getTrinket() {
            return this.trinket;
        }
    }

    public static void init() {
    }

    static {
        Set<ResourceLocation> banned = Configs.GENERAL.blackList.get().stream()
                .map(ResourceLocation::new)
                .collect(Collectors.toCollection(Sets::newHashSet));
        Set<ResourceLocation> nonRandom = Configs.GENERAL.nonRandom.get().stream()
                .map(ResourceLocation::new)
                .collect(Collectors.toCollection(Sets::newHashSet));
        ForgeRegistries.ITEMS.getValues().forEach(item -> {
            if (item instanceof ITrinket) {
                ITrinket trinket = (ITrinket) item;
                ResourceLocation rl = item.getRegistryName();
                if (banned.contains(rl)) {
                    LOGGER.warn(MARKER, "Banned Trinket: " + rl);
                } else {
                    TRINKETS.add(trinket);
                    if (trinket.isUnlockable() && !nonRandom.contains(rl)) {
                        UNLOCKABLE_TRINKETS.add(trinket);
                    } else {
                        LOGGER.warn(MARKER, "Not Unlockable Trinket: " + rl);
                    }
                }
            }
        });
    }

    public static Set<ITrinket> getTrinkets() {
        return Collections.unmodifiableSet(TRINKETS);
    }
}
