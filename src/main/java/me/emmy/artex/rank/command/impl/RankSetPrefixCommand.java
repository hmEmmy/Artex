package me.emmy.artex.rank.command.impl;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.util.CC;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

/**
 * @author Emmy
 * @project Artex
 * @date 30/08/2024 - 22:42
 */
public class RankSetPrefixCommand extends BaseCommand {
    @Command(name = "rank.setprefix", permission = "artex.command.rank.setprefix", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 2) {
            sender.sendMessage(CC.translate("&6Usage: &e/rank setprefix &b(name) &b(prefix)"));
            return;
        }

        String name = args[0];
        String prefix = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        Rank rank = Artex.getInstance().getRankService().getRank(name);
        if (rank == null) {
            sender.sendMessage(CC.translate("&cA rank with that name does not exist."));
            return;
        }

        rank.setPrefix(prefix);
        Artex.getInstance().getRankService().saveRank(rank);
        sender.sendMessage(CC.translate("&aSuccessfully set the prefix of &4" + name + " &ato &4" + prefix + "&a."));
    }
}
