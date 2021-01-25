package owmii.losttrinkets.api.trinket;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeItem;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public interface ITrinket extends IForgeItem, IItemProvider {
    default void addTrinketDescription(ItemStack stack, List<ITextComponent> lines) {
        lines.add(new TranslationTextComponent(Util.makeTranslationKey("info", ForgeRegistries.ITEMS.getKey(stack.getItem()))).mergeStyle(TextFormatting.GRAY));
    }

    void onActivated(World world, BlockPos pos, PlayerEntity player);

    void onDeactivated(World world, BlockPos pos, PlayerEntity player);

    Rarity getRarity();

    boolean isUnlockable();

    void setUnlockable(boolean unlockable);
}
