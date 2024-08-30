package me.emmy.artex.instance.menu;

import me.emmy.artex.Artex;
import me.emmy.artex.api.menu.Button;
import me.emmy.artex.api.menu.Menu;
import me.emmy.artex.api.menu.button.BuilderButton;
import me.emmy.artex.instance.menu.button.ShutDownButton;
import me.emmy.artex.locale.Locale;
import me.emmy.artex.util.DateUtils;
import me.emmy.artex.util.TPSUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Emmy
 * @project Artex
 * @date 30/08/2024 - 21:01
 */
public class InstanceMenu extends Menu {
    @Override
    public String getTitle(Player player) {
        return "Server Instance (" + Locale.SERVER_NAME.getString() + ")";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        
        buttons.put(11, new BuilderButton("&4&lServer Details", new ItemStack(Material.PAPER), 0,
                Arrays.asList(
                        "",
                        "&f● Server Name: &4" + Locale.SERVER_NAME.getString(),
                        "&f● Server Region: &4" + Locale.SERVER_REGION.getString(),
                        "&f● Version: &4" + Artex.getInstance().getBukkitVersionExact(),
                        "&f● Spigot: &4" + Bukkit.getName(),
                        "&f● Server Port: &4" + Bukkit.getServer().getPort(),
                        "&f● Server MOTD:",
                        "&f&l| &r" + String.format(Bukkit.getServer().getMotd()),
                        ""
                )));

        buttons.put(13, new BuilderButton("&4&lServer Status", new ItemStack(Material.BEACON), 0,
                Arrays.asList(
                        "",
                        "&f● Online Players: &4" + Bukkit.getOnlinePlayers().size(),
                        "&f● Max Players: &4" + Bukkit.getMaxPlayers(),
                        "&f● Server TPS: &4" + TPSUtils.getTPSStatus(TPSUtils.getRecentTps()[0]),
                        "&f● Uptime: &4" + DateUtils.formatTimeMillis(System.currentTimeMillis() - ManagementFactory.getRuntimeMXBean().getStartTime()),
                        ""
                )));

        buttons.put(15, new BuilderButton("&4&lServer Information", new ItemStack(Material.BOOK), 0,
                Arrays.asList(
                        "",
                        "&f● Server Version: &4" + Bukkit.getVersion(),
                        "&f● Server OS: &4" + System.getProperty("os.name"),
                        "&f● Server OS Version: &4" + System.getProperty("os.version"),
                        ""
                )));

        buttons.put(31, new ShutDownButton("&4&lShutdown Server", new ItemStack(Material.BARRIER), 0,
                Arrays.asList(
                        "",
                        "&f● Click to shutdown the server.",
                        ""
                )));

        addGlass(buttons, 15);
        
        return buttons;
    }

    @Override
    public int getSize() {
        return 5 * 9;
    }
}
