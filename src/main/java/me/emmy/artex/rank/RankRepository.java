package me.emmy.artex.rank;

import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.var;
import me.emmy.artex.Artex;
import me.emmy.artex.config.ConfigHandler;
import me.emmy.artex.util.Logger;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Emmy
 * @project Artex
 * @date 15/08/2024 - 22:22
 */
@Getter
public class RankRepository {
    private final List<Rank> ranks = new ArrayList<>();
    private final FileConfiguration ranksConfig;

    public RankRepository() {
        this.ranksConfig = Artex.getInstance().getConfigHandler().getConfigs().get("ranks");
        this.loadRanks();
    }

    /**
     * Initialize the rank repository
     */
    public void loadRanks() {
        if (this.isMongo()) {
            this.ranks.clear();

            var rankCollection = Artex.getInstance().getDatabaseService().getDatabase().getCollection("ranks");

            var cursor = rankCollection.find();
            if (!cursor.iterator().hasNext()) {
                this.createDefaultRank();
                Logger.debug("No ranks found in the database. Creating default rank.");
                return;
            }

            for (var document : cursor) {
                Rank rank = documentToRank(document);
                this.ranks.add(rank);
            }
        } else if (this.isFlatFile()) {
            Logger.debug("Loading ranks from the YAML file.");
            this.ranks.clear();

            if (!this.ranksConfig.contains("ranks")) {
                this.createDefaultRank();
                Logger.debug("No ranks found in the YAML file. Creating default rank.");
                return;
            }

            for (String rankName : this.ranksConfig.getConfigurationSection("ranks").getKeys(false)) {
                Rank rank = new Rank();
                rank.setName(rankName);
                rank.setPrefix(this.ranksConfig.getString("ranks." + rankName + ".prefix"));
                rank.setSuffix(this.ranksConfig.getString("ranks." + rankName + ".suffix"));
                rank.setWeight(this.ranksConfig.getInt("ranks." + rankName + ".weight"));
                rank.setColor(ChatColor.valueOf(this.ranksConfig.getString("ranks." + rankName + ".color")));
                rank.setBold(this.ranksConfig.getBoolean("ranks." + rankName + ".bold"));
                rank.setItalic(this.ranksConfig.getBoolean("ranks." + rankName + ".italic"));
                rank.setDefaultRank(this.ranksConfig.getBoolean("ranks." + rankName + ".defaultRank"));
                rank.setPermissions(this.ranksConfig.getStringList("ranks." + rankName + ".permissions"));
                this.ranks.add(rank);
            }

            Logger.debug("Loaded " + this.ranks.size() + " ranks from the YAML file.");
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

            for (Rank rank : this.ranks) {
                Logger.debug("Saving rank " + rank.getName() + " to the database.");
                Document rankDocument = this.rankToDocument(rank);
                rankCollection.replaceOne(new Document("name", rank.getName()), rankDocument, new ReplaceOptions().upsert(true));
            }
        } else if (this.isFlatFile()) {
            Logger.debug("Deleting all ranks from the YAML file.");
            this.ranksConfig.set("ranks", null);

            Logger.debug("Saving ranks to the YAML file.");
            for (Rank rank : this.ranks) {
                Logger.debug("Saving rank " + rank.getName() + " to the YAML file.");
                this.ranksConfig.set("ranks." + rank.getName() + ".prefix", rank.getPrefix());
                this.ranksConfig.set("ranks." + rank.getName() + ".suffix", rank.getSuffix());
                this.ranksConfig.set("ranks." + rank.getName() + ".weight", rank.getWeight());
                this.ranksConfig.set("ranks." + rank.getName() + ".color", rank.getColor().name());
                this.ranksConfig.set("ranks." + rank.getName() + ".bold", rank.isBold());
                this.ranksConfig.set("ranks." + rank.getName() + ".italic", rank.isItalic());
                this.ranksConfig.set("ranks." + rank.getName() + ".defaultRank", rank.isDefaultRank());
                this.ranksConfig.set("ranks." + rank.getName() + ".permissions", rank.getPermissions());
            }

            ConfigHandler.getInstance().saveConfig(ConfigHandler.getInstance().getConfigFile("ranks"), this.ranksConfig);
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

            Document rankDocument = this.rankToDocument(rank);
            rankCollection.replaceOne(new Document("name", rank.getName()), rankDocument);
        } else if (this.isFlatFile()) {
            this.ranksConfig.set("ranks." + rank.getName() + ".prefix", rank.getPrefix());
            this.ranksConfig.set("ranks." + rank.getName() + ".suffix", rank.getSuffix());
            this.ranksConfig.set("ranks." + rank.getName() + ".weight", rank.getWeight());
            this.ranksConfig.set("ranks." + rank.getName() + ".color", rank.getColor().name());
            this.ranksConfig.set("ranks." + rank.getName() + ".bold", rank.isBold());
            this.ranksConfig.set("ranks." + rank.getName() + ".italic", rank.isItalic());
            this.ranksConfig.set("ranks." + rank.getName() + ".defaultRank", rank.isDefaultRank());
            this.ranksConfig.set("ranks." + rank.getName() + ".permissions", rank.getPermissions());

            ConfigHandler.getInstance().saveConfig(ConfigHandler.getInstance().getConfigFile("ranks"), this.ranksConfig);
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
    @SuppressWarnings("unchecked")
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
            for (Rank rank : this.ranks) {
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

            this.ranks.add(rank);
        } else if (this.isFlatFile()) {
            for (String rankName : this.ranksConfig.getConfigurationSection("ranks").getKeys(false)) {
                if (this.ranksConfig.getBoolean("ranks." + rankName + ".defaultRank")) {
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

            this.ranks.add(rank);
        } else {
            Logger.logError("No database type found. Please check your configuration.");
        }

        this.saveRanks();
    }

    /**
     * Get a rank by name
     *
     * @param name the name of the rank
     * @return the rank
     */
    public Rank getRank(String name) {
        return this.ranks.stream()
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
        for (Rank rank : this.ranks) {
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

        this.ranks.add(rank);
        this.saveRank(rank);
    }

    /**
     * Deletes a rank.
     *
     * @param rank the rank to delete
     */
    public void deleteRank(Rank rank) {
        if (this.isMongo()) {
            this.ranks.remove(rank);
            this.saveRanks();
        } else if (this.isFlatFile()) {
            this.ranksConfig.set("ranks." + rank.getName(), null);
            ConfigHandler.getInstance().saveConfig(ConfigHandler.getInstance().getConfigFile("ranks"), this.ranksConfig);
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
