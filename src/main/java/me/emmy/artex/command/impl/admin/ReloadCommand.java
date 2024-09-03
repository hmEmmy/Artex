package me.emmy.artex.command.impl.admin;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import me.emmy.artex.util.ProjectInfo;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project Artex
 * @date 03/09/2024 - 15:55
 */
public class ReloadCommand extends BaseCommand {
    @Command(name = "reload", permission = "artex.command.reload", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        Artex.getInstance().reloadConfig();
        sender.sendMessage(CC.translate("&6Successfully reloaded &4" + ProjectInfo.NAME + "&6."));
    }
}
