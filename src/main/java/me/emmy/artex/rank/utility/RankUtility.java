package me.emmy.artex.rank.utility;

import lombok.experimental.UtilityClass;
import me.emmy.artex.Artex;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.rank.RankRepository;
import me.emmy.artex.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;

/**
 * @author Emmy
 * @project Artex
 * @date 01/09/2024 - 13:27
 */
@UtilityClass
public class RankUtility {

    /**
     * Create default ranks and debug each step.
     */
    public void createDefaultRanks() {
        RankRepository rankRepository = Artex.getInstance().getRankRepository();

        Logger.debug("Starting to create default ranks.");
        Logger.debug("Creating default rank.");
        rankRepository.createDefaultRank();
        Logger.debug("Default rank created.");

        if (rankRepository.getRank("VIP") == null) {
            Logger.debug("Creating VIP rank.");
            Rank VIP = new Rank();
            VIP.setName("VIP");
            VIP.setPrefix("&7[&2VIP&7] ");
            VIP.setSuffix("");
            VIP.setWeight(100);
            VIP.setColor(ChatColor.DARK_GREEN);
            VIP.setBold(false);
            VIP.setItalic(false);
            VIP.setDefaultRank(false);
            VIP.setPermissions(new ArrayList<>());
            VIP.getPermissions().add("artex.vip");
            Logger.debug("Saving VIP rank.");
            rankRepository.saveRank(VIP);
            rankRepository.getRanks().add(VIP);
            Logger.debug("VIP rank created.");
        } else {
            Logger.debug("VIP rank already exists.");
        }

        if (rankRepository.getRank("Moderator") == null) {
            Logger.debug("Creating Moderator rank.");
            Rank Moderator = new Rank();
            Moderator.setName("Moderator");
            Moderator.setPrefix("&7[&3Moderator&7] ");
            Moderator.setSuffix("");
            Moderator.setWeight(500);
            Moderator.setColor(ChatColor.DARK_AQUA);
            Moderator.setBold(false);
            Moderator.setItalic(false);
            Moderator.setDefaultRank(false);
            Moderator.setPermissions(new ArrayList<>());
            Moderator.getPermissions().add("artex.moderator");
            Logger.debug("Saving Moderator rank.");
            rankRepository.saveRank(Moderator);
            rankRepository.getRanks().add(Moderator);
            Logger.debug("Moderator rank created.");
        } else {
            Logger.debug("Moderator rank already exists.");
        }

        if (rankRepository.getRank("Admin") == null) {
            Logger.debug("Creating Admin rank.");
            Rank Admin = new Rank();
            Admin.setName("Admin");
            Admin.setPrefix("&7[&cAdmin&7] ");
            Admin.setSuffix("");
            Admin.setWeight(1000);
            Admin.setColor(ChatColor.RED);
            Admin.setBold(false);
            Admin.setItalic(false);
            Admin.setDefaultRank(false);
            Admin.setPermissions(new ArrayList<>());
            Admin.getPermissions().add("artex.admin");
            Logger.debug("Saving Admin rank.");
            rankRepository.saveRank(Admin);
            rankRepository.getRanks().add(Admin);
            Logger.debug("Admin rank created.");
        } else {
            Logger.debug("Admin rank already exists.");
        }

        if (rankRepository.getRank("Owner") == null) {
            Logger.debug("Creating Owner rank.");
            Rank Owner = new Rank();
            Owner.setName("Owner");
            Owner.setPrefix("&7[&4Owner&7] ");
            Owner.setSuffix("");
            Owner.setWeight(10000);
            Owner.setColor(ChatColor.DARK_RED);
            Owner.setBold(false);
            Owner.setItalic(false);
            Owner.setDefaultRank(false);
            Owner.setPermissions(new ArrayList<>());
            Owner.getPermissions().add("*");
            Owner.getPermissions().add("artex.owner");
            Logger.debug("Saving Owner rank.");
            rankRepository.saveRank(Owner);
            rankRepository.getRanks().add(Owner);
            Logger.debug("Owner rank created.");
        } else {
            Logger.debug("Owner rank already exists.");
        }

        Logger.debug("Saving ranks.");
        rankRepository.saveRanks();

        Logger.debug("Default ranks created.");
        Logger.debug("&4&lShutting down server.");
        Bukkit.shutdown();
    }
}
