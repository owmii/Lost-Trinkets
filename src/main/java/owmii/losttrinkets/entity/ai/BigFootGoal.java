package owmii.losttrinkets.entity.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.vector.Vector3d;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;

import javax.annotation.Nullable;

public class BigFootGoal extends Goal {
    protected final PathNavigator navigation;
    private CreatureEntity entity;
    @Nullable
    protected Path path;
    @Nullable
    protected PlayerEntity player;

    public BigFootGoal(CreatureEntity entity) {
        this.navigation = entity.getNavigator();
        this.entity = entity;
    }

    @Override
    public void startExecuting() {
        this.navigation.setPath(this.path, 1.4F);
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
    public boolean shouldExecute() {
        this.player = this.entity.world.getClosestPlayer(this.entity, 8.0D);
        if (this.player != null && this.entity.isNonBoss()) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(this.player);
            if (trinkets.isActive(Itms.BIG_FOOT)) {
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
