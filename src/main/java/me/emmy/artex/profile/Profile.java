package me.emmy.artex.profile;

import lombok.Getter;
import lombok.Setter;
import me.emmy.artex.Artex;
import me.emmy.artex.grant.Grant;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.tag.Tag;
import me.emmy.artex.util.Logger;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Comparator;
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
        this.tag = "";
        this.rank = Artex.getInstance().getRankService().getDefaultRank();
        this.grants = new ArrayList<>();
    }

    public void load() {
        Artex.getInstance().getProfileRepository().getIProfile().loadProfile(this);
        Logger.debug("Loaded profile for " + this.username + ".");
    }

    public void save() {
        Artex.getInstance().getProfileRepository().getIProfile().saveProfile(this);
        Logger.debug("Saved profile for " + this.username + ".");
    }

    /**
     * Get the highest rank based on the grants of the player.
     * Filter by active grants and their rank weight.
     *
     * @return the highest rank based on the grants of the player or else the default rank.
     */
    public Rank getHighestRankBasedOnGrant() {
        return this.getGrants().stream()
                .filter(Grant::isActive)
                .max(Comparator.comparingInt(grant -> grant.getRank().getWeight()))
                .map(Grant::getRank)
                .orElse(Artex.getInstance().getRankService().getDefaultRank());
    }

    /**
     * Check if the player has the default grant.
     *
     * @return true if the player has the default grant, false otherwise
     */
    public boolean hasDefaultGrant() {
        return Artex.getInstance().getProfileRepository().getProfileWithNoAdding(this.uuid).getGrants()
                .stream()
                .anyMatch(grant -> grant.getRank().isDefaultRank());
    }

    public Tag getTag() {
        return Artex.getInstance().getTagService().getTag(this.tag);
    }
}