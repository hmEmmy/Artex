package me.emmy.artex.api.menu.button;

import lombok.AllArgsConstructor;
import me.emmy.artex.api.menu.Button;
import me.emmy.artex.util.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author Emmy
 * @project Tulip
 * @date 15/06/2024 - 23:57
 */
@AllArgsConstructor
public class CommandButton extends Button {

    private String command;
    private ItemStack itemStack;
    private List<String> lore;


    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(itemStack)
                .lore(lore)
                .build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if (clickType != ClickType.LEFT) {
            return;
        }

        player.performCommand(command);
    }
}
