package me.emmy.artex.godmode;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Emmy
 * @project Artex
 * @date 03/09/2024 - 22:43
 */
@Getter
@Setter
public class GodModeRepository {
    private Set<Player> godModePlayers;

    public GodModeRepository() {
        this.godModePlayers = new HashSet<>();
    }

    /**
     * Enable godmode for a player
     *
     * @param player The player to enable godmode for
     */
    public void enableGodMode(Player player) {
        player.setAllowFlight(true);
        player.setFlying(true);

        this.godModePlayers.add(player);
    }

    /**
     * Disable godmode for a player
     *
     * @param player The player to disable godmode for
     */
    public void disableGodMode(Player player) {
        player.setAllowFlight(false);
        player.setFlying(false);

        this.godModePlayers.remove(player);
    }

    /**
     * Check if a player is in godmode
     *
     * @param player The player to check
     * @return If the player is in godmode
     */
    public boolean isGodModeEnabled(Player player) {
        return this.godModePlayers.contains(player);
    }
}