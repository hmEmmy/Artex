package me.emmy.artex.grant.command;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.grant.Grant;
import me.emmy.artex.locale.Locale;
import me.emmy.artex.profile.Profile;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

/**
 * @author Emmy
 * @project Artex
 * @date 28/08/2024 - 22:44
 */
public class SetRankCommand extends BaseCommand {
    @Command(name = "setrank", permission = "artex.command.rank.set", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 4) {
            sender.sendMessage(CC.translate("&6Usage: &e/setrank &b(name) &b(rank) &b(duration/perm) &b(reason)"));
            sender.sendMessage(CC.translate("&6Example: &e/setrank &ahmEmmy &4Owner &cperm &7Reason can have spaces."));
            return;
        }

        String targetName = args[0];
        String rankName = args[1];
        long duration;
        String reason = String.join(" ", Arrays.copyOfRange(args, 3, args.length));

        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);
        Profile profile = Artex.getInstance().getProfileRepository().getProfile(target.getUniqueId());
        Rank grantedRank = Artex.getInstance().getRankRepository().getRank(rankName);

        if (grantedRank == null) {
            sender.sendMessage(CC.translate("&cThe rank '" + rankName + "' does not exist."));
            return;
        }

        boolean isPermanent;
        if (args[2].equalsIgnoreCase("perm") || args[2].equalsIgnoreCase("permanent")) {
            duration = -1;
            isPermanent = true;
        } else {
            try {
                duration = Long.parseLong(args[2]);
                isPermanent = false;
            } catch (NumberFormatException e) {
                sender.sendMessage(CC.translate("&cInvalid duration number!"));
                return;
            }
        }

        if (profile.getGrants().stream().anyMatch(grant1 -> grant1.getRank().equals(grantedRank) && grant1.isActive())) {
            sender.sendMessage(CC.translate("&cThat player already has the rank '" + rankName + "' granted and active."));
            return;
        }

        Grant grant = new Grant();
        grant.setRank(grantedRank.getName());
        grant.setDuration(duration);
        grant.setReason(reason);
        grant.setAddedBy(sender.getName());
        grant.setAddedAt(System.currentTimeMillis());
        grant.setPermanent(isPermanent);
        grant.setActive(true);
        grant.setAddedOn(Locale.SERVER_NAME.getString());

        profile.getGrants().add(grant);
        profile.setRank(grantedRank);

        sender.sendMessage(CC.translate("&aYou have granted the rank &4" + rankName + " &ato &4" + targetName + "&a."));

        if (target.isOnline()) {
            target.getPlayer().sendMessage(CC.translate("&aYou have been granted the rank &4" + rankName + "&a."));
        }
    }
}