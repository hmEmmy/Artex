package me.emmy.artex.chat.command;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project Artex
 * @date 04/06/2024 - 20:28
 */
public class MuteChatCommand extends BaseCommand {
    @Command(name = "mutechat", permission = "artex.command.mutechat", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        Artex.getInstance().getChatRepository().setChatMuted(true);
        Bukkit.broadcastMessage(CC.translate("&cChat has been muted by " + sender.getName() + "."));
    }
}