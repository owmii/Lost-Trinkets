package owmii.losttrinkets.item;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Item;
import owmii.lib.registry.Registry;
import owmii.losttrinkets.LostTrinkets;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.block.Blcks;
import owmii.losttrinkets.item.trinkets.*;

public class Itms {
    public static final Registry<Item> REG = new Registry<>(LostTrinkets.MOD_ID, Blcks.REG.getBlockItems(ItemGroups.MAIN));
    public static final Trinket PIGGY = REG.register("piggy", new Trinket(Rarity.COMMON, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket CREEPO = REG.register("creepo", new CreepoTrinket(Rarity.COMMON, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket HORSESHOE = REG.register("horseshoe", new HorseshoeTrinket(Rarity.COMMON, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket BUTCHERS_CLEAVER = REG.register("butchers_cleaver", new ButchersCleaverTrinket(Rarity.COMMON, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket SLINGSHOT = REG.register("slingshot", new SlingshotTrinket(Rarity.COMMON, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket MAGNETO = REG.register("magneto", new MagnetoTrinket(Rarity.COMMON, new Item.Properties().group(ItemGroups.MAIN)));

    public static final Trinket ROCK_CANDY = REG.register("rock_candy", new RockCandyTrinket(Rarity.UNCOMMON, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket LUNCH_BAG = REG.register("lunch_bag", new LunchBagTrinket(Rarity.UNCOMMON, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket LUCK_COIN = REG.register("luck_coin", new LuckCoinTrinket(Rarity.UNCOMMON, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket MINERS_PICK = REG.register("miners_pick", new MinersPickTrinket(Rarity.UNCOMMON, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket THA_CLOUD = REG.register("tha_cloud", new ThaCloudTrinket(Rarity.UNCOMMON, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket TURTLE_SHELL = REG.register("turtle_shell", new TurtleShellTrinket(Rarity.UNCOMMON, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket ICE_SHARD = REG.register("ice_shard", new IceShardTrinket(Rarity.UNCOMMON, new Item.Properties().group(ItemGroups.MAIN)));

    public static final Trinket EMPTY_AMULET = REG.register("empty_amulet", new Trinket(Rarity.RARE, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket THA_SPIDER = REG.register("tha_spider", new Trinket(Rarity.RARE, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket GLASS_SHARD = REG.register("glass_shard", new Trinket(Rarity.RARE, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket BLAZE_HEART = REG.register("blaze_heart", new BlazeHeartTrinket(Rarity.RARE, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket THA_GHOST = REG.register("tha_ghost", new ThaGhostTrinket(Rarity.RARE, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket TREBLE_HOOKS = REG.register("treble_hooks", new TrebleHooksTrinket(Rarity.RARE, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket THA_WIZARD = REG.register("tha_wizard", new ThaWizardTrinket(Rarity.RARE, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket THA_BAT = REG.register("tha_bat", new ThaBatTrinket(Rarity.RARE, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket BLANK_EYES = REG.register("blank_eyes", new Trinket(Rarity.RARE, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket BIG_FOOT = REG.register("big_foot", new WarmVoidTrinket(Rarity.RARE, new Item.Properties().group(ItemGroups.MAIN)));

    public static final Trinket BOOK_O_ENCHANTING = REG.register("book_o_enchanting", new Trinket(Rarity.MASTER, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket WARM_VOID = REG.register("warm_void", new WarmVoidTrinket(Rarity.MASTER, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket GOLDEN_MELON = REG.register("golden_melon", new GoldenMelonTrinket(Rarity.MASTER, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket WITHER_NAIL = REG.register("wither_nail", new WitherNailTrinket(Rarity.MASTER, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket SERPENT_TOOTH = REG.register("serpent_tooth", new SerpentToothTrinket(Rarity.MASTER, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket MAD_PIGGY = REG.register("mad_piggy", new MadPiggyTrinket(Rarity.MASTER, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket MINDS_EYE = REG.register("minds_eye", new Trinket(Rarity.MASTER, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket GOLDEN_SWATTER = REG.register("golden_swatter", new GoldenSwatterTrinket(Rarity.MASTER, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket STICKY_MIND = REG.register("sticky_mind", new StickyMindTrinket(Rarity.MASTER, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket FIRE_MIND = REG.register("fire_mind", new FireMindTrinket(Rarity.MASTER, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket THA_GOLEM = REG.register("tha_golem", new Trinket(Rarity.MASTER, new Item.Properties().group(ItemGroups.MAIN))).add(Attributes.KNOCKBACK_RESISTANCE, "afb13d18-56f2-4e1f-8281-8cc7e3005eef", 1.0D);
    public static final Trinket DRAGON_BREATH = REG.register("dragon_breath", new DragonBreathTrinket(Rarity.MASTER, new Item.Properties().group(ItemGroups.MAIN)));

    public static final Trinket KARMA = REG.register("karma", new Trinket(Rarity.ELITE, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket DARK_DAGGER = REG.register("dark_dagger", new DarkDaggerTrinket(Rarity.ELITE, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket STARFISH = REG.register("starfish", new StarfishTrinket(Rarity.ELITE, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket DROP_SPINDLE = REG.register("drop_spindle", new DropSpindleTrinket(Rarity.ELITE, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket EMBER = REG.register("ember", new EmberTrinket(Rarity.ELITE, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket TEA_LEAF = REG.register("tea_leaf", new TeaLeafTrinket(Rarity.ELITE, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket COFFEE_BEAN = REG.register("coffee_bean", new CoffeeBeanTrinket(Rarity.ELITE, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket OXALIS = REG.register("oxalis", new OxalisTrinket(Rarity.ELITE, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket GOLDEN_SKULL = REG.register("golden_skull", new GoldenSkullTrinket(Rarity.ELITE, new Item.Properties().group(ItemGroups.MAIN)));

    public static final Trinket DARK_EGG = REG.register("dark_egg", new DarkEggTrinket(Rarity.EPIC, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket PILLOW_OF_SECRETS = REG.register("pillow_of_secrets", new PillowOfSecretsTrinket(Rarity.EPIC, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket THA_SPIRIT = REG.register("tha_spirit", new ThaSpiritTrinket(Rarity.EPIC, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket MIRROR_SHARD = REG.register("mirror_shard", new MirrorShardTrinket(Rarity.EPIC, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket MOSSY_RING = REG.register("mossy_ring", new MossyRingTrinket(Rarity.EPIC, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket MOSSY_BELT = REG.register("mossy_belt", new MossyBeltTrinket(Rarity.EPIC, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket TREASURE_RING = REG.register("treasure_ring", new TreasureRingTrinket(Rarity.EPIC, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket OCTOPICK = REG.register("octopick", new OctopickTrinket(Rarity.EPIC, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket SILVER_NAIL = REG.register("silver_nail", new Trinket(Rarity.EPIC, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket GLORY_SHARDS = REG.register("glory_shards", new Trinket(Rarity.EPIC, new Item.Properties().group(ItemGroups.MAIN)));

    public static final Trinket ASH_GLOVES = REG.register("ash_gloves", new Trinket(Rarity.LEGENDARY, new Item.Properties().group(ItemGroups.MAIN)).add(Attributes.ATTACK_SPEED, "1a71bd06-0d8b-459e-b961-fbd992d61c5d", 1024.0D));
    public static final Trinket RUBY_HEART = REG.register("ruby_heart", new RubyHeartTrinket(Rarity.LEGENDARY, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket GOLDEN_HORSESHOE = REG.register("golden_horseshoe", new Trinket(Rarity.LEGENDARY, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket GOLDEN_TOOTH = REG.register("golden_tooth", new Trinket(Rarity.LEGENDARY, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket BROKEN_HEART_1 = REG.register("broken_heart_1", new Trinket(Rarity.LEGENDARY, new Item.Properties().group(ItemGroups.MAIN)).add(Attributes.MAX_HEALTH, "092962d0-2711-48e0-9a84-f768ea4aeeb2", 4.0D));
    public static final Trinket BROKEN_HEART_2 = REG.register("broken_heart_2", new Trinket(Rarity.LEGENDARY, new Item.Properties().group(ItemGroups.MAIN)).add(Attributes.MAX_HEALTH, "bf4f459a-d398-4cc1-a146-9d3828f2201a", 4.0D));
    public static final Trinket BROKEN_HEART_3 = REG.register("broken_heart_3", new Trinket(Rarity.LEGENDARY, new Item.Properties().group(ItemGroups.MAIN)).add(Attributes.MAX_HEALTH, "cb979db5-2f24-40b5-b2ef-4b7d29491ef4", 4.0D));
    public static final Trinket BROKEN_HEART_4 = REG.register("broken_heart_4", new Trinket(Rarity.LEGENDARY, new Item.Properties().group(ItemGroups.MAIN)).add(Attributes.MAX_HEALTH, "1816e016-b569-4258-889e-d45829628248", 4.0D));
    public static final Trinket BROKEN_HEART_5 = REG.register("broken_heart_5", new Trinket(Rarity.LEGENDARY, new Item.Properties().group(ItemGroups.MAIN)).add(Attributes.MAX_HEALTH, "a3fee661-c9e0-40d9-8386-ac245576bed0", 4.0D));
    public static final Trinket OCTOPUS_LEG = REG.register("octopus_leg", new OctopusLegTrinket(Rarity.LEGENDARY, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket MAGICAL_HERBS = REG.register("magical_herbs", new MagicalHerbsTrinket(Rarity.LEGENDARY, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket MAGICAL_FEATHERS = REG.register("magical_feathers", new MagicalFeathersTrinket(Rarity.LEGENDARY, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket MAD_AURA = REG.register("mad_aura", new MadAuraTrinket(Rarity.LEGENDARY, new Item.Properties().group(ItemGroups.MAIN)));
    public static final Trinket BROKEN_TOTEM = REG.register("broken_totem", new Trinket(Rarity.LEGENDARY, new Item.Properties().group(ItemGroups.MAIN)));

    public static final Item TREASURE_BAG = REG.register("treasure_bag", new TreasureBagItem(new Item.Properties().group(ItemGroups.MAIN)));
}
