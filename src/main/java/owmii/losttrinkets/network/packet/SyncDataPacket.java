package owmii.losttrinkets.network.packet;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import owmii.lib.client.util.MC;
import owmii.lib.network.IPacket;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.player.PlayerData;
import owmii.losttrinkets.client.screen.Screens;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class SyncDataPacket implements IPacket<SyncDataPacket> {
    private final UUID uuid;
    private final CompoundNBT nbt;

    protected SyncDataPacket(UUID uuid, CompoundNBT nbt) {
        this.uuid = uuid;
        this.nbt = nbt;
    }

    public SyncDataPacket() {
        this(new UUID(0, 0), new CompoundNBT());
    }

    public SyncDataPacket(PlayerEntity player) {
        this(player.getUniqueID(), LostTrinketsAPI.getData(player).serializeNBT());
    }

    @Override
    public void encode(SyncDataPacket msg, PacketBuffer buffer) {
        buffer.writeUniqueId(msg.uuid);
        buffer.writeCompoundTag(msg.nbt);
    }

    @Override
    public SyncDataPacket decode(PacketBuffer buffer) {
        return new SyncDataPacket(
                buffer.readUniqueId(),
                Objects.requireNonNull(buffer.readCompoundTag())
        );
    }

    @Override
    public void handle(SyncDataPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            MC.world().ifPresent(world -> {
                PlayerEntity player = world.getPlayerByUuid(msg.uuid);
                if (player != null) {
                    PlayerData data = LostTrinketsAPI.getData(player);
                    data.deserializeNBT(msg.nbt);
                    Screens.checkScreenRefresh();
                }
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
