package me.emmy.artex.instance.menu.button;

import lombok.AllArgsConstructor;
import me.emmy.artex.Artex;
import me.emmy.artex.api.menu.Button;
import me.emmy.artex.instance.menu.ConfirmMenu;
import me.emmy.artex.profile.Profile;
import me.emmy.artex.util.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author Emmy
 * @project Artex
 * @date 30/08/2024 - 21:03
 */
@AllArgsConstructor
public class ShutDownButton extends Button {

    private String name;
    private ItemStack itemStack;
    private int durability;
    private List<String> lore;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(itemStack)
                .name(name)
                .lore(lore)
                .durability(durability)
                .build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if (clickType != ClickType.LEFT) return;

        if (player.isOp()) {
            new ConfirmMenu().openMenu(player);
            return;
        }

        player.closeInventory();
        player.sendMessage("&cYou do not have permission to do this.");
    }
}
