package owmii.losttrinkets.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import owmii.losttrinkets.LostTrinkets;

public class ItemGroups {
    public static final ItemGroup MAIN = new ItemGroup(LostTrinkets.MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(Itms.CREEPO);
        }
    };
}
