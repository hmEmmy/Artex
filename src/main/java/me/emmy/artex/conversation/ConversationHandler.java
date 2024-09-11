package me.emmy.artex.conversation;

import lombok.Getter;
import me.emmy.artex.Artex;
import me.emmy.artex.grant.Grant;
import me.emmy.artex.profile.Profile;
import me.emmy.artex.rank.Rank;
import me.emmy.artex.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.UUID;

/**
 * Skidded from FlowerCore, I couldn't be bothered to write this again. (REAL)
 *
 * @author Emmy
 * @project Skidding FlowerCore session
 * @date 11/09/2024 - 12:43
 */
@Getter
public class ConversationHandler {

    private final HashMap<UUID, UUID> conversations;

    /**
     * Constructor for the ConversationHandler.
     */
    public ConversationHandler() {
        conversations = new HashMap<>();
    }

    /**
     * Sends a message to a player.
     *
     * @param sender  The sender of the message.
     * @param target  The target of the message.
     * @param message The message to send.
     */
    public void sendMessage(UUID sender, UUID target, String message) {
        conversations.put(sender, target);
        conversations.put(target, sender);

        Player senderPlayer = Bukkit.getServer().getPlayer(sender);
        Player targetPlayer = Bukkit.getServer().getPlayer(target);

        Rank senderRank = Artex.getInstance().getProfileRepository().getProfile(sender).getHighestRankBasedOnGrant();
        Rank targetRank = Artex.getInstance().getProfileRepository().getProfile(target).getHighestRankBasedOnGrant();

        if (targetPlayer == null && senderPlayer != null) {
            senderPlayer.sendMessage(CC.translate("&cThat player is currently offline."));
            return;
        }

        assert targetPlayer != null;
        targetPlayer.sendMessage(CC.translate(senderRank == null ? "&a" : senderRank.getColor() + senderPlayer.getName() + " &4» &f" + message));

        targetPlayer.playSound(targetPlayer.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);

        assert senderPlayer != null;
        senderPlayer.sendMessage(CC.translate(targetRank == null ? "&a" : targetRank.getColor() + targetPlayer.getName() + " &4» &f" + message));
        senderPlayer.playSound(senderPlayer.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
    }

    /**
     * Gets the last conversant of a player.
     *
     * @param player The player to get the last conversant of.
     * @return The last conversant of the player.
     */
    public UUID getLastConversant(UUID player) {
        return conversations.get(player);
    }
}