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
 * @date 03/09/2024 - 14:11
 */
public class TagAdminSetItalicCommand extends BaseCommand {
    @Command(name = "tagadmin.setitalic", permission = "artex.tag.admin.setitalic", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 2) {
            sender.sendMessage(CC.translate("&6Usage: &e/tagadmin setitalic &b(name) &b(true/false)"));
            return;
        }

        String name = args[0];
        Tag tag = Artex.getInstance().getTagService().getTag(name);
        if (tag == null) {
            sender.sendMessage(CC.translate("&cA tag with that name does not exist."));
            return;
        }

        boolean italic;

        try {
            italic = Boolean.parseBoolean(args[1]);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(CC.translate("&cIncorrect usage. Please use 'true' or 'false'."));
            return;
        }

        tag.setItalic(italic);
        Artex.getInstance().getTagService().saveTag(tag);
        sender.sendMessage(CC.translate("&aSuccessfully set the italic of the tag &f" + name + "&r&a to &f" + italic + "&r&a."));
    }
}
