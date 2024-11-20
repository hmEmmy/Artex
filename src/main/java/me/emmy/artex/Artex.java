package me.emmy.artex;

import lombok.Getter;
import me.emmy.artex.api.command.CommandFramework;
import me.emmy.artex.api.menu.listener.MenuListener;
import me.emmy.artex.broadcast.BroadcastTask;
import me.emmy.artex.chat.ChatService;
import me.emmy.artex.chat.listener.ChatListener;
import me.emmy.artex.command.CommandUtility;
import me.emmy.artex.config.ConfigHandler;
import me.emmy.artex.conversation.ConversationHandler;
import me.emmy.artex.database.DatabaseService;
import me.emmy.artex.godmode.GodModeRepository;
import me.emmy.artex.profile.ProfileRepository;
import me.emmy.artex.profile.listener.ProfileListener;
import me.emmy.artex.rank.RankService;
import me.emmy.artex.spawn.SpawnHandler;
import me.emmy.artex.tag.TagService;
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
    private RankService rankService;
    private ProfileRepository profileRepository;
    private TagService tagService;
    private ChatService chatService;
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
        this.setupDatabase();
        this.initializeRepositoriesAndServices();
        this.registerHandlers();
        this.registerEvents();
        this.runTasks();

        //this.sendCustomFont();
        CC.sendEnableMessage();
    }

    @Override
    public void onDisable() {
        if (this.profileRepository.getIProfile() != null) {
            this.profileRepository.saveProfiles();
        }

        CC.sendDisableMessage();
    }

    private void registerChannels() {
        this.measureRuntime("register", "BungeeChannel", () -> this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord"));
    }

    private void initializeConfigHandler() {
        this.measureRuntime("initialize", "ConfigHandler", () -> this.configHandler = new ConfigHandler());
    }

    private void registerCommands() {
        this.measureRuntime("initialize", "CommandFramework", () -> this.commandFramework = new CommandFramework());
        this.measureRuntime("register", "Commands", CommandUtility::registerCommands);
    }

    private void setupDatabase() {
        this.measureRuntime("setup", "DatabaseService", () -> this.databaseService = new DatabaseService());
    }

    private void initializeRepositoriesAndServices() {
        this.measureRuntime("load", "RankService", () -> this.rankService = new RankService(this.configHandler));
        this.measureRuntime("load", "TagService", () -> this.tagService = new TagService(this.configHandler));
        this.measureRuntime("initialize", "ProfileRepository", () -> this.profileRepository = new ProfileRepository(this.databaseService));
        this.measureRuntime("load", "ChatService", () -> this.chatService = new ChatService(false));
        this.measureRuntime("initialize", "GodModeRepository", () -> this.godModeRepository = new GodModeRepository());
    }

    private void registerHandlers() {
        this.measureRuntime("load", "SpawnHandler", () -> this.spawnHandler = new SpawnHandler(this.getConfig()));
        this.measureRuntime("load", "ConversationHandler", () -> this.conversationHandler = new ConversationHandler(this.getConfig()));
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
            this.measureRuntime("run", "BroadcastTask", () -> new BroadcastTask(this.getConfig()).runTaskTimerAsynchronously(this, 20L * this.broadcastInterval(), 20L * this.broadcastInterval()));
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

    /**
     * Measure the runtime of a task
     *
     * @param action the action
     * @param task the task to measure
     * @param runnable the runnable to run
     */
    private void measureRuntime(String action, String task, Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        long runtime = System.currentTimeMillis() - start;
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7(&4&l" + this.getDescription().getName() + "&7) " + "&c" + task + " &ftook &4" + runtime + "ms &fto " + action + "."));
    }

    private void sendCustomFont() {
        Arrays.stream(ARTEX_ASCII).forEach(line -> Bukkit.getConsoleSender().sendMessage(CC.translate(line)));
    }

    private final String[] ARTEX_ASCII = {
            "",
            "        _         _            ",
            "       / \\   _ __| |_ _____  __",
            "      / _ \\ | '__| __/ _ \\ \\/ /",
            "     / ___ \\| |  | ||  __/>  < ",
            "    /_/   \\_\\_|   \\__\\___/_/\\_\\",
            ""
    };
}