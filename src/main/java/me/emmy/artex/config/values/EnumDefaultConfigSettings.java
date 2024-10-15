package me.emmy.artex.config.values;

import lombok.Getter;

/**
 * @author Emmy
 * @project Artex
 * @date 15/10/2024 - 19:21
 */
@Getter
public enum EnumDefaultConfigSettings {
    TEST_MESSAGE("test.messages.config", "This is a test message to be saved in the messages config.")

    ;

    private final String path;
    private final Object defaultValue;

    EnumDefaultConfigSettings(String path, Object defaultValue) {
        this.path = path;
        this.defaultValue = defaultValue;
    }
}