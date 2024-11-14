package me.emmy.artex;

import lombok.Getter;
import me.emmy.artex.api.command.CommandFramework;
import me.emmy.artex.api.menu.listener.MenuListener;
import me.emmy.artex.broadcast.BroadcastTask;
import me.emmy.artex.chat.ChatRepository;
import me.emmy.artex.chat.listener.ChatListener;
import me.emmy.artex.command.CommandUtility;
import me.emmy.artex.config.ConfigHandler;
import me.emmy.artex.conversation.ConversationHandler;
import me.emmy.artex.database.DatabaseService;
import me.emmy.artex.godmode.GodModeRepository;
import me.emmy.artex.profile.ProfileRepository;
import me.emmy.artex.profile.listener.ProfileListener;
import me.emmy.artex.rank.RankRepository;
import me.emmy.artex.spawn.SpawnHandler;
import me.emmy.artex.tag.TagRepository;
import me.emmy.artex.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

/**
 * |------------------------------------------------------------------------------------------------------------------------------------------------|
 *   This project is licensed under Apache License 2.0, see LICENSE.
 *   Copying, modifying or distributing this project is allowed as long as credits maintain.
 *   Credentials are these file headers, the plugin.yml and anything else in relation with the author's name, including the LICENSE file.
 *   Basically, if you do not follow the usage, you're stealing this project, aka. skidding it, which is not cool :|
 * |------------------------------------------------------------------------------------------------------------------------------------------------|
 *
 * @author Emmy
 * @project Artex
 * @date 15/08/2024 - 20:11
 */
@Getter
public class Artex extends JavaPlugin {

    @Getter
    private static Artex instance;

    private ConfigHandler configHandler;
    private CommandFramework commandFramework;
    private DatabaseService databaseService;
    private RankRepository rankRepository;
    private ProfileRepository profileRepository;
    private TagRepository tagRepository;
    private ChatRepository chatRepository;
    private GodModeRepository godModeRepository;
    private SpawnHandler spawnHandler;
    private ConversationHandler conversationHandler;

    @Override
    public void onEnable() {
        instance = this;

        this.registerChannels();
        this.registerCommands();
        this.saveDefaultConfig();
        this.initializeConfigHandler();
        this.setupMongoDatabase();
        this.initializeRepositories();
        this.registerHandlers();
        this.registerEvents();
        this.runTasks();

        CC.sendEnableMessage();
    }

    @Override
    public void onDisable() {
        this.profileRepository.saveProfiles();

        CC.sendDisableMessage();
    }

    private void registerChannels() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    private void initializeConfigHandler() {
        this.configHandler = new ConfigHandler();
    }

    private void registerCommands() {
        this.commandFramework = new CommandFramework();
        CommandUtility.registerCommands();
    }

    private void setupMongoDatabase() {
        this.databaseService = new DatabaseService();
    }

    private void initializeRepositories() {
        this.rankRepository = new RankRepository();
        this.tagRepository = new TagRepository();
        this.profileRepository = new ProfileRepository();
        this.profileRepository.initializeEveryProfile();
        this.chatRepository = new ChatRepository(false);
        this.godModeRepository = new GodModeRepository();
    }

    private void registerHandlers() {
        this.spawnHandler = new SpawnHandler();
        this.conversationHandler = new ConversationHandler();
    }

    private void registerEvents() {
        List<Listener> listeners = Arrays.asList(
                new ProfileListener(),
                new ChatListener(),
                new MenuListener()
        );
        listeners.forEach(event -> Bukkit.getPluginManager().registerEvents(event, this));
    }

    private void runTasks() {
        if (this.getConfig().getBoolean("broadcast.enabled")) {
            BroadcastTask broadcastTask = new BroadcastTask();
            broadcastTask.runTaskTimerAsynchronously(this, 20L * this.broadcastInterval(), 20L * this.broadcastInterval());
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

    /**
     * Get the broadcast interval from the config
     *
     * @return the broadcast interval
     */
    private int broadcastInterval() {
        return this.getConfig().getInt("broadcast.send-every");
    }
}