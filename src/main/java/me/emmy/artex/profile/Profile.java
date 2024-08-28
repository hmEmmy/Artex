package me.emmy.artex.profile;

import lombok.Getter;
import lombok.Setter;
import me.emmy.artex.Artex;
import me.emmy.artex.grant.Grant;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.util.CC;
import me.emmy.artex.util.Logger;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.UUID;

/**
 * @author Emmy
 * @project Artex
 * @date 15/08/2024 - 21:44
 */
@Getter
@Setter
public class Profile {

    private final UUID uuid;
    private String username;
    private Rank rank;
    private List<Grant> grants;

    /**
     * Constructor for the Profile class.
     *
     * @param uuid the UUID of the profile
     */
    public Profile(UUID uuid) {
        this.uuid = uuid;
    }

    public void load() {
        Artex.getInstance().getProfileRepository().loadProfile(uuid);
        Logger.debug("Loaded profile for " + username + ".");
    }

    public void save() {
        Artex.getInstance().getProfileRepository().saveProfile(this);
        Logger.debug("Saved profile for " + username + ".");
    }

    /**
     * Get the highest rank of a player
     *
     * @param playerUUID player's UUID
     * @return the highest rank
     */
    public Rank getHighestRank(UUID playerUUID) {
        Profile profile = Artex.getInstance().getProfileRepository().getProfile(playerUUID);
        List<Grant> grants = profile.getGrants();
        if (grants == null || grants.isEmpty()) {
            return Artex.getInstance().getRankRepository().getDefaultRank();
        }
        Grant highestGrant = null;
        for (Grant grant : grants) {
            if (highestGrant == null || grant.getRank().getWeight() > highestGrant.getRank().getWeight()) {
                highestGrant = grant;
            }
        }
        return highestGrant.getRank();
    }
}