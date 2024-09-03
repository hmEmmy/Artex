package me.emmy.artex.chat.utility;

import lombok.experimental.UtilityClass;
import me.emmy.artex.locale.Locale;
import me.emmy.artex.profile.Profile;
import me.emmy.artex.util.CC;
import me.emmy.artex.util.Logger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author Emmy
 * @project Artex
 * @date 03/09/2024 - 15:51
 */
@UtilityClass
public class ChatResponseUtility {
    /**
     * Send a response to the player based on the message they sent.
     *
     * @param message the message the player sent
     * @param config the configuration file
     * @param player the player who sent the message
     * @param profile the player's profile
     */
    public void sendResponse(String message, FileConfiguration config, Player player, Profile profile) {
        String response = loadResponses(message, config);

        if (response != null) {
            response = CC.translate(response
                    .replace("{store}", Locale.STORE.getString())
                    .replace("{player}", player.getName())
                    .replace("{rank}", profile.getHighestRankBasedOnGrant(player.getUniqueId()).getName())
                    .replace("{tag}", profile.getTag().getName())
                    .replace("{server}", Locale.SERVER_NAME.getString())
                    .replace("{discord}", Locale.DISCORD.getString())
                    .replace("{website}", Locale.WEBSITE.getString())
                    .replace("{teamspeak}", Locale.TEAMSPEAK.getString())
                    .replace("{twitter}", Locale.TWITTER.getString())
                    .replace("{instagram}", Locale.INSTAGRAM.getString())
                    .replace("{youtube}", Locale.YOUTUBE.getString())
            );

            player.sendMessage("");
            player.sendMessage(response);
            player.sendMessage("");
        }
    }

    /**
     * Load the responses from the configuration file.
     *
     * @param message the message the player sent
     * @param config the configuration file
     * @return the response to send to the player
     */
    private String loadResponses(String message, FileConfiguration config) {
        ConfigurationSection repliesSection = config.getConfigurationSection("chat-replies.replies");

        if (repliesSection == null) {
            Logger.debug("Replies section is null.");
            return null;
        }

        for (String key : repliesSection.getKeys(false)) {
            ConfigurationSection replyData = repliesSection.getConfigurationSection(key);

            if (replyData == null) {
                Logger.debug("Reply data is null for key: " + key);
                continue;
            }

            List<String> triggers = replyData.getStringList("trigger");

            for (String trigger : triggers) {
                if (message.equalsIgnoreCase(trigger)) {
                    return replyData.getString("response");
                }
            }
        }

        Logger.debug("Returning null for chat responses.");
        return null;
    }
}
