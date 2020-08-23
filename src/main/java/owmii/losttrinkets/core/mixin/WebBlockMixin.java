package owmii.losttrinkets.core.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WebBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.IForgeShearable;
import org.spongepowered.asm.mixin.Mixin;
import owmii.lib.util.Stack;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;

@Mixin(WebBlock.class)
public class WebBlockMixin extends Block implements IForgeShearable {
    public WebBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        boolean flag = false;
        if (entity instanceof PlayerEntity) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets((PlayerEntity) entity);
            if (trinkets.isActive(Itms.GLASS_SHARD)) {
                world.destroyBlock(pos, false);
                Stack.drop(entity, new ItemStack(Items.STRING, 1 + world.rand.nextInt(2)));
                flag = true;
            }
        }
        if (!flag) {
            entity.setMotionMultiplier(state, new Vector3d(0.25D, (double) 0.05F, 0.25D));
        }
    }
}
