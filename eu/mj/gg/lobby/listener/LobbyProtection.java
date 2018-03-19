package eu.mj.gg.lobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class LobbyProtection implements Listener {

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			e.setCancelled(true);
			return;
		}
		if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
			e.setCancelled(true);
			return;
		}
		if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)) {
			e.setCancelled(true);
			return;
		} else {
			e.setCancelled(false);
		}
	}
}
