package me.emmy.artex.util;

import lombok.experimental.UtilityClass;
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
    public void logError(String message) {
        Bukkit.getConsoleSender().sendMessage(CC.translate("&4" + ProjectInfo.NAME + " &8- &cError: &7" + message));
    }
}