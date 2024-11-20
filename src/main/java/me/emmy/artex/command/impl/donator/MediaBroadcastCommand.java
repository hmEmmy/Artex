package me.emmy.artex.command.impl.donator;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.profile.Profile;
import me.emmy.artex.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

/**
 * @author Emmy
 * @project Artex
 * @date 23/10/2024 - 12:00
 */
public class MediaBroadcastCommand extends BaseCommand {
    @Command(name = "mediabroadcast", aliases = {"mb"}, permission = "artex.command.mediabroadcast")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&6Usage: &e/mediabroadcast &b<link>"));
            return;
        }

        String message = String.join(" ", Arrays.copyOfRange(args, 0, args.length));
        Profile profile = Artex.getInstance().getProfileRepository().getIProfile(player.getUniqueId());
        List<String> broadcastMessage = Arrays.asList(
                "",
                "&c&l" + profile.getHighestRankBasedOnGrant().getColor() + command.getPlayer().getName() + " &fis currently live!",
                "",
                " &7» &c" + message,
                ""
        );

        for (Player players : Bukkit.getOnlinePlayers()) {
            broadcastMessage.forEach(line -> players.sendMessage(CC.translate(line)));
        }
    }
}