package owmii.losttrinkets.client.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import owmii.lib.client.screen.widget.IconButton;
import owmii.losttrinkets.LostTrinkets;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.ITrinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.client.screen.widget.TrinketButton;
import owmii.losttrinkets.config.Configs;
import owmii.losttrinkets.network.packet.UnlockSlotPacket;

import java.util.ArrayList;
import java.util.List;

public class TrinketsScreen extends AbstractLTScreen {
    private int x, y, columns = 10, rows = 4, btnDim = 28;

    public TrinketsScreen() {
        super(new TranslationTextComponent("gui.losttrinkets.trinket.active"));
    }

    @Override
    protected void init() {
        if (this.mc.player != null) {
            this.x = this.width / 2 - this.columns * this.btnDim / 2;
            this.y = this.height / 2 - this.rows * this.btnDim / 2;
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(this.mc.player);
            int cost = Configs.GENERAL.calcCost(trinkets);
            List<ITrinket> all = trinkets.getActiveTrinkets();
            label:
            for (int j1 = 0; j1 < this.rows; ++j1) {
                for (int j2 = 0; j2 < this.columns; ++j2) {
                    int i = j2 + j1 * this.columns;
                    if (i + 1 <= all.size()) {
                        ITrinket trinket = all.get(i);
                        addButton(new TrinketButton(this.x + j2 * this.btnDim, this.y + j1 * this.btnDim, Textures.TRINKET_ACTIVE_BG, trinket, button -> {
                            this.mc.displayGuiScreen(new TrinketOptionScreen(trinket, this));
                        }, (button, matrix, i1, i2) -> {
                            ItemStack stack = new ItemStack(trinket);
                            ArrayList<ITextComponent> list = Lists.newArrayList();
                            list.add(stack.getDisplayName());
                            stack.getItem().addInformation(stack, this.mc.world, list, this.mc.gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);
                            GuiUtils.drawHoveringText(matrix, list, i1, i2, this.width, this.height, 240, this.font);
                        }));
                    } else {
                        boolean locked = i + 1 > trinkets.getSlots();
                        if (locked && cost < 0) {
                            break label;
                        }
                        addButton(new IconButton(this.x + j2 * this.btnDim, this.y + j1 * this.btnDim, locked ? Textures.TRINKET_BG_LOCKED : Textures.TRINKET_BG_ADD, button -> {
                            if (locked) {
                                LostTrinkets.NET.toServer(new UnlockSlotPacket());
                                setRefreshScreen(this);
                            } else {
                                this.mc.displayGuiScreen(new AvailableTrinketsScreen(this, 0));
                            }
                        }, this).setTooltip(tooltip -> {
                            if (locked) {
                                tooltip.add(new TranslationTextComponent("gui.losttrinkets.trinket.slot.locked").mergeStyle(TextFormatting.DARK_PURPLE));
                                if (!this.mc.player.isCreative()) {
                                    tooltip.add(new TranslationTextComponent("gui.losttrinkets.trinket.slot.cost", cost).mergeStyle(TextFormatting.DARK_GRAY));
                                }
                                tooltip.add(new StringTextComponent(""));
                                tooltip.add(new TranslationTextComponent("gui.losttrinkets.trinket.slot.click.unlock").mergeStyle(TextFormatting.GRAY));
                            } else {
                                tooltip.add(new TranslationTextComponent("gui.losttrinkets.trinket.slot.click.add").mergeStyle(TextFormatting.GRAY));
                            }
                        }));
                        if (locked) {
                            break label;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(MatrixStack matrix, int mx, int my, float pt) {
        renderBackground(matrix);
        super.render(matrix, mx, my, pt);
        String s = getTitle().getString();
        this.font.drawStringWithShadow(matrix, s, this.width / 2 - this.font.getStringWidth(s) / 2, this.y - 20, 0x999999);
    }
}
