package me.emmy.artex.tag.command.admin.impl;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.tag.Tag;
import me.emmy.artex.util.CC;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project Artex
 * @date 03/09/2024 - 14:08
 */
public class TagAdminSetBoldCommand extends BaseCommand {
    @Command(name = "tagadmin.setbold", permission = "artex.tag.admin.setbold", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 2) {
            sender.sendMessage(CC.translate("&6Usage: &e/tagadmin setbold &b(name) &b(true/false)"));
            return;
        }

        String name = args[0];
        Tag tag = Artex.getInstance().getTagService().getTag(name);
        if (tag == null) {
            sender.sendMessage(CC.translate("&cA tag with that name does not exist."));
            return;
        }

        boolean bold;

        try {
            bold = Boolean.parseBoolean(args[1]);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(CC.translate("&cIncorrect usage. Please use 'true' or 'false'."));
            return;
        }

        tag.setBold(bold);
        Artex.getInstance().getTagService().saveTag(tag);
        sender.sendMessage(CC.translate("&aSuccessfully set the bold of the tag &f" + name + "&r&a to &f" + bold + "&r&a."));
    }
}
