package me.emmy.artex.tag;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * @author Emmy
 * @project Artex
 * @date 30/08/2024 - 20:27
 */
@Getter
@Setter
public class Tag {

    private String name;
    private String displayName;

    private Material icon;

    private ChatColor color;

    private int durability;

    private boolean bold;
    private boolean italic;

    /**
     * Constructor for the Tag class.
     *
     * @param name the name of the tag
     * @param displayName the display name of the tag
     * @param icon the material of the tag icon
     * @param color the color of the tag
     * @param durability the data/durability of the tag icon
     */
    public Tag(String name, String displayName, Material icon, ChatColor color, int durability, boolean bold, boolean italic) {
        this.name = name;
        this.displayName = displayName;
        this.icon = icon;
        this.color = color;
        this.durability = durability;
        this.bold = bold;
        this.italic = italic;
    }

    public ChatColor getColorOrDefault() {
        return color == null ? ChatColor.WHITE : color;
    }

    public String getTagNameOrNull() {
        return name == null ? "" : name;
    }
}
