package me.emmy.artex.spawn.command;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Artex
 * @date 03/09/2024 - 23:03
 */
public class TeleportToSpawnCommand extends BaseCommand {
    @Command(name = "tpspawn", aliases = {"tpjoinloc", "tpjoinlocation"},permission = "artex.command.spawn")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        Artex.getInstance().getSpawnHandler().teleportToSpawn(player);

        player.sendMessage(CC.translate("&eTeleported you to the spawn location."));
    }
}