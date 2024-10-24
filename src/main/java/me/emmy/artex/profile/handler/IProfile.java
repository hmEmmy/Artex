package me.emmy.artex.profile.handler;

import me.emmy.artex.profile.Profile;

/**
 * @author Remi
 * @project Tulip
 * @date 6/23/2024
 */
public interface IProfile {

    /**
     * Load a profile from the database
     *
     * @param profile the profile to load
     */
    void loadProfile(Profile profile);

    /**
     * Save a profile to the database
     *
     * @param profile the profile to save
     */
    void saveProfile(Profile profile);
}