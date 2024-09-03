package me.emmy.artex;

import lombok.Getter;
import lombok.Setter;
import me.emmy.artex.api.command.CommandFramework;
import me.emmy.artex.api.menu.listener.MenuListener;
import me.emmy.artex.broadcast.BroadcastTask;
import me.emmy.artex.chat.ChatRepository;
import me.emmy.artex.chat.listener.ChatListener;
import me.emmy.artex.database.DatabaseService;
import me.emmy.artex.profile.ProfileRepository;
import me.emmy.artex.profile.listener.ProfileListener;
import me.emmy.artex.rank.RankRepository;
import me.emmy.artex.tag.TagRepository;
import me.emmy.artex.util.CC;
import me.emmy.artex.command.CommandUtility;
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
    private TagRepository tagRepository;
    private ChatRepository chatRepository;
    private BroadcastTask broadcastTask;

    @Override
    public void onEnable() {
        instance = this;

        registerCommands();
        saveDefaultConfig();
        setupMongoDatabase();
        initializeRepositories();
        registerEvents();
        runTasks();

        CC.sendEnableMessage();
    }

    @Override
    public void onDisable() {
        this.profileRepository.saveProfiles();
        this.databaseService.close();

        CC.sendDisableMessage();
    }

    /**
     * Register all commands.
     */
    private void registerCommands() {
        this.commandFramework = new CommandFramework();
        CommandUtility.registerCommands();
    }

    /**
     * Connect to the mongo database.
     */
    private void setupMongoDatabase() {
        this.databaseService = new DatabaseService();
    }

    /**
     * Initialize all repositories.
     */
    private void initializeRepositories() {
        this.profileRepository = new ProfileRepository();

        this.rankRepository = new RankRepository();

        this.tagRepository = new TagRepository();

        this.chatRepository = new ChatRepository(false);
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

    /**
     * Run all tasks.
     */
    private void runTasks() {
        this.broadcastTask = new BroadcastTask();
        if (getConfig().getBoolean("broadcast.enabled")) {
            int interval = getConfig().getInt("broadcast.send-every");
            this.broadcastTask.runTaskTimerAsynchronously(this, 20L * interval, 20L * interval);
        }
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
