package me.emmy.artex.profile.listener;

import me.emmy.artex.Artex;
import me.emmy.artex.profile.Profile;
import me.emmy.artex.profile.ProfileRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Emmy
 * @project Artex
 * @date 15/08/2024 - 22:01
 */
public class ProfileListener implements Listener {

    @EventHandler
    private void onLogin(PlayerLoginEvent event) {
        Profile profile = new Profile(event.getPlayer().getUniqueId());
        profile.load();

        ProfileRepository profileRepository = Artex.getInstance().getProfileRepository();
        profileRepository.getProfiles().put(profile.getUuid(), profile);
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Profile profile = Artex.getInstance().getProfileRepository().getProfile(player.getUniqueId());
        profile.setName(player.getName());

        event.setJoinMessage(null);
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Profile profile = Artex.getInstance().getProfileRepository().getProfile(player.getUniqueId());
        profile.save();

        event.setQuitMessage(null);
    }
}
