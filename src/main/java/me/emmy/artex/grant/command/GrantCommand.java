package me.emmy.artex.grant.command;

import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.grant.menu.grant.GrantMenu;
import me.emmy.artex.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * @author Emmy
 * @project Artex
 * @date 29/08/2024 - 09:34
 */
public class GrantCommand extends BaseCommand {
    @Command(name = "grant", permission = "artex.command.grant")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /grant (player) [reason]"));
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        String reason;
        if (args.length > 2) {
            reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            new GrantMenu(target, reason).openMenu(player);
            return;
        }

        reason = "No reason specified.";
        new GrantMenu(target, reason).openMenu(player);
    }
}
