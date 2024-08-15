package me.emmy.artex.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.emmy.artex.Artex;
import me.emmy.artex.util.CC;
import org.bukkit.Bukkit;

/**
 * @author Emmy
 * @project Artex
 * @date 15/08/2024 - 21:33
 */
@Getter
public class DatabaseHandler {

    private MongoDatabase database;
    private MongoCollection collection;

    /**
     * Create a connection to the database
     */
    public void createConnection() {
        String host = Artex.getInstance().getConfig().getString("mongo.host");
        String databaseName = Artex.getInstance().getConfig().getString("mongo.database");
        int port = Artex.getInstance().getConfig().getInt("mongo.port");

        try {
            database = new MongoClient(host, port).getDatabase(databaseName);
            Bukkit.getConsoleSender().sendMessage(CC.translate("&aSuccessfully connected to the database!"));
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&cFailed to connect to the database!"));
            Bukkit.shutdown();
        }

        collection = database.getCollection("profiles");
    }
}
