package me.emmy.artex.tag.command.admin.impl;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.tag.TagRepository;
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
        TagRepository tagRepository = Artex.getInstance().getTagRepository();

        sender.sendMessage("");
        sender.sendMessage(CC.translate("&4&lArtex &8- &7Tag List"));
        sender.sendMessage("");

        if (tagRepository.getTags().isEmpty()) {
            sender.sendMessage(CC.translate("&f&l● &4No tags found."));
            sender.sendMessage("");
            return;
        }

        tagRepository.getTags().forEach((name, tag) -> {
            sender.sendMessage(CC.translate("&f&l● &7" + name + " &f- &7" + tag.getNiceName()));
        });

        sender.sendMessage("");
    }
}
