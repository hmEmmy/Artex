package me.emmy.artex.profile.handler.impl;

import me.emmy.artex.Artex;
import me.emmy.artex.grant.GrantSerializer;
import me.emmy.artex.profile.Profile;
import me.emmy.artex.profile.handler.IProfile;
import me.emmy.artex.util.Logger;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.UUID;

/**
 * @author Emmy
 * @project Artex
 * @date 23/10/2024 - 12:18
 */
public class FlatFileProfileHandler implements IProfile {
    /**
     * Load the profile of a player from the configuration file.
     *
     * @param profile the profile of the player
     */
    public void loadProfile(Profile profile) {
        FileConfiguration config = Artex.getInstance().getConfigHandler().getConfig("profiles");
        UUID uuid = profile.getUuid();

        if (!config.contains(uuid.toString())) {
            Logger.debug("Profile for " + profile.getUsername() + " not found.");
            return;
        }

        profile.setUsername(config.getString(uuid + ".username"));
        if (config.contains(uuid + ".tag")) profile.setTag(config.getString(uuid + ".tag"));
        profile.setRank(Artex.getInstance().getRankService().getRank(config.getString(uuid + ".rank")));

        List<String> grants = config.getStringList(uuid + ".grants");
        profile.setGrants(GrantSerializer.deserialize(grants));
    }

    /**
     * Save the profile of a player to the configuration file.
     *
     * @param profile the profile of the player
     */
    public void saveProfile(Profile profile) {
        FileConfiguration config = Artex.getInstance().getConfigHandler().getConfig("profiles");
        UUID uuid = profile.getUuid();

        config.set(uuid + ".username", profile.getUsername());
        if (profile.getTag() != null) config.set(uuid + ".tag", profile.getTag().getName());
        config.set(uuid + ".rank", profile.getHighestRankBasedOnGrant().getName());
        config.set(uuid + ".grants", GrantSerializer.serialize(profile.getGrants()));

        Artex.getInstance().getConfigHandler().saveConfig(Artex.getInstance().getConfigHandler().getConfigFile("profiles"), config);
    }
}