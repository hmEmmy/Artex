package me.emmy.artex.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.emmy.artex.Artex;
import me.emmy.artex.util.CC;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

/**
 * @author Emmy
 * @project Artex
 * @date 25/08/2024 - 21:05
 */
@Getter
public class DatabaseService {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> profilesCollection;

    public DatabaseService() {
        loadMongo();
    }

    /**
     * Load MongoDB settings from the config and initialize the connection.
     */
    public void loadMongo() {
        try {
            FileConfiguration config = Artex.getInstance().getConfig();
            String host = config.getString("mongo.host");
            int port = config.getInt("mongo.port");
            String databaseName = Objects.requireNonNull(config.getString("mongo.database"));

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString("mongodb://" + host + ":" + port))
                    .build();

            mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase(databaseName);
            profilesCollection = database.getCollection("profiles");
            Bukkit.getConsoleSender().sendMessage(CC.translate("&aConnected to MongoDB!"));
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&cFailed to connect to MongoDB: &f" + e.getMessage()));
        }
    }

    /**
     * Close the MongoDB connection.
     */
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}