package me.emmy.artex.rank.command.impl;

import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project Artex
 * @date 28/09/2024 - 19:49
 */
public class RankHelpCommand extends BaseCommand {
    @Command(name = "rank.help", permission = "artex.command.rank.help", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        sender.sendMessage("");
        sender.sendMessage(CC.translate("&4&lArtex &8- &7Rank Instructions"));
        sender.sendMessage("");

        String[] messages = {
                "&c&lCreating a Rank",
                " &fUse &4/rank create &f(name) (doPrefix)",
                " &fNo color codes in the name. Use &c/rank setcolor &fcommand.",
                " &fExample: &6/rank &esetcolor &fTestRank &4DARK_RED",
                " &fAppend 'true' in create command to set a prefix.",
                " &fChange prefix later with &c/rank setprefix &fcommand.",
                "",
                "",
                "",
                "&c&lSetting a Rank Prefix",
                " &fUse &c/rank setprefix &4(name) (prefix)",
                " &fPrefix can contain color codes.",
                " &fExample: &6/rank &esetprefix &fTestRank &4[&cTest&4]",
                "",
                "",
                "",
                "&c&lSetting a Rank Color",
                " &fUse &c/rank setcolor &4(name) (color)",
                " &fExample: &6/rank &esetcolor &fTestRank &4DARK_RED",
                "",
                "",
                "",
                "&c&lSetting a Rank Weight",
                " &fUse &c/rank setweight &4(name) (weight)",
                " &fExample: &6/rank &esetweight &fTestRank &41",
                "",
                "",
                "&cFor more info, contact the plugin developer.",
                "",
                ""
        };

        for (String message : messages) {
            sender.sendMessage(CC.translate(message));
        }
        sender.sendMessage("");
    }
}
