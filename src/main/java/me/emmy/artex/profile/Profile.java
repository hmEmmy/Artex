package me.emmy.artex.profile;

import lombok.Getter;
import lombok.Setter;
import me.emmy.artex.Artex;
import me.emmy.artex.grant.Grant;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.tag.Tag;
import me.emmy.artex.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;

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
    }

    public void save() {
        Artex.getInstance().getProfileRepository().getIProfile().saveProfile(this);
    }

    /**
     * Get the highest rank based on the grants of the player.
     * Filter by active grants and their rank weight.
     *
     * @return the highest rank based on the grants of the player or else the default rank.
     */
    public Rank getHighestRankBasedOnGrant() {
        return this.getGrants().stream()
                .filter(grant -> grant.isActive() && grant.getRank() != null)
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

    /**
     * Usually when an admin executes /rank delete, the rank of the grant still remains (theoretically).
     * But the rank is null, so it causes errors upon loading profile because we call getHighestRankBasedOnGrant().
     * Therefore, we remove the grant whose rank is null.
     */
    public void removeGrantWithNullRank() {
        //this.grants.removeIf(grant -> grant.getRank() == null);
    }

    /**
     * Get all ranks from the grants and return their permissions.
     *
     * @return a list of permissions
     */
    private List<Permission> getAllRanksAndTheirPerms() {
        List<Permission> permissions = new ArrayList<>();
        this.grants.forEach(grant -> {
            if (grant.getRank() != null) {
                grant.getRank().getPermissions().forEach(permission -> permissions.add(new Permission(permission)));
            }
        });
        return permissions;
    }

    /**
     * Attach permissions to the player based on their rank.
     */
    public void attachPermsBasedOnRank() {
        List<Permission> permissions = getAllRanksAndTheirPerms();
        permissions.forEach(permission -> Bukkit.getPlayer(this.uuid).addAttachment(Artex.getInstance(), permission.getName(), true));
    }
}