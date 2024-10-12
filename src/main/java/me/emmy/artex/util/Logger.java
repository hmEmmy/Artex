package me.emmy.artex.util;

import lombok.experimental.UtilityClass;
import me.emmy.artex.Artex;
import org.bukkit.Bukkit;

/**
 * @author Emmy
 * @project Artex
 * @date 28/08/2024 - 15:55
 */
@UtilityClass
public class Logger {
    /**
     * Log a message to the console.
     *
     * @param message The message to log.
     */
    public void debug(String message) {
        if (Artex.getInstance().getConfig().getBoolean("plugin.debugging")) {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&8&o[&7&oDEBUG&8&o] &f&o" + message));
        }
    }

    /**
     * Log a message to the console.
     *
     * @param message The message to log.
     */
    public void logError(String message) {
        Bukkit.getConsoleSender().sendMessage(CC.translate("&4" + ProjectInfo.NAME + " &8- &cError: &7" + message));
    }
}