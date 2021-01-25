package owmii.losttrinkets.api.trinket;

import net.minecraft.util.text.Color;
import net.minecraft.util.text.Style;

public enum Rarity {
    COMMON(4800, 0x777777),
    UNCOMMON(2700, 0x628ab5),
    RARE(1300, 0xf26a52),
    MASTER(400, 0x98f252),
    ELITE(290, 0xf2c552),
    EPIC(130, 0xf29752),
    LEGENDARY(70, 0xf26a52);

    private final int weight;
    private final int color;
    private final Style style;

    Rarity(int weight, int color) {
        this.weight = weight;
        this.color = color;
        this.style = Style.EMPTY.setColor(Color.func_240743_a_(this.color));
    }

    public int getWeight() {
        return this.weight;
    }

    public int getColor() {
        return this.color;
    }

    public Style getStyle() {
        return this.style;
    }
}
