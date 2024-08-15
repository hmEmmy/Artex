package me.emmy.artex.util;

import lombok.experimental.UtilityClass;
import me.emmy.artex.Artex;
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

    private final String PREFIX = "§8[§bArtex§8] §7";

    /**
     * Translate a string with color codes
     * @param string the string to translate
     * @return the translated string
     */
    public String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public void sendEnableMessage() {
        List<String> message = Arrays.asList(
                "",
                "    &d&l" + Artex.getInstance().getDescription().getName(),
                "",
                "    &7| &d&lAuthor: &f" + Artex.getInstance().getDescription().getAuthors().get(0),
                "    &7| &d&lVersion: &f" + Artex.getInstance().getDescription().getVersion(),
                "    &7| &d&lDescription: &f" + Artex.getInstance().getDescription().getDescription(),
                "    &7| &d&lWebsite: &f" + Artex.getInstance().getDescription().getWebsite(),
                "",
                "    &dDatabase Info:",
                "    &7| &dHost: &f" + Artex.getInstance().getConfig().getString("mongo.host"),
                "    &7| &dPort: &f" + Artex.getInstance().getConfig().getInt("mongo.port"),
                "    &7| &dDatabase: &f" + Artex.getInstance().getConfig().getString("mongo.database"),
                ""
        );

        for (String line : message) {
            Bukkit.getConsoleSender().sendMessage(translate(line));
        }
    }

    public void sendDisableMessage() {
        Bukkit.getConsoleSender().sendMessage(translate(PREFIX + "&cPlugin has been disabled!"));
    }
}
