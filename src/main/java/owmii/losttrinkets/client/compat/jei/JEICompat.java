package owmii.losttrinkets.client.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import owmii.losttrinkets.LostTrinkets;
import owmii.losttrinkets.handler.UnlockManager;

@JeiPlugin
public class JEICompat implements IModPlugin {
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        UnlockManager.getTrinkets().stream().map(ItemStack::new).forEach(stack -> registration.addIngredientInfo(stack, VanillaTypes.ITEM, I18n.format(Util.makeTranslationKey("info", Registry.ITEM.getKey(stack.getItem())))));
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(LostTrinkets.MOD_ID, "main");
    }
}
