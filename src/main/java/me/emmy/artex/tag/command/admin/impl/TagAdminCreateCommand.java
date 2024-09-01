package me.emmy.artex.tag.command.admin.impl;

import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project Artex
 * @date 01/09/2024 - 20:31
 */
public class TagAdminCreateCommand extends BaseCommand {
    @Command(name = "tagadmin.create", permission = "artex.tag.admin", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        sender.sendMessage(CC.translate("&cI am too lazy to implement this command."));
    }
}
