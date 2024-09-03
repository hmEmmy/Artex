package me.emmy.artex.godmode.command;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.godmode.GodModeRepository;
import me.emmy.artex.util.CC;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Artex
 * @date 03/09/2024 - 22:46
 */
public class GodModeCommand extends BaseCommand {
    @Command(name = "godmode", permission = "arte.command.godmode")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        GodModeRepository godModeRepository = Artex.getInstance().getGodModeRepository();

        if (args.length < 1) {
            if (!godModeRepository.isGodModeEnabled(player)) {
                godModeRepository.enableGodMode(player);
                player.sendMessage(CC.translate("&aGod mode has been enabled."));
            } else {
                godModeRepository.disableGodMode(player);
                player.sendMessage(CC.translate("&cGod mode has been disabled."));
            }

            return;
        }

        Player target = player.getServer().getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(CC.translate("&cPlayer not found."));
            return;
        }

        if (!godModeRepository.isGodModeEnabled(target)) {
            godModeRepository.enableGodMode(target);
            player.sendMessage(CC.translate("&aGod mode has been enabled for " + target.getName() + "."));
            target.sendMessage(CC.translate("&aGod mode has been enabled."));
        } else {
            godModeRepository.disableGodMode(target);
            player.sendMessage(CC.translate("&cGod mode has been disabled for " + target.getName() + "."));
            target.sendMessage(CC.translate("&cGod mode has been disabled."));
        }
    }
}
