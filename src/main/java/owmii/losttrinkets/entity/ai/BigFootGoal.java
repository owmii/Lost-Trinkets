package owmii.losttrinkets.entity.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.vector.Vector3d;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.item.Itms;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class BigFootGoal extends Goal {
    public static final double SPEED = 1.4;
    protected final PathNavigator navigation;
    private CreatureEntity entity;
    @Nullable
    protected Path path;
    @Nullable
    protected PlayerEntity player;

    public BigFootGoal(CreatureEntity entity) {
        this.setMutexFlags(EnumSet.of(Flag.MOVE));
        this.navigation = entity.getNavigator();
        this.entity = entity;
    }

    @Override
    public void startExecuting() {
        this.navigation.setPath(this.path, SPEED);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !this.navigation.noPath();
    }

    @Override
    public void resetTask() {
        this.player = null;
    }

    @Override
    public void tick() {
        // Speed gets set very slow sometimes without this
        this.navigation.setSpeed(SPEED);
    }

    @Override
    public boolean shouldExecute() {
        if (this.entity.isNonBoss() && this.entity.isChild()) {
            this.player = this.entity.world.getClosestPlayer(this.entity.getPosX(), this.entity.getPosY(), this.entity.getPosZ(), 8.0,
                    target -> target instanceof PlayerEntity && LostTrinketsAPI.getTrinkets((PlayerEntity) target).isActive(Itms.BIG_FOOT));
            if (this.player != null) {
                Vector3d vector3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.entity, 16, 7, this.player.getPositionVec());
                if (vector3d == null) {
                    return false;
                } else if (this.player.getDistanceSq(vector3d.x, vector3d.y, vector3d.z) < this.player.getDistanceSq(this.entity)) {
                    return false;
                } else {
                    this.path = this.navigation.getPathToPos(vector3d.x, vector3d.y, vector3d.z, 0);
                    return this.path != null;
                }
            }
        }
        return false;
    }
}
