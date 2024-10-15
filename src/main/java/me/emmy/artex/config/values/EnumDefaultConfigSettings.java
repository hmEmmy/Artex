package me.emmy.artex.config.values;

import lombok.Getter;
import org.apache.commons.lang3.compare.ObjectToStringComparator;

/**
 * @author Emmy
 * @project Artex
 * @date 15/10/2024 - 19:21
 */
@Getter
public enum EnumDefaultConfigSettings {
    TEST_MESSAGE("test.messages.config", "This is a test message to be saved in the settings config.")

    ;

    private final String path;
    private final String defaultValue;

    /**
     * Constructor for the SettingsConfigValues enum
     *
     * @param path the path to the value in the config
     * @param defaultValue the default value to set if the value is not found
     */
    EnumDefaultConfigSettings(String path, String defaultValue) {
        this.path = path;
        this.defaultValue = defaultValue;
    }
}