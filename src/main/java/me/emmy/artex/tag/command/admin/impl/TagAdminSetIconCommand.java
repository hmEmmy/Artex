package me.emmy.artex.tag.command.admin.impl;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.tag.Tag;
import me.emmy.artex.util.CC;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Artex
 * @date 03/09/2024 - 14:02
 */
public class TagAdminSetIconCommand extends BaseCommand {
    @Command(name = "tagadmin.seticon", permission = "artex.tag.admin.seticon")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&6Usage: &e/tagadmin seticon &b(name)"));
            return;
        }

        String name = args[0];
        Tag tag = Artex.getInstance().getTagService().getTag(name);
        if (tag == null) {
            player.sendMessage(CC.translate("&cA tag with that name does not exist."));
            return;
        }

        tag.setIcon(player.getItemInHand().getType());
        tag.setDurability(player.getItemInHand().getDurability());
        Artex.getInstance().getTagService().saveTag(tag);
        player.sendMessage(CC.translate("&aSuccessfully set the icon of the tag &f" + name + "&r&a to &f" + player.getItemInHand().getType().name() + "&r&a."));
    }
}
