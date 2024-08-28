package me.emmy.artex.chat.listener;

import lombok.Getter;
import me.emmy.artex.Artex;
import me.emmy.artex.profile.Profile;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.util.CC;
import me.emmy.artex.util.Logger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

/**
 * @author Emmy
 * @project Artex
 * @date 28/08/2024 - 16:28
 */
@Getter
public class ChatListener implements Listener {

    @EventHandler
    private void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Profile profile = Artex.getInstance().getProfileRepository().getProfile(player.getUniqueId());

        String message = event.getMessage();
        String rankPrefix = profile.getHighestRank(player.getUniqueId()).getPrefix();
        String rankSuffix = profile.getHighestRank(player.getUniqueId()).getSuffix();
        ChatColor rankColor = profile.getHighestRank(player.getUniqueId()).getColor();

        if (profile.getHighestRank(player.getUniqueId()) == null) {
            Logger.debug("Highest rank is null for " + player.getName() + ".");
            event.setFormat(CC.translate("&7" + player.getName() + "&f" + ": " + message));
            return;
        }

        if (profile.getRank() == null) {
            Logger.debug("Rank is null for " + player.getName() + ".");
            event.setFormat(CC.translate("&7" + player.getName() + "&f" + ": " + message));
            return;
        }

        if (profile.getGrants() == null || profile.getGrants().isEmpty()) {
            Logger.debug("Grants are null or empty for " + player.getName() + ".");
            event.setFormat(CC.translate(rankPrefix + rankColor + player.getName() + rankSuffix + "&f" + ": " + message));
            return;
        }

        event.setFormat(CC.translate(rankPrefix + rankColor + player.getName() + rankSuffix + "&f" + ": " + message));
    }

}
