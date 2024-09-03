package me.emmy.artex.tag.command.admin.impl;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.tag.Tag;
import me.emmy.artex.util.CC;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

/**
 * @author Emmy
 * @project Artex
 * @date 03/09/2024 - 14:03
 */
public class TagAdminSetDisplayNameCommand extends BaseCommand {
    @Command(name = "tagadmin.setdisplayname", aliases = {"tagadmin.setdisplay"},permission = "artex.tag.admin.setdisplayname", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 2) {
            sender.sendMessage(CC.translate("&6Usage: &e/tagadmin setdisplayname &b(name) &b(displayName)"));
            return;
        }

        String name = args[0];
        String displayName = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        Tag tag = Artex.getInstance().getTagRepository().getTag(name);
        if (tag == null) {
            sender.sendMessage(CC.translate("&cA tag with that name does not exist."));
            return;
        }

        tag.setDisplayName(displayName);
        Artex.getInstance().getTagRepository().saveTag(tag);
        sender.sendMessage(CC.translate("&aSuccessfully set the display name of the tag &f" + name + "&r&a to &f" + displayName + "&r&a."));
    }
}
