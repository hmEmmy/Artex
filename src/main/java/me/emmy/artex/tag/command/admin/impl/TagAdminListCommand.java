package me.emmy.artex.tag.command.admin.impl;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.tag.TagService;
import me.emmy.artex.util.CC;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project Artex
 * @date 03/09/2024 - 13:58
 */
public class TagAdminListCommand extends BaseCommand {
    @Command(name = "tagadmin.list", permission = "artex.tag.admin.list", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        TagService tagService = Artex.getInstance().getTagService();

        sender.sendMessage("");
        sender.sendMessage(CC.translate("&4&lArtex &8- &7Tag List"));
        sender.sendMessage("");

        if (tagService.getTags().isEmpty()) {
            sender.sendMessage(CC.translate("&f&l● &4No tags found."));
            sender.sendMessage("");
            return;
        }

        tagService.getTags().stream().sorted().forEach(tag -> sender.sendMessage(CC.translate("&f&l● &7" + tag.getName() + " &f- &7" + tag.getNiceName())));

        sender.sendMessage("");
    }
}
