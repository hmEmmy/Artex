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
        List<String> message = Arrays.asList(
                "",
                " &c<---------- &4&l" + Artex.getInstance().getDescription().getName() + " Core &c---------->",
                "",
                "   &4Plugin Info:",
                "   &7| &4&lAuthor: &f" + Artex.getInstance().getDescription().getAuthors().get(0),
                "   &7| &4&lVersion: &f" + Artex.getInstance().getDescription().getVersion(),
                "   &7| &4&lWebsite: &f" + Artex.getInstance().getDescription().getWebsite(),
                "   &7| &4&lDescription: &f" + Artex.getInstance().getDescription().getDescription(),
                "",
                "   &4Database Info:",
                "   &7| &4Host: &f" + Artex.getInstance().getConfig().getString("mongo.host"),
                "   &7| &4Port: &f" + Artex.getInstance().getConfig().getInt("mongo.port"),
                "   &7| &4Database: &f" + Artex.getInstance().getConfig().getString("mongo.database"),
                "",
                "   &4Instance:",
                "   &7| &4&lServer Name: &f" + Locale.SERVER_NAME.getString(),
                "   &7| &4&lServer Region: &f" + Locale.SERVER_REGION.getString(),
                "   &7| &4&lServer Version: &f" + Artex.getInstance().getBukkitVersionExact(),
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
