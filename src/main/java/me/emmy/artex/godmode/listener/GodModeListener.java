package me.emmy.artex.godmode.listener;

import me.emmy.artex.Artex;
import me.emmy.artex.godmode.GodModeRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * @author Emmy
 * @project Artex
 * @date 03/09/2024 - 22:44
 */
public class GodModeListener implements Listener {

    private final GodModeRepository godModeRepository = Artex.getInstance().getGodModeRepository();

    @EventHandler
    private void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (godModeRepository.isGodModeEnabled(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (godModeRepository.isGodModeEnabled(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (godModeRepository.isGodModeEnabled(player)) {
                event.setCancelled(true);
            }
        }
    }
}
