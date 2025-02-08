package me.emmy.artex.tag.utility.command;

import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.tag.utility.TagUtility;
import me.emmy.artex.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project Artex
 * @since 08/02/2025
 */
public class CreateDefaultTagsCommand extends BaseCommand {
    @Command(name = "createdefaulttags", permission = "artex.command.createdefaulttags", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            sender.sendMessage(CC.translate("&cYou can only create default tags when no players are online."));
            return;
        }

        TagUtility.createDefaultTags();
    }
}