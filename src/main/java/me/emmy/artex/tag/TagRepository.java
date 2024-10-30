package me.emmy.artex.tag;

import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.var;
import me.emmy.artex.Artex;
import me.emmy.artex.tag.utility.TagUtility;
import me.emmy.artex.util.Logger;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmy
 * @project Artex
 * @date 30/08/2024 - 20:27
 */
@Getter
public class TagRepository {
    private final List<Tag> tags = new ArrayList<>();

    /**
     * Automatically load the tags
     */
    public TagRepository() {
        this.loadTags();
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
            TagUtility.createDefaultTags();
            return;
        }

        for (var document : cursor) {
            Tag tag = documentToTag(document);
            tags.add(tag);
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

        for (Tag tag : tags) {
            Logger.debug("Saving tag " + tag.getName() + " to the database.");
            Document rankDocument = tagToDocument(tag);
            tagCollection.replaceOne(new Document("name", tag.getName()), rankDocument, new ReplaceOptions().upsert(true));
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
     * Get a tag by its name
     *
     * @param name the name of the tag
     * @return the tag
     */
    public Tag getTag(String name) {
        return tags.stream()
                .filter(tag -> tag.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void createTag(String name, String displayName, Material icon, ChatColor color, int durability, boolean bold, boolean italic) {
        Tag tag = new Tag(name, displayName, icon, color, durability, bold, italic);
        tags.add(tag);
        saveTag(tag);
    }

    /**
     * Delete a tag
     *
     * @param tag the tag to delete
     */
    public void deleteTag(Tag tag) {
        tags.remove(tag);
        saveTags();
    }
}