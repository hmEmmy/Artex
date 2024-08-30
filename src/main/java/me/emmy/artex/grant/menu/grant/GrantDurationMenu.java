package me.emmy.artex.grant.menu.grant;

import lombok.AllArgsConstructor;
import me.emmy.artex.api.menu.Button;
import me.emmy.artex.api.menu.Menu;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.util.CC;
import me.emmy.artex.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Emmy
 * @project Artex
 * @date 29/08/2024 - 09:36
 */
@AllArgsConstructor
public class GrantDurationMenu extends Menu {

    private OfflinePlayer target;
    private Rank rank;
    private String reason;


    @Override
    public String getTitle(Player player) {
        return "Select a duration";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(21, new GrantSelectDurationButton(target, rank, reason));
        buttons.put(23, new GrantPermanentButton(target, rank, reason));

        addBorder(buttons, (byte) 15, 5);

        return buttons;
    }

    @Override
    public int getSize() {
        return 5 * 9;
    }

    @AllArgsConstructor
    private static class GrantPermanentButton extends Button {

        private OfflinePlayer target;
        private Rank rank;
        private String reason;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.WATCH)
                    .name("&3&lPermanent")
                    .lore("", CC.translate("&bClick to set the &3duration &bto &3permanent&3."), "")
                    .build();
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            if (clickType != ClickType.LEFT) return;

            new GrantConfirmMenu(target, rank, reason, 0, true).openMenu(player);
        }
    }

    @AllArgsConstructor
    private static class GrantSelectDurationButton extends Button {

        private final OfflinePlayer target;
        private final Rank rank;
        private final String reason;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.ITEM_FRAME)
                    .name("&3&l30 Days")
                    .lore("", "&bClick to set the &3duration&b.", "")
                    .build();
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            if (clickType != ClickType.LEFT) return;

            long durationInMillis = 30L * 24 * 60 * 60 * 1000;

            new GrantConfirmMenu(target, rank, reason, durationInMillis, false).openMenu(player);
        }
    }
}