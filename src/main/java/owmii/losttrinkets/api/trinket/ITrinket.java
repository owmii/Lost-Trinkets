package owmii.losttrinkets.api.trinket;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeItem;

public interface ITrinket extends IForgeItem, IItemProvider {
    void onActivated(World world, BlockPos pos, PlayerEntity player);

    void onDeactivated(World world, BlockPos pos, PlayerEntity player);

    Rarity getRarity();

    boolean isUnlockable();

    void setUnlockable(boolean unlockable);
}
