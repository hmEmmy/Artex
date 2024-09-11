package me.emmy.artex.rank;

import me.emmy.artex.Artex;
import me.emmy.artex.util.CC;
import me.emmy.artex.util.Logger;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import lombok.Getter;
import lombok.Setter;
import lombok.var;

import java.util.*;

/**
 * @author Emmy
 * @project Artex
 * @date 15/08/2024 - 22:22
 */
@Getter
@Setter
public class RankRepository {

    private Map<String, Rank> ranks = new HashMap<>();

    public RankRepository() {
        loadRanks();
    }

    /**
     * Initialize the rank repository
     */
    public void loadRanks() {
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
            ranks.put(rank.getName(), rank);
        }
    }

    /**
     * Save the ranks to the database
     */
    public void saveRanks() {
        Logger.debug("Saving ranks to the database.");
        var rankCollection = Artex.getInstance().getDatabaseService().getDatabase().getCollection("ranks");

        Logger.debug("Deleting all ranks from the database.");
        rankCollection.deleteMany(new Document());

        for (Rank rank : ranks.values()) {
            Logger.debug("Saving rank " + rank.getName() + " to the database.");
            Document rankDocument = rankToDocument(rank);
            rankCollection.insertOne(rankDocument);
        }
    }

    /**
     * Save a rank to the database
     *
     * @param rank the rank to save
     */
    public void saveRank(Rank rank) {
        var rankCollection = Artex.getInstance().getDatabaseService().getDatabase().getCollection("ranks");

        Document rankDocument = rankToDocument(rank);
        rankCollection.replaceOne(new Document("name", rank.getName()), rankDocument);
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
        for (Rank rank : ranks.values()) {
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

        ranks.put(rank.getName(), rank);

        saveRanks();
    }

    /**
     * Get a rank by name
     *
     * @param name the name of the rank
     * @return the rank
     */
    public Rank getRank(String name) {
        return ranks.get(name);
    }

    /**
     * Get the default rank
     *
     * @return the default rank
     */
    public Rank getDefaultRank() {
        for (Rank rank : ranks.values()) {
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

        ranks.put(rank.getName(), rank);
        saveRank(rank);
    }

    /**
     * Deletes a rank.
     *
     * @param rank the rank to delete
     */
    public void deleteRank(Rank rank) {
        ranks.remove(rank.getName());
        saveRanks();
    }
}
