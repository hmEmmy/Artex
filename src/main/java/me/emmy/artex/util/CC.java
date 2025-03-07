package me.emmy.artex.util;

import lombok.experimental.UtilityClass;
import me.emmy.artex.Artex;
import me.emmy.artex.locale.Locale;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

/**
 * @author Emmy
 * @project Artex
 * @date 15/08/2024 - 21:25
 */
@UtilityClass
public class CC {
    public final String PREFIX = "§8[§4Artex§8] §7";

    /**
     * Translate a string with color codes
     *
     * @param string the string to translate
     * @return the translated string
     */
    public String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * Send an enable message
     */
    public void sendEnableMessage() {
        if (Artex.getInstance().getDatabaseService().isMongo()) {
            List<String> message = Arrays.asList(
                    "",
                    " &c<---------- &4&l" + ProjectInfo.NAME + " Core &c---------->",
                    "",
                    "   &4Plugin Info:",
                    "   &7| &4&lAuthor: &f" + ProjectInfo.AUTHOR,
                    "   &7| &4&lVersion: &f" + ProjectInfo.VERSION,
                    "   &7| &4&lWebsite: &f" + ProjectInfo.WEBSITE,
                    "   &7| &4&lDescription: &f" + ProjectInfo.DESCRIPTION,
                    "   &7| &4&lDiscord: &f" + ProjectInfo.DISCORD,
                    "   &7| &4&lGitHub: &f" + ProjectInfo.GITHUB,
                    "",
                    "   &4Database Info: &a&lMongoDB",
                    "   &7| &4Uri: &f" + Artex.getInstance().getConfig().getString("mongo.uri"),
                    "   &7| &4Database: &f" + Artex.getInstance().getConfig().getString("mongo.database"),
                    "",
                    "   &4Instance:",
                    "   &7| &4&lServer Name: &f" + Locale.SERVER_NAME.getString(),
                    "   &7| &4&lServer Region: &f" + Locale.SERVER_REGION.getString(),
                    "   &7| &4&lServer Version: &f" + ProjectInfo.BUKKIT_VERSION_EXACT,
                    "   &7| &4&lSpigot: &f" + Bukkit.getName(),
                    "",
                    "   &4&lStorage:",
                    "   &7| &4&lRanks: &f" + Artex.getInstance().getRankService().getRanks().size(),
                    "   &7| &4&lTags: &f" + Artex.getInstance().getTagService().getTags().size(),
                    "",
                    " &c<-------------------------------->",
                    ""
            );

            for (String line : message) {
                Bukkit.getConsoleSender().sendMessage(translate(line));
            }
        } else if (Artex.getInstance().getDatabaseService().isFlatFile()) {
            List<String> message = Arrays.asList(
                    "",
                    " &c<---------- &4&l" + ProjectInfo.NAME + " Core &c---------->",
                    "",
                    "   &4Plugin Info:",
                    "   &7| &4&lAuthor: &f" + ProjectInfo.AUTHOR,
                    "   &7| &4&lVersion: &f" + ProjectInfo.VERSION,
                    "   &7| &4&lWebsite: &f" + ProjectInfo.WEBSITE,
                    "   &7| &4&lDescription: &f" + ProjectInfo.DESCRIPTION,
                    "   &7| &4&lDiscord: &f" + ProjectInfo.DISCORD,
                    "   &7| &4&lGitHub: &f" + ProjectInfo.GITHUB,
                    "",
                    "   &4Database Info: &a&lFlatFile",
                    "   &7| &7&oProfiles are stored in 'profiles.yml'.",
                    "",
                    "   &4Instance:",
                    "   &7| &4&lServer Name: &f" + Locale.SERVER_NAME.getString(),
                    "   &7| &4&lServer Region: &f" + Locale.SERVER_REGION.getString(),
                    "   &7| &4&lServer Version: &f" + ProjectInfo.BUKKIT_VERSION_EXACT,
                    "   &7| &4&lSpigot: &f" + Bukkit.getName(),
                    "",
                    "   &4&lStorage:",
                    "   &7| &4&lRanks: &f" + Artex.getInstance().getRankService().getRanks().size(),
                    "   &7| &4&lTags: &f" + Artex.getInstance().getTagService().getTags().size(),
                    "",
                    " &c<-------------------------------->",
                    ""
            );

            for (String line : message) {
                Bukkit.getConsoleSender().sendMessage(translate(line));
            }
        }
    }

    /**
     * Send a disable message
     */
    public void sendDisableMessage() {
        Bukkit.getConsoleSender().sendMessage(translate(PREFIX + "&cPlugin has been disabled!"));
    }
}