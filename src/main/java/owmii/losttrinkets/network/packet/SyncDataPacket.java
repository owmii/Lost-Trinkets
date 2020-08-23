package owmii.losttrinkets.network.packet;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import owmii.lib.client.util.Client;
import owmii.lib.network.IPacket;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.player.PlayerData;
import owmii.losttrinkets.client.screen.Screens;

import java.util.Objects;
import java.util.function.Supplier;

public class SyncDataPacket implements IPacket<SyncDataPacket> {
    private CompoundNBT nbt;

    public SyncDataPacket(CompoundNBT nbt) {
        this.nbt = nbt;
    }

    public SyncDataPacket() {
        this(new CompoundNBT());
    }

    @Override
    public void encode(SyncDataPacket msg, PacketBuffer buffer) {
        buffer.writeCompoundTag(msg.nbt);
    }

    @Override
    public SyncDataPacket decode(PacketBuffer buffer) {
        return new SyncDataPacket(Objects.requireNonNull(buffer.readCompoundTag()));
    }

    @Override
    public void handle(SyncDataPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Client.player().ifPresent(player -> {
                PlayerData data = LostTrinketsAPI.getData(player);
                data.deserializeNBT(msg.nbt);
                Screens.checkScreenRefresh();
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
