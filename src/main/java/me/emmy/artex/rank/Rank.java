package me.emmy.artex.rank;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.util.List;

/**
 * @author Emmy
 * @project Artex
 * @date 15/08/2024 - 22:19
 */
@Getter
@Setter
public class Rank {
    private String name;
    private String prefix;
    private String suffix;

    private int weight;

    private ChatColor color;

    private boolean bold;
    private boolean italic;
    private boolean defaultRank;

    private List<String> permissions;

    /**
     * Get color and name of the rank.
     *
     * @return the rank color and the rank name
     */
    public String getRankWithColor() {
        return color + name;
    }
}
