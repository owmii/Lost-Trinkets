package owmii.losttrinkets.item.trinkets;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.item.Itms;

import java.util.List;

@Mod.EventBusSubscriber
public class TrebleHooksTrinket extends Trinket<TrebleHooksTrinket> {
    public TrebleHooksTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @SubscribeEvent
    public static void onFished(ItemFishedEvent event) {
        PlayerEntity player = event.getPlayer();
        World world = player.world;
        if (world instanceof ServerWorld) {
            if (LostTrinketsAPI.getTrinkets(player).isActive(Itms.TREBLE_HOOKS)) {
                ItemStack stack = player.getHeldItemMainhand();
                ItemStack stack1 = player.getHeldItemOffhand();
                ItemStack rod = stack.getItem() instanceof FishingRodItem ? stack : stack1;
                for (int i = 0; i < 2; i++) {
                    FishingBobberEntity hook = event.getHookEntity();
                    Entity entity = hook.func_234616_v_();
                    if (entity != null) {
                        LootContext.Builder builder = (new LootContext.Builder((ServerWorld) world)).withParameter(LootParameters.field_237457_g_, hook.getPositionVec()).withParameter(LootParameters.TOOL, rod).withParameter(LootParameters.THIS_ENTITY, hook).withRandom(world.rand).withLuck((float) EnchantmentHelper.getFishingLuckBonus(rod) + player.getLuck());
                        builder.withParameter(LootParameters.KILLER_ENTITY, entity).withParameter(LootParameters.THIS_ENTITY, hook);
                        MinecraftServer server = world.getServer();
                        if (server != null) {
                            LootTable loottable = server.getLootTableManager().getLootTableFromLocation(LootTables.GAMEPLAY_FISHING);
                            List<ItemStack> list = loottable.generate(builder.build(LootParameterSets.FISHING));
                            for (ItemStack itemstack : list) {
                                ItemEntity itemEntity = new ItemEntity(world, hook.getPosX(), hook.getPosY(), hook.getPosZ(), itemstack);
                                double d0 = player.getPosX() - hook.getPosX();
                                double d1 = player.getPosY() - hook.getPosY();
                                double d2 = player.getPosZ() - hook.getPosZ();
                                itemEntity.setMotion(d0 * 0.1D, d1 * 0.1D + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08D, d2 * 0.1D);
                                world.addEntity(itemEntity);
                            }
                        }
                    }
                }
            }
        }
    }
}
