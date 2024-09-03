package me.emmy.artex.chat.listener;

import lombok.Getter;
import me.emmy.artex.Artex;
import me.emmy.artex.chat.utility.ChatResponseUtility;
import me.emmy.artex.profile.Profile;
import me.emmy.artex.util.CC;
import me.emmy.artex.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

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
        FileConfiguration config = Artex.getInstance().getConfig();

        String message = event.getMessage();
        String rankPrefix = profile.getHighestRankBasedOnGrant(player.getUniqueId()).getPrefix();
        String rankSuffix = profile.getHighestRankBasedOnGrant(player.getUniqueId()).getSuffix();

        ChatColor rankColor = profile.getHighestRankBasedOnGrant(player.getUniqueId()).getColor();

        if (profile.getHighestRankBasedOnGrant(player.getUniqueId()) == null) {
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

        if (profile.getTag() == null || profile.getTag().getName().isEmpty()) {
            Logger.debug("Tag is null or empty for " + player.getName() + ".");
            event.setFormat(CC.translate(rankPrefix + rankColor + player.getName() + rankSuffix + "&f" + ": " + message));
            return;
        }

        event.setFormat(CC.translate(rankPrefix + rankColor + player.getName() + rankSuffix + " " + profile.getTag().getColor() + profile.getTag().getNiceName() + "&f" + ": " + message));

        if (Artex.getInstance().getChatRepository().isChatMuted()) {
            if (player.hasPermission("artex.bypass.mutechat")) {
                return;
            }

            event.setCancelled(true);
            player.sendMessage(CC.translate("&cChat is currently muted."));
        }

        if (!config.getBoolean("chat-replies.enabled")) {
            Logger.debug("Chat replies are disabled. Therefore, not sending any responses to " + player.getName() + ".");
            return;
        }

        Bukkit.getScheduler().runTaskLater(Artex.getInstance(), () -> ChatResponseUtility.sendResponse(message, config, player, profile), 1L);
    }
}