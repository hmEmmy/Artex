package me.emmy.artex.spawn;

import lombok.Getter;
import me.emmy.artex.Artex;
import me.emmy.artex.util.LocationUtil;
import me.emmy.artex.util.Logger;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


/**
 * @author Emmy
 * @project Artex
 * @date 03/09/2024 - 22:51
 */
@Getter
public class SpawnHandler {

    private Location location;

    /**
     * Automatically load the spawn location
     */
    public SpawnHandler() {
        loadLocation();
    }

    /**
     * Load the spawn location from the config file
     */
    public void loadLocation() {
        FileConfiguration config = Artex.getInstance().getConfig();

        Location location = LocationUtil.deserialize(config.getString("spawn.join-location"));

        if (location == null) {
            Logger.logError("Spawn location is null.");
            return;
        }

        this.location = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    /**
     * Set the spawn location and save it to the config
     *
     * @param location the location to set
     */
    public void saveLocation(Location location) {
        this.location = location;
        FileConfiguration config = Artex.getInstance().getConfig();

        config.set("spawn.join-location", LocationUtil.serialize(location));

        Artex.getInstance().saveConfig();
    }

    /**
     * Teleport a player to the spawn location
     *
     * @param player the player to teleport
     */
    public void teleportToSpawn(Player player) {
        Location location = getLocation();
        if (location == null) {
            Logger.logError("Spawn location is null.");
            return;
        }

        player.teleport(location);
    }
}