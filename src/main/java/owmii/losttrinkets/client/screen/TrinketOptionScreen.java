package owmii.losttrinkets.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
import owmii.losttrinkets.LostTrinkets;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.ITrinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.network.packet.SetInactivePacket;

import javax.annotation.Nullable;


public class TrinketOptionScreen extends AbstractLTScreen {
    private final ITrinket trinket;
    @SuppressWarnings("NullableProblems")
    private Button button;

    @Nullable
    protected final Screen prevScreen;

    protected TrinketOptionScreen(ITrinket trinket, @Nullable Screen prevScreen) {
        super(new TranslationTextComponent(trinket.getItem().getTranslationKey()));
        this.trinket = trinket;
        this.prevScreen = prevScreen;
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 60 / 2;
        int y = this.height / 3 - 20 / 2;
        if (this.mc.player != null) {
            this.button = addButton(new Button(x, y + 70, 60, 20, new TranslationTextComponent("Remove"), (p_214293_1_) -> {
                Trinkets trinkets = LostTrinketsAPI.getTrinkets(this.mc.player);
                int i = trinkets.getActiveTrinkets().indexOf(this.trinket);
                if (i >= 0) {
                    LostTrinkets.NET.toServer(new SetInactivePacket(i));
                    trinkets.setInactive(this.trinket, this.mc.player);
                    setRefreshScreen(new TrinketsScreen());
                }
            }));
        }
    }

    @Override
    public void render(MatrixStack matrix, int mx, int my, float pt) {
        renderBackground(matrix);
        int x = this.width / 2 - 16 / 2;
        int y = this.height / 3 - 16 / 2;
        RenderSystem.pushMatrix();
        RenderSystem.translated(x - 16.0F, y - 16.0F, 0.0F);
        RenderSystem.scaled(3.0F, 3.0F, 1.0F);
        this.mc.getItemRenderer().renderItemAndEffectIntoGUI(new ItemStack(this.trinket), 0, 0);
        RenderSystem.popMatrix();

        super.render(matrix, mx, my, pt);

        String name = I18n.format(this.trinket.getItem().getTranslationKey());
        this.font.drawString(matrix, name, 8 + x - this.font.getStringWidth(name) / 2, y + 32, 0x999999);
    }

    @Override
    public void closeScreen() {
        if (this.prevScreen instanceof TrinketsScreen) {
            this.mc.displayGuiScreen(this.prevScreen);
        }
    }
}
