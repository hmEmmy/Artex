package me.emmy.artex.tag.utility;

import lombok.experimental.UtilityClass;
import me.emmy.artex.Artex;
import me.emmy.artex.tag.Tag;
import me.emmy.artex.tag.TagRepository;
import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * @author Emmy
 * @project Artex
 * @date 23/10/2024 - 12:11
 */
@UtilityClass
public class TagUtility {
    /**
     * Create the default tag
     */
    public void createDefaultTags() {
        TagRepository tagRepository = Artex.getInstance().getTagRepository();
        
        Tag Heart = new Tag("Heart", "❤", Material.NAME_TAG, ChatColor.RED, 0, false, false);
        Tag BlackHeart = new Tag("BlackHeart", "❤", Material.NAME_TAG, ChatColor.BLACK, 0, true, false);
        Tag Diamond = new Tag("Diamond", "&7[&b&l♦&7]", Material.NAME_TAG, ChatColor.AQUA, 0, false, false);
        Tag Star = new Tag("Star", "★", Material.NAME_TAG, ChatColor.YELLOW, 0, true, false);
        Tag BestWW = new Tag("BestWW", "BestWW", Material.NAME_TAG, ChatColor.DARK_RED, 0, true, false);
        Tag Crown = new Tag("Crown", "&7[&6&l♛&7]", Material.NAME_TAG, ChatColor.GOLD, 0, false, false);
        Tag King = new Tag("King", "King ♚", Material.NAME_TAG, ChatColor.RED, 0, true, false);
        Tag Queen = new Tag("Queen", "Queen ♛", Material.NAME_TAG, ChatColor.LIGHT_PURPLE, 0, true, false);
        Tag Tick = new Tag("Tick", "✔", Material.NAME_TAG, ChatColor.GREEN, 0, false, false);
        Tag Flower = new Tag("Flower", "&7[&d&l❖&7]", Material.NAME_TAG, ChatColor.LIGHT_PURPLE, 0, false, false);
        Tag Cross = new Tag("Cross", "✖", Material.NAME_TAG, ChatColor.RED, 0, true, false);
        Tag Blood = new Tag("Blood", "BLOOD", Material.EMERALD, ChatColor.RED, 0, true, false);
        Tag Goat = new Tag("Goat", "GOAT", Material.EMERALD, ChatColor.AQUA, 0, true, false);
        Tag Banana = new Tag("Banana", "Banana", Material.EMERALD, ChatColor.YELLOW, 0, true, true);
        Tag Love = new Tag("Love", "Love", Material.EMERALD, ChatColor.RED, 0, true, false);
        Tag Yurrrrrrr = new Tag("Yurrrrrrr", "yurrrrrrr", Material.EMERALD, ChatColor.GREEN, 0, false, false);
        Tag Legend = new Tag("Legend", "&r&k|&r&9Legend&r&k|&r", Material.EMERALD, ChatColor.GOLD, 0, false, false);
        Tag First = new Tag("#1", "#1", Material.EMERALD, ChatColor.RED, 0, true, false);
        Tag Godly = new Tag("Godly", "Godly", Material.EMERALD, ChatColor.DARK_RED, 0, true, false);
        Tag Prince = new Tag("Prince", "Prince", Material.RED_ROSE, ChatColor.LIGHT_PURPLE, 1, false, false);
        Tag Princess = new Tag("Princess", "Princess", Material.RED_ROSE, ChatColor.LIGHT_PURPLE, 7, false, false);

        tagRepository.getTags().add(Heart);
        tagRepository.getTags().add(BlackHeart);
        tagRepository.getTags().add(Diamond);
        tagRepository.getTags().add(Star);
        tagRepository.getTags().add(BestWW);
        tagRepository.getTags().add(Crown);
        tagRepository.getTags().add(King);
        tagRepository.getTags().add(Queen);
        tagRepository.getTags().add(Tick);
        tagRepository.getTags().add(Flower);
        tagRepository.getTags().add(Cross);
        tagRepository.getTags().add(Blood);
        tagRepository.getTags().add(Goat);
        tagRepository.getTags().add(Banana);
        tagRepository.getTags().add(Love);
        tagRepository.getTags().add(Yurrrrrrr);
        tagRepository.getTags().add(Legend);
        tagRepository.getTags().add(First);
        tagRepository.getTags().add(Godly);
        tagRepository.getTags().add(Prince);
        tagRepository.getTags().add(Princess);

        tagRepository.saveTags();
    }
}