package me.emmy.artex.grant.command;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.grant.Grant;
import me.emmy.artex.profile.Profile;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project Artex
 * @date 29/08/2024 - 13:42
 */
public class RemoveRankCommand extends BaseCommand {
    @Command(name = "removerank", permission = "artex.command.rank.remove", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 2) {
            sender.sendMessage(CC.translate("&6Usage: &e/removerank &b(player) &b(rank)"));
            return;
        }

        String targetName = args[0];
        String rankName = args[1];

        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);
        Profile profile = Artex.getInstance().getProfileRepository().getProfileWithNoAdding(target.getUniqueId());
        if (profile == null) {
            sender.sendMessage(CC.translate("&cA profile with that name does not exist."));
            return;
        }

        if (profile.getGrants().isEmpty()) {
            sender.sendMessage(CC.translate("&cThat player does not have any grants."));
            return;
        }

        boolean updated = false;
        for (Grant grant : profile.getGrants()) {
            if (grant.getRank().getName().equalsIgnoreCase(rankName) && grant.isActive()) {
                grant.setActive(false);
                grant.setRemovedBy(sender.getName());
                grant.setRemovedAt(System.currentTimeMillis());
                updated = true;
                break;
            }
        }

        if (updated) {
            sender.sendMessage(CC.translate("&aSuccessfully removed the &b" + rankName + " &arank from &b" + target.getName() + "&a."));

            if (target.isOnline()) {
                target.getPlayer().sendMessage(CC.translate("&aYour &b" + rankName + " &arank has been removed by &b" + sender.getName() + "&a."));
            }
        } else {
            sender.sendMessage(CC.translate("&cThat player does not have the &b" + rankName + " &crank or the grant is not active."));
        }
    }
}