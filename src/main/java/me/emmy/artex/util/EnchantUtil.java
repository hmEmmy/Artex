package me.emmy.artex.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Emmy
 * @project Alley
 * @date 28/05/2024 - 20:53
 */
public class EnchantUtil {
    private static final Map<String, String> enchantment = new HashMap<>();

    static {
        enchantment.put("sharpness", "DAMAGE_ALL");
        enchantment.put("efficiency", "DIG_SPEED");
        enchantment.put("unbreaking", "DURABILITY");
        enchantment.put("fortune", "LOOT_BONUS_BLOCKS");
        enchantment.put("power", "ARROW_DAMAGE");
        enchantment.put("punch", "ARROW_KNOCKBACK");
        enchantment.put("flame", "ARROW_FIRE");
        enchantment.put("infinity", "ARROW_INFINITE");
    }

    /**
     * Get the enchantment name by the key
     *
     * @param key the key
     * @return the enchantment name
     */
    public static String getEnchantment(String key) {
        return enchantment.get(key.toLowerCase());
    }
}