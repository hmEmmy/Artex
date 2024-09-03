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
 * @date 30/08/2024 - 22:46
 */
public class RankSetColorCommand extends BaseCommand {
    @Command(name = "rank.setcolor", permission = "artex.command.rank.setcolor", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 2) {
            sender.sendMessage(CC.translate("&6Usage: &e/rank setcolor &b(name) &b(color)"));
            return;
        }

        String name = args[0];
        Rank rank = Artex.getInstance().getRankRepository().getRank(name);
        if (rank == null) {
            sender.sendMessage(CC.translate("&cA rank with that name does not exist."));
            return;
        }

        ChatColor color;

        try {
            color = ChatColor.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage(CC.translate("&cInvalid color. Please use a valid ChatColor. &7&o(Example: DARK_RED)"));
            return;
        }

        rank.setColor(color);
        Artex.getInstance().getRankRepository().saveRank(rank);
        sender.sendMessage(CC.translate("&aSuccessfully set the color of &4" + name + " &ato &4" + color + "&a."));
    }
}
