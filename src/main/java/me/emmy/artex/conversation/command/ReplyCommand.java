package me.emmy.artex.conversation.command;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author Emmy
 * @project Artex
 * @date 11/09/2024 - 23:03
 */
public class ReplyCommand extends BaseCommand {
    @Command(name = "reply", aliases = {"r"})
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        UUID lastConversantUUID = Artex.getInstance().getConversationHandler().getLastConversant(player.getUniqueId());
        Player lastConversant = Bukkit.getServer().getPlayer(lastConversantUUID);

        if (args.length < 1) {
            if (lastConversant == null) {
                player.sendMessage(CC.translate("&cYou haven't been in a conversation yet."));
                return;
            }

            player.sendMessage(CC.translate("&bYou've last messaged: &3" + Artex.getInstance().getProfileRepository().getIProfile(lastConversantUUID).getHighestRankBasedOnGrant().getColor() + lastConversant.getName()));
            return;
        }

        if (lastConversantUUID == null) {
            player.sendMessage(CC.translate("&cYou haven't been in a conversation yet."));
            return;
        }

        Player target = Bukkit.getServer().getPlayer(lastConversantUUID);
        String message = String.join(" ", Arrays.copyOfRange(args, 0, args.length));
        Artex.getInstance().getConversationHandler().startConversation(player.getUniqueId(), target.getUniqueId(), message);
    }
}