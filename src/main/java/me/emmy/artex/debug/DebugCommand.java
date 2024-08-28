package me.emmy.artex.debug;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Artex
 * @date 28/08/2024 - 22:58
 */
public class DebugCommand extends BaseCommand {
    @Command(name = "debug", permission = "artex.command.debug")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        player.sendMessage("");
        player.sendMessage(CC.translate("&cgetRank: &f" + Artex.getInstance().getProfileRepository().getProfile(player.getUniqueId()).getRank().getName()));
        player.sendMessage(CC.translate("&cgetPrefix: &f" + Artex.getInstance().getProfileRepository().getProfile(player.getUniqueId()).getRank().getPrefix()));
        player.sendMessage("");
        player.sendMessage(CC.translate("&cgetHighestRankBasedOnGrant: &f" + Artex.getInstance().getProfileRepository().getProfile(player.getUniqueId()).getHighestRankBasedOnGrant(player.getUniqueId()).getName()));
        player.sendMessage("");
    }
}
