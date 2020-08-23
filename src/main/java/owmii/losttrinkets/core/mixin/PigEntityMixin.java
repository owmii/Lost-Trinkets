package owmii.losttrinkets.core.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.IRideable;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.item.Itms;

@Mixin(PigEntity.class)
public abstract class PigEntityMixin extends AnimalEntity implements IRideable, IEquipable {
    protected PigEntityMixin(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
    }

    @Override
    public boolean canBeSteered() {
        Entity entity = getControllingPassenger();
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (player.getHeldItemMainhand().getItem() == Items.CARROT_ON_A_STICK || player.getHeldItemOffhand().getItem() == Items.CARROT_ON_A_STICK) {
                return true;
            } else if (LostTrinketsAPI.getTrinkets(player).isActive(Itms.PIGGY)) {
                return player.moveForward > 0.0F;
            }
        }
        return false;
    }

    @Override
    public float getMountedSpeed() {
        Entity entity = getControllingPassenger();
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (LostTrinketsAPI.getTrinkets(player).isActive(Itms.PIGGY)) {
                return 0.45F;
            }
        }
        return (float) (getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.225F);
    }
}
