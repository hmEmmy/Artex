package me.emmy.artex.chat.listener;

import lombok.Getter;
import me.emmy.artex.Artex;
import me.emmy.artex.profile.Profile;
import me.emmy.artex.util.CC;
import org.bukkit.ChatColor;
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

        String message = event.getMessage();
        String rankPrefix = CC.translate(profile.getHighestRankBasedOnGrant().getPrefix());
        String rankSuffix = CC.translate(profile.getHighestRankBasedOnGrant().getSuffix());
        ChatColor rankColor = profile.getHighestRankBasedOnGrant().getColor();
        boolean translate = player.hasPermission("artex.chat.color");
        String colon = CC.translate("&7: &r");

        String tag = profile.getTag() == null ? "" : " " + CC.translate(profile.getTag().getColor() + profile.getTag().getNiceName());
        event.setFormat(rankPrefix + rankColor + player.getName() + rankSuffix + tag + colon + (translate ?  CC.translate(message) : message));

        if (Artex.getInstance().getChatService().isChatMuted()) {
            if (player.hasPermission("artex.bypass.mutechat")) {
                return;
            }

            event.setCancelled(true);
            player.sendMessage(CC.translate("&cChat is currently muted."));
        }
    }
}