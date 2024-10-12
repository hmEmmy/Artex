package me.emmy.artex.util;

import lombok.experimental.UtilityClass;
import me.emmy.artex.Artex;
import me.emmy.artex.locale.Locale;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
     * Make a string readable by capitalizing the first letter of each word
     * and splitting the words by spaces and underscores
     *
     * @param name the name to make readable
     * @return the readable name
     */
    public String toReadAble(String name) {
        return Arrays.stream(name.split("_"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

    /**
     * Send an enable message
     */
    public void sendEnableMessage() {
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
                "   &4Database Info:",
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
                "   &7| &4&lRanks: &f" + Artex.getInstance().getRankRepository().getRanks().size(),
                "   &7| &4&lTags: &f" + Artex.getInstance().getTagRepository().getTags().size(),
                "",
                " &c<-------------------------------->",
                ""
        );

        for (String line : message) {
            Bukkit.getConsoleSender().sendMessage(translate(line));
        }
    }

    /**
     * Send a disable message
     */
    public void sendDisableMessage() {
        Bukkit.getConsoleSender().sendMessage(translate(PREFIX + "&cPlugin has been disabled!"));
    }
}