package owmii.losttrinkets.network.packet;

import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import owmii.lib.client.util.Client;
import owmii.lib.network.IPacket;
import owmii.losttrinkets.api.trinket.ITrinket;
import owmii.losttrinkets.client.Sounds;
import owmii.losttrinkets.client.handler.hud.HudHandler;
import owmii.losttrinkets.client.handler.hud.Toast;

import java.util.function.Supplier;

public class TrinketUnlockedPacket implements IPacket<TrinketUnlockedPacket> {
    private String key;

    public TrinketUnlockedPacket(String key) {
        this.key = key;
    }

    public TrinketUnlockedPacket() {
        this("");
    }

    @Override
    public void encode(TrinketUnlockedPacket msg, PacketBuffer buffer) {
        buffer.writeString(msg.key);
    }

    @Override
    public TrinketUnlockedPacket decode(PacketBuffer buffer) {
        return new TrinketUnlockedPacket(buffer.readString(32767));
    }

    @Override
    public void handle(TrinketUnlockedPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Client.player().ifPresent(player -> {
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(msg.key));
                if (item instanceof ITrinket) {
                    HudHandler.add(new Toast((ITrinket) item));
                    player.playSound(Sounds.UNLOCK, 1.0F, 1.0F);
                }
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
