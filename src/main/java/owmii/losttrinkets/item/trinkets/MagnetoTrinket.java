package owmii.losttrinkets.item.trinkets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import owmii.losttrinkets.LostTrinkets;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.client.handler.KeyHandler;
import owmii.losttrinkets.item.Itms;
import owmii.losttrinkets.network.packet.MagnetoPacket;

import java.util.List;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class MagnetoTrinket extends Trinket<MagnetoTrinket> {
    public MagnetoTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void trySendCollect(PlayerEntity player) {
        Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
        if (trinkets.isActive(Itms.MAGNETO)) {
            LostTrinkets.NET.toServer(new MagnetoPacket());
        }
    }

    @SubscribeEvent
    public static void collectUse(PlayerInteractEvent.RightClickEmpty event) {
        if (KeyHandler.MAGNETO.isInvalid() && event.getHand() == Hand.MAIN_HAND) {
            trySendCollect(event.getPlayer());
        }
    }

    @Override
    public void addTrinketDescription(ItemStack stack, List<ITextComponent> lines) {
        super.addTrinketDescription(stack, lines);
        String translationKey = Util.makeTranslationKey("info", Registry.ITEM.getKey(stack.getItem()));
        if (KeyHandler.MAGNETO.isInvalid()) {
            lines.add(new TranslationTextComponent(translationKey + ".unbound").mergeStyle(TextFormatting.GRAY));
        } else {
            lines.add(new TranslationTextComponent(translationKey + ".bound", KeyHandler.MAGNETO.func_238171_j_()).mergeStyle(TextFormatting.GRAY));
        }
    }
}
