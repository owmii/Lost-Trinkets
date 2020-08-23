package owmii.losttrinkets.core.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.MerchantResultSlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import owmii.losttrinkets.handler.UnlockHandler;

@Mixin(MerchantResultSlot.class)
public class MerchantResultSlotMixin {
    @Inject(method = "onTake", at = @At("HEAD"))
    private void trade(PlayerEntity player, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        UnlockHandler.trade(player);
    }
}
