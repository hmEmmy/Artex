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
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Emmy
 * @project Artex
 * @date 01/09/2024 - 10:56
 */
public class BroadcastTask extends BukkitRunnable {
    private final List<Broadcast> broadcasts;
    private final FileConfiguration config;

    /**
     * Constructor for the BroadcastTask class.
     *
     * @param config the configuration file
     */
    public BroadcastTask(FileConfiguration config) {
        this.config = config;
        this.broadcasts = new ArrayList<>();
        this.loadBroadcastMessages();
    }

    /**
     * Load the broadcast messages from the configuration file.
     */
    public void loadBroadcastMessages() {
        this.broadcasts.clear();

        for (String key : this.config.getConfigurationSection("broadcast.list").getKeys(false)) {
            List<String> list = this.config.getStringList("broadcast.list." + key);
            this.broadcasts.add(new Broadcast(list));
        }
    }

    @Override
    public void run() {
        if (this.broadcasts.isEmpty()) {
            return;
        }

        for (String line : this.broadcasts.get(ThreadLocalRandom.current().nextInt(this.broadcasts.size())).getLines()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Rank playerRank = Artex.getInstance().getProfileRepository().getProfileWithNoAdding(player.getUniqueId()).getHighestRankBasedOnGrant();
                player.sendMessage(CC.translate(line)
                        .replace("{online}", String.valueOf(Bukkit.getOnlinePlayers().size()))
                        .replace("{max}", String.valueOf(Bukkit.getMaxPlayers()))
                        .replace("{player}", player.getName())
                        .replace("{server}", this.config.getString("server.name"))
                        .replace("{region}", this.config.getString("server.region"))
                        .replace("{discord}", this.config.getString("socials.discord"))
                        .replace("{youtube}", this.config.getString("socials.youtube"))
                        .replace("{twitter}", this.config.getString("socials.twitter"))
                        .replace("{website}", this.config.getString("socials.website"))
                        .replace("{tiktok}", this.config.getString("socials.tiktok"))
                        .replace("{store}", this.config.getString("socials.store"))
                        .replace("{github}", this.config.getString("socials.github"))
                        .replace("{teamspeak}", this.config.getString("socials.teamspeak"))
                        .replace("{facebook}", this.config.getString("socials.facebook"))
                        .replace("{instagram}", this.config.getString("socials.instagram"))
                        .replace("{rank}", playerRank.getRankWithColor())
                );
            }
        }
    }
}