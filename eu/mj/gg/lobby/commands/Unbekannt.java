package eu.mj.gg.lobby.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

import eu.mj.gg.lobby.main.Main;

public class Unbekannt implements Listener {
	@EventHandler
	public void onUKC(PlayerCommandPreprocessEvent e){
		if(!(e.isCancelled())){
		String er = e.getMessage().split(" ")[0];
		Player p = e.getPlayer();
		HelpTopic t = Bukkit.getServer().getHelpMap().getHelpTopic(er);
		if(t == null) {
			p.sendMessage(Main.pr + "Unbekannter Befehl!");
			e.setCancelled(true);
		}
		}
	}
}
