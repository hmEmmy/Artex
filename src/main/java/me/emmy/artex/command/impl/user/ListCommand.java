package me.emmy.artex.command.impl.user;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.profile.Profile;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Emmy
 * @project Artex
 * @date 29/08/2024 - 12:39
 */
public class ListCommand extends BaseCommand {
    @Command(name = "list", aliases = "who", permission = "artex.command.list", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String seperator = "&f, &r";

        int onlinePlayers = Bukkit.getOnlinePlayers().size();
        int maxPlayers = Bukkit.getMaxPlayers();

        sender.sendMessage("");

        List<Rank> sortedRanks = Artex.getInstance().getRankService().getRanks().stream()
                .sorted(Comparator.comparingInt(Rank::getWeight).reversed())
                .collect(Collectors.toList());

        StringBuilder rankList = new StringBuilder();
        for (int i = 0; i < sortedRanks.size(); i++) {
            Rank rank = sortedRanks.get(i);
            rankList.append(CC.translate(rank.getColor() + rank.getName()));
            if (i < sortedRanks.size() - 1) {
                rankList.append(seperator);
            }
        }

        StringBuilder playerList = new StringBuilder();
        for (Player player : Bukkit.getOnlinePlayers()) {
            Profile profile = Artex.getInstance().getProfileRepository().getIProfile(player.getUniqueId());
            Rank playerRank = profile.getHighestRankBasedOnGrant();
            String playerName = player.getName();
            ChatColor rankColor = playerRank.getColor();

            playerList.append(rankColor).append(playerName).append(seperator);
        }

        if (playerList.length() > 0) {
            playerList.setLength(playerList.length() - 2);
        }

        String playerListMessage = playerList.toString();
        if (onlinePlayers > 200) {
            playerListMessage = CC.translate("&7and " + (onlinePlayers - 200) + " more players");
        }

        sender.sendMessage(CC.translate(rankList.toString()));
        sender.sendMessage(CC.translate("&f(" + onlinePlayers + "/" + maxPlayers + "): " + playerListMessage));
        sender.sendMessage("");
    }
}
