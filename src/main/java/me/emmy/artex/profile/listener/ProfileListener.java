package me.emmy.artex.profile.listener;

import me.emmy.artex.Artex;
import me.emmy.artex.profile.Profile;
import me.emmy.artex.util.PlayerUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Emmy
 * @project Artex
 * @date 15/08/2024 - 22:01
 */
public class ProfileListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            return;
        }

        Profile profile = new Profile(player.getUniqueId());
        profile.load();

        Artex.getInstance().getProfileRepository().addProfile(profile.getUuid(), profile);
        profile.determineRankAndAttachPerms();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Profile profile = Artex.getInstance().getProfileRepository().getProfile(player.getUniqueId());
        profile.setUsername(player.getName());

        FileConfiguration config = Artex.getInstance().getConfig();
        if (config.getBoolean("on-join.tp-to-spawn")) {
            Artex.getInstance().getSpawnHandler().teleportToSpawn(player);
        }

        event.setJoinMessage(null);

        if (config.getBoolean("on-join.reset-player", false)) {
            PlayerUtil.onJoinReset(player);
        }

        PlayerUtil.sendWelcomeMessage(player, profile, config);
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Profile profile = Artex.getInstance().getProfileRepository().getProfile(player.getUniqueId());
        profile.save();

        event.setQuitMessage(null);
    }

    @EventHandler
    private void onKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        Profile profile = Artex.getInstance().getProfileRepository().getProfile(player.getUniqueId());
        profile.save();
    }
}
