package owmii.losttrinkets.core.mixin;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import owmii.losttrinkets.handler.TargetHandler;

@Mixin(EntityPredicate.class)
public class EntityPredicateMixin {
    @Inject(method = "canTarget(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/LivingEntity;)Z", at = @At("TAIL"), cancellable = true)
    public void canTarget(LivingEntity attacker, LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ()) {
            if (TargetHandler.preventTargeting(attacker, target)) {
                cir.setReturnValue(false);
            }
        }
    }
}
