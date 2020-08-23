package owmii.losttrinkets.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.items.ItemHandlerHelper;
import owmii.lib.item.ItemBase;
import owmii.lib.util.Server;

import java.util.List;
import java.util.Objects;

public class TreasureBagItem extends ItemBase {
    public TreasureBagItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if (world instanceof ServerWorld) {
            LootContext.Builder builder = new LootContext.Builder((ServerWorld) world);
            builder.withParameter(LootParameters.field_237457_g_, player.getPositionVec()).withSeed(world.rand.nextLong());
            builder.withLuck(player.getLuck()).withParameter(LootParameters.THIS_ENTITY, player);
            ResourceLocation rl = Objects.requireNonNull(getRegistryName());
            LootTable lootTable = Server.get().getLootTableManager().getLootTableFromLocation(rl);
            List<ItemStack> stacks = lootTable.generate(builder.build(LootParameterSets.GIFT));
            stacks.forEach(stack -> ItemHandlerHelper.giveItemToPlayer(player, stack.copy()));
            if (!player.isCreative()) {
                player.getHeldItem(hand).shrink(1);
            }
        }
        return ActionResult.resultConsume(player.getHeldItem(hand));
    }
}
