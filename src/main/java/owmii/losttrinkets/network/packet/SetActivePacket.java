package owmii.losttrinkets.network.packet;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import owmii.lib.network.IPacket;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.ITrinket;
import owmii.losttrinkets.api.trinket.Trinkets;

import java.util.List;
import java.util.function.Supplier;

public class SetActivePacket implements IPacket<SetActivePacket> {
    private int trinket;

    public SetActivePacket(int trinket) {
        this.trinket = trinket;
    }

    public SetActivePacket() {
        this(0);
    }

    @Override
    public void encode(SetActivePacket msg, PacketBuffer buffer) {
        buffer.writeInt(msg.trinket);
    }

    @Override
    public SetActivePacket decode(PacketBuffer buffer) {
        return new SetActivePacket(buffer.readInt());
    }

    @Override
    public void handle(SetActivePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            PlayerEntity player = ctx.get().getSender();
            if (player != null) {
                Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
                List<ITrinket> items = trinkets.getAvailableTrinkets();
                if (!items.isEmpty()) {
                    trinkets.setActive(items.get(msg.trinket), player);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}