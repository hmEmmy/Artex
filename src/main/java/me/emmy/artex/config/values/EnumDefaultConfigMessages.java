package me.emmy.artex.config.values;

import lombok.Getter;

/**
 * @author Emmy
 * @project Artex
 * @date 15/10/2024 - 19:28
 */
@Getter
public enum EnumDefaultConfigMessages {
    TEST_MESSAGE("test.settings.config", "This is a test message to be saved in the settings config.")

    ;

    private final String path;
    private final String defaultValue;

    /**
     * Constructor for the MessagesConfigValues enum
     *
     * @param path the path to the value in the config
     * @param defaultValue the default value to set if the value is not found
     */
    EnumDefaultConfigMessages(String path, String defaultValue) {
        this.path = path;
        this.defaultValue = defaultValue;
    }
}