package owmii.losttrinkets.core.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import owmii.losttrinkets.handler.UnlockHandler;

import javax.annotation.Nullable;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Inject(method = "harvestBlock", at = @At("HEAD"))
    private void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack, CallbackInfo ci) {
        UnlockHandler.checkBlockHarvest(player, world, pos, state);
    }
}
