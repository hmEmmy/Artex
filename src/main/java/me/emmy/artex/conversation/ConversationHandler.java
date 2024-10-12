package me.emmy.artex.conversation;

import lombok.Getter;
import me.emmy.artex.Artex;
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
    private final HashMap<UUID, UUID> conversations = new HashMap<>();
    private final String sentFormat = CC.translate(Artex.getInstance().getConfig().getString("conversation.format.sent"));
    private final String receivedFormat = CC.translate(Artex.getInstance().getConfig().getString("conversation.format.received"));

    /**
     * Starts a conversation across two players.
     *
     * @param sender  The player who is starting the conversation.
     * @param target  The player who is being messaged.
     * @param message The message to send.
     */
    public void startConversation(UUID sender, UUID target, String message) {
        conversations.put(sender, target);
        conversations.put(target, sender);

        Player senderPlayer = Bukkit.getServer().getPlayer(sender);
        Player targetPlayer = Bukkit.getServer().getPlayer(target);

        if (targetPlayer == null && senderPlayer != null) {
            senderPlayer.sendMessage(CC.translate("&cThat player is currently offline."));
            return;
        }

        assert targetPlayer != null;
        sendMessage(message, targetPlayer, senderPlayer);
    }

    /**
     * Sends a message to a player with the correct format and profile color.
     *
     * @param message     The message to send.
     * @param targetPlayer The player to send the message to.
     * @param senderPlayer The player who is sending the message.
     */
    private void sendMessage(String message, Player targetPlayer, Player senderPlayer) {
        Rank senderRank = Artex.getInstance().getProfileRepository().getProfile(senderPlayer.getUniqueId()).getHighestRankBasedOnGrant();
        Rank targetRank = Artex.getInstance().getProfileRepository().getProfile(targetPlayer.getUniqueId()).getHighestRankBasedOnGrant();

        targetPlayer.sendMessage(CC.translate(receivedFormat
                .replace("{sender}", senderRank == null ? "&a" + senderPlayer.getName() : senderRank.getColor() + senderPlayer.getName())
                .replace("{message}", message)));
        targetPlayer.playSound(targetPlayer.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);

        senderPlayer.sendMessage(CC.translate(sentFormat
                .replace("{sender}", targetRank == null ? "&a" + targetPlayer.getName() : targetRank.getColor() + targetPlayer.getName())
                .replace("{message}", message)));
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