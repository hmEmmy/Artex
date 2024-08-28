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
 * @date 28/08/2024 - 19:10
 */
public class RankCreateCommand extends BaseCommand {
    @Command(name = "rank.create", permission = "artex.command.rank.create", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();
        boolean doPrefix;

        if (args.length < 2 ) {
            sender.sendMessage(CC.translate("&cUsage: /rank create (name) (doPrefix)"));
            sender.sendMessage(CC.translate("&7&oIf 'doPrefix' is true, the rank will automatically have a prefix."));
            return;
        }

        String name = args[0];
        Rank rank = Artex.getInstance().getRankRepository().getRank(name);
        if (rank != null) {
            sender.sendMessage(CC.translate("&cA rank with that name already exists."));
            return;
        }

        switch (args[1]) {
            case "true":
                doPrefix = true;
                break;
            case "false":
                doPrefix = false;
                break;
            default:
                sender.sendMessage(CC.translate("&cInvalid boolean value. Please use 'true' or 'false'."));
                return;
        }

        Artex.getInstance().getRankRepository().createRank(name, doPrefix);
        sender.sendMessage(CC.translate("&aSuccessfully created a new rank called &b" + name + "&a."));
    }
}
