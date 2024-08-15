package me.emmy.artex;

import lombok.Getter;
import lombok.Setter;
import me.emmy.artex.database.DatabaseHandler;
import me.emmy.artex.profile.ProfileHandler;
import me.emmy.artex.profile.ProfileRepository;
import me.emmy.artex.rank.RankRepository;
import me.emmy.artex.util.CC;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@Setter
public class Artex extends JavaPlugin {

    @Getter
    private static Artex instance;

    private DatabaseHandler databaseHandler;
    private ProfileRepository profileRepository;
    private ProfileHandler profileHandler;
    private RankRepository rankRepository;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        initializeHandlers();

        CC.sendEnableMessage();
    }

    /**
     * Initialize all handlers
     */
    private void initializeHandlers() {
        this.databaseHandler = new DatabaseHandler();
        this.databaseHandler.createConnection();

        this.profileRepository = new ProfileRepository();
        this.profileRepository.initialize();

        this.profileHandler = new ProfileHandler();

        this.rankRepository = new RankRepository();
        this.rankRepository.createDefaultRank();
        this.rankRepository.saveRanks();
    }

    @Override
    public void onDisable() {
        CC.sendDisableMessage();
    }
}
