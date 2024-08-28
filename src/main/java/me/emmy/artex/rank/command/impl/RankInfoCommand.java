package me.emmy.artex.rank.command.impl;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.util.CC;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project Artex
 * @date 28/08/2024 - 22:16
 */
public class RankInfoCommand extends BaseCommand {
    @Command(name = "rank.info", permission = "artex.command.rank.info", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 1) {
            sender.sendMessage(CC.translate("&cUsage: /rank info (name)"));
            return;
        }

        String name = args[0];
        Rank rank = Artex.getInstance().getRankRepository().getRank(name);
        if (rank == null) {
            sender.sendMessage(CC.translate("&cA rank with that name does not exist."));
            return;
        }

        sender.sendMessage("");
        sender.sendMessage(CC.translate("&b&lArtex &8- &7Rank Information"));
        sender.sendMessage("");
        sender.sendMessage(CC.translate(" &f&l● &bName: &f" + rank.getName()));
        sender.sendMessage(CC.translate(" &f&l● &bPrefix: &f" + rank.getPrefix()));
        sender.sendMessage(CC.translate(" &f&l● &bSuffix: &f" + rank.getSuffix()));
        sender.sendMessage(CC.translate(" &f&l● &bWeight: &f" + rank.getWeight()));
        sender.sendMessage(CC.translate(" &f&l● &bColor: &f" + rank.getColor().toString()));
        sender.sendMessage(CC.translate(" &f&l● &bBold: &f" + rank.isBold()));
        sender.sendMessage(CC.translate(" &f&l● &bItalic: &f" + rank.isItalic()));
        sender.sendMessage(CC.translate(" &f&l● &bDefault: &f" + rank.isDefaultRank()));
        sender.sendMessage(CC.translate(" &f&l● &bPermissions:"));
        if (rank.getPermissions().isEmpty()) {
            sender.sendMessage(CC.translate("   &7None"));
            sender.sendMessage("");
            return;
        }

        rank.getPermissions().forEach(permission -> {
            sender.sendMessage(CC.translate("   &f- &7" + permission));
        });
        sender.sendMessage("");
    }
}
