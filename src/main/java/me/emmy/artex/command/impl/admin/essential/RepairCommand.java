package me.emmy.artex.command.impl.admin.essential;

import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Artex
 * @date 07/09/2024 - 12:45
 */
public class RepairCommand extends BaseCommand {
    @Command(name = "repair", permission = "artex.command.repair")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (player.getItemInHand() == null) {
            player.sendMessage(CC.translate("&cYou must be holding an item to repair!"));
            return;
        }

        player.getItemInHand().setDurability((short) 0);
        player.sendMessage(CC.translate("&aSuccessfully repaired the &b" + player.getItemInHand().getItemMeta().getDisplayName() + "&a!"));
    }
}
