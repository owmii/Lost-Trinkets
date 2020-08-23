package owmii.losttrinkets.api.trinket;

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

    Rarity(int weight, int color) {
        this.weight = weight;
        this.color = color;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getColor() {
        return this.color;
    }
}
