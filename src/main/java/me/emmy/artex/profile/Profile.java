package me.emmy.artex.profile;

import lombok.Getter;
import lombok.Setter;
import me.emmy.artex.Artex;
import me.emmy.artex.grant.Grant;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.tag.Tag;
import me.emmy.artex.util.Logger;
import org.bukkit.Bukkit;

import java.util.*;

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
    private String tag;
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
        this.tag = "";
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
        return profile.getGrants().stream()
                .filter(Grant::isActive)
                .max(Comparator.comparingInt(grant -> grant.getRank().getWeight()))
                .map(Grant::getRank)
                .orElse(Artex.getInstance().getRankRepository().getDefaultRank());
    }

    /**
     * Check if the player has a grant with the default rank
     *
     * @param playerUUID player's UUID
     * @return if the player has a default grant
     */
    public boolean hasDefaultGrant(UUID playerUUID) {
        return Artex.getInstance().getProfileRepository().getProfileWithNoAdding(playerUUID).getGrants()
                .stream()
                .anyMatch(grant -> grant.getRank().isDefaultRank());
    }

    public Tag getTag() {
        return Artex.getInstance().getTagRepository().getTag(tag);
    }
}