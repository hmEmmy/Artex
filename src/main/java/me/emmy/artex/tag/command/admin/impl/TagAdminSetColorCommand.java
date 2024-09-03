package me.emmy.artex.tag.command.admin.impl;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.tag.Tag;
import me.emmy.artex.util.CC;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project Artex
 * @date 03/09/2024 - 14:06
 */
public class TagAdminSetColorCommand extends BaseCommand {
    @Command(name = "tagadmin.setcolor", permission = "artex.tag.admin.setcolor", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 2) {
            sender.sendMessage(CC.translate("&6Usage: &e/tagadmin setcolor &b(name) &b(color)"));
            return;
        }

        String name = args[0];
        Tag tag = Artex.getInstance().getTagRepository().getTag(name);
        if (tag == null) {
            sender.sendMessage(CC.translate("&cA tag with that name does not exist."));
            return;
        }
        ChatColor color;

        try {
            color = ChatColor.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage(CC.translate("&cInvalid color."));
            return;
        }

        tag.setColor(color);
        Artex.getInstance().getTagRepository().saveTag(tag);
        sender.sendMessage(CC.translate("&aSuccessfully set the color of the tag &f" + name + "&r&a to &f" + color + "&r&a."));
    }
}
