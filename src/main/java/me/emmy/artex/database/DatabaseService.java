package me.emmy.artex.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.emmy.artex.Artex;
import me.emmy.artex.util.CC;
import me.emmy.artex.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Emmy
 * @project Artex
 * @date 25/08/2024 - 21:05
 */
@Getter
public class DatabaseService {
    private MongoDatabase database;
    private MongoClient mongoClient;

    /**
     * Constructor for the DatabaseService class
     */
    public DatabaseService() {
        startMongo();
    }

    /**
     * Start the MongoDB connection
     */
    public void startMongo() {
        if (this.isMongo()) {
            try {
                FileConfiguration config = Artex.getInstance().getConfig();

                String databaseName = config.getString("mongo.database");
                Bukkit.getConsoleSender().sendMessage(CC.translate("&6Connecting to the MongoDB database..."));

                ConnectionString connectionString = new ConnectionString(Objects.requireNonNull(config.getString("mongo.uri")));
                MongoClientSettings.Builder settings = MongoClientSettings.builder();
                settings.applyConnectionString(connectionString);
                settings.applyToConnectionPoolSettings(builder -> builder.maxConnectionIdleTime(30, TimeUnit.SECONDS));
                settings.retryWrites(true);

                this.mongoClient = MongoClients.create(settings.build());
                this.database = mongoClient.getDatabase(databaseName);

                Bukkit.getConsoleSender().sendMessage(CC.translate("&aSuccessfully connected to the MongoDB database."));
            } catch (Exception exception) {
                Bukkit.getConsoleSender().sendMessage(CC.translate("&cFailed to connect to the MongoDB database."));
                Bukkit.getPluginManager().disablePlugin(Artex.getInstance());
            }
        } else {
            Logger.debug("The storage type is not set to MongoDB.");
            Bukkit.getPluginManager().disablePlugin(Artex.getInstance());
        }
    }

    /**
     * Check if the database type is MongoDB
     *
     * @return true if the database type is MongoDB
     */
    public boolean isMongo() {
        return Artex.getInstance().getConfig().getString("storage-type").equalsIgnoreCase("MONGO");
    }

    /**
     * Check if the database type is flat file
     *
     * @return true if the database type is flat file
     */
    public boolean isFlatFile() {
        return Artex.getInstance().getConfig().getString("storage-type").equalsIgnoreCase("FLAT_FILE");
    }
}