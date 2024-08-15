package me.emmy.artex.rank;

import lombok.Getter;
import lombok.Setter;
import me.emmy.artex.Artex;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
    public void saveRanks() {
        Document document = Artex.getInstance().getDatabaseHandler().getDatabase().getCollection("ranks").find().first();

        for (String key : document.keySet()) {
            Document rankDocument = (Document) document.get(key);
            ChatColor color = ChatColor.valueOf(rankDocument.getString("color"));

            Rank rank = new Rank();
            rank.setName(rankDocument.getString("name"));
            rank.setPrefix(rankDocument.getString("prefix"));
            rank.setSuffix(rankDocument.getString("suffix"));
            rank.setWeight(rankDocument.getInteger("weight"));
            rank.setColor(color);
            rank.setBold(rankDocument.getBoolean("bold"));
            rank.setItalic(rankDocument.getBoolean("italic"));
            rank.setDefaultRank(rankDocument.getBoolean("defaultRank"));
            rank.setPermissions((List<String>) rankDocument.get("permissions"));

            ranks.put(rank.getName(), rank);
        }
    }

    /**
     * Load the ranks from the database
     */
    public void loadRanks() {
        Document document = new Document();

        for (Map.Entry<String, Rank> entry : ranks.entrySet()) {
            Rank rank = entry.getValue();
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

            document.put(rank.getName(), rankDocument);
        }

        Artex.getInstance().getDatabaseHandler().getDatabase().getCollection("ranks").insertOne(document);
    }

    /**
     * Create the default rank
     */
    public void createDefaultRank() {
        for (Rank rank : ranks.values()) {
            if (rank.isDefaultRank()) {
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
