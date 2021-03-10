package owmii.losttrinkets.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import owmii.losttrinkets.LostTrinkets;
import owmii.losttrinkets.api.LostTrinketsAPI;
import owmii.losttrinkets.handler.UnlockManager;

import java.util.Collection;

@Mod.EventBusSubscriber
public class MainCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal(LostTrinkets.MOD_ID)
                .then(Commands.literal("unlock").requires(source -> {
                    return source.hasPermissionLevel(2);
                }).then(Commands.argument("targets", EntityArgument.players()).then(Commands.literal("all").executes(context -> {
                    Collection<ServerPlayerEntity> players = EntityArgument.getPlayers(context, "targets");
                    players.forEach(player -> {
                        UnlockManager.getTrinkets().forEach(trinket -> {
                            UnlockManager.unlock(player, trinket, false, false);
                        });
                    });
                    context.getSource().sendFeedback(new TranslationTextComponent("chat.losttrinkets.unlocked.all"), true);
                    return players.size();
                })).then(Commands.literal("random").executes(context -> {
                    Collection<ServerPlayerEntity> players = EntityArgument.getPlayers(context, "targets");
                    players.forEach(LostTrinketsAPI.get()::unlock);
                    return players.size();
                }))))
                .then(Commands.literal("clear").requires(source -> {
                    return source.hasPermissionLevel(2);
                }).then(Commands.argument("targets", EntityArgument.players()).executes(context -> {
                    Collection<ServerPlayerEntity> players = EntityArgument.getPlayers(context, "targets");
                    players.forEach(player -> {
                        LostTrinketsAPI.getTrinkets(player).clear();
                    });
                    return players.size();
                }))));
    }

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        register(event.getDispatcher());
    }
}
