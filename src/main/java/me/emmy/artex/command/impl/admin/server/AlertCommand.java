package me.emmy.artex.command.impl.admin.server;

import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

/**
 * @author Emmy
 * @project Artex
 * @date 15/11/2024 - 10:41
 */
public class AlertCommand extends BaseCommand {
    @Command(name = "alert", permission = "artex.command.alert", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 1) {
            sender.sendMessage(CC.translate("&6Usage: &e/alert &b<message>"));
            return;
        }

        String message = String.join(" ", Arrays.copyOfRange(args, 0, args.length));
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(CC.translate("&4&lAlert &8- &7" + message)));
    }
}