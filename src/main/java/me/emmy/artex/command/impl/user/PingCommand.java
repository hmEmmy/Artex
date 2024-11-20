package me.emmy.artex.command.impl.user;

import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Artex
 * @date 15/11/2024 - 10:51
 */
public class PingCommand extends BaseCommand {
    @Command(name = "ping")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&c" + player.getName() + "&f's ping: &4" + ((CraftPlayer) player).getHandle().ping) + "ms");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(CC.translate("&cPlayer not found."));
            return;
        }

        player.sendMessage(CC.translate("&c" + target.getName() + "&f's ping: &4" + ((CraftPlayer) target).getHandle().ping) + "ms");
    }
}