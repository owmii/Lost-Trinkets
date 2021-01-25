package owmii.losttrinkets.network.packet;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.network.NetworkEvent;
import owmii.lib.network.IPacket;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.item.Itms;

import java.util.List;
import java.util.function.Supplier;

public class MagnetoPacket implements IPacket<MagnetoPacket> {
    @Override
    public void encode(MagnetoPacket msg, PacketBuffer buffer) {}

    @Override
    public MagnetoPacket decode(PacketBuffer buffer) {
        return new MagnetoPacket();
    }

    @Override
    public void handle(MagnetoPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            PlayerEntity player = ctx.get().getSender();
            if (player != null && LostTrinketsAPI.getTrinkets(player).isActive(Itms.MAGNETO)) {
                AxisAlignedBB bb = new AxisAlignedBB(player.getPosition()).grow(10);
                List<ItemEntity> entities = player.world.getEntitiesWithinAABB(ItemEntity.class, bb);
                List<ExperienceOrbEntity> orbEntities = player.world.getEntitiesWithinAABB(ExperienceOrbEntity.class, bb);
                entities.stream().filter(Entity::isAlive).forEach(entity -> {
                    entity.setNoPickupDelay();
                    entity.onCollideWithPlayer(player);
                });
                orbEntities.stream().filter(Entity::isAlive).forEach(orb -> {
                    orb.delayBeforeCanPickup = 0;
                    player.xpCooldown = 0;
                    orb.onCollideWithPlayer(player);
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
