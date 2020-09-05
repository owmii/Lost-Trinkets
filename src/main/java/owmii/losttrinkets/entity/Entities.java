package owmii.losttrinkets.entity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import owmii.lib.registry.Registry;
import owmii.losttrinkets.LostTrinkets;

public class Entities {
    @SuppressWarnings("unchecked")
    public static final Registry<EntityType<?>> REG = new Registry(EntityType.class, LostTrinkets.MOD_ID);
    public static final EntityType<DarkVexEntity> DARK_VEX = REG.register("dark_vex", DarkVexEntity::new, EntityClassification.CREATURE, 0.4F, 0.8F, 3, 80, true);

    public static void register() {
        GlobalEntityTypeAttributes.put(DARK_VEX, DarkVexEntity.getAttribute().create());
    }
}
