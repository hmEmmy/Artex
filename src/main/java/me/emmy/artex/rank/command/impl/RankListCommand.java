package me.emmy.artex.rank.command.impl;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.rank.RankRepository;
import me.emmy.artex.util.CC;
import org.bukkit.command.CommandSender;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Emmy
 * @project Artex
 * @date 28/08/2024 - 22:13
 */
public class RankListCommand extends BaseCommand {
    @Command(name = "rank.list", permission = "artex.command.rank.list", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();


        sender.sendMessage("");
        sender.sendMessage(CC.translate("&b&lArtex &8- &7Ranks"));
        sender.sendMessage("");

        RankRepository rankRepository = Artex.getInstance().getRankRepository();
        if (rankRepository.getRanks() == null || rankRepository.getRanks().isEmpty()) {
            sender.sendMessage(CC.translate("&f&l● &bNo ranks found."));
            sender.sendMessage("");
            return;
        }

        List<Rank> sortedRanks = rankRepository.getRanks().values().stream()
                .sorted(Comparator.comparingInt(Rank::getWeight).reversed())
                .collect(Collectors.toList());

        for (Rank rank : sortedRanks) {
            sender.sendMessage(CC.translate("&f&l● &b" + rank.getName() + " &7- &f" + rank.getPrefix() + "&8[&7" + rank.getWeight() + "&8]" + (rank.isDefaultRank() ? " &7&o(Default Rank)" : "")));
        }
        sender.sendMessage("");

    }
}
