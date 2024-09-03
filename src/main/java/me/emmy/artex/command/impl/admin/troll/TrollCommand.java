package me.emmy.artex.command.impl.admin.troll;

import me.emmy.artex.Artex;
import me.emmy.artex.api.command.BaseCommand;
import me.emmy.artex.api.command.CommandArgs;
import me.emmy.artex.api.command.annotation.Command;
import me.emmy.artex.util.CC;
import me.emmy.artex.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class TrollCommand extends BaseCommand {
    @Override
    @Command(name = "troll", aliases = "playertroll", inGameOnly = false, permission = "flowercore.command.troll")
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 1) {
            sender.sendMessage(CC.translate("&6Usage: &e/troll &b(player)"));
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            sender.sendMessage(CC.translate("&cPlayer not found."));
            return;
        }

        try {
            String path = Artex.getInstance().getServer().getClass().getPackage().getName();
            String version = path.substring(path.lastIndexOf(".") + 1);

            Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
            Class<?> PacketPlayOutGameStateChange = Class.forName("net.minecraft.server." + version + ".PacketPlayOutGameStateChange");
            Class<?> Packet = Class.forName("net.minecraft.server." + version + ".Packet");
            Constructor<?> playOutConstructor = PacketPlayOutGameStateChange.getConstructor(int.class, float.class);
            Object packet = playOutConstructor.newInstance(5, 0);
            Object craftPlayerObject = craftPlayer.cast(targetPlayer);
            Method getHandleMethod = craftPlayer.getMethod("getHandle");
            Object handle = getHandleMethod.invoke(craftPlayerObject);
            Object pc = handle.getClass().getField("playerConnection").get(handle);
            Method sendPacketMethod = pc.getClass().getMethod("sendPacket", Packet);
            sendPacketMethod.invoke(pc, packet);
        } catch (Exception ex) {
            Logger.debug("An error occurred while trying to troll " + targetPlayer.getName() + ": " + ex.getMessage());
        }

        sender.sendMessage(CC.translate("&eYou have trolled &d" + targetPlayer.getName() + "&e."));
        targetPlayer.sendMessage(CC.translate("&eYou have been trolled."));
    }
}