package me.emmy.artex.command;

import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.locale.Locale;
import me.emmy.artex.util.CC;
import me.emmy.artex.util.ProjectInfo;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project Artex
 * @date 28/08/2024 - 23:10
 */
public class ArtexCommand extends BaseCommand {
    @Command(name = "artex", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        sender.sendMessage("");
        sender.sendMessage(CC.translate("  &4&l" + ProjectInfo.NAME + " Core &8- &7Information"));
        sender.sendMessage(CC.translate("   &4● &fAuthor: &4" + ProjectInfo.AUTHOR));
        sender.sendMessage(CC.translate("   &4● &fVersion: &4" + ProjectInfo.VERSION));
        sender.sendMessage(CC.translate("   &4● &fDiscord: &4" + ProjectInfo.DISCORD));
        sender.sendMessage(CC.translate("   &4● &fDescription: &4" + ProjectInfo.DESCRIPTION));
        sender.sendMessage(CC.translate("   &4● &fGitHub: &4" + ProjectInfo.GITHUB));
        sender.sendMessage("");
    }
}
