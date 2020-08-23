package owmii.losttrinkets.core.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import owmii.losttrinkets.item.trinkets.DragonBreathTrinket;

import java.util.List;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {
    @Shadow
    abstract BlockState getSelf();

    @Inject(method = "getDrops", at = @At("TAIL"), cancellable = true)
    public void getDrops(LootContext.Builder builder, CallbackInfoReturnable<List<ItemStack>> cir) {
        LootContext context = builder.withParameter(LootParameters.BLOCK_STATE, getSelf()).build(LootParameterSets.BLOCK);
        List<ItemStack> drops = cir.getReturnValue();
        Entity entity = context.get(LootParameters.THIS_ENTITY);
        if (entity instanceof PlayerEntity) {
            cir.setReturnValue(DragonBreathTrinket.autoSmelt(drops, (PlayerEntity) entity));
        }
    }
}
