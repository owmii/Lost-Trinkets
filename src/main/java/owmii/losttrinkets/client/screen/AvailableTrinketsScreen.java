package owmii.losttrinkets.client.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
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
import owmii.losttrinkets.network.packet.SetActivePacket;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

public class AvailableTrinketsScreen extends AbstractLTScreen {
    @Nullable
    protected final Screen prevScreen;
    private int x, y, columns = 14, rows = 5, btnDim = 28, headID;

    public AvailableTrinketsScreen(@Nullable Screen prevScreen, int headID) {
        super(new TranslationTextComponent("gui.losttrinkets.trinket.available"));
        this.prevScreen = prevScreen;
        this.headID = headID;
    }

    @Override
    protected void init() {
        if (this.mc.player != null) {
            this.x = this.width / 2 - this.columns * this.btnDim / 2;
            this.y = this.height / 2 - this.rows * this.btnDim / 2;
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(this.mc.player);
            List<ITrinket> all = trinkets.getAvailableTrinkets();
            int total = all.size();
            int cur = 0;
            for (int j1 = 0; j1 < this.rows; ++j1) {
                for (int j2 = 0; j2 < this.columns; ++j2) {
                    int i = j2 + j1 * this.columns + this.headID;
                    if (i + 1 <= total) {
                        final ITrinket trinket = all.get(i);
                        addButton(new TrinketButton(this.x + j2 * this.btnDim, this.y + j1 * this.btnDim, Textures.TRINKET_BG, trinket, button -> {
                            LostTrinkets.NET.toServer(new SetActivePacket(i));
                            trinkets.setActive(trinket, this.mc.player);
                            setRefreshScreen(new TrinketsScreen());
                        }, (button, matrix, i1, i2) -> {
                            ItemStack stack = new ItemStack(trinket);
                            List<ITextComponent> list = Lists.newArrayList();
                            list.add(new StringTextComponent("").append(stack.getDisplayName()).mergeStyle(TextFormatting.LIGHT_PURPLE));
                            list.add(new TranslationTextComponent(Util.makeTranslationKey("info", Registry.ITEM.getKey(stack.getItem()))).mergeStyle(TextFormatting.GRAY));
                            list.add(new TranslationTextComponent("gui.losttrinkets.rarity." + trinket.getRarity().name().toLowerCase(Locale.ENGLISH)).mergeStyle(TextFormatting.DARK_GRAY));
                            GuiUtils.drawHoveringText(matrix, list, i1, i2, this.width, this.height, 240, this.font);
                        }));
                        cur++;
                    }
                }
            }
            int x1 = this.x + this.columns * this.btnDim / 2 - this.btnDim / 2 - 30;
            int y1 = this.y + 150;
            int i = this.columns * this.rows;
            if (cur == this.columns * this.rows && total > this.headID) {
                addButton(new IconButton(60 + x1, y1, Textures.TRINKET_NEXT, button -> {
                    this.mc.displayGuiScreen(new AvailableTrinketsScreen(this.prevScreen, this.headID + i));
                }, this));
            }
            if (this.headID > 0) {
                addButton(new IconButton(x1, y1, Textures.TRINKET_PREV, button -> {
                    this.mc.displayGuiScreen(new AvailableTrinketsScreen(this.prevScreen, Math.max(0, this.headID - i)));
                }, this));
            }
        }
    }

    @Override
    public void render(MatrixStack matrix, int mx, int my, float pt) {
        renderBackground(matrix);
        if (this.mc.player != null) {
            List<ITrinket> all = LostTrinketsAPI.getTrinkets(this.mc.player).getAvailableTrinkets();
            if (all.isEmpty()) {
                int x = this.width / 2;
                int y = this.height / 2;
                String name = I18n.format("gui.losttrinkets.trinket.empty");
                this.font.drawString(matrix, name, x - this.font.getStringWidth(name) / 2, y - 5, 0x999999);
            }
        }
        super.render(matrix, mx, my, pt);
        String s = getTitle().getString();
        this.font.drawStringWithShadow(matrix, s, this.width / 2 - this.font.getStringWidth(s) / 2, this.y - 20, 0x999999);
    }

    @Override
    public void closeScreen() {
        if (this.prevScreen instanceof TrinketsScreen) {
            this.mc.displayGuiScreen(this.prevScreen);
        }
    }
}
