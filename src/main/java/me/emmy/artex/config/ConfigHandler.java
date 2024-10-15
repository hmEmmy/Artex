package me.emmy.artex.config;

import lombok.Getter;
import me.emmy.artex.Artex;
import me.emmy.artex.config.values.EnumDefaultConfigMessages;
import me.emmy.artex.config.values.EnumDefaultConfigSettings;
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
    @Getter private static ConfigHandler instance;

    private final Map<String, FileConfiguration> configs = new HashMap<>();
    private final Map<String, File> configFiles = new HashMap<>();

    private final String[] configurations = {
            "settings",
            "messages",
    };

    public ConfigHandler() {
        instance = this;

        loadConfigs(configurations);
    }

    /**
     * Load the configuration files
     *
     * @param configNames the names of the configuration files
     */
    public void loadConfigs(String[] configNames) {
        for (String configName : configNames) {
            File configFile = new File(Artex.getInstance().getDataFolder(), configName + ".yml");
            if (!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                Artex.getInstance().saveResource(configName + ".yml", false);
            }
            FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
            configs.put(configName, config);
            configFiles.put(configName, configFile);

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

            saveConfig(config, configName);
        }
    }

    /**
     * Get a configuration file
     *
     * @param configName the name of the configuration file
     * @return the configuration file
     */
    public FileConfiguration getConfig(String configName) {
        return configs.get(configName);
    }

    /**
     * Get a configuration file
     *
     * @param configName the name of the configuration file
     * @return the configuration file
     */
    public File getConfigFile(String configName) {
        return configFiles.get(configName);
    }

    /**
     * Save a configuration file
     *
     * @param config the configuration file
     * @param configName the name of the configuration file
     */
    public void saveConfig(FileConfiguration config, String configName) {
        try {
            config.save(configFiles.get(configName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig(File file, FileConfiguration config) {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}