package me.emmy.artex.command.impl.admin.gamemode;

import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.util.CC;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Artex
 * @date 27/07/2024 - 19:38
 */
public class CreativeCommand extends BaseCommand {
    @Command(name = "gmc", aliases = {"gm.c", "gamemode.c", "gm.1", "gm1", "gamemode.1", "gamemode.creative"}, permission = "artex.command.gmc")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(CC.translate("&eYour gamemode has been updated to Creative."));
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            player.sendMessage(CC.translate("&cPlayer not found."));
            return;
        }

        targetPlayer.setGameMode(GameMode.CREATIVE);
        player.sendMessage(CC.translate("&eYou have updated &d" + targetPlayer.getName() + "'s &egamemode to Creative."));
        targetPlayer.sendMessage(CC.translate("&eYour gamemode has been updated to Creative."));
    }
}
