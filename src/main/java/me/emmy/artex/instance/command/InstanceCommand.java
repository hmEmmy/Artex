package me.emmy.artex.instance.command;

import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.instance.menu.InstanceMenu;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Artex
 * @date 30/08/2024 - 21:00
 */
public class InstanceCommand extends BaseCommand {
    @Command(name = "instance", permission = "artex.command.instance")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        new InstanceMenu().openMenu(player);
    }
}
