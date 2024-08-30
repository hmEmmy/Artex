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
 * @date 30/08/2024 - 22:55
 */
public class RankAddPermissionCommand extends BaseCommand {
    @Command(name = "rank.addpermission", permission = "artex.command.rank.addpermission", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 2) {
            sender.sendMessage(CC.translate("&cUsage: /rank addpermission (name) (permission)"));
            return;
        }

        String name = args[0];
        Rank rank = Artex.getInstance().getRankRepository().getRank(name);
        if (rank == null) {
            sender.sendMessage(CC.translate("&cA rank with that name does not exist."));
            return;
        }

        String permission = args[1];

        if (rank.getPermissions().contains(permission)) {
            rank.getPermissions().remove(permission);
            Artex.getInstance().getRankRepository().saveRank(rank);
            sender.sendMessage(CC.translate("&aSuccessfully &cremoved &athe permission &4" + permission + " &afrom &4" + name + "&a."));
            return;
        }

        rank.getPermissions().add(permission);
        Artex.getInstance().getRankRepository().saveRank(rank);
        sender.sendMessage(CC.translate("&aSuccessfully &eadded the permission &4" + permission + " &ato &4" + name + "&a."));
    }
}
