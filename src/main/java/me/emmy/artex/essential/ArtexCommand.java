package me.emmy.artex.essential;

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
        sender.sendMessage(CC.translate("  &d&l" + Artex.getInstance().getDescription().getName() + " Core &8- &7Information"));
        sender.sendMessage(CC.translate("   &d● &fAuthor: &d" + Artex.getInstance().getDescription().getAuthors().get(0)));
        sender.sendMessage(CC.translate("   &d● &fVersion: &d" + Artex.getInstance().getDescription().getVersion()));
        sender.sendMessage(CC.translate("   &d● &fDiscord: &dhttps://discord.gg/eT4B65k5E4"));
        sender.sendMessage(CC.translate("   &d● &fDescription: &d" + Artex.getInstance().getDescription().getDescription()));
        sender.sendMessage(CC.translate("   &d● &fGitHub: &dhttps://github.com/Cloudy-Development/Artex"));
        sender.sendMessage("");
    }
}
