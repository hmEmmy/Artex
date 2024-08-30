package me.emmy.artex.command.gamemode;

import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Artex
 * @date 30/08/2024 - 20:59
 */
public class SpectatorCommand extends BaseCommand {
    @Command(name = "gmsp", aliases = {"gm.sp", "gamemode.sp", "gm.3", "gm3", "gamemode.3", "gamemode.spectator"}, permission = "artex.command.gmsp")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(CC.translate("&eYour gamemode has been updated to Spectator."));
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            player.sendMessage(CC.translate("&cPlayer not found."));
            return;
        }

        targetPlayer.setGameMode(GameMode.SPECTATOR);
        player.sendMessage(CC.translate("&eYou have updated &d" + targetPlayer.getName() + "'s &egamemode to Spectator."));
        targetPlayer.sendMessage(CC.translate("&eYour gamemode has been updated to Spectator."));
    }
}
