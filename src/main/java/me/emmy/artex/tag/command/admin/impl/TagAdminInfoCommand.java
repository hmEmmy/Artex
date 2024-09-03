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
 * @date 03/09/2024 - 13:56
 */
public class TagAdminInfoCommand extends BaseCommand {
    @Command(name = "tagadmin.info", permission = "artex.tag.admin.info", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 1) {
            sender.sendMessage(CC.translate("&6Usage: &e/tagadmin info &b(name)"));
            return;
        }

        String name = args[0];
        Tag tag = Artex.getInstance().getTagRepository().getTag(name);
        if (tag == null) {
            sender.sendMessage(CC.translate("&cA tag with that name does not exist."));
            return;
        }

        sender.sendMessage("");
        sender.sendMessage(CC.translate("&4&lArtex &8- &7Tag Information"));
        sender.sendMessage("");
        sender.sendMessage(CC.translate(" &f&l● &7Name: &f" + tag.getName()));
        sender.sendMessage(CC.translate(" &f&l● &7Display Name: &f" + tag.getDisplayName()));
        sender.sendMessage(CC.translate(" &f&l● &7Icon: &f" + tag.getIcon().name()));
        sender.sendMessage(CC.translate(" &f&l● &7Color: &f" + tag.getColor().name()));
        sender.sendMessage(CC.translate(" &f&l● &7Bold: &f" + tag.isBold()));
        sender.sendMessage(CC.translate(" &f&l● &7Italic: &f" + tag.isItalic()));
        sender.sendMessage("");
        sender.sendMessage(CC.translate(" &f&l● &7Appearance: &f" + tag.getNiceName()));
        sender.sendMessage("");
    }
}
