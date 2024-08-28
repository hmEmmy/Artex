package me.emmy.artex;

import lombok.Getter;
import lombok.Setter;
import me.emmy.artex.api.command.CommandFramework;
import me.emmy.artex.api.menu.listener.MenuListener;
import me.emmy.artex.chat.listener.ChatListener;
import me.emmy.artex.database.DatabaseService;
import me.emmy.artex.profile.ProfileRepository;
import me.emmy.artex.profile.listener.ProfileListener;
import me.emmy.artex.rank.RankRepository;
import me.emmy.artex.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class Artex extends JavaPlugin {

    @Getter
    private static Artex instance;

    private CommandFramework commandFramework;
    private DatabaseService databaseService;
    private ProfileRepository profileRepository;
    private RankRepository rankRepository;

    @Override
    public void onEnable() {
        instance = this;

        this.commandFramework = new CommandFramework();
        this.commandFramework.registerCommandsInPackage("me.emmy.artex");

        saveDefaultConfig();
        setupMongoDatabase();
        initializeRepositories();
        registerEvents();

        CC.sendEnableMessage();
    }

    /**
     * Connect to the mongo database.
     */
    private void setupMongoDatabase() {
        this.databaseService = new DatabaseService();
    }

    /**
     * Initialize all handlers.
     */
    private void initializeRepositories() {
        this.profileRepository = new ProfileRepository();

        this.rankRepository = new RankRepository();
        this.rankRepository.loadRanks();
    }

    /**
     * Register all events by looping through a list of listeners.
     */
    private void registerEvents() {
        List<Listener> listeners = Arrays.asList(
                new ProfileListener(),
                new ChatListener(),
                new MenuListener()
        );
        listeners.forEach(event -> Bukkit.getPluginManager().registerEvents(event, this));
    }

    @Override
    public void onDisable() {
        this.databaseService.close();
        this.profileRepository.saveProfiles();

        CC.sendDisableMessage();
    }

    /**
     * Get the exact bukkit version
     *
     * @return the exact bukkit version
     */
    public String getBukkitVersionExact() {
        String version = Bukkit.getServer().getVersion();
        version = version.split("MC: ")[1];
        version = version.split("\\)")[0];
        return version;
    }
}
