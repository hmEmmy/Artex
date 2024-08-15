package me.emmy.artex.rank;

import lombok.Getter;
import lombok.Setter;
import lombok.var;
import me.emmy.artex.Artex;
import me.emmy.artex.util.CC;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Emmy
 * @project Artex
 * @date 15/08/2024 - 22:22
 */
@Getter
@Setter
public class RankRepository {

    private Map<String, Rank> ranks = new HashMap<>();

    /**
     * Initialize the rank repository
     */
    public void loadRanks() {
        ranks.clear();
        var rankCollection = Artex.getInstance().getDatabaseHandler().getDatabase().getCollection("ranks");

        var cursor = rankCollection.find();
        if (!cursor.iterator().hasNext()) {
            createDefaultRank();
            Bukkit.getConsoleSender().sendMessage(CC.translate(CC.PREFIX + "&cNo ranks found! Default rank created."));
            return;
        }

        for (var document : cursor) {
            Rank rank = documentToRank(document);
            ranks.put(rank.getName(), rank);
        }
    }

    /**
     * Load the ranks from the database
     */
    public void saveRanks() {
        var rankCollection = Artex.getInstance().getDatabaseHandler().getDatabase().getCollection("ranks");

        rankCollection.deleteMany(new Document());

        for (Rank rank : ranks.values()) {
            Document rankDocument = rankToDocument(rank);
            rankCollection.insertOne(rankDocument);
        }
    }

    /**
     * Convert a Rank object to a Document
     */
    private Document rankToDocument(Rank rank) {
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
    private void createDefaultRank() {
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
}
