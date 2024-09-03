package me.emmy.artex.command.impl.admin.troll;

import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * @author Emmy
 * @project Artex
 * @date 03/09/2024 - 14:35
 */
public class LaunchCommand extends BaseCommand {
    @Override
    @Command(name = "launch", inGameOnly = false, permission = "flowercore.command.launch")
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 1) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(CC.translate("&cUsage: /launch (player)"));
                return;
            }

            Player player = (Player) sender;
            player.setVelocity(new Vector(0, 1, 0).multiply(15));
            player.sendMessage(CC.translate("&bYou've launched &3yourself &binto the air!"));
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            sender.sendMessage(CC.translate("&cPlayer not found."));
            return;
        }

        targetPlayer.setVelocity(new Vector(0, 1, 0).multiply(15));
        sender.sendMessage(CC.translate("&bYou've launched &3" + targetPlayer.getDisplayName() + " &binto the air!"));
        targetPlayer.sendMessage(CC.translate("&cYou have been launched into the air by &4" + sender.getName() + " &c!"));
    }
}