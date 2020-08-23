package owmii.losttrinkets.item.trinkets;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;
import owmii.lib.util.Stack;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.api.trinket.Rarity;
import owmii.losttrinkets.api.trinket.Trinket;
import owmii.losttrinkets.api.trinket.Trinkets;
import owmii.losttrinkets.item.Itms;

import java.util.ArrayList;
import java.util.List;

public class OctopickTrinket extends Trinket<OctopickTrinket> {
    public OctopickTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void mine(PlayerEntity player, World world, BlockPos pos, BlockState state) {
        if (ForgeHooks.canHarvestBlock(state, player, world, pos)) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (!world.isRemote && trinkets.isActive(Itms.OCTOPICK)) {
                List<BlockPos> toBreak = Lists.newArrayList(pos);
                if (Tags.Blocks.ORES.contains(state.getBlock()) || state.getBlock() == Blocks.OBSIDIAN) {
                    for (BlockPos pos1 : BlockPos.getAllInBoxMutable(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
                        if (toBreak.contains(pos1)) continue;
                        BlockState state1 = world.getBlockState(pos1);
                        if (state.getBlock() == state1.getBlock()) {
                            toBreak.add(pos1.toImmutable());
                            for (BlockPos pos2 : BlockPos.getAllInBoxMutable(pos1.add(-1, -1, -1), pos1.add(1, 1, 1))) {
                                if (toBreak.contains(pos2)) continue;
                                BlockState state2 = world.getBlockState(pos2);
                                if (state.getBlock() == state2.getBlock()) {
                                    toBreak.add(pos2.toImmutable());
                                }
                            }
                        }
                    }
                }
                if (toBreak.size() > 1) {
                    List<ItemStack> drops = new ArrayList<>();
                    toBreak.forEach(pos1 -> {
                        BlockState state1 = world.getBlockState(pos1);
                        TileEntity tileentity = state1.hasTileEntity() ? world.getTileEntity(pos1) : null;
                        List<ItemStack> stacks = Block.getDrops(state1, (ServerWorld) world, pos1, tileentity, player, ItemStack.EMPTY);
                        boolean flag = state1.canHarvestBlock(world, pos1, player);
                        if (state1.removedByPlayer(world, pos1, player, flag, world.getFluidState(pos1))) {
                            drops.addAll(stacks);
                        }
                    });
                    drops.forEach(stack1 -> Stack.drop(player, stack1));
                }
            }
        }
    }
}
