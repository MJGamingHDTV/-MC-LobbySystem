package eu.mj.gg.lobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import eu.mj.gg.lobby.functions.Settings;
import eu.mj.gg.lobby.main.Main;

public class RideonMe implements Listener {
	
	Settings settings = new Settings();
	
	@EventHandler
	public void onRightClick(PlayerInteractAtEntityEvent e) {
		e.setCancelled(true);
		try {
			Player p = (Player) e.getRightClicked();
			Player c = e.getPlayer();
			if (settings.Ride.contains(p.getName())) {
				p.setPassenger(c);
			} else {
				c.sendMessage(Main.pr + "�cDieser Spieler hat das RideonMe Feature nicht aktiv!");
			}
		} catch (ClassCastException c) {

		}
	}
}
