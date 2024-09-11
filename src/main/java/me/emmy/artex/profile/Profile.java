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
        tag = "";
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
     * Get the highest rank based on the grants of the player.
     * Filter by active grants and their rank weight.
     *
     * @return the highest rank based on the grants of the player or else the default rank.
     */
    public Rank getHighestRankBasedOnGrant() {
        Profile profile = Artex.getInstance().getProfileRepository().getProfile(uuid);
        return profile.getGrants().stream()
                .filter(Grant::isActive)
                .max(Comparator.comparingInt(grant -> grant.getRank().getWeight()))
                .map(Grant::getRank)
                .orElse(Artex.getInstance().getRankRepository().getDefaultRank());
    }

    /**
     * Check if the player has the default grant.
     *
     * @return true if the player has the default grant, false otherwise
     */
    public boolean hasDefaultGrant() {
        return Artex.getInstance().getProfileRepository().getProfileWithNoAdding(uuid).getGrants()
                .stream()
                .anyMatch(grant -> grant.getRank().isDefaultRank());
    }

    public Tag getTag() {
        return Artex.getInstance().getTagRepository().getTag(tag);
    }
}