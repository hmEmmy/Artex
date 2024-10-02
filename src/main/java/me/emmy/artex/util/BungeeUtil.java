package me.emmy.artex.util;

import lombok.experimental.UtilityClass;
import me.emmy.artex.Artex;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Emmy
 * @project Artex/FlowerCore
 * @date 24/03/2024 - 12:17
 */
@UtilityClass
public class BungeeUtil {

    /**
     * Sends a player to a server
     *
     * @param player The player to send
     * @param server The server to send the player to
     */
    public void sendPlayer(Player player, String server) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(Artex.getInstance(), "BungeeCord", b.toByteArray());
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                player.sendMessage(me.emmy.artex.util.CC.translate("&cAn error occurred while trying to send you to the server. Please try again."));
            }
        };
        task.runTaskLater(Artex.getInstance(), 20);
    }
}