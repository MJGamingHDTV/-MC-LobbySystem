package eu.mj.gg.lobby.listener;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import eu.mj.gg.lobby.functions.Settings;
import eu.mj.gg.lobby.main.Main;
import eu.mj.gg.lobby.methods.LobbyScore;

public class MainListener implements Listener {
	
	Settings settings = new Settings();

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		for (Player all : Bukkit.getOnlinePlayers()) {
			try {
				String c = settings.Color.get(all);
				LobbyScore.setSidebar(all, c);
			} catch (NullPointerException ex) {
				LobbyScore.setSidebar(all, "0");
			}
		}

		if (e.getPlayer().hasPermission("ranks.hadmin")) {
			e.setQuitMessage("§8[§c-§8] §4" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.admin")) {
			e.setQuitMessage("§8[§c-§8] §c" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.des")) {
			e.setQuitMessage("§8[§c-§8] §c" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.dev")) {
			e.setQuitMessage("§8[§c-§8] §3" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.mod")) {
			e.setQuitMessage("§8[§c-§8] §a" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.build")) {
			e.setQuitMessage("§8[§c-§8] §2" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.sup")) {
			e.setQuitMessage("§8[§c-§8] §9" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.elite")) {
			e.setQuitMessage("§8[§c-§8] §1" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.yt")) {
			e.setQuitMessage("§8[§c-§8] §5" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.mvp")) {
			e.setQuitMessage("§8[§c-§8] §b" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.vip")) {
			e.setQuitMessage("§8[§c-§8] §6" + e.getPlayer().getName());
		} else {
			e.setQuitMessage("§8[§c-§8] §7" + e.getPlayer().getName());
		}
	}

	@EventHandler
	public void onKick(PlayerKickEvent e) {
		e.setLeaveMessage(null);
		for (Player all : Bukkit.getOnlinePlayers()) {
			try {
				String c = settings.Color.get(all);
				LobbyScore.setSidebar(all, c);
			} catch (NullPointerException ex) {
				LobbyScore.setSidebar(all, "0");
			}
		}
		if (e.getPlayer().hasPermission("ranks.hadmin")) {
			e.setLeaveMessage("§8[§c-§8] §4" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.admin")) {
			e.setLeaveMessage("§8[§c-§8] §c" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.des")) {
			e.setLeaveMessage("§8[§c-§8] §c" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.dev")) {
			e.setLeaveMessage("§8[§c-§8] §3" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.mod")) {
			e.setLeaveMessage("§8[§c-§8] §a" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.build")) {
			e.setLeaveMessage("§8[§c-§8] §2" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.sup")) {
			e.setLeaveMessage("§8[§c-§8] §9" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.elite")) {
			e.setLeaveMessage("§8[§c-§8] §1" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.yt")) {
			e.setLeaveMessage("§8[§c-§8] §5" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.mvp")) {
			e.setLeaveMessage("§8[§c-§8] §b" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.vip")) {
			e.setLeaveMessage("§8[§c-§8] §6" + e.getPlayer().getName());
		} else {
			e.setLeaveMessage("§8[§c-§8] §7" + e.getPlayer().getName());
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.getDrops().clear();
		e.setDroppedExp(0);
	}

	@EventHandler
	public void onWeather(WeatherChangeEvent e) {
		World w = e.getWorld();
		if (e.toWeatherState()) {
			w.setAnimalSpawnLimit(0);
			w.setMonsterSpawnLimit(0);
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onCMD(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		String[] cmd = e.getMessage().substring(1).split(" ");
		if (cmd[0].startsWith("bukkit"))
			if (!p.isOp()) {
				p.sendMessage(Main.pr + "§cDazu hast du keine Rechte!");
				e.setCancelled(true);
				return;
			}
		if (cmd[0].equalsIgnoreCase("plugin") || cmd[0].equalsIgnoreCase("pl") || cmd[0].equalsIgnoreCase("plugins")) {
			if (!p.isOp()) {
				p.sendMessage(Main.pr
						+ "§6Unsere Plugins haben derzeit §aUrlaub, §cschreib oder such dir deine eigenen §ePlugins!");
				e.setCancelled(true);

			}
		}
		if (cmd[0].equalsIgnoreCase("?") || cmd[0].equalsIgnoreCase("help")) {
			if (!p.isOp()) {
				p.sendMessage(Main.pr + "§aDeine §9Hilfeschreie §ahört hier niemand!");
				e.setCancelled(true);

			}
		}
		if (cmd[0].equalsIgnoreCase("ver") || cmd[0].equalsIgnoreCase("version") || cmd[0].equalsIgnoreCase("about")) {
			if (!p.isOp()) {
				p.sendMessage(Main.pr + "§6Version: §aDu kannst mich mal!");
				e.setCancelled(true);

			}
		}
		if (cmd[0].equalsIgnoreCase("me")) {
			if (!p.isOp()) {
				p.sendMessage(Main.pr + "§bMEEP :)");
				e.setCancelled(true);

			}

		}
	}
}
