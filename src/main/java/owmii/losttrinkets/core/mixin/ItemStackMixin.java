package owmii.losttrinkets.core.mixin;

import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.extensions.IForgeItemStack;
import org.spongepowered.asm.mixin.Mixin;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin extends CapabilityProvider<ItemStack> implements IForgeItemStack {
    protected ItemStackMixin(Class<ItemStack> baseClass) {
        super(baseClass);
    }

    @Override
    public boolean isEnderMask(PlayerEntity player, EndermanEntity endermanEntity) {
        boolean enderMask = getStack().getItem().isEnderMask(getStack(), player, endermanEntity);
        if (!enderMask) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            return trinkets.isActive(Itms.BLANK_EYES);
        }
        return true;
    }
}
