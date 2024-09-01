package me.emmy.artex.tag.command.admin;

import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project Artex
 * @date 01/09/2024 - 20:28
 */
public class TagAdminCommand extends BaseCommand {
    @Command(name = "tagadmin", aliases = {"tagsadmin"}, permission = "artex.tag.admin", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        sender.sendMessage("");
        sender.sendMessage(CC.translate("&4&lArtex &8- &7Tag Commands"));
        sender.sendMessage("");
        sender.sendMessage(CC.translate(" &f&l● &4/tagadmin create &f(name) - &7Create a tag"));
        sender.sendMessage(CC.translate(" &f&l● &4/tagadmin delete &f(name) - &7Delete a tag"));
        sender.sendMessage(CC.translate(" &f&l● &4/tagadmin list - &7List all tags"));
        sender.sendMessage(CC.translate(" &f&l● &4/tagadmin info &f(name) - &7View tag information"));
        sender.sendMessage(CC.translate(" &f&l● &4/tagadmin setdisplayname &f(name) (displayname) - &7Set the display name of a tag"));
        sender.sendMessage(CC.translate(" &f&l● &4/tagadmin seticon &f(name) - &7Set the icon of a tag"));
        sender.sendMessage(CC.translate(" &f&l● &4/tagadmin setcolor &f(name) (color) - &7Set the color of a tag"));
        sender.sendMessage(CC.translate(" &f&l● &4/tagadmin setbold &f(name) (true/false) - &7Set the bold of a tag"));
        sender.sendMessage(CC.translate(" &f&l● &4/tagadmin setitalic &f(name) (true/false) - &7Set the italic of a tag"));
        sender.sendMessage("");
    }
}
