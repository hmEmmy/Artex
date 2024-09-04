package me.emmy.artex.profile;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import me.emmy.artex.Artex;
import me.emmy.artex.grant.Grant;
import me.emmy.artex.grant.GrantSerializer;
import me.emmy.artex.locale.Locale;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.tag.Tag;
import me.emmy.artex.tag.TagRepository;
import me.emmy.artex.util.Logger;
import me.emmy.artex.util.ProjectInfo;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.*;

/**
 * @author Emmy
 * @project Artex
 * @date 15/08/2024 - 21:42
 */
@Getter
public class ProfileRepository {

    private final Map<UUID, Profile> profiles = new HashMap<>();

    /**
     * Save the profile to the database.
     *
     * @param profile the profile to save
     */
    public void saveProfile(Profile profile) {
        MongoCollection<Document> collection = Artex.getInstance().getDatabaseService().getProfilesCollection();
        Document document = new Document("uuid", profile.getUuid().toString())
                .append("name", profile.getUsername())
                .append("tag", profile.getTag() == null ? "none" : profile.getTag().getName())
                .append("rank", profile.getHighestRankBasedOnGrant(profile.getUuid()).getName())
                .append("grants", GrantSerializer.serialize(profile.getGrants()))

                ;


        collection.replaceOne(Filters.eq("uuid", profile.getUuid().toString()), document, new ReplaceOptions().upsert(true));
    }

    /**
     * Load the profile from the database.
     *
     * @param uuid the UUID of the profile
     */
    public void loadProfile(UUID uuid) {
        MongoCollection<Document> collection = Artex.getInstance().getDatabaseService().getProfilesCollection();
        Document document = collection.find(Filters.eq("uuid", uuid.toString())).first();

        if (document != null) {
            Profile profile = new Profile(uuid);
            profile.setUsername(document.getString("name"));
            profile.setTag(Artex.getInstance().getTagRepository().getTag(document.getString("tag")).getName());
            profile.setRank(Artex.getInstance().getRankRepository().getRank(document.getString("rank")));
            profile.setGrants(GrantSerializer.deserialize(document.getList("grants", String.class)));

            profiles.put(uuid, profile);
        } else {
            Logger.debug("Profile not found for " + uuid + ".");
        }
    }

    /**
     * Add a new profile or retrieve an existing one.
     *
     * @param uuid the UUID of the profile
     */
    public void addProfile(UUID uuid) {
        Profile profile = new Profile(uuid);
        profiles.put(uuid, profile);
    }

    /**
     * Add a new profile with default values.
     *
     * @param uuid the UUID of the profile
     * @return the profile
     */
    public Profile addProfileWithDefaultValues(UUID uuid) {
        Profile profile = new Profile(uuid);
        profiles.put(uuid, profile);
        profile.setUsername(Bukkit.getOfflinePlayer(uuid).getName());
        profile.setRank(Artex.getInstance().getRankRepository().getDefaultRank());
        profile.setGrants(new ArrayList<>());
        profile.setTag(null);
        Artex.getInstance().getProfileRepository().addFirstDefaultGrant(uuid);
        return profile;
    }

    /**
     * Get a profile by UUID, or create a new one if it doesn't exist.
     *
     * @param uuid the UUID of the profile
     * @return the profile
     */
    public Profile getProfile(UUID uuid) {
        if (profiles.get(uuid) == null) {
            Logger.debug("getProfile method called for " + uuid.toString() + ", adding new profile.");
            return addProfileWithDefaultValues(uuid);
        }

        return profiles.get(uuid);
    }

    /**
     * Get a profile by UUID without adding a new one if it doesn't exist.
     *
     * @param uuid the UUID of the profile
     * @return the profile
     */
    public Profile getProfileWithNoAdding(UUID uuid) {
        return profiles.get(uuid);
    }

    /**
     * Remove a profile from the repository and database.
     *
     * @param uuid the UUID of the profile
     */
    public void removeProfile(UUID uuid) {
        profiles.remove(uuid);
        MongoCollection<Document> collection = Artex.getInstance().getDatabaseService().getProfilesCollection();
        collection.deleteOne(Filters.eq("uuid", uuid.toString()));
    }

    /**
     * Save all profiles to the database.
     */
    public void saveProfiles() {
        profiles.values().forEach(this::saveProfile);
    }

    /**
     * Add the default grant to the player's profile
     *
     * @param uuid player's UUID
     */
    public void addFirstDefaultGrant(UUID uuid) {
        Logger.debug("addFirstDefaultGrant method called for " + uuid.toString() + ".");
        Grant grant = new Grant();
        grant.setRank(Artex.getInstance().getRankRepository().getDefaultRank().getName());
        grant.setPermanent(true);
        grant.setDuration(0);
        grant.setReason("Default rank");
        grant.setAddedBy("Console");
        grant.setAddedAt(System.currentTimeMillis());
        grant.setAddedOn(Locale.SERVER_NAME.getString());
        grant.setActive(true);

        Artex.getInstance().getProfileRepository().getProfile(uuid).setRank(Artex.getInstance().getRankRepository().getDefaultRank());

        addGrant(uuid, grant);
    }

    public void addGrant(UUID playerUUID, Grant grant) {
        Profile profile = profiles.get(playerUUID);
        List<Grant> grants = profile.getGrants();

        Logger.debug("addGrant method called for " + profile.getUsername() + " with rank " + grant.getRank().getName() + ".");

        grants.add(grant);
        profile.save();
    }

    /**
     * Determine the player's rank based on their grants
     *
     * @param profile the player's profile
     */
    public void determineRank(Profile profile) {
        ProfileRepository profileRepository = Artex.getInstance().getProfileRepository();

        Logger.debug("Checking if player has default grant for " + profile.getUsername() + ".");
        if (!profile.hasDefaultGrant(profile.getUuid())) {
            Logger.debug("Adding default grant for " + profile.getUsername() + ".");
            profileRepository.addFirstDefaultGrant(profile.getUuid());
        }

        Logger.debug("Getting highest rank based on grant for " + profile.getUsername() + ".");
        Rank highestGrant = profile.getHighestRankBasedOnGrant(profile.getUuid());
        profile.setRank(highestGrant);
    }

    public void determineTag(Profile profile) {
        Logger.debug("determineTag called for " + profile.getUsername() + ", checking if tag is null.");
        Tag tag = profile.getTag();
        if (tag == null) {
            Logger.debug("Tag is null for " + profile.getUsername() + ", returning.");
            return;
        }

        Logger.debug("Tag is not null for " + profile.getUsername() + ", setting tag.");
        profile.setTag(Artex.getInstance().getTagRepository().getTag(tag.getName()).getName());
    }
}