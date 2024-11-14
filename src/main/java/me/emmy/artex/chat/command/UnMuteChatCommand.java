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
 * @date 04/06/2024 - 20:41
 */
public class UnMuteChatCommand extends BaseCommand {
    @Command(name = "unmutechat", permission = "artex.command.unmutechat", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        Artex.getInstance().getChatService().setChatMuted(false);
        Bukkit.broadcastMessage(CC.translate("&a&lChat has been unmuted by " + sender.getName() + "."));
    }
}