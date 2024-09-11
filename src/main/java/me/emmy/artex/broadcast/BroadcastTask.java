package me.emmy.artex.broadcast;

import me.emmy.artex.Artex;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Emmy
 * @project Artex
 * @date 01/09/2024 - 10:56
 */
public class BroadcastTask extends BukkitRunnable {

    private final List<Broadcast> announcements = new ArrayList<>();
    private final Random random = new Random();

    /**
     * Constructor for the BroadcastTask class.
     */
    public BroadcastTask() {
        loadBroadcastMessages();
    }

    /**
     * Load the broadcast messages from the configuration file.
     */
    public void loadBroadcastMessages() {
        FileConfiguration config = Artex.getInstance().getConfig();

        announcements.clear();

        for (String key : config.getConfigurationSection("broadcast.list").getKeys(false)) {
            List<String> list = config.getStringList("broadcast.list." + key);
            announcements.add(new Broadcast(list));
        }
    }

    @Override
    public void run() {
        if (announcements.isEmpty()) {
            return;
        }

        for (String line : announcements.get(random.nextInt(announcements.size())).getLines()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                FileConfiguration config = Artex.getInstance().getConfig();
                Rank playerRank = Artex.getInstance().getProfileRepository().getProfileWithNoAdding(player.getUniqueId()).getHighestRankBasedOnGrant();

                player.sendMessage(CC.translate(line)
                        .replace("{online}", String.valueOf(Bukkit.getOnlinePlayers().size()))
                        .replace("{max}", String.valueOf(Bukkit.getMaxPlayers()))
                        .replace("{player}", player.getName())
                        .replace("{server}", config.getString("server.name"))
                        .replace("{region}", config.getString("server.region"))
                        .replace("{discord}", config.getString("socials.discord"))
                        .replace("{youtube}", config.getString("socials.youtube"))
                        .replace("{twitter}", config.getString("socials.twitter"))
                        .replace("{website}", config.getString("socials.website"))
                        .replace("{tiktok}", config.getString("socials.tiktok"))
                        .replace("{store}", config.getString("socials.store"))
                        .replace("{github}", config.getString("socials.github"))
                        .replace("{teamspeak}", config.getString("socials.teamspeak"))
                        .replace("{facebook}", config.getString("socials.facebook"))
                        .replace("{instagram}", config.getString("socials.instagram"))
                        .replace("{rank}", playerRank.getRankWithColor())
                );
            }
        }
    }
}