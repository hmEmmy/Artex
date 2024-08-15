package me.emmy.artex.profile;

import lombok.Getter;
import lombok.Setter;
import me.emmy.artex.Artex;
import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * @author Emmy
 * @project Artex
 * @date 15/08/2024 - 21:44
 */
@Getter
@Setter
public class Profile {

    private String name;
    private UUID uuid;

    /**
     * Create a new profile
     *
     * @param uuid the UUID of the profile
     */
    public Profile(UUID uuid) {
        this.uuid = uuid;
        this.name = Bukkit.getOfflinePlayer(uuid).getName();
    }

    /**
     * Load the profile
     */
    public void load() {
        Artex.getInstance().getProfileHandler().loadProfile(this);
    }

    /**
     * Save the profile
     */
    public void save() {
        Artex.getInstance().getProfileHandler().saveProfile(this);
    }
}
