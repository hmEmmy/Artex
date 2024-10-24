package me.emmy.artex.profile;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import me.emmy.artex.Artex;
import me.emmy.artex.config.ConfigHandler;
import me.emmy.artex.grant.Grant;
import me.emmy.artex.locale.Locale;
import me.emmy.artex.profile.handler.impl.FlatFileProfileHandler;
import me.emmy.artex.profile.handler.impl.MongoProfileHandler;
import me.emmy.artex.profile.handler.IProfile;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.util.Logger;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import java.util.*;

/**
 * @author Emmy
 * @project Artex
 * @date 15/08/2024 - 21:42
 */
@Getter
public class ProfileRepository {
    private final Map<UUID, Profile> profiles = new HashMap<>();
    public MongoCollection<Document> collection;
    public final IProfile profile;

    public ProfileRepository() {
        if (Artex.getInstance().getDatabaseService().isMongo()) {
            this.profile = new MongoProfileHandler();
        } else if (Artex.getInstance().getDatabaseService().isFlatFile()) {
            this.profile = new FlatFileProfileHandler();
        } else {
            Logger.logError("No database type found.");
            Bukkit.getPluginManager().disablePlugin(Artex.getInstance());
            this.profile = null;
        }
    }

    public void initializeEveryProfile() {
        if (Artex.getInstance().getDatabaseService().isMongo()) {
            this.initializeEveryMongoProfile();
        } else if (Artex.getInstance().getDatabaseService().isFlatFile()) {
            this.initializeEveryFlatFileProfile();
        } else {
            Logger.logError("No database type found.");
            Bukkit.getPluginManager().disablePlugin(Artex.getInstance());
        }
    }

    private void initializeEveryMongoProfile() {
        this.collection = Artex.getInstance().getDatabaseService().getDatabase().getCollection("profiles");
        this.collection.find().forEach(this::loadProfile);
    }

    private void initializeEveryFlatFileProfile() {
        FileConfiguration config = ConfigHandler.getInstance().getConfig("profiles.yml");
        for (String key : config.getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            Profile profile = new Profile(uuid);
            profile.load();

            profiles.put(profile.getUuid(), profile);
        }
    }

    /**
     * Load a profile from a document
     *
     * @param document the document to load the profile from
     */
    private void loadProfile(Document document) {
        UUID uuid = UUID.fromString(document.getString("uuid"));
        Profile profile = new Profile(uuid);
        profile.load();

        profiles.put(profile.getUuid(), profile);
    }

    /**
     * Get a profile by its UUID without adding it to the map if it doesn't exist
     *
     * @param uuid the UUID of the profile
     * @return the profile
     */
    public Profile getProfileWithNoAdding(UUID uuid) {
        return profiles.get(uuid);
    }

    /**
     * Get a profile by its UUID and add it to the map if it doesn't exist
     *
     * @param uuid the UUID of the profile
     * @return the profile
     */
    public Profile getProfile(UUID uuid) {
        if (profiles.containsKey(uuid)) {
            return profiles.get(uuid);
        }

        Profile profile = new Profile(uuid);
        profile.load();

        addProfile(uuid, profile);
        return profile;
    }

    /**
     * Add or update a profile by its UUID.
     *
     * @param uuid    the UUID of the profile
     * @param profile the profile to add or update
     */
    public void addProfile(UUID uuid, Profile profile) {
        profiles.put(uuid, profile);
    }

    /**
     * Save all profiles to the database.
     */
    public void saveProfiles() {
        profiles.values().forEach(Profile::save);
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
        if (!profile.hasDefaultGrant()) {
            Logger.debug("Adding default grant for " + profile.getUsername() + ".");
            profileRepository.addFirstDefaultGrant(profile.getUuid());
        }

        Logger.debug("Getting highest rank based on grant for " + profile.getUsername() + ".");
        Rank highestGrant = profile.getHighestRankBasedOnGrant();
        profile.setRank(highestGrant);
    }
}