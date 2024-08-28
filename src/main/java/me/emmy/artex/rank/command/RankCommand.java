package me.emmy.artex.rank.command;

import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project Artex
 * @date 28/08/2024 - 19:06
 */
public class RankCommand extends BaseCommand {
    @Command(name = "rank", permission = "artex.command.rank", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        sender.sendMessage("");
        sender.sendMessage(CC.translate("&b&lArtex &8- &7Rank Commands"));
        sender.sendMessage(CC.translate(" &f&l● &b/rank create (name)"));
        sender.sendMessage(CC.translate(" &f&l● &b/rank delete (name)"));
        sender.sendMessage(CC.translate(" &f&l● &b/rank setprefix (name) (prefix)"));
        sender.sendMessage(CC.translate(" &f&l● &b/rank setsuffix (name) (suffix)"));
        sender.sendMessage(CC.translate(" &f&l● &b/rank setweight (name) (weight)"));
        sender.sendMessage(CC.translate(" &f&l● &b/rank setcolor (name) (color)"));
        sender.sendMessage(CC.translate(" &f&l● &b/rank setbold (name) (true/false)"));
        sender.sendMessage(CC.translate(" &f&l● &b/rank setitalic (name) (true/false)"));
        sender.sendMessage(CC.translate(" &f&l● &b/rank setdefault (name)"));
        sender.sendMessage(CC.translate(" &f&l● &b/rank addpermission (name) (permission)"));
        sender.sendMessage("");
    }
}
