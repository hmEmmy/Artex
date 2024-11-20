package me.emmy.artex.rank.command.impl;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.util.CC;
import me.emmy.artex.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project Artex
 * @date 30/08/2024 - 22:51
 */
public class RankSetDefaultCommand extends BaseCommand {
    @Command(name = "rank.setdefault", permission = "artex.command.rank.setdefault", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 1) {
            sender.sendMessage(CC.translate("&6Usage: &e/rank setdefault &b(name)"));
            return;
        }

        if (Bukkit.getOnlinePlayers().size() > 1) {
            sender.sendMessage(CC.translate("&cYou can only set the default rank when no players are online."));
            return;
        }

        String name = args[0];
        Rank rank = Artex.getInstance().getRankService().getRank(name);
        if (rank == null) {
            sender.sendMessage(CC.translate("&cA rank with that name does not exist."));
            return;
        }

        Artex.getInstance().getRankService().getRanks().stream().filter(Rank::isDefaultRank).forEach(rank1 -> {
            rank1.setDefaultRank(false);
            Artex.getInstance().getRankService().saveRank(rank1);
        });

        rank.setDefaultRank(true);
        Artex.getInstance().getRankService().saveRank(rank);

        sender.sendMessage(CC.translate("&aSuccessfully set &4" + name + " &ato the default rank&a."));
    }
}
