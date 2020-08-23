package owmii.losttrinkets.item.trinkets;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class DragonBreathTrinket extends Trinket<DragonBreathTrinket> {
    public DragonBreathTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static List<ItemStack> autoSmelt(List<ItemStack> stacks, PlayerEntity player) {
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, player.getHeldItemMainhand()) <= 0) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (trinkets.isActive(Itms.DRAGON_BREATH)) {
                List<ItemStack> drops1 = new ArrayList<>();
                List<ItemStack> stacks1 = new ArrayList<>(stacks);
                Iterator<ItemStack> itr = stacks1.iterator();
                while (itr.hasNext()) {
                    Optional<FurnaceRecipe> recipe = player.world.getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(itr.next()), player.world);
                    if (recipe.isPresent()) {
                        ItemStack output = recipe.get().getRecipeOutput().copy();
                        if (!output.isEmpty()) {
                            drops1.add(output);
                            itr.remove();
                        }
                    }
                }
                drops1.addAll(stacks1);
                return drops1;
            }
        }
        return stacks;
    }
}
