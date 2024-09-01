package me.emmy.artex.util;

import lombok.experimental.UtilityClass;
import me.emmy.artex.Artex;
import org.bukkit.Bukkit;

/**
 * @author Emmy
 * @project Artex
 * @date 01/09/2024 - 11:36
 */
@UtilityClass
public class ProjectInfo {

    private final Artex plugin = Artex.getInstance();

    public String NAME = plugin.getDescription().getName();
    public String VERSION = plugin.getDescription().getVersion();
    public String AUTHOR = plugin.getDescription().getAuthors().get(0);
    public String AUTHORS = plugin.getDescription().getAuthors().toString().replace("[", "").replace("]", "");
    public String DESCRIPTION = plugin.getDescription().getDescription();

    public String WEBSITE = plugin.getDescription().getWebsite();
    public String DISCORD = "https://discord.gg/eT4B65k5E4";
    public String GITHUB = "https://github.com/Cloudy-Development/Artex";

    public String BUKKIT_VERSION_EXACT = plugin.getBukkitVersionExact();
    public String SPIGOT = Bukkit.getName();
}
