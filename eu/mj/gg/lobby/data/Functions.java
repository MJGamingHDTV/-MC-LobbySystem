package eu.mj.gg.lobby.data;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import eu.mj.gg.lobby.listener.AFKListener;
import eu.mj.gg.lobby.main.Main;
import eu.mj.gg.lobby.listener.AFK;

public class Functions {
	
	public File f = new File("plugins/GGLobby/", "afk.yml");
	public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
	
	public void AFK(Player player, boolean set) {
		World world = Bukkit.getWorld(cfg.getString("AFK.World"));
		double X = cfg.getDouble("AFK.X");
		double Y = cfg.getDouble("AFK.Y");
		double Z = cfg.getDouble("AFK.Z");
		float yaw = cfg.getInt("AFK.Yaw");
		float pitch = cfg.getInt("AFK.Pitch");
		Location loc = new Location(world, X, Y, Z, yaw, pitch);
		if (set) {
			Main.plugin.afk.put(player, set);
			AFK.time.put(player, 10 * 20);
			AFKListener.radius.put(player, player.getLocation());
			player.teleport(loc);
		} else {
			Main.plugin.afk.remove(player);
			AFKListener.radius.remove(player);
			player.teleport(Data.spawn);
		}
	}

	public boolean isInt(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException nFE) {
			return false;
		}
	}

	public boolean isAFK(Player p) {
		if (Main.plugin.afk.containsKey(p)) {
			return true;
		} else {
			return false;
		}
	}

	public void NoLongerAFK(Player p) {
		
	}

}