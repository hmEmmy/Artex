package me.emmy.artex.config;

import lombok.Getter;
import me.emmy.artex.Artex;
import me.emmy.artex.config.values.EnumDefaultConfigMessages;
import me.emmy.artex.config.values.EnumDefaultConfigSettings;
import me.emmy.artex.util.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Emmy
 * @project Artex
 * @date 15/10/2024 - 19:13
 */
@Getter
public class ConfigHandler {
    private final Map<String, FileConfiguration> configs = new HashMap<>();
    private final Map<String, File> configFiles = new HashMap<>();

    private final String[] configurations = {
            "settings",
            "messages",
            "profiles",
            "ranks",
            "tags",
            "profiles"
    };

    public ConfigHandler() {
        this.loadConfigs(configurations);
    }

    /**
     * Load the configuration files.
     *
     * @param configNames the names of the configuration files
     */
    public void loadConfigs(String[] configNames) {
        for (String configName : configNames) {
            File configFile = new File(Artex.getInstance().getDataFolder(), configName + ".yml");
            if (!configFile.exists()) {
                this.createDefaultConfig(configFile, configName);
            }
            FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
            this.configs.put(configName, config);
            this.configFiles.put(configName, configFile);

            switch (configName) {
                case "settings":
                    for (EnumDefaultConfigSettings utility : EnumDefaultConfigSettings.values()) {
                        if (!config.contains(utility.getPath())) {
                            config.set(utility.getPath(), utility.getDefaultValue());
                        }
                    }
                    break;
                case "messages":
                    for (EnumDefaultConfigMessages message : EnumDefaultConfigMessages.values()) {
                        if (!config.contains(message.getPath())) {
                            config.set(message.getPath(), message.getDefaultValue());
                        }
                    }
                    break;
            }

            this.saveConfig(configFile, config);
        }
    }

    /**
     * Create a default configuration file.
     *
     * @param configFile the file to create
     * @param configName the name of the configuration file
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createDefaultConfig(File configFile, String configName) {
        try {
            configFile.getParentFile().mkdirs();
            configFile.createNewFile();
            FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

            switch (configName) {
                case "settings":
                    for (EnumDefaultConfigSettings defaultConfigSettings : EnumDefaultConfigSettings.values()) {
                        config.set(defaultConfigSettings.getPath(), defaultConfigSettings.getDefaultValue());
                    }
                    break;
                case "messages":
                    for (EnumDefaultConfigMessages defaultConfigMessages : EnumDefaultConfigMessages.values()) {
                        config.set(defaultConfigMessages.getPath(), defaultConfigMessages.getDefaultValue());
                    }
                    break;
            }

            config.save(configFile);
        } catch (IOException e) {
            Logger.logError("Could not create the " + configFile.getName() + " file: " + e.getMessage());
        }
    }

    /**
     * Get a configuration file
     *
     * @param configName the name of the configuration file
     * @return the configuration file
     */
    public FileConfiguration getConfig(String configName) {
        return this.configs.get(configName);
    }

    /**
     * Get a configuration file
     *
     * @param configName the name of the configuration file
     * @return the configuration file
     */
    public File getConfigFile(String configName) {
        return this.configFiles.get(configName);
    }

    /**
     * Save a configuration file
     *
     * @param file the file to save
     * @param config the configuration file
     */
    public void saveConfig(File file, FileConfiguration config) {
        try {
            config.save(file);
        } catch (IOException e) {
            Logger.logError("Could not save the " + file.getName() + " file: " + e.getMessage());
        }
    }
}