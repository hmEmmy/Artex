package me.emmy.artex.profile;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import me.emmy.artex.Artex;
import me.emmy.artex.database.DatabaseService;
import me.emmy.artex.grant.Grant;
import me.emmy.artex.locale.Locale;
import me.emmy.artex.profile.handler.IProfile;
import me.emmy.artex.profile.handler.impl.FlatFileProfileHandler;
import me.emmy.artex.profile.handler.impl.MongoProfileHandler;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.util.Logger;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Emmy
 * @project Artex
 * @date 15/08/2024 - 21:42
 */
@Getter
public class ProfileRepository {
    private final DatabaseService databaseService;
    public MongoCollection<Document> collection;
    private final Map<UUID, Profile> profiles;
    public final IProfile iProfile;

    /**
     * Constructor for the ProfileRepository class
     *
     * @param databaseService the database service
     */
    public ProfileRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
        this.profiles = new HashMap<>();

        this.iProfile = this.determineHandler();
    }

    /**
     * Get a profile by its UUID without adding it to the map if it doesn't exist
     *
     * @param uuid the UUID of the profile
     * @return the profile
     */
    public Profile getProfileWithNoAdding(UUID uuid) {
        return this.profiles.get(uuid);
    }

    /**
     * Get a profile by its UUID and add it to the map if it doesn't exist
     *
     * @param uuid the UUID of the profile
     * @return the profile
     */
    public Profile getIProfile(UUID uuid) {
        if (this.profiles.containsKey(uuid)) {
            return this.profiles.get(uuid);
        }

        Profile profile = new Profile(uuid);
        profile.load();

        this.addProfile(uuid, profile);
        return profile;
    }

    /**
     * Add or update a profile by its UUID.
     *
     * @param uuid    the UUID of the profile
     * @param profile the profile to add or update
     */
    public void addProfile(UUID uuid, Profile profile) {
        this.profiles.put(uuid, profile);
    }

    /**
     * Save all profiles to the database.
     */
    public void saveProfiles() {
        this.profiles.values().forEach(Profile::save);
    }

    /**
     * Add the default grant to the player's profile
     *
     * @param uuid player's UUID
     */
    public void addFirstDefaultGrant(UUID uuid) {
        Grant grant = new Grant();
        grant.setRank(Artex.getInstance().getRankService().getDefaultRank().getName());
        grant.setPermanent(true);
        grant.setDuration(0);
        grant.setReason("Default rank");
        grant.setAddedBy("Console");
        grant.setAddedAt(System.currentTimeMillis());
        grant.setAddedOn(Locale.SERVER_NAME.getString());
        grant.setActive(true);

        Artex.getInstance().getProfileRepository().getIProfile(uuid).setRank(Artex.getInstance().getRankService().getDefaultRank());

        this.addGrant(uuid, grant);
    }

    public void addGrant(UUID playerUUID, Grant grant) {
        Profile profile = this.profiles.get(playerUUID);
        List<Grant> grants = profile.getGrants();
        grants.add(grant);
        profile.save();
    }

    /**
     * Determine the player's rank based on their grants
     *
     * @param profile the player's profile
     */
    public void determineRank(Profile profile) {
        if (!profile.hasDefaultGrant()) {
            this.addFirstDefaultGrant(profile.getUuid());
        }

        Rank highestGrant = profile.getHighestRankBasedOnGrant();
        profile.setRank(highestGrant);
    }

    /**
     * Determine the handler for the profile
     *
     * @return the handler for the profile
     */
    private IProfile determineHandler() {
        IProfile iProfile;
        if (this.databaseService.isMongo()) {
            this.collection = this.databaseService.getDatabase().getCollection("profiles");
            iProfile = new MongoProfileHandler();
        } else if (this.databaseService.isFlatFile()) {
            iProfile = new FlatFileProfileHandler();
        } else {
            Logger.logError("No database type found.");
            Bukkit.getPluginManager().disablePlugin(Artex.getInstance());
            iProfile = null;
        }
        return iProfile;
    }
}