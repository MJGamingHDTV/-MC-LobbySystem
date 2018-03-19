package eu.mj.gg.lobby.data;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import eu.mj.gg.lobby.main.Main;

public class Data {

	public static String NoPerm = Main.pr + "§cDu hast keine Rechte um dies zu tun!";
	public static File file = new File("plugins//GGLobby//spawns.yml");
	public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

	public static Location spawn;
	public static Location spawn1;
	public static Location spawn2;
	public static Location spawn3;
	public static Location spawn4;
	public static Location spawn5;
	public static Location spawn6;
}
