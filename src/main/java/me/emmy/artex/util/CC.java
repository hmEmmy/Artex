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

    public final String PREFIX = "§8[§bArtex§8] §7";

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
                " &5<---------- &d&l" + Artex.getInstance().getDescription().getName() + " Core &5---------->",
                "",
                "   &dPlugin Info:",
                "   &7| &d&lAuthor: &f" + Artex.getInstance().getDescription().getAuthors().get(0),
                "   &7| &d&lVersion: &f" + Artex.getInstance().getDescription().getVersion(),
                "   &7| &d&lWebsite: &f" + Artex.getInstance().getDescription().getWebsite(),
                "   &7| &d&lDescription: &f" + Artex.getInstance().getDescription().getDescription(),
                "",
                "   &dDatabase Info:",
                "   &7| &dHost: &f" + Artex.getInstance().getConfig().getString("mongo.host"),
                "   &7| &dPort: &f" + Artex.getInstance().getConfig().getInt("mongo.port"),
                "   &7| &dDatabase: &f" + Artex.getInstance().getConfig().getString("mongo.database"),
                "",
                "   &dInstance:",
                "   &7| &d&lServer Name: &f" + Locale.SERVER_NAME.getString(),
                "   &7| &d&lServer Region: &f" + Locale.SERVER_REGION.getString(),
                "   &7| &d&lServer Version: &f" + Artex.getInstance().getBukkitVersionExact(),
                "   &7| &d&lSpigot: &f" + Bukkit.getName(),
                "   &7| &d&lRanks: &f" + Artex.getInstance().getRankRepository().getRanks().size(),
                "",
                " &5<-------------------------------->",
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
