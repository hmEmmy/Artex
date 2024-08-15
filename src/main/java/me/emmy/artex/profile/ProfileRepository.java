package me.emmy.artex.profile;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import lombok.Setter;
import me.emmy.artex.Artex;
import org.bson.Document;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Emmy
 * @project Artex
 * @date 15/08/2024 - 21:42
 */
@Getter
@Setter
public class ProfileRepository {

    private Map<UUID, Profile> profiles = new HashMap<>();
    private MongoCollection<Document> mongoCollection;

    /**
     * Initialize the profile repository
     */
    public void initialize() {
        mongoCollection = Artex.getInstance().getDatabaseHandler().getDatabase().getCollection("profiles");
        for (Document document : mongoCollection.find()) {
            loadProfile(document);
        }
    }

    /**
     * Load a profile from the database
     *
     * @param document the document to load
     */
    public void loadProfile(Document document) {
        UUID uuid = UUID.fromString(document.getString("uuid"));
        Profile profile = new Profile(uuid);
        profile.load();

        profiles.put(profile.getUuid(), profile);
    }

    /**
     * Get a profile by UUID
     *
     * @param uuid the UUID to get the profile by
     * @return the profile
     */
    public Profile getProfile(UUID uuid) {
        if (!profiles.containsKey(uuid)) {
            Profile profile = new Profile(uuid);
            profiles.put(uuid, profile);
            return profile;
        }
        return profiles.get(uuid);
    }
}
