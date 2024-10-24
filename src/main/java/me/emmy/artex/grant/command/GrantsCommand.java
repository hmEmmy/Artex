package me.emmy.artex.grant.command;

import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.grant.menu.grants.GrantsMenu;
import me.emmy.artex.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Artex
 * @date 29/08/2024 - 15:43
 */
public class GrantsCommand extends BaseCommand {
    @Command(name = "grants", permission = "artex.command.grants")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&6Usage: &e/grants &b(player)"));
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        new GrantsMenu(target, false).openMenu(player);
    }
}