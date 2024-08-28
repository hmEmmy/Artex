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
import me.emmy.artex.util.Logger;
import org.bson.Document;

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
                .append("rank", profile.getRank().getName())
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
            profile.setRank(Artex.getInstance().getRankRepository().getRank(document.getString("rank")));
            profile.setGrants(GrantSerializer.deserialize(document.getList("grants", String.class)))

            ;

            profiles.put(uuid, profile);
        } else {
            Logger.debug("Profile not found for " + uuid + ".");
        }
    }

    /**
     * Add a new profile or retrieve an existing one.
     *
     * @param uuid the UUID of the profile
     * @return the profile
     */
    public Profile addProfile(UUID uuid) {
        Profile profile = new Profile(uuid);
        profiles.put(uuid, profile);
        return profile;
    }

    /**
     * Get a profile by UUID, or create a new one if it doesn't exist.
     *
     * @param uuid the UUID of the profile
     * @return the profile
     */
    public Profile getProfile(UUID uuid) {
        if (!profiles.containsKey(uuid)) return addProfile(uuid);
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
        profile.setGrants(grants);

        profile.save();
    }

    /**
     * Determine the player's rank based on their grants
     *
     * @param profile the player's profile
     */
    public void determineRank(Profile profile) {
        ProfileRepository profileRepository = Artex.getInstance().getProfileRepository();

        Logger.debug("Getting grant list for " + profile.getUsername() + ".");
        List<Grant> grants = profile.getGrants();

        Logger.debug("Checking if player has default grant for " + profile.getUsername() + ".");
        if (!profile.hasDefaultGrant(profile.getUuid())) {
            Logger.debug("Adding default grant for " + profile.getUsername() + ".");
            profileRepository.addFirstDefaultGrant(profile.getUuid());
        }

        Rank highestGrant = profile.getHighestRankBasedOnGrant(profile.getUuid());
        profile.setRank(highestGrant);
    }
}