package owmii.losttrinkets.block;

import net.minecraft.tileentity.TileEntityType;
import owmii.lib.registry.Registry;
import owmii.losttrinkets.LostTrinkets;

public class ITiles {
    @SuppressWarnings("unchecked")
    public static final Registry<TileEntityType<?>> REG = new Registry(TileEntityType.class, LostTrinkets.MOD_ID);
}
