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

public class SetInactivePacket implements IPacket<SetInactivePacket> {
    private int trinket;

    public SetInactivePacket(int trinket) {
        this.trinket = trinket;
    }

    public SetInactivePacket() {
        this(0);
    }

    @Override
    public void encode(SetInactivePacket msg, PacketBuffer buffer) {
        buffer.writeInt(msg.trinket);
    }

    @Override
    public SetInactivePacket decode(PacketBuffer buffer) {
        return new SetInactivePacket(buffer.readInt());
    }

    @Override
    public void handle(SetInactivePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            PlayerEntity player = ctx.get().getSender();
            if (player != null) {
                Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
                List<ITrinket> items = trinkets.getActiveTrinkets();
                if (!items.isEmpty()) {
                    trinkets.setInactive(items.get(msg.trinket), player);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}