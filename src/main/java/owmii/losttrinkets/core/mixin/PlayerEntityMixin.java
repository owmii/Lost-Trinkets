package owmii.losttrinkets.core.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import owmii.losttrinkets.item.trinkets.IceShardTrinket;
import owmii.losttrinkets.item.trinkets.ThaSpiderTrinket;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void frostWalk(BlockPos pos) {
        IceShardTrinket.frostWalk(this, pos);
        super.frostWalk(pos);
    }

    @Override
    public boolean isOnLadder() {
        if (!super.isOnLadder()) {
            return ThaSpiderTrinket.doClimb(this);
        }
        return ForgeHooks.isLivingOnLadder(getBlockState(), this.world, getPosition(), this);
    }
}
