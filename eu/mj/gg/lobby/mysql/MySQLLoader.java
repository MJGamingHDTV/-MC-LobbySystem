package eu.mj.gg.lobby.mysql;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class MySQLLoader {

	File f = new File("plugins/GGLobby/", "MySQL.yml");
	YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

	Plugin plugin;
	String host;
	int port;
	String user;
	String pw;
	String db;

	public void loadConf() {
		host = cfg.getString("host");
		port = cfg.getInt("port");
		user = cfg.getString("user");
		pw = cfg.getString("password");
		db = cfg.getString("databank");
	}

	public MySQLLoader(Plugin pl) {
		this.plugin = pl;
	}

	public void loadMySQL() {
		this.loadConf();
		new AsyncMySQL(plugin, host, port, user, pw, db);
	}
}
