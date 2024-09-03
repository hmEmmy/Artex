package me.emmy.artex.command.impl.admin.essential;

import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Artex
 * @date 03/09/2024 - 14:27
 */
public class HealCommand extends BaseCommand {
    @Command(name = "heal", permission = "artex.command.heal")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.setHealth(20.0);
            player.setFoodLevel(20);
            player.setFireTicks(0);
            player.sendMessage(CC.translate("&eYou have been healed."));
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            player.sendMessage(CC.translate("&cPlayer not found."));
            return;
        }

        targetPlayer.setHealth(20.0);
        targetPlayer.setFoodLevel(20);
        targetPlayer.setFireTicks(0);
        player.sendMessage(CC.translate("&eYou have healed &d" + targetPlayer.getName() + "&e."));
        targetPlayer.sendMessage(CC.translate("&eYou have been healed."));
    }
}
