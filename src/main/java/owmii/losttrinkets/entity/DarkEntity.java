package owmii.losttrinkets.entity;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import owmii.lib.util.Player;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DarkEntity extends CreatureEntity {
    @Nullable
    protected UUID owner;
    @Nullable
    protected PlayerEntity player;

    public DarkEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote) {
            if (this.owner == null) {
                vanish();
            } else {
                Optional<ServerPlayerEntity> player = Player.get(this.owner);
                if (player.isPresent()) {
                    this.player = player.get();
                } else vanish();
            }
            if (getAttackTarget() == null || !getAttackTarget().isAlive()) {
                List<MobEntity> entities = this.world.getEntitiesWithinAABB(MobEntity.class, new AxisAlignedBB(getPosition()).grow(24));
                boolean flag = false;
                for (MobEntity entity : entities) {
                    if (entity.getAttackTarget() != null) {
                        if (this.owner.equals(entity.getAttackTarget().getUniqueID())) {
                            setAttackTarget(entity);
                            flag = true;
                            break;
                        }
                    }
                }
                if (!flag) vanish();
            }
        }
    }

    protected void vanish() {
        remove();
        spawnExplosionParticle();
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("owner")) {
            this.owner = compound.getUniqueId("owner");
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (this.owner != null) {
            compound.putUniqueId("owner", this.owner);
        }
    }

    @Nullable
    public UUID getOwner() {
        return this.owner;
    }

    public void setOwner(PlayerEntity owner) {
        this.owner = owner.getUniqueID();
    }
}
