package owmii.losttrinkets.api.trinket;

import com.google.common.collect.Maps;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import owmii.losttrinkets.api.LostTrinketsAPI;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class Trinket<T extends Trinket> extends Item implements ITrinket {
    private final Map<Attribute, AttributeModifier> attributes = Maps.newHashMap();
    private final Rarity rarity;
    protected boolean unlockable = true;

    public Trinket(Rarity rarity, Properties properties) {
        super(properties.maxStackSize(1));
        this.rarity = rarity;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if (LostTrinketsAPI.get().unlock(player, this)) {
            ItemStack stack = player.getHeldItem(hand);
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            return ActionResult.resultConsume(stack);
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if (LostTrinketsAPI.get().isDisabled(this)) {
            tooltip.add(new TranslationTextComponent("gui.losttrinkets.disabled").mergeStyle(TextFormatting.DARK_RED));
        } else if (LostTrinketsAPI.get().isNonRandom(this)) {
            tooltip.add(new TranslationTextComponent("gui.losttrinkets.non_random").mergeStyle(TextFormatting.GRAY));
        }
        addTrinketDescription(stack, tooltip);
        tooltip.add(new TranslationTextComponent("gui.losttrinkets.rarity." + getRarity().name().toLowerCase(Locale.ENGLISH)).mergeStyle(TextFormatting.DARK_GRAY));
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return super.getDisplayName(stack).deepCopy().mergeStyle(this.getRarity().getStyle());
    }

    @Override
    public void onActivated(World world, BlockPos pos, PlayerEntity player) {}

    @Override
    public void onDeactivated(World world, BlockPos pos, PlayerEntity player) {}

    @Override
    public Rarity getRarity() {
        return this.rarity;
    }

    @Override
    public boolean isUnlockable() {
        return this.unlockable;
    }

    public Trinket noUnlock() {
        this.unlockable = false;
        return this;
    }

    @Override
    public void setUnlockable(boolean unlockable) {
        this.unlockable = unlockable;
    }

    @SuppressWarnings("unchecked")
    public T add(Attribute attribute, String uuid, double amount) {
        AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(uuid), "Attribute", amount, AttributeModifier.Operation.ADDITION);
        getAttributes().put(attribute, attributemodifier);
        return (T) this;
    }

    public void applyAttributes(PlayerEntity player) {
        for (Map.Entry<Attribute, AttributeModifier> entry : getAttributes().entrySet()) {
            ModifiableAttributeInstance attribute = player.getAttribute(entry.getKey());
            if (attribute != null) {
                AttributeModifier attributeModifier = entry.getValue();
                if (!attribute.hasModifier(attributeModifier)) {
                    attribute.applyPersistentModifier(attributeModifier);
                }
            }
        }
    }

    public void removeAttributes(PlayerEntity player) {
        for (Map.Entry<Attribute, AttributeModifier> entry : getAttributes().entrySet()) {
            ModifiableAttributeInstance attribute = player.getAttribute(entry.getKey());
            if (attribute != null) {
                attribute.removeModifier(entry.getValue());
            }
        }
    }

    public Map<Attribute, AttributeModifier> getAttributes() {
        return this.attributes;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}
