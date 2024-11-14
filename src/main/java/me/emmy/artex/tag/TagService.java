package me.emmy.artex.tag;

import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.var;
import me.emmy.artex.Artex;
import me.emmy.artex.config.ConfigHandler;
import me.emmy.artex.tag.utility.TagUtility;
import me.emmy.artex.util.Logger;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmy
 * @project Artex
 * @date 30/08/2024 - 20:27
 */
@Getter
public class TagService {
    private final List<Tag> tags;
    private final FileConfiguration tagsConfig;

    /**
     * Constructor for the TagRepository class.
     *
     * @param configHandler the config handler
     */
    public TagService(ConfigHandler configHandler) {
        this.tags = new ArrayList<>();
        this.tagsConfig = configHandler.getConfig("tags");
        this.loadTags();
    }

    /**
     * Load the tags based on the database type.
     */
    public void loadTags() {
        if (this.isMongo()) {
            tags.clear();

            var tagCollection = Artex.getInstance().getDatabaseService().getDatabase().getCollection("tags");

            var cursor = tagCollection.find();
            if (!cursor.iterator().hasNext()) {
                Logger.debug("No tags found in the database, creating default tags.");
                TagUtility.createDefaultTags();
                return;
            }

            for (var document : cursor) {
                Tag tag = this.documentToTag(document);
                this.tags.add(tag);
            }
        } else if (this.isFlatFile()) {
            if (!this.tags.isEmpty()) {
                this.tags.clear();
            }

            if (this.tagsConfig.getConfigurationSection("tags") == null || this.tagsConfig.getConfigurationSection("tags").getKeys(false).isEmpty()) {
                Logger.debug("No tags found in the flat file.");
                return;
            }

            this.tagsConfig.getConfigurationSection("tags").getKeys(false).forEach(tagName -> {
                String displayName = tagsConfig.getString("tags." + tagName + ".displayName");
                Material icon = Material.valueOf(tagsConfig.getString("tags." + tagName + ".icon"));
                ChatColor color = ChatColor.valueOf(tagsConfig.getString("tags." + tagName + ".color"));
                int durability = tagsConfig.getInt("tags." + tagName + ".durability");
                boolean bold = tagsConfig.getBoolean("tags." + tagName + ".bold");
                boolean italic = tagsConfig.getBoolean("tags." + tagName + ".italic");

                Tag tag = new Tag(tagName, displayName, icon, color, durability, bold, italic);
                this.tags.add(tag);
            });

            Logger.debug("Loaded " + this.tags.size() + " tags from the YAML file.");
        } else {
            Logger.logError("No database type found.");
        }
    }

    /**
     * Save the tags to the database
     */
    public void saveTags() {
        if (this.isMongo()) {
            Logger.debug("Saving tags to the database.");
            var tagCollection = Artex.getInstance().getDatabaseService().getDatabase().getCollection("tags");

            Logger.debug("Deleting all tags from the database.");
            tagCollection.deleteMany(new Document());

            for (Tag tag : this.tags) {
                Logger.debug("Saving tag " + tag.getName() + " to the database.");
                Document rankDocument = this.tagToDocument(tag);
                tagCollection.replaceOne(new Document("name", tag.getName()), rankDocument, new ReplaceOptions().upsert(true));
            }
        } else if (this.isFlatFile()) {
            Logger.debug("Saving tags to the flat file.");

            if (this.tagsConfig.contains("tags")) {
                this.tagsConfig.set("tags", null);
            }

            for (Tag tag : this.tags) {
                Logger.debug("Saving tag " + tag.getName() + " to the flat file.");
                this.tagsConfig.set("tags." + tag.getName() + ".displayName", tag.getDisplayName());
                this.tagsConfig.set("tags." + tag.getName() + ".icon", tag.getIcon().name());
                this.tagsConfig.set("tags." + tag.getName() + ".color", tag.getColor().name());
                this.tagsConfig.set("tags." + tag.getName() + ".durability", tag.getDurability());
                this.tagsConfig.set("tags." + tag.getName() + ".bold", tag.isBold());
                this.tagsConfig.set("tags." + tag.getName() + ".italic", tag.isItalic());
            }

            Artex.getInstance().getConfigHandler().saveConfig(Artex.getInstance().getConfigHandler().getConfigFile("tags"), this.tagsConfig);
        } else {
            Logger.logError("No database type found.");
        }
    }

    /**
     * Save a tag to the database
     *
     * @param tag the tag to save
     */
    public void saveTag(Tag tag) {
        if (this.isMongo()) {
            var tagCollection = Artex.getInstance().getDatabaseService().getDatabase().getCollection("tags");

            Document tagDocument = this.tagToDocument(tag);
            tagCollection.replaceOne(new Document("name", tag.getName()), tagDocument);
        } else if (this.isFlatFile()) {
            this.tagsConfig.set("tags." + tag.getName() + ".displayName", tag.getDisplayName());
            this.tagsConfig.set("tags." + tag.getName() + ".icon", tag.getIcon().name());
            this.tagsConfig.set("tags." + tag.getName() + ".color", tag.getColor().name());
            this.tagsConfig.set("tags." + tag.getName() + ".durability", tag.getDurability());
            this.tagsConfig.set("tags." + tag.getName() + ".bold", tag.isBold());
            this.tagsConfig.set("tags." + tag.getName() + ".italic", tag.isItalic());

            Artex.getInstance().getConfigHandler().saveConfig(Artex.getInstance().getConfigHandler().getConfigFile("tags"), this.tagsConfig);
        } else {
            Logger.logError("No database type found.");
        }
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
        return this.tags.stream()
                .filter(tag -> tag.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void createTag(String name, String displayName, Material icon, ChatColor color, int durability, boolean bold, boolean italic) {
        Tag tag = new Tag(name, displayName, icon, color, durability, bold, italic);
        this.tags.add(tag);
        this.saveTag(tag);
    }

    /**
     * Delete a tag
     *
     * @param tag the tag to delete
     */
    public void deleteTag(Tag tag) {
        this.tags.remove(tag);
        this.saveTags();
    }

    /**
     * Check if the database is mongo
     *
     * @return if the database is mongo
     */
    private boolean isMongo() {
        return Artex.getInstance().getDatabaseService().isMongo();
    }

    /**
     * Check if the database is flat file
     *
     * @return if the database is flat file
     */
    private boolean isFlatFile() {
        return Artex.getInstance().getDatabaseService().isFlatFile();
    }
}