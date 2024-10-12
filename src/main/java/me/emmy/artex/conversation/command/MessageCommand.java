package me.emmy.artex.conversation.command;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * @author Emmy
 * @project Artex
 * @date 11/09/2024 - 23:03
 */
public class MessageCommand extends BaseCommand {
    @Command(name = "message", aliases = {"msg", "tell", "whisper"})
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 2) {
            player.sendMessage(CC.translate("&6Usage: &e/message &b<player> <message>"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(CC.translate("&cPlayer not found."));
            return;
        }

        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        Artex.getInstance().getConversationHandler().startConversation(player.getUniqueId(), target.getUniqueId(), message);
    }
}