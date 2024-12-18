package me.emmy.artex.profile.handler.impl;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import me.emmy.artex.Artex;
import me.emmy.artex.grant.GrantSerializer;
import me.emmy.artex.profile.Profile;
import me.emmy.artex.profile.handler.IProfile;
import me.emmy.artex.util.Logger;
import org.bson.Document;

/**
 * @author Emmy
 * @project Artex
 * @date 29/09/2024 - 09:58
 */
public class MongoProfileHandler implements IProfile {
    /**
     * Load a profile from the database
     *
     * @param profile the profile to load
     */
    public void loadProfile(Profile profile) {
        Document document = Artex.getInstance().getProfileRepository().getCollection()
                .find(Filters.eq("uuid", profile.getUuid().toString())).first();

        if (document == null) {
            Logger.logError("Profile not found for " + profile.getUsername() + ".");
            return;
        }

        profile.setUsername(document.getString("name"));

        if (document.getString("tag") != null) profile.setTag(document.getString("tag"));
        profile.setRank(Artex.getInstance().getRankService().getRank(document.getString("rank")));
        profile.setGrants(GrantSerializer.deserialize(document.getList("grants", String.class)));
    }

    /**
     * Save a profile to the database
     *
     * @param profile the profile to save
     */
    public void saveProfile(Profile profile) {
        Document document = new Document();
        document.put("uuid", profile.getUuid().toString());
        document.put("name", profile.getUsername());
        if (profile.getTag() != null) document.put("tag", profile.getTag().getName());
        document.put("rank", profile.getHighestRankBasedOnGrant().getName());
        document.put("grants", GrantSerializer.serialize(profile.getGrants()));

        Artex.getInstance().getProfileRepository().getCollection()
                .replaceOne(Filters.eq("uuid", profile.getUuid().toString()), document, new ReplaceOptions().upsert(true));
    }
}