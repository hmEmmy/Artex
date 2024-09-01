package me.emmy.artex.grant.menu.grants;

import lombok.AllArgsConstructor;
import me.emmy.artex.Artex;
import me.emmy.artex.api.menu.Button;
import me.emmy.artex.api.menu.pagination.PaginatedMenu;
import me.emmy.artex.grant.Grant;
import me.emmy.artex.util.CC;
import me.emmy.artex.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Emmy
 * @project Artex
 * @date 29/08/2024 - 15:32
 */
@AllArgsConstructor
public class GrantsMenu extends PaginatedMenu {

    private final OfflinePlayer target;
    private boolean displayInactiveGrants;

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "Grants " + target.getName() + " (" + (displayInactiveGrants ? "Inactive" : "Active") + ")";
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        addGlassHeader(buttons, 15);

        buttons.put(4, new ToggleInactiveGrantsButton());

        return buttons;
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        List<Grant> grants = Artex.getInstance().getProfileRepository().getProfileWithNoAdding(target.getUniqueId()).getGrants();

        if (displayInactiveGrants) {
            grants.stream()
                    .filter(grant -> !grant.isActive())
                    .sorted(Comparator.comparingLong(Grant::getRemovedAt).reversed())
                    .forEach(grant -> buttons.put(buttons.size(), new GrantsButton(target, grant)));
        } else {
            grants.stream()
                    .filter(Grant::isActive)
                    .sorted(Comparator.comparingLong(Grant::getAddedAt).reversed())
                    .forEach(grant -> buttons.put(buttons.size(), new GrantsButton(target, grant)));
        }

        return buttons;
    }

    private class GrantsButton extends Button {

        private final OfflinePlayer target;
        private final Grant grant;

        public GrantsButton(OfflinePlayer target, Grant grant) {
            this.target = target;
            this.grant = grant;
        }

        @Override
        public ItemStack getButtonItem(Player player) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            String addedAtFormatted = dateFormat.format(new Date(grant.getAddedAt()));
            String removedAtFormatted = grant.getRemovedAt() > 0 ? dateFormat.format(new Date(grant.getRemovedAt())) : "N/A";
            String expiresAtFormatted = grant.isPermanent() ? "Never" : dateFormat.format(new Date(grant.getAddedAt() + grant.getDuration()));

            List<String> activeLore = Arrays.asList(
                    "",
                    grant.getRank().getColor() + "Grant Info",
                    "&f● Expires at: &4" + grant.getRank().getColor() + expiresAtFormatted,
                    "&f● Reason: &4" + grant.getRank().getColor() + grant.getReason(),
                    "&f● Added by: &4" + grant.getRank().getColor() + grant.getAddedBy(),
                    "&f● Added at: &4" + grant.getRank().getColor() + addedAtFormatted,
                    "&f● Added on: &4" + grant.getRank().getColor() + grant.getAddedOn(),
                    "&f● Permanent: &4" + grant.getRank().getColor() + grant.isPermanent(),
                    "",
                    "&aClick to remove this grant."
            );

            List<String> inActiveLore = Arrays.asList(
                    "",
                    grant.getRank().getColor() + "Grant Info",
                    "&f● Expires at: &4" + grant.getRank().getColor() + expiresAtFormatted,
                    "&f● Reason: &4" + grant.getRank().getColor() + grant.getReason(),
                    "&f● Added by: &4" + grant.getRank().getColor() + grant.getAddedBy(),
                    "&f● Added at: &4" + grant.getRank().getColor() + addedAtFormatted,
                    "&f● Added on: &4" + grant.getRank().getColor() + grant.getAddedOn(),
                    "&f● Permanent: &4" + grant.getRank().getColor() + grant.isPermanent(),
                    "",
                    "&c&l● Removed by: &4" + grant.getRank().getColor() + grant.getRemovedBy(),
                    "&c&l● Removed at: &4" + grant.getRank().getColor() + removedAtFormatted,
                    "",
                    "&cThis grant was already removed."
            );

            return new ItemBuilder(Material.PAPER)
                    .name(grant.getRank().getRankWithColor())
                    .lore(displayInactiveGrants ? inActiveLore : activeLore)
                    .build();
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            if (clickType != ClickType.LEFT) return;

            if (!grant.isActive()) {
                player.sendMessage(CC.translate("&cThis grant was already removed."));
                return;
            }

            if (grant.getRank().isDefaultRank()) {
                player.sendMessage(CC.translate("&cYou cannot remove the default rank."));
                return;
            }

            grant.setActive(false);
            grant.setRemovedReason("Removed by " + player.getName());
            grant.setRemovedAt(System.currentTimeMillis());
            grant.setRemovedBy(player.getName());

            player.sendMessage(CC.translate("&aSuccessfully removed the &b" + grant.getRank().getName() + " &arank from &b" + target.getName() + "&a."));

            if (target.isOnline()) {
                target.getPlayer().sendMessage(CC.translate("&aYour &b" + grant.getRank().getName() + " &arank has been removed by &b" + player.getName() + "&a."));
            }

            new GrantsMenu(target, displayInactiveGrants).openMenu(player);
        }
    }

    private class ToggleInactiveGrantsButton extends Button {

        @Override
        public ItemStack getButtonItem(Player player) {
            Material material = displayInactiveGrants ? Material.EMERALD : Material.REDSTONE;
            String status = displayInactiveGrants ? "Showing Inactive Grants" : "Showing Active Grants";

            return new ItemBuilder(material)
                    .name("&b" + status)
                    .lore(Collections.singletonList("&7Click to toggle."))
                    .build();
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            if (clickType != ClickType.LEFT) return;

            displayInactiveGrants = !displayInactiveGrants;

            new GrantsMenu(target, displayInactiveGrants).openMenu(player);
        }
    }
}
