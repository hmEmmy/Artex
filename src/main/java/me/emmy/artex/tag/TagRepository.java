package me.emmy.artex.tag;

import lombok.Getter;
import lombok.var;
import me.emmy.artex.Artex;
import me.emmy.artex.util.Logger;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Emmy
 * @project Artex
 * @date 30/08/2024 - 20:27
 */
@Getter
public class TagRepository {

    private Map<String, Tag> tags = new HashMap<>();

    /**
     * Automatically load the tags
     */
    public TagRepository() {
        loadTags();
    }

    /**
     * Load the tags from the database
     */
    public void loadTags() {
        tags.clear();

        var tagCollection = Artex.getInstance().getDatabaseService().getDatabase().getCollection("tags");

        var cursor = tagCollection.find();
        if (!cursor.iterator().hasNext()) {
            Logger.debug("No tags found in the database, creating default tags.");
            createDefaultTags();
            return;
        }

        for (var document : cursor) {
            Tag tag = documentToTag(document);
            tags.put(tag.getName(), tag);
        }
    }

    /**
     * Save the tags to the database
     */
    public void saveTags() {
        Logger.debug("Saving tags to the database.");
        var tagCollection = Artex.getInstance().getDatabaseService().getDatabase().getCollection("tags");

        Logger.debug("Deleting all tags from the database.");
        tagCollection.deleteMany(new Document());

        for (Tag tag : tags.values()) {
            Logger.debug("Saving tag " + tag.getName() + " to the database.");
            Document rankDocument = tagToDocument(tag);
            tagCollection.insertOne(rankDocument);
        }
    }

    /**
     * Save a tag to the database
     *
     * @param tag the tag to save
     */
    public void saveTag(Tag tag) {
        var tagCollection = Artex.getInstance().getDatabaseService().getDatabase().getCollection("tags");

        Document tagDocument = tagToDocument(tag);
        tagCollection.replaceOne(new Document("name", tag.getName()), tagDocument);
    }

    /**
     * Convert a Tag object to a Document
     *
     * @param tag the tag to convert
     * @return the converted document
     */
    public Document tagToDocument(Tag tag) {
        Logger.debug("Converting tag " + tag.getName() + " to a document.");
        return new Document("name", tag.getName())
                .append("displayName", tag.getDisplayName())
                .append("icon", tag.getIcon().name())
                .append("color", tag.getColor().name())
                .append("durability", tag.getDurability())
                .append("bold", tag.isBold())
                .append("italic", tag.isItalic());
    }

    /**
     * Convert a Document to a Tag object
     *
     * @param document the document to convert
     * @return the converted tag
     */
    private Tag documentToTag(Document document) {
        Logger.debug("Converting document to tag.");
        return new Tag(
                document.getString("name"),
                document.getString("displayName"),
                Material.valueOf(document.getString("icon")),
                ChatColor.valueOf(document.getString("color")),
                document.getInteger("durability"),
                document.getBoolean("bold"),
                document.getBoolean("italic")
        );
    }

    /**
     * Create the default tag
     */
    private void createDefaultTags() {
        Tag heart = new Tag("Heart", "❤", Material.NAME_TAG, ChatColor.RED, 0, false, false);
        Tag BlackHeart = new Tag("BlackHeart", "🖤", Material.NAME_TAG, ChatColor.BLACK, 0, false, false);
        Tag diamond = new Tag("Diamond", "♦", Material.NAME_TAG, ChatColor.AQUA, 0, false, false);
        Tag star = new Tag("Star", "★", Material.NAME_TAG, ChatColor.YELLOW, 0, false, false);
        Tag BestWW = new Tag("BestWW", "BestWW", Material.NAME_TAG, ChatColor.DARK_RED, 0, true, false);
        Tag Crown = new Tag("Crown", "♛", Material.NAME_TAG, ChatColor.GOLD, 0, false, false);
        Tag King = new Tag("King", "King ♚", Material.NAME_TAG, ChatColor.RED, 0, false, false);
        Tag Queen = new Tag("Queen", "Queen ♛", Material.NAME_TAG, ChatColor.LIGHT_PURPLE, 0, false, false);
        Tag tick = new Tag("Tick", "✔", Material.NAME_TAG, ChatColor.GREEN, 0, false, false);

        tags.put(heart.getName(), heart);
        tags.put(diamond.getName(), diamond);
        tags.put(star.getName(), star);
        tags.put(BlackHeart.getName(), BlackHeart);
        tags.put(BestWW.getName(), BestWW);
        tags.put(Crown.getName(), Crown);
        tags.put(King.getName(), King);
        tags.put(Queen.getName(), Queen);
        tags.put(tick.getName(), tick);

        saveTags();
    }

    /**
     * Get a tag by its name
     *
     * @param name the name of the tag
     * @return the tag
     */
    public Tag getTag(String name) {
        return tags.get(name);
    }

    /**
     * Get a tag by the tag object
     *
     * @param tag the tag
     * @return the tag
     */
    public Tag getTag(Tag tag) {
        return tags.get(tag.getName());
    }
}
