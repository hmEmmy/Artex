package me.emmy.artex.tag.command.admin.impl;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.tag.Tag;
import me.emmy.artex.util.CC;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

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
        String[] args = command.getArgs();

        if (args.length < 2) {
            sender.sendMessage("");
            sender.sendMessage(CC.translate("&6Usage: &e/tagadmin create &b(name) &b(displayName)"));
            sender.sendMessage("");
            sender.sendMessage(CC.translate(
                    "&7Note: &cPlease avoid using color codes in the display name. \n&cUse &e'/tagadmin setcolor' &cfor color settings. \n&cBold and italic may not work \n&cwith additional formatting codes like &nunderline&r&7."
            ));
            sender.sendMessage("");
            sender.sendMessage(CC.translate("&cExample:"));
            sender.sendMessage(CC.translate(" &c1. &f'/tagadmin create &6Artex &7Artex #1' &f- DisplayName supports spaces."));
            sender.sendMessage(CC.translate(" &c2. &f'/tagadmin setcolor &4DARK_RED' &f- Color codes won't work. Use the color name."));
            sender.sendMessage(CC.translate(" &c3. &f'/tagadmin setbold &atrue' &f- Set the tag to bold."));
            sender.sendMessage("");
            return;

        }

        String name = args[0];
        String displayName = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        Tag tag = Artex.getInstance().getTagRepository().getTag(name);
        if (tag != null) {
            sender.sendMessage(CC.translate("&cA tag with that name already exists."));
            return;
        }

        Artex.getInstance().getTagRepository().createTag(name, displayName, Material.NAME_TAG, ChatColor.WHITE, 0, false, false);
    }
}
