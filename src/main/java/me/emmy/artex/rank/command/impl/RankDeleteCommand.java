package me.emmy.artex.rank.command.impl;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.util.CC;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project Artex
 * @date 28/08/2024 - 22:11
 */
public class RankDeleteCommand extends BaseCommand {
    @Command(name = "rank.delete", permission = "artex.command.rank.delete", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 1) {
            sender.sendMessage(CC.translate("&6Usage: &e/rank delete &b(name)"));
            return;
        }

        String name = args[0];
        Rank rank = Artex.getInstance().getRankRepository().getRank(name);
        if (rank == null) {
            sender.sendMessage(CC.translate("&cA rank with that name does not exist."));
            return;
        }

        Artex.getInstance().getRankRepository().deleteRank(rank);
        sender.sendMessage(CC.translate("&aSuccessfully deleted the rank " + rank.getName() + "."));
    }
}
