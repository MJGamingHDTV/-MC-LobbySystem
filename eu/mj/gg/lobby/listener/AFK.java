package eu.mj.gg.lobby.listener;

import java.util.HashMap;

import org.bukkit.entity.Player;

import eu.mj.gg.lobby.data.Functions;

public class AFK implements Runnable {

	public static HashMap<Player, Integer> time = new HashMap<Player, Integer>();
	public Functions functions = new Functions();

	@Override
	public void run() {
		for (Player p : time.keySet()) {
			if (time.get(p) == 10 * 20) {
				functions.AFK(p, true);
			}
		}
	}
}
