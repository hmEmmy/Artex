package me.emmy.artex.profile.listener;

import me.emmy.artex.Artex;
import me.emmy.artex.profile.Profile;
import me.emmy.artex.profile.ProfileRepository;
import me.emmy.artex.util.CC;
import me.emmy.artex.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * @author Emmy
 * @project Artex
 * @date 15/08/2024 - 22:01
 */
public class ProfileListener implements Listener {

    @EventHandler
    private void onLogin(PlayerLoginEvent event) {
        ProfileRepository profileRepository = Artex.getInstance().getProfileRepository();
        UUID uuid = event.getPlayer().getUniqueId();
        Profile profile = profileRepository.getProfile(uuid);

        if (profile == null) {
            Logger.debug("Creating new profile for " + event.getPlayer().getName());
            profile = new Profile(uuid);
            profileRepository.addProfile(uuid);
        }

        profile.load();

        Logger.debug("Determining rank for " + event.getPlayer().getName() + ".");
        profileRepository.determineRank(profile);

        Logger.debug("Rank determined for " + event.getPlayer().getName() + ".");

    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Profile profile = Artex.getInstance().getProfileRepository().getProfile(player.getUniqueId());

        event.setJoinMessage(null);
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
