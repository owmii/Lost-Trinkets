package owmii.losttrinkets.core.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.EnchantmentContainer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;

import javax.annotation.Nullable;

@Mixin(EnchantmentContainer.class)
public class EnchantmentContainerMixin {
    @Nullable
    private PlayerEntity player;

    @Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/util/IWorldPosCallable;)V", at = @At("RETURN"))
    private void enchantmentContainer(int id, PlayerInventory playerInventory, IWorldPosCallable worldPosCallable, CallbackInfo ci) {
        this.player = playerInventory.player;
    }

    @Inject(method = "getPower", at = @At("TAIL"), cancellable = true, remap = false)
    private void getPower(World world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        if (this.player != null) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(this.player);
            if (trinkets.isActive(Itms.BOOK_O_ENCHANTING)) {
                cir.setReturnValue(15.0F);
            }
        }
    }
}
