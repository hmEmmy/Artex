package me.emmy.artex.command.impl.admin.essential;

import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Emmy
 * @project Alley
 * @date 28/05/2024 - 20:16
 */
public class RenameCommand extends BaseCommand {
    @Override
    @Command(name = "rename", permission = "alley.admin")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.translate("&6Usage: &e/rename &b<name>"));
            return;
        }

        String itemRename = String.join(" ", command.getArgs());

        ItemStack itemStack = player.getItemInHand();
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            player.sendMessage(CC.translate("&cFailed to rename the item."));
            return;
        }

        itemMeta.setDisplayName(CC.translate(itemRename));
        itemStack.setItemMeta(itemMeta);

        player.updateInventory();
        player.sendMessage(CC.translate("&aSuccessfully renamed the &b" + itemStack.getType().name() + " &aitem to &b" + itemRename + "&a."));
    }
}