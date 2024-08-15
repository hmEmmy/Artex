package me.emmy.artex.profile;

import com.mongodb.client.model.Filters;
import lombok.Getter;
import me.emmy.artex.Artex;
import me.emmy.artex.rank.Rank;
import org.bson.Document;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Artex
 * @date 15/08/2024 - 21:54
 */
@Getter
public class ProfileHandler {

    /**
     * Load a profile from the database
     *
     * @param profile the profile to load
     */
    public void loadProfile(Profile profile) {
        Document document = Artex.getInstance().getProfileRepository().getMongoCollection().find(Filters.eq("uuid", profile.getUuid())).first();
        if (document == null) {
            this.saveProfile(profile);
            return;
        }

        Artex.getInstance().getProfileRepository().loadProfile(document);
    }

    /**
     * Save a profile to the database
     *
     * @param profile the profile to save
     */
    public void saveProfile(Profile profile) {
        Document document = new Document();
        document.put("uuid", profile.getUuid().toString());
        document.put("name", profile.getName());

        Artex.getInstance().getProfileRepository().getMongoCollection().insertOne(document);
    }

    /**
     * Attach permissions to a player based on their rank
     *
     * @param player the player to attach the permissions to
     * @param rankName the rank to get the permissions from
     */
    public void attachPermsBasedOnRank(Player player, String rankName) {

        //TODO: Must be profile based (grants, ect...)

        Rank rank = Artex.getInstance().getRankRepository().getRank(rankName);
        for (String permission : rank.getPermissions()) {
            player.addAttachment(Artex.getInstance()).setPermission(permission, true);
        }
    }
}
