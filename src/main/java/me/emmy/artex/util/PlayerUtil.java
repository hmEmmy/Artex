package me.emmy.artex.util;

import lombok.experimental.UtilityClass;
import me.emmy.artex.locale.Locale;
import me.emmy.artex.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author Emmy
 * @project Artex
 * @date 01/09/2024 - 12:56
 */
@UtilityClass
public class PlayerUtil {

    /**
     * Reset the player's health, food level, fire ticks, saturation, inventory, armor contents, and game mode.
     *
     * @param player the player to reset
     */
    public void onJoinReset(Player player) {
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setFireTicks(0);
        player.setSaturation(20);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setGameMode(GameMode.SURVIVAL);
    }

    /**
     * Send the welcome message to the player.
     *
     * @param player the player to send the message to
     * @param profile the player's profile
     * @param config the configuration file
     */
    public void sendWelcomeMessage(Player player, Profile profile, FileConfiguration config) {
        if (config.getBoolean("on-join.welcome-message.enabled", false)) {
            List<String> messages = config.getStringList("on-join.welcome-message.context");
            Logger.debug("Sending on-join messages to " + player.getName() + ".");
            messages.forEach(message -> player.sendMessage(CC.translate(message)
                    .replace("{player}", player.getName())
                    .replace("{color}", profile.getRank().getColor().toString())
                    .replace("{rank}", profile.getRank().getRankWithColor())
                    .replace("{online}", String.valueOf(Bukkit.getOnlinePlayers().size()))
                    .replace("{max-online}", String.valueOf(Bukkit.getMaxPlayers()))
                    .replace("{rank}", profile.getRank().getRankWithColor())
                    .replace("{store}", Locale.STORE.getString())
                    .replace("{discord}", Locale.DISCORD.getString())
                    .replace("{website}", Locale.WEBSITE.getString())
                    .replace("{teamspeak}", Locale.TEAMSPEAK.getString())
                    .replace("{twitter}", Locale.TWITTER.getString())
                    .replace("{instagram}", Locale.INSTAGRAM.getString())
                    .replace("{youtube}", Locale.YOUTUBE.getString())
                    .replace("{tiktok}", Locale.TIKTOK.getString())
                    .replace("{facebook}", Locale.FACEBOOK.getString())
                    .replace("{github}", Locale.GITHUB.getString())
                    .replace("{server}", Locale.SERVER_NAME.getString())
                    .replace("{region}", Locale.SERVER_REGION.getString())
            ));
        }
    }
}
