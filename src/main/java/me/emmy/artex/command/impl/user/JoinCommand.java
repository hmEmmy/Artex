package me.emmy.artex.command.impl.user;

import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.BungeeUtil;
import me.emmy.artex.util.CC;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Artex
 * @date 15/09/2024 - 18:11
 */
public class JoinCommand extends BaseCommand {
    @Command(name = "join")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length <1) {
            player.sendMessage(CC.translate("&6Usage: &e/join &b<server>"));
            return;
        }

        BungeeUtil.sendPlayer(player, args[0]);
        player.sendMessage(CC.translate("&aSending you to &b" + args[0] + "&a..."));
    }
}