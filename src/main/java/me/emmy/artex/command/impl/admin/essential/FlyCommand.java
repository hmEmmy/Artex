package me.emmy.artex.command.impl.admin.essential;

import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Artex
 * @date 03/09/2024 - 22:40
 */
public class FlyCommand extends BaseCommand {
    @Command(name = "fly", permission = "artex.command.fly")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.setAllowFlight(!player.getAllowFlight());
            player.sendMessage(CC.translate("&6You have " + (player.getAllowFlight() ? "&aenabled" : "&cdisabled") + " &6fly mode."));
            return;
        }

        Player target = player.getServer().getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(CC.translate("&cPlayer not found."));
            return;
        }

        target.setAllowFlight(!target.getAllowFlight());
        target.sendMessage(CC.translate("&6Your fly mode has been " + (target.getAllowFlight() ? "&aenabled" : "&cdisabled") + " &6by " + player.getName() + "&6."));
        player.sendMessage(CC.translate("&6You have " + (target.getAllowFlight() ? "&aenabled" : "&cdisabled") + " &6fly mode for " + target.getName() + "&6."));
    }
}
