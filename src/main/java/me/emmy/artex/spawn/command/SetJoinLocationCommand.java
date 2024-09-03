package me.emmy.artex.spawn.command;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Artex
 * @date 03/09/2024 - 22:56
 */
public class SetJoinLocationCommand extends BaseCommand {
    @Command(name = "setjoinlocation", permission = "artex.command.setjoinlocation")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        Location location = player.getLocation();
        Artex.getInstance().getSpawnHandler().saveLocation(location);

        player.sendMessage(CC.translate("&eSuccessfully set the join location."));
        player.sendMessage(CC.translate(" &7&o(" + location.getBlockX() + "&8&o, &7&o" + location.getBlockY() + "&8&o, &7&o" + location.getBlockZ() + location.getYaw() + "&8&o, &7&o" + location.getPitch() + " &8&oin &7&o" + location.getWorld().getName() + "&8&o)"));
    }
}
