package me.emmy.artex.grant.menu.grant;

import lombok.AllArgsConstructor;
import me.emmy.artex.Artex;
import me.emmy.artex.api.menu.Button;
import me.emmy.artex.api.menu.pagination.PaginatedMenu;
import me.emmy.artex.profile.Profile;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.util.CC;
import me.emmy.artex.util.ItemBuilder;
import me.emmy.artex.util.ProjectInfo;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Emmy
 * @project Artex
 * @date 29/08/2024 - 09:26
 */
@AllArgsConstructor
public class GrantMenu extends PaginatedMenu {

    private OfflinePlayer target;
    private String reason;

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "Grant " + target.getName();
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        addGlassHeader(buttons, 15);

        return buttons;
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        List<Rank> sortedRanks = Artex.getInstance().getRankRepository().getRanks().values().stream()
                .sorted(Comparator.comparingInt(Rank::getWeight).reversed())
                .collect(Collectors.toList());

        for (int i = 0; i < sortedRanks.size(); i++) {
            Rank rank = sortedRanks.get(i);
            buttons.put(i, new GrantButton(target, rank, reason));
        }

        return buttons;
    }

    @Override
    public int getSize() {
        return 9 * 2;
    }

    @AllArgsConstructor
    private static class GrantButton extends Button {

        private OfflinePlayer target;
        private Rank rank;
        private String reason;

        @Override
        public ItemStack getButtonItem(Player player) {
            List<String> lore = Arrays.asList(
                    "",
                    rank.getColor() + "Rank info",
                    " &f● Name: &4" + rank.getRankWithColor(),
                    " &f● Weight: &4" + rank.getColor() + rank.getWeight(),
                    " &f● Prefix: &4" + rank.getPrefix(),
                    " &f● Suffix: &4" + rank.getSuffix(),
                    " &f● Bold: &4" + rank.getColor() + rank.isBold(),
                    " &f● Italic: &4" + rank.getColor() + rank.isItalic(),
                    " &f● Color: &4" + rank.getColor() + rank.getColor().name(),
                    "",
                    rank.isDefaultRank() ? "&cYou cannot grant the default rank." : "&aClick to grant the rank."
            );
            return new ItemBuilder(Material.PAPER)
                    .name(rank.getRankWithColor())
                    .lore(lore)
                    .build();
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            if (clickType != ClickType.LEFT) return;

            if (rank.isDefaultRank()) {
                player.sendMessage(CC.translate("&cYou cannot grant the default rank."));
                return;
            }

            Profile profile = Artex.getInstance().getProfileRepository().getProfileWithNoAdding(target.getUniqueId());
            if (profile.getGrants().stream().anyMatch(grant -> grant.getRank().equals(rank) && grant.isActive())) {
                player.sendMessage(CC.translate("&4" + target.getName() + " &calready has that rank granted."));
                return;
            }

            new GrantDurationMenu(target, rank, reason).openMenu(player);
        }
    }
}
