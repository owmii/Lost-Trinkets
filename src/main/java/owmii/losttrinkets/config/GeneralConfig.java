package owmii.losttrinkets.config;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import owmii.losttrinkets.api.trinket.Trinkets;

import java.util.List;

public class GeneralConfig {
    public final ForgeConfigSpec.BooleanValue unlockEnabled;
    public final ForgeConfigSpec.ConfigValue<List<String>> blackList;
    public final ForgeConfigSpec.ConfigValue<List<String>> nonRandom;

    public final ForgeConfigSpec.IntValue startSlots;
    public final ForgeConfigSpec.IntValue slotCost;
    public final ForgeConfigSpec.IntValue slotUpFactor;
    public final ForgeConfigSpec.BooleanValue killingUnlockEnabled;
    public final ForgeConfigSpec.IntValue killing;
    public final ForgeConfigSpec.BooleanValue bossKillingUnlockEnabled;
    public final ForgeConfigSpec.IntValue bossKilling;
    public final ForgeConfigSpec.BooleanValue farmingUnlockEnabled;
    public final ForgeConfigSpec.IntValue farming;
    public final ForgeConfigSpec.BooleanValue oresMiningUnlockEnabled;
    public final ForgeConfigSpec.IntValue oresMining;
    public final ForgeConfigSpec.BooleanValue tradingUnlockEnabled;
    public final ForgeConfigSpec.IntValue trading;
    public final ForgeConfigSpec.BooleanValue woodCuttingUnlockEnabled;
    public final ForgeConfigSpec.IntValue woodCutting;

    public GeneralConfig(ForgeConfigSpec.Builder builder) {
        builder.push("Trinket_Slots");
        this.startSlots = builder.comment("Numbers of trinket slots the player will start with (Only effect newer players!!).").defineInRange("startSlots", 1, 0, 40);
        this.slotCost = builder.comment("Levels of xp needed to unlock a trinket slot.").defineInRange("slotCost", 15, 0, 1000);
        this.slotUpFactor = builder.comment("Amount of Xp levels added to the next unlocking cost.").defineInRange("slotUpFactor", 3, 0, 20);
        builder.pop();

        this.unlockEnabled = builder.comment("Set to false to disable the default way of unlocking trinkets.").define("unlockEnabled", true);
        this.blackList = builder
                .comment("List of banned trinkets eg: [\"losttrinkets:piggy\", \"losttrinkets:magical_feathers\"]"
                        , "The trinkets listed in here will also be removed from players that already unlocked them.")
                .define("blackList", Lists.newArrayList());
        this.nonRandom = builder
                .comment("List of trinkets that can't be unlocked randomly eg: [\"losttrinkets:piggy\", \"losttrinkets:magical_feathers\"]"
                        , "The trinkets listed in here will not be removed from players that already unlocked them.")
                .define("nonRandom", Lists.newArrayList());

        builder.push("Killing_Unlocks");
        this.killingUnlockEnabled = builder.comment("Set to false to disable unlocking trinkets from killing non-Boss entities.").define("killingUnlockEnabled", true);
        this.killing = builder.comment("Rarity of unlocking a trinket from killing non-Boss entities. (Greater number = more rare)").defineInRange("killing", 120, 2, 100000);
        builder.pop();
        builder.push("Bosses_Killing_Unlocks");
        this.bossKillingUnlockEnabled = builder.comment("Set to false to disable unlocking trinkets from killing Bosses.").define("bossKillingUnlockEnabled", true);
        this.bossKilling = builder.comment("Rarity of unlocking a trinket from killing Bosses. (Greater number = more rare)").defineInRange("bossKilling", 10, 2, 100000);
        builder.pop();
        builder.push("Farming_Unlocks");
        this.farmingUnlockEnabled = builder.comment("Set to false to disable unlocking trinkets from farming.").define("farmingUnlockEnabled", true);
        this.farming = builder.comment("Rarity of unlocking a trinket from farming. (Greater number = more rare)").defineInRange("farming", 140, 2, 100000);
        builder.pop();
        builder.push("Ores_Mining_Unlocks");
        this.oresMiningUnlockEnabled = builder.comment("Set to false to disable unlocking trinkets from mining ores.").define("oresMiningUnlockEnabled", true);
        this.oresMining = builder.comment("Rarity of unlocking a trinket from mining ores. (Greater number = more rare)").defineInRange("oresMining", 100, 2, 100000);
        builder.pop();
        builder.push("Trading_Unlocks");
        this.tradingUnlockEnabled = builder.comment("Set to false to disable unlocking trinkets from trading.").define("tradingUnlockEnabled", true);
        this.trading = builder.comment("Rarity of unlocking a trinket from trading. (Greater number = more rare)").defineInRange("trading", 30, 2, 100000);
        builder.pop();
        builder.push("Wood_Cutting_Unlocks");
        this.woodCuttingUnlockEnabled = builder.comment("Set to false to disable unlocking trinkets from cutting trees.").define("woodCuttingUnlockEnabled", true);
        this.woodCutting = builder.comment("Rarity of unlocking a trinket from cutting trees. (Greater number = more rare)").defineInRange("woodCutting", 170, 2, 100000);
        builder.pop();
    }

    public int calcCost(Trinkets trinkets) {
        return this.slotCost.get() + ((trinkets.getSlots() - this.startSlots.get()) * this.slotUpFactor.get());
    }
}
