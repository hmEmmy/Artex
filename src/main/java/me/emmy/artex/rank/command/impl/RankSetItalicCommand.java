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
 * @date 30/08/2024 - 22:49
 */
public class RankSetItalicCommand extends BaseCommand {
    @Command(name = "rank.setitalic", permission = "artex.command.rank.setitalic", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 2) {
            sender.sendMessage(CC.translate("&6Usage: &e/rank setitalic &b(name) &b(true/false)"));
            return;
        }

        String name = args[0];
        Rank rank = Artex.getInstance().getRankService().getRank(name);
        if (rank == null) {
            sender.sendMessage(CC.translate("&cA rank with that name does not exist."));
            return;
        }

        boolean italic;

        switch (args[1]) {
            case "true":
                italic = true;
                break;
            case "false":
                italic = false;
                break;
            default:
                sender.sendMessage(CC.translate("&cInvalid boolean value. Please use 'true' or 'false'."));
                return;
        }

        rank.setItalic(italic);
        Artex.getInstance().getRankService().saveRank(rank);
        sender.sendMessage(CC.translate("&aSuccessfully set the italic of &4" + name + " &ato &4" + italic + "&a."));
    }
}
