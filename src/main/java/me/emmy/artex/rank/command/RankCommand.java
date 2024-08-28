package me.emmy.artex.rank.command;

import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project Artex
 * @date 28/08/2024 - 19:06
 */
public class RankCommand extends BaseCommand {
    @Command(name = "rank", permission = "artex.command.rank", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();
        int page = 1;

        if (args.length > 0) {
            try {
                page = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                sender.sendMessage(CC.translate("&cInvalid page number!"));
                return;
            }
        }

        if (page < 1 || page > 3) {
            sender.sendMessage("");
            sender.sendMessage(CC.translate("&d&lArtex &8- &7Rank Commands - &7(1&8/&73)"));
            sender.sendMessage("");
            sender.sendMessage(CC.translate(" &f&l● &d/rank create &f(name) - &7Create a rank"));
            sender.sendMessage(CC.translate(" &f&l● &d/rank delete &f(name) - &7Delete a rank"));
            sender.sendMessage(CC.translate(" &f&l● &d/rank list - &7List all ranks"));
            sender.sendMessage(CC.translate(" &f&l● &d/rank info &f(name) - &7View rank information"));
            sender.sendMessage("");
            return;
        }

        sender.sendMessage("");
        sender.sendMessage(CC.translate("&d&lArtex &8- &7Rank Commands - &7(" + page + "&8/&73)"));

        if (page == 1) {
            sender.sendMessage("");
            sender.sendMessage(CC.translate(" &f&l● &d/rank create &f(name) - &7Create a rank"));
            sender.sendMessage(CC.translate(" &f&l● &d/rank delete &f(name) - &7Delete a rank"));
            sender.sendMessage(CC.translate(" &f&l● &d/rank list - &7List all ranks"));
            sender.sendMessage(CC.translate(" &f&l● &d/rank info &f(name) - &7View rank information"));
        } else if (page == 2) {
            sender.sendMessage("");
            sender.sendMessage(CC.translate(" &f&l● &d/rank setprefix &f(name) (prefix) - &7Set the prefix of a rank"));
            sender.sendMessage(CC.translate(" &f&l● &d/rank setsuffix &f(name) (suffix) - &7Set the suffix of a rank"));
            sender.sendMessage(CC.translate(" &f&l● &d/rank setcolor &f(name) (color) - &7Set the color of a rank"));
            sender.sendMessage(CC.translate(" &f&l● &d/rank setbold &f(name) (true/false) - &7Makes the rank bold"));
            sender.sendMessage(CC.translate(" &f&l● &d/rank setitalic &f(name) (true/false) - &7Makes the rank italic"));
        } else if (page == 3) {
            sender.sendMessage("");
            sender.sendMessage(CC.translate(" &f&l● &d/rank setweight &f(name) (weight) - &7Set the weight of a rank"));
            sender.sendMessage(CC.translate(" &f&l● &d/rank setdefault &f(name) - &7Set the default rank"));
            sender.sendMessage(CC.translate(" &f&l● &d/rank addpermission &f(name) (permission) - &7Add a permission to a rank"));
        }


        sender.sendMessage("");
    }
}
