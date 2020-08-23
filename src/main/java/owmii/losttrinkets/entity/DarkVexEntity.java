package owmii.losttrinkets.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class DarkVexEntity extends DarkEntity {
    protected static final DataParameter<Byte> VEX_FLAGS = EntityDataManager.createKey(DarkVexEntity.class, DataSerializers.BYTE);
    @Nullable
    private BlockPos boundOrigin;

    public DarkVexEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
        this.moveController = new DarkVexEntity.MoveHelperController(this);
    }

    public static AttributeModifierMap.MutableAttribute getAttribute() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 20.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 8.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(4, new DarkVexEntity.ChargeAttackGoal());
        this.goalSelector.addGoal(8, new DarkVexEntity.MoveRandomGoal());
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, AbstractRaiderEntity.class)).setCallsForHelp());
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(VEX_FLAGS, (byte) 0);
    }

    @Override
    public void tick() {
        this.noClip = true;
        super.tick();
        this.noClip = false;
        setNoGravity(true);
    }

    @Nullable
    public BlockPos getBoundOrigin() {
        return this.boundOrigin;
    }

    public void setBoundOrigin(@Nullable BlockPos boundOriginIn) {
        this.boundOrigin = boundOriginIn;
    }

    private boolean getVexFlag(int mask) {
        int i = this.dataManager.get(VEX_FLAGS);
        return (i & mask) != 0;
    }

    private void setVexFlag(int mask, boolean value) {
        int i = this.dataManager.get(VEX_FLAGS);
        if (value) {
            i = i | mask;
        } else {
            i = i & ~mask;
        }
        this.dataManager.set(VEX_FLAGS, (byte) (i & 255));
    }

    public boolean isCharging() {
        return getVexFlag(1);
    }

    public void setCharging(boolean charging) {
        setVexFlag(1, charging);
    }

    @Override
    public void playAmbientSound() {
        if (this.rand.nextInt(7) == 0) {
            super.playAmbientSound();
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_VEX_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VEX_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_VEX_HURT;
    }

    @Override
    public float getBrightness() {
        return 1.0F;
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData data, @Nullable CompoundNBT nbt) {
        setEquipmentBasedOnDifficulty(difficulty);
        setEnchantmentBasedOnDifficulty(difficulty);
        return super.onInitialSpawn(world, difficulty, reason, data, nbt);
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
        setDropChance(EquipmentSlotType.MAINHAND, 0.0F);
    }

    class ChargeAttackGoal extends Goal {
        public ChargeAttackGoal() {
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean shouldExecute() {
            if (DarkVexEntity.this.getAttackTarget() != null && !DarkVexEntity.this.getMoveHelper().isUpdating() && DarkVexEntity.this.rand.nextInt(7) == 0) {
                return DarkVexEntity.this.getDistanceSq(DarkVexEntity.this.getAttackTarget()) > 4.0D;
            } else {
                return false;
            }
        }

        public boolean shouldContinueExecuting() {
            return DarkVexEntity.this.getMoveHelper().isUpdating() && DarkVexEntity.this.isCharging() && DarkVexEntity.this.getAttackTarget() != null && DarkVexEntity.this.getAttackTarget().isAlive();
        }

        public void startExecuting() {
            LivingEntity livingentity = DarkVexEntity.this.getAttackTarget();
            if (livingentity != null) {
                Vector3d vector3d = livingentity.getEyePosition(1.0F);
                DarkVexEntity.this.moveController.setMoveTo(vector3d.x, vector3d.y, vector3d.z, 1.0D);
                DarkVexEntity.this.setCharging(true);
                DarkVexEntity.this.playSound(SoundEvents.ENTITY_VEX_CHARGE, 1.0F, 1.0F);
            }
        }

        public void resetTask() {
            DarkVexEntity.this.setCharging(false);
        }

        public void tick() {
            LivingEntity livingentity = DarkVexEntity.this.getAttackTarget();
            if (livingentity != null) {
                if (DarkVexEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                    DarkVexEntity.this.attackEntityAsMob(livingentity);
                    DarkVexEntity.this.setCharging(false);
                } else {
                    double d0 = DarkVexEntity.this.getDistanceSq(livingentity);
                    if (d0 < 9.0D) {
                        Vector3d vector3d = livingentity.getEyePosition(1.0F);
                        DarkVexEntity.this.moveController.setMoveTo(vector3d.x, vector3d.y, vector3d.z, 1.0D);
                    }
                }
            }
        }
    }

    class MoveHelperController extends MovementController {
        public MoveHelperController(DarkVexEntity vex) {
            super(vex);
        }

        public void tick() {
            if (this.action == MovementController.Action.MOVE_TO) {
                Vector3d vector3d = new Vector3d(this.posX - DarkVexEntity.this.getPosX(), this.posY - DarkVexEntity.this.getPosY(), this.posZ - DarkVexEntity.this.getPosZ());
                double d0 = vector3d.length();
                if (d0 < DarkVexEntity.this.getBoundingBox().getAverageEdgeLength()) {
                    this.action = MovementController.Action.WAIT;
                    DarkVexEntity.this.setMotion(DarkVexEntity.this.getMotion().scale(0.5D));
                } else {
                    DarkVexEntity.this.setMotion(DarkVexEntity.this.getMotion().add(vector3d.scale(this.speed * 0.05D / d0)));
                    if (DarkVexEntity.this.getAttackTarget() == null) {
                        Vector3d vector3d1 = DarkVexEntity.this.getMotion();
                        DarkVexEntity.this.rotationYaw = -((float) MathHelper.atan2(vector3d1.x, vector3d1.z)) * (180F / (float) Math.PI);
                        DarkVexEntity.this.renderYawOffset = DarkVexEntity.this.rotationYaw;
                    } else {
                        double d2 = DarkVexEntity.this.getAttackTarget().getPosX() - DarkVexEntity.this.getPosX();
                        double d1 = DarkVexEntity.this.getAttackTarget().getPosZ() - DarkVexEntity.this.getPosZ();
                        DarkVexEntity.this.rotationYaw = -((float) MathHelper.atan2(d2, d1)) * (180F / (float) Math.PI);
                        DarkVexEntity.this.renderYawOffset = DarkVexEntity.this.rotationYaw;
                    }
                }

            }
        }
    }

    class MoveRandomGoal extends Goal {
        public MoveRandomGoal() {
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean shouldExecute() {
            return !DarkVexEntity.this.getMoveHelper().isUpdating() && DarkVexEntity.this.rand.nextInt(7) == 0;
        }

        public boolean shouldContinueExecuting() {
            return false;
        }

        public void tick() {
            BlockPos blockpos = DarkVexEntity.this.getBoundOrigin();
            if (blockpos == null) {
                blockpos = DarkVexEntity.this.getPosition();
            }

            for (int i = 0; i < 3; ++i) {
                BlockPos pos = blockpos.add(DarkVexEntity.this.rand.nextInt(15) - 7, DarkVexEntity.this.rand.nextInt(11) - 5, DarkVexEntity.this.rand.nextInt(15) - 7);
                if (DarkVexEntity.this.world.isAirBlock(pos)) {
                    DarkVexEntity.this.moveController.setMoveTo((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 0.25D);
                    if (DarkVexEntity.this.getAttackTarget() == null) {
                        DarkVexEntity.this.getLookController().setLookPosition((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 180.0F, 20.0F);
                    }
                    break;
                }
            }

        }
    }
}
