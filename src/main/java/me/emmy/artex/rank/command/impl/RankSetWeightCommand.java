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
 * @date 30/08/2024 - 22:50
 */
public class RankSetWeightCommand extends BaseCommand {
    @Command(name = "rank.setweight", permission = "artex.command.rank.setweight", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 2) {
            sender.sendMessage(CC.translate("&6Usage: &e/rank setweight &b(name) &b(weight)"));
            return;
        }

        String name = args[0];
        Rank rank = Artex.getInstance().getRankRepository().getRank(name);
        if (rank == null) {
            sender.sendMessage(CC.translate("&cA rank with that name does not exist."));
            return;
        }

        int weight;

        try {
            weight = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(CC.translate("&cInvalid integer value."));
            return;
        }

        rank.setWeight(weight);
        Artex.getInstance().getRankRepository().saveRank(rank);
        sender.sendMessage(CC.translate("&aSuccessfully set the weight of &4" + name + " &ato &4" + weight + "&a."));
    }
}
