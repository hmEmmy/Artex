package me.emmy.artex.profile;

import lombok.Getter;
import lombok.Setter;
import me.emmy.artex.Artex;
import me.emmy.artex.grant.Grant;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.util.Logger;
import org.bukkit.Bukkit;

import java.util.ArrayList;
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
        this.username = Bukkit.getOfflinePlayer(this.uuid).getName();
        this.grants = new ArrayList<>();
    }

    public void load() {
        Artex.getInstance().getProfileRepository().loadProfile(uuid);
        Logger.debug("Loaded profile for " + username + ".");
    }

    public void save() {
        Artex.getInstance().getProfileRepository().saveProfile(this);
        Logger.debug("Saved profile for " + username + ".");
    }

    /*public Rank getHighestRank(UUID playerUUID) {
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
    }*/

    /**
     * Get the highest rank of a player
     *
     * @param playerUUID player's UUID
     * @return the highest rank
     */
    public Rank getHighestRankBasedOnGrant(UUID playerUUID) {
        Profile profile = Artex.getInstance().getProfileRepository().getProfile(playerUUID);
        List<Grant> grants = profile.getGrants();

        if (grants == null || grants.isEmpty()) {
            return Artex.getInstance().getRankRepository().getDefaultRank();
        }

        Grant highestGrant = null;
        for (Grant grant : grants) {
            if (!grant.isActive() || grant.hasExpired()) {
                continue;
            }

            if (highestGrant == null || grant.getRank().getWeight() > highestGrant.getRank().getWeight()) {
                highestGrant = grant;
            }
        }

        if (highestGrant == null) {
            return Artex.getInstance().getRankRepository().getDefaultRank();
        }

        return highestGrant.getRank();
    }

    /**
     * Check if the player has a grant with the default rank
     *
     * @param playerUUID player's UUID
     * @return if the player has a default grant
     */
    public boolean hasDefaultGrant(UUID playerUUID) {
        return Artex.getInstance().getProfileRepository().getProfile(playerUUID).getGrants()
                .stream()
                .anyMatch(grant -> grant.getRank().isDefaultRank());
    }
}