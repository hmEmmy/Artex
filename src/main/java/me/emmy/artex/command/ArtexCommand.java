package me.emmy.artex.command;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
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
        sender.sendMessage(CC.translate("  &4&l" + Artex.getInstance().getDescription().getName() + " Core &8- &7Information"));
        sender.sendMessage(CC.translate("   &4● &fAuthor: &4" + Artex.getInstance().getDescription().getAuthors().get(0)));
        sender.sendMessage(CC.translate("   &4● &fVersion: &4" + Artex.getInstance().getDescription().getVersion()));
        sender.sendMessage(CC.translate("   &4● &fDiscord: &4https://discord.gg/eT4B65k5E4"));
        sender.sendMessage(CC.translate("   &4● &fDescription: &4" + Artex.getInstance().getDescription().getDescription()));
        sender.sendMessage(CC.translate("   &4● &fGitHub: &4https://github.com/Cloudy-Development/Artex"));
        sender.sendMessage("");
    }
}
