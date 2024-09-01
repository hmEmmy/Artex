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
        Logger.debug("Clearing ranks.");
        rankRepository.getRanks().clear();
        Logger.debug("Ranks cleared.");
        Logger.debug("Creating default rank.");
        rankRepository.createDefaultRank();
        Logger.debug("Default rank created.");

        Logger.debug("Creating VIP rank.");
        Rank VIP = new Rank();
        Logger.debug("Setting name VIP.");
        VIP.setName("VIP");
        Logger.debug("Setting prefix &7[&2VIP&7] ");
        VIP.setPrefix("&7[&2VIP&7] ");
        Logger.debug("Setting suffix empty.");
        VIP.setSuffix("");
        Logger.debug("Setting weight 100.");
        VIP.setWeight(100);
        Logger.debug("Setting color dark green.");
        VIP.setColor(ChatColor.DARK_GREEN);
        Logger.debug("Setting bold false.");
        VIP.setBold(false);
        Logger.debug("Setting italic false.");
        VIP.setItalic(false);
        Logger.debug("Setting default rank false.");
        VIP.setDefaultRank(false);
        Logger.debug("Adding permission artex.vip.");
        VIP.setPermissions(new ArrayList<>());
        VIP.getPermissions().add("artex.vip");
        Logger.debug("Saving rank VIP.");
        rankRepository.saveRank(VIP);
        Logger.debug("Putting rank VIP in ranks.");
        rankRepository.getRanks().put(VIP.getName(), VIP);

        Logger.debug("Creating Moderator rank.");
        Rank Moderator = new Rank();
        Logger.debug("Setting name Moderator.");
        Moderator.setName("Moderator");
        Logger.debug("Setting prefix &7[&3Moderator&7] ");
        Moderator.setPrefix("&7[&3Moderator&7] ");
        Logger.debug("Setting suffix empty.");
        Moderator.setSuffix("");
        Logger.debug("Setting weight 500.");
        Moderator.setWeight(500);
        Logger.debug("Setting color dark aqua.");
        Moderator.setColor(ChatColor.DARK_AQUA);
        Logger.debug("Setting bold false.");
        Moderator.setBold(false);
        Logger.debug("Setting italic false.");
        Moderator.setItalic(false);
        Logger.debug("Setting default rank false.");
        Moderator.setDefaultRank(false);
        Logger.debug("Adding permission artex.moderator.");
        Moderator.setPermissions(new ArrayList<>());
        Moderator.getPermissions().add("artex.moderator");
        Logger.debug("Saving rank Moderator.");
        rankRepository.saveRank(Moderator);
        Logger.debug("Putting rank Moderator in ranks.");
        rankRepository.getRanks().put(Moderator.getName(), Moderator);

        Logger.debug("Creating Admin rank.");
        Rank Admin = new Rank();
        Logger.debug("Setting name Admin.");
        Admin.setName("Admin");
        Logger.debug("Setting prefix &7[&cAdmin&7] ");
        Admin.setPrefix("&7[&cAdmin&7] ");
        Logger.debug("Setting suffix empty.");
        Admin.setSuffix("");
        Logger.debug("Setting weight 1000.");
        Admin.setWeight(1000);
        Logger.debug("Setting color red.");
        Admin.setColor(ChatColor.RED);
        Logger.debug("Setting bold false.");
        Admin.setBold(false);
        Logger.debug("Setting italic false.");
        Admin.setItalic(false);
        Logger.debug("Setting default rank false.");
        Admin.setDefaultRank(false);
        Logger.debug("Adding permission artex.admin.");
        Admin.setPermissions(new ArrayList<>());
        Admin.getPermissions().add("artex.admin");
        Logger.debug("Saving rank Admin.");
        rankRepository.saveRank(Admin);
        Logger.debug("Putting rank Admin in ranks.");
        rankRepository.getRanks().put(Admin.getName(), Admin);

        Logger.debug("Creating Owner rank.");
        Rank Owner = new Rank();
        Logger.debug("Setting name Owner.");
        Owner.setName("Owner");
        Logger.debug("Setting prefix &7[&4Owner&7] ");
        Owner.setPrefix("&7[&4Owner&7] ");
        Logger.debug("Setting suffix empty.");
        Owner.setSuffix("");
        Logger.debug("Setting weight 10000.");
        Owner.setWeight(10000);
        Logger.debug("Setting color dark red.");
        Owner.setColor(ChatColor.DARK_RED);
        Logger.debug("Setting bold false.");
        Owner.setBold(false);
        Logger.debug("Setting italic false.");
        Owner.setItalic(false);
        Logger.debug("Setting default rank false.");
        Owner.setDefaultRank(false);
        Logger.debug("Adding permission * and artex.owner.");
        Owner.setPermissions(new ArrayList<>());
        Owner.getPermissions().add("*");
        Owner.getPermissions().add("artex.owner");
        Logger.debug("Saving rank Owner.");
        rankRepository.saveRank(Owner);
        Logger.debug("Putting rank Owner in ranks.");
        rankRepository.getRanks().put(Owner.getName(), Owner);

        Logger.debug("Saving ranks.");
        rankRepository.saveRanks();

        Logger.debug("Default ranks created.");
        Logger.debug("&4&lShutting down server.");
        Bukkit.shutdown();
    }
}
