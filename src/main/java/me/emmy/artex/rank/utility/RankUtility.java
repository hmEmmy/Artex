package me.emmy.artex.rank.utility;

import lombok.experimental.UtilityClass;
import me.emmy.artex.Artex;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.rank.RankService;
import me.emmy.artex.util.CC;
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
        if (Artex.getInstance().getDatabaseService().getDatabase() != null && Artex.getInstance().getDatabaseService().isMongo()) {
            RankService rankService = Artex.getInstance().getRankService();

            rankService.createDefaultRank();

            if (rankService.getRank("VIP") == null) {
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
                rankService.saveRank(VIP);
                rankService.getRanks().add(VIP);
            } else {
                Logger.logError("VIP rank already exists.");
            }

            if (rankService.getRank("Moderator") == null) {
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
                rankService.saveRank(Moderator);
                rankService.getRanks().add(Moderator);
            } else {
                Logger.logError("Moderator rank already exists.");
            }

            if (rankService.getRank("Admin") == null) {
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
                rankService.saveRank(Admin);
                rankService.getRanks().add(Admin);
            } else {
                Logger.logError("Admin rank already exists.");
            }

            if (rankService.getRank("Owner") == null) {
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
                rankService.saveRank(Owner);
                rankService.getRanks().add(Owner);
            } else {
                Logger.logError("Owner rank already exists.");
            }

            rankService.saveRanks();

            Bukkit.getConsoleSender().sendMessage(CC.translate("&aDefault ranks have been created. &4&lRestart the server to apply the changes."));
            Bukkit.shutdown();
        } else if (Artex.getInstance().getDatabaseService().isFlatFile()) {
            Logger.logError("Default ranks can only be created with MongoDB because the config file already has a variety of ranks.");
        } else {
            Logger.logError("Database service is not set.");
        }
    }
}