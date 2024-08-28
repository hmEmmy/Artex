package me.emmy.artex.api.menu.button;

import me.emmy.artex.api.menu.Button;
import me.emmy.artex.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PageGlassButton extends Button {

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(Material.STAINED_GLASS_PANE)
                .durability(15)
                .build();
    }
}
