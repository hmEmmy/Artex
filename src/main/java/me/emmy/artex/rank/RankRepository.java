package me.emmy.artex.rank;

import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import lombok.var;
import me.emmy.artex.Artex;
import me.emmy.artex.config.ConfigHandler;
import me.emmy.artex.database.DatabaseService;
import me.emmy.artex.util.Logger;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This repository class is responsible for managing the ranks and saving them to the database or flat file based on the configuration.
 * It also provides methods to create, delete, and get ranks.
 *
 * @author Emmy
 * @project Artex
 * @date 15/08/2024 - 22:22
 */
@Getter
@Setter
public class RankRepository {
    private List<Rank> ranks = new ArrayList<>();

    public RankRepository() {
        this.loadRanks();
    }

    /**
     * Initialize the rank repository
     */
    public void loadRanks() {
        if (this.isMongo()) {
            ranks.clear();

            var rankCollection = Artex.getInstance().getDatabaseService().getDatabase().getCollection("ranks");

            var cursor = rankCollection.find();
            if (!cursor.iterator().hasNext()) {
                createDefaultRank();
                Logger.debug("No ranks found in the database. Creating default rank.");
                return;
            }

            for (var document : cursor) {
                Rank rank = documentToRank(document);
                ranks.add(rank);
            }
        } else if (this.isFlatFile()) {
            Logger.debug("Loading ranks from the YAML file.");
            ranks.clear();

            FileConfiguration config = ConfigHandler.getInstance().getConfig("ranks");

            if (!config.contains("ranks")) {
                createDefaultRank();
                Logger.debug("No ranks found in the YAML file. Creating default rank.");
                return;
            }

            for (String rankName : config.getConfigurationSection("ranks").getKeys(false)) {
                Rank rank = new Rank();
                rank.setName(rankName);
                rank.setPrefix(config.getString("ranks." + rankName + ".prefix"));
                rank.setSuffix(config.getString("ranks." + rankName + ".suffix"));
                rank.setWeight(config.getInt("ranks." + rankName + ".weight"));
                rank.setColor(ChatColor.valueOf(config.getString("ranks." + rankName + ".color")));
                rank.setBold(config.getBoolean("ranks." + rankName + ".bold"));
                rank.setItalic(config.getBoolean("ranks." + rankName + ".italic"));
                rank.setDefaultRank(config.getBoolean("ranks." + rankName + ".defaultRank"));
                rank.setPermissions(config.getStringList("ranks." + rankName + ".permissions"));
                ranks.add(rank);
            }

            Logger.debug("Loaded " + ranks.size() + " ranks from the YAML file.");
        } else {
            Logger.logError("No database type found. Please check your configuration.");
        }
    }

    /**
     * Save the ranks to the database
     */
    public void saveRanks() {
        if (this.isMongo()) {
            Logger.debug("Saving ranks to the database.");
            var rankCollection = Artex.getInstance().getDatabaseService().getDatabase().getCollection("ranks");

            Logger.debug("Deleting all ranks from the database.");
            rankCollection.deleteMany(new Document());

            for (Rank rank : ranks) {
                Logger.debug("Saving rank " + rank.getName() + " to the database.");
                Document rankDocument = rankToDocument(rank);
                rankCollection.replaceOne(new Document("name", rank.getName()), rankDocument, new ReplaceOptions().upsert(true));
            }
        } else if (this.isFlatFile()) {
            Logger.debug("Saving ranks to the YAML file.");
            FileConfiguration config = ConfigHandler.getInstance().getConfig("ranks");

            Logger.debug("Deleting all ranks from the YAML file.");
            config.set("ranks", null);

            for (Rank rank : ranks) {
                Logger.debug("Saving rank " + rank.getName() + " to the YAML file.");
                config.set("ranks." + rank.getName() + ".prefix", rank.getPrefix());
                config.set("ranks." + rank.getName() + ".suffix", rank.getSuffix());
                config.set("ranks." + rank.getName() + ".weight", rank.getWeight());
                config.set("ranks." + rank.getName() + ".color", rank.getColor().name());
                config.set("ranks." + rank.getName() + ".bold", rank.isBold());
                config.set("ranks." + rank.getName() + ".italic", rank.isItalic());
                config.set("ranks." + rank.getName() + ".defaultRank", rank.isDefaultRank());
                config.set("ranks." + rank.getName() + ".permissions", rank.getPermissions());
            }

            ConfigHandler.getInstance().saveConfig(ConfigHandler.getInstance().getConfigFile("ranks"), config);
        } else {
            Logger.logError("No database type found. Please check your configuration.");
        }
    }

    /**
     * Save a rank to the database
     *
     * @param rank the rank to save
     */
    public void saveRank(Rank rank) {
        if (this.isMongo()) {
            var rankCollection = Artex.getInstance().getDatabaseService().getDatabase().getCollection("ranks");

            Document rankDocument = rankToDocument(rank);
            rankCollection.replaceOne(new Document("name", rank.getName()), rankDocument);
        } else if (this.isFlatFile()) {
            FileConfiguration config = ConfigHandler.getInstance().getConfig("ranks");
            config.set("ranks." + rank.getName() + ".prefix", rank.getPrefix());
            config.set("ranks." + rank.getName() + ".suffix", rank.getSuffix());
            config.set("ranks." + rank.getName() + ".weight", rank.getWeight());
            config.set("ranks." + rank.getName() + ".color", rank.getColor().name());
            config.set("ranks." + rank.getName() + ".bold", rank.isBold());
            config.set("ranks." + rank.getName() + ".italic", rank.isItalic());
            config.set("ranks." + rank.getName() + ".defaultRank", rank.isDefaultRank());
            config.set("ranks." + rank.getName() + ".permissions", rank.getPermissions());

            ConfigHandler.getInstance().saveConfig(ConfigHandler.getInstance().getConfigFile("ranks"), config);
        } else {
            Logger.logError("No database type found. Please check your configuration.");
        }
    }

    /**
     * Convert a Rank object to a Document
     */
    private Document rankToDocument(Rank rank) {
        Logger.debug("Converting rank " + rank.getName() + " to document.");
        Document rankDocument = new Document();
        rankDocument.put("name", rank.getName());
        rankDocument.put("prefix", rank.getPrefix());
        rankDocument.put("suffix", rank.getSuffix());
        rankDocument.put("weight", rank.getWeight());
        rankDocument.put("color", rank.getColor().name());
        rankDocument.put("bold", rank.isBold());
        rankDocument.put("italic", rank.isItalic());
        rankDocument.put("defaultRank", rank.isDefaultRank());
        rankDocument.put("permissions", rank.getPermissions());
        return rankDocument;
    }

    /**
     * Convert a Document to a Rank object
     */
    private Rank documentToRank(Document document) {
        Logger.debug("Converting document to rank.");
        Rank rank = new Rank();
        rank.setName(document.getString("name"));
        rank.setPrefix(document.getString("prefix"));
        rank.setSuffix(document.getString("suffix"));
        rank.setWeight(document.getInteger("weight"));
        rank.setColor(ChatColor.valueOf(document.getString("color")));
        rank.setBold(document.getBoolean("bold"));
        rank.setItalic(document.getBoolean("italic"));
        rank.setDefaultRank(document.getBoolean("defaultRank"));
        rank.setPermissions((List<String>) document.get("permissions"));
        return rank;
    }

    /**
     * Create the default rank
     */
    public void createDefaultRank() {
        if (this.isMongo()) {
            for (Rank rank : ranks) {
                if (rank.isDefaultRank()) {
                    Logger.debug(rank.getName() + " has defaultRank set as true. Not creating the default rank.");
                    return;
                }
            }

            Rank rank = new Rank();
            rank.setName("Default");
            rank.setPrefix("");
            rank.setSuffix("");
            rank.setWeight(0);
            rank.setColor(ChatColor.GREEN);
            rank.setBold(false);
            rank.setItalic(false);
            rank.setDefaultRank(true);
            rank.setPermissions(Arrays.asList("example.permission", "example.permission2"));

            ranks.add(rank);
        } else if (this.isFlatFile()) {
            FileConfiguration config = ConfigHandler.getInstance().getConfig("ranks");

            for (String rankName : config.getConfigurationSection("ranks").getKeys(false)) {
                if (config.getBoolean("ranks." + rankName + ".defaultRank")) {
                    Logger.debug(rankName + " has defaultRank set as true. Not creating the default rank.");
                    return;
                }
            }

            Rank rank = new Rank();
            rank.setName("Default");
            rank.setPrefix("");
            rank.setSuffix("");
            rank.setWeight(0);
            rank.setColor(ChatColor.GREEN);
            rank.setBold(false);
            rank.setItalic(false);
            rank.setDefaultRank(true);
            rank.setPermissions(Arrays.asList("example.permission", "example.permission2"));

            ranks.add(rank);
        } else {
            Logger.logError("No database type found. Please check your configuration.");
        }

        saveRanks();
    }

    /**
     * Get a rank by name
     *
     * @param name the name of the rank
     * @return the rank
     */
    public Rank getRank(String name) {
        return ranks.stream()
                .filter(rank -> rank.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get the default rank
     *
     * @return the default rank
     */
    public Rank getDefaultRank() {
        for (Rank rank : ranks) {
            if (rank.isDefaultRank()) {
                return rank;
            }
        }

        return null;
    }

    /**
     * Creates a new rank with the specified name.
     *
     * @param name the name of the rank
     * @param doPrefix whether to add a prefix to the rank
     */
    public void createRank(String name, boolean doPrefix) {
        Rank rank = new Rank();

        rank.setName(name);

        if (doPrefix) {
            rank.setPrefix("&7[&4" + name + "&7] ");
        } else {
            rank.setPrefix("");
        }

        rank.setSuffix("");
        rank.setWeight(0);
        rank.setColor(ChatColor.GREEN);
        rank.setBold(false);
        rank.setItalic(false);
        rank.setDefaultRank(false);
        rank.setPermissions(new ArrayList<>());

        ranks.add(rank);
        saveRank(rank);
    }

    /**
     * Deletes a rank.
     *
     * @param rank the rank to delete
     */
    public void deleteRank(Rank rank) {
        if (this.isMongo()) {
            ranks.remove(rank);
            saveRanks();
        } else if (this.isFlatFile()) {
            FileConfiguration config = ConfigHandler.getInstance().getConfig("ranks");
            config.set("ranks." + rank.getName(), null);
            ConfigHandler.getInstance().saveConfig(ConfigHandler.getInstance().getConfigFile("ranks"), config);
        } else {
            Logger.logError("No database type found. Please check your configuration.");
        }
    }

    /**
     * Check if the database is MongoDB
     *
     * @return whether the database is MongoDB
     */
    private boolean isMongo() {
        return Artex.getInstance().getDatabaseService().isMongo();
    }

    /**
     * Check if the database is flat file
     *
     * @return whether the database is flat file
     */
    private boolean isFlatFile() {
        return Artex.getInstance().getDatabaseService().isFlatFile();
    }
}
