package me.emmy.artex.tag.menu;

import lombok.AllArgsConstructor;
import me.emmy.artex.Artex;
import me.emmy.artex.api.menu.Button;
import me.emmy.artex.api.menu.pagination.PaginatedMenu;
import me.emmy.artex.profile.Profile;
import me.emmy.artex.tag.Tag;
import me.emmy.artex.util.CC;
import me.emmy.artex.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Emmy
 * @project Artex
 * @date 01/09/2024 - 20:14
 */
@AllArgsConstructor
public class TagMenu extends PaginatedMenu {

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "Select a tag";
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        addGlassHeader(buttons, 15);

        buttons.put(4, new RemoveCurrentTagButton());

        return buttons;
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        int slot = 0;

        for (Tag tag : Artex.getInstance().getTagRepository().getTags().values()) {
            buttons.put(slot++, new TagButton(tag));
        }

        return buttons;
    }

    @AllArgsConstructor
    private static class TagButton extends Button {

        private Tag tag;

        @Override
        public ItemStack getButtonItem(Player player) {
            String displayName = tag.getDisplayName();

            if (tag.isBold()) {
                displayName = ChatColor.BOLD + displayName;
            }

            if (tag.isItalic()) {
                displayName = ChatColor.ITALIC + displayName;
            }

            String coloredName = tag.getColor() + displayName;

            List<String> lore = Arrays.asList(
                    "",
                    tag.getColor() + "Tag Info:",
                    " &f● Name: &4" + tag.getColor() + tag.getName(),
                    " &f● Display Name: &4" + coloredName,
                    " &f● Color: &4" + tag.getColor() + tag.getColor().name(),
                    " &f● Bold: &4" + tag.getColor() + tag.isBold(),
                    " &f● Italic: &4" + tag.getColor() + tag.isItalic(),
                    "",
                    "&aClick to select this tag."
            );

            return new ItemBuilder(tag.getIcon())
                    .name(coloredName)
                    .lore(lore)
                    .durability(tag.getDurability())
                    .build();
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            if (clickType != ClickType.LEFT) return;

            if (!player.hasPermission("artex.tag." + tag.getName())) {
                player.sendMessage(CC.translate("&cYou do not have permission to select this tag."));
                return;
            }

            Profile profile = Artex.getInstance().getProfileRepository().getProfileWithNoAdding(player.getUniqueId());

            if (profile.getTag() != null && profile.getTag().equals(tag)) {
                player.sendMessage(CC.translate("&cYou already have this tag selected."));
                return;
            }

            profile.setTag(tag.getName());
            player.sendMessage(CC.translate("&aYou have selected the &f" + tag.getDisplayName() + "&a tag."));
            player.closeInventory();
        }
    }

    @AllArgsConstructor
    private static class RemoveCurrentTagButton extends Button {
        @Override
        public ItemStack getButtonItem(Player player) {
            Profile profile = Artex.getInstance().getProfileRepository().getProfileWithNoAdding(player.getUniqueId());
            String tag = profile.getTag() == null ? "None" : profile.getTag().getName();

            List<String> lore = Arrays.asList(
                    "",
                    "&fCurrent Tag: &c" + tag,
                    "",
                    profile.getTag() == null ? "&cYou do not have a tag selected." : "&aClick to remove your current tag."
            );

            return new ItemBuilder(profile.getTag() == null ? Material.BARRIER : profile.getTag().getIcon())
                    .name(profile.getTag() == null ? "&cNone selected." : profile.getTag().getNiceName())
                    .lore(lore)
                    .durability(profile.getTag() == null ? 0 : profile.getTag().getDurability())
                    .build();
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            if (clickType != ClickType.LEFT) return;
            Profile profile = Artex.getInstance().getProfileRepository().getProfileWithNoAdding(player.getUniqueId());

            if (profile.getTag() == null) {
                player.sendMessage(CC.translate("&cYou do not have a tag selected."));
                return;
            }

            profile.setTag(null);
            player.sendMessage(CC.translate("&cYou have removed your current tag."));
            player.closeInventory();
        }
    }
}
