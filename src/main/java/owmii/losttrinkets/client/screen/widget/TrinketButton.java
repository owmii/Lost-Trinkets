package owmii.losttrinkets.client.screen.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import owmii.lib.client.screen.Texture;
import owmii.losttrinkets.api.trinket.ITrinket;

public class TrinketButton extends Button {
    protected final ITrinket trinket;
    private final Texture texture;

    public TrinketButton(int x, int y, Texture texture, ITrinket trinket, IPressable pressable, ITooltip tooltip) {
        super(x, y, texture.getWidth(), texture.getHeight(), new StringTextComponent(""), pressable, tooltip);
        this.trinket = trinket;
        this.texture = texture;
    }


    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float pt) {
        if (this.visible) {
            this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            this.texture.draw(matrix, this.x, this.y);
            int i = (this.texture.getWidth() - 16) / 2;
            int j = (this.texture.getHeight() - 16) / 2;
            RenderSystem.pushMatrix();
            Minecraft mc = Minecraft.getInstance();
            RenderSystem.translated(i + this.x - 2.0F, j + this.y - 2.0F, 0.0F);
            RenderSystem.scaled(1.25F, 1.25F, 1.0F);
            mc.getItemRenderer().renderItemAndEffectIntoGUI(new ItemStack(this.trinket), 0, 0);
            RenderSystem.popMatrix();
        }
    }
}
