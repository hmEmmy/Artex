package me.emmy.artex.command.impl.admin.essential;

import me.emmy.artex.util.EnchantUtil;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Emmy
 * @project Alley
 * @date 28/05/2024 - 20:28
 */
public class EnchantCommand extends BaseCommand {

    @Override
    @Command(name = "enchant", permission = "alley.admin")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 2) {
            player.sendMessage(CC.translate("&6Usage: &e/enchant &b<enchantment> <level>"));
            return;
        }

        String enchantmentName = EnchantUtil.getEnchantment(args[0]);
        if (enchantmentName == null) {
            player.sendMessage(CC.translate("&cInvalid enchantment name!"));
            return;
        }

        Enchantment enchantment = Enchantment.getByName(enchantmentName);
        if (enchantment == null) {
            player.sendMessage(CC.translate("&cInvalid enchantment name!"));
            return;
        }

        int level;
        try {
            level = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(CC.translate("&cEnchantment level must be a number!"));
            return;
        }

        ItemStack itemInHand = player.getInventory().getItemInHand();
        if (itemInHand == null || itemInHand.getType() == Material.AIR) {
            player.sendMessage(CC.translate("&cYou must be holding an item to enchant!"));
            return;
        }

        itemInHand.addUnsafeEnchantment(enchantment, level);
        player.sendMessage(CC.translate("&aSuccessfully enchanted the &b" + player.getItemInHand().getItemMeta().getDisplayName() + " &aitem with &b" + enchantment.getName() + " &alevel &b" + level + "&a!"));
    }
}