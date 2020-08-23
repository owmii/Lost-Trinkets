package owmii.losttrinkets.client.screen;

import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.ITextComponent;
import owmii.lib.client.screen.ScreenBase;
import owmii.losttrinkets.client.handler.KeyHandler;

import javax.annotation.Nullable;

public class AbstractLTScreen extends ScreenBase {
    private boolean refresh;
    @Nullable
    private AbstractLTScreen toRefresh;

    protected AbstractLTScreen(ITextComponent title) {
        super(title);
    }

    @Override
    public void tick() {
        if (this.refresh && this.toRefresh != null) {
            this.mc.displayGuiScreen(this.toRefresh);
            this.refresh = false;
            this.toRefresh = null;
        }
    }

    public void refresh() {
        this.refresh = true;
    }

    public void setRefreshScreen(@Nullable AbstractLTScreen screen) {
        this.toRefresh = screen;
    }

    @Override
    public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
        if (super.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_)) {
            return true;
        } else {
            InputMappings.Input code = InputMappings.getInputByCode(p_231046_1_, p_231046_2_);
            if (KeyHandler.TRINKET_GUI.isActiveAndMatches(code)) {
                if (this.mc.player != null) {
                    closeScreen();
                }
                return true;
            }
        }
        return false;
    }
}
