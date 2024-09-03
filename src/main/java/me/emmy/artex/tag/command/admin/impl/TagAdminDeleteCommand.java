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
 * @date 03/09/2024 - 13:53
 */
public class TagAdminDeleteCommand extends BaseCommand {
    @Command(name = "tagadmin.delete", permission = "artex.tag.admin.delete", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 1) {
            sender.sendMessage(CC.translate("&6Usage: &e/tagadmin delete &b(name)"));
            return;
        }

        String name = args[0];
        Tag tag = Artex.getInstance().getTagRepository().getTag(name);
        if (tag == null) {
            sender.sendMessage(CC.translate("&cA tag with that name does not exist."));
            return;
        }

        Artex.getInstance().getTagRepository().deleteTag(tag);
        sender.sendMessage(CC.translate("&aSuccessfully deleted the tag &f" + name + "&r&a."));
    }
}
