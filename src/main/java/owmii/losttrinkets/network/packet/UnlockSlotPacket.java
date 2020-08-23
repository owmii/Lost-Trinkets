package owmii.losttrinkets.network.packet;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import owmii.lib.network.IPacket;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.config.Configs;

import java.util.function.Supplier;

public class UnlockSlotPacket implements IPacket<UnlockSlotPacket> {
    @Override
    public void encode(UnlockSlotPacket msg, PacketBuffer buffer) {
    }

    @Override
    public UnlockSlotPacket decode(PacketBuffer buffer) {
        return new UnlockSlotPacket();
    }

    @Override
    public void handle(UnlockSlotPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            PlayerEntity player = ctx.get().getSender();
            if (player != null) {
                Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
                int cost = Configs.GENERAL.calcCost(trinkets);
                if (player.isCreative()) {
                    trinkets.unlockSlot();
                } else if (player.experienceLevel >= cost) {
                    trinkets.unlockSlot();
                    player.addExperienceLevel(-cost);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}