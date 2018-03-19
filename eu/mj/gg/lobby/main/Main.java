package eu.mj.gg.lobby.main;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.slikey.effectlib.EffectManager;
import eu.mj.gg.lobby.commands.AFKLobby;
import eu.mj.gg.lobby.commands.BuildCMD;
import eu.mj.gg.lobby.commands.ServerNPC;
import eu.mj.gg.lobby.commands.SetSpawn;
import eu.mj.gg.lobby.commands.Unbekannt;
import eu.mj.gg.lobby.commands.WFCMD;
import eu.mj.gg.lobby.data.Data;
import eu.mj.gg.lobby.functions.Compass;
import eu.mj.gg.lobby.functions.Settings;
import eu.mj.gg.lobby.listener.AFK;
import eu.mj.gg.lobby.listener.AFKListener;
import eu.mj.gg.lobby.listener.BuildListener;
import eu.mj.gg.lobby.listener.Chat;
import eu.mj.gg.lobby.listener.LobbyProtection;
import eu.mj.gg.lobby.listener.MainListener;
import eu.mj.gg.lobby.listener.NPCListener;
import eu.mj.gg.lobby.listener.RideonMe;
import eu.mj.gg.lobby.mysql.MySQLLoader;
import eu.mj.gg.lobby.nick.DataUtil;
import eu.mj.gg.lobby.nick.MySQL;
import eu.mj.gg.lobby.nick.UTF8YamlConfiguration;
import net.milkbowl.vault.permission.Permission;

public class Main extends JavaPlugin {
	public static Main plugin;
	public static String pr = "§8[§9GG | Lobby§8] ";
	static int i;
	public static ItemStack OnlineLobby;
	public static ItemStack OfflineLobby;
	private static final Logger log = Logger.getLogger("Minecraft");
	private static Permission perms = null;
	public static JavaPlugin instance;
	public HashMap<Player, Boolean> afk = new HashMap<Player, Boolean>();
	public static EffectManager effectManager;
	private File configFile;
	public YamlConfiguration cfg;
	private MySQL sql;
	private DataUtil util;

	@SuppressWarnings("deprecation")
	public void onEnable() {
		plugin = this;

		effectManager = new EffectManager(this);

		Bukkit.getConsoleSender().sendMessage(pr + "§ewird aktiviert");
		Bukkit.getConsoleSender().sendMessage(pr + "§ewird aktiviert.");
		Bukkit.getConsoleSender().sendMessage(pr + "§ewird aktiviert..");
		Bukkit.getConsoleSender().sendMessage(pr + "§ewird aktiviert...");

		i = 0;

		Bukkit.getConsoleSender().sendMessage("§6§m-----------------------------------------");
		Bukkit.getConsoleSender().sendMessage(pr);
		Bukkit.getConsoleSender().sendMessage("§9coded by: §a" + this.getDescription().getAuthors());
		Bukkit.getConsoleSender().sendMessage("§cVersion: §a" + this.getDescription().getVersion());
		Bukkit.getConsoleSender().sendMessage("§6§m-----------------------------------------");

		load1();
		load2();
		load3();
		load4();
		load5();
		load6();

		Bukkit.getPluginManager().registerEvents(new Compass(this), this);
		Bukkit.getPluginManager().registerEvents(new LobbyProtection(), this);
		Bukkit.getPluginManager().registerEvents(new Settings(), this);
		Bukkit.getPluginManager().registerEvents(new MainListener(), this);
		Bukkit.getPluginManager().registerEvents(new SetSpawn(), this);
		Bukkit.getPluginManager().registerEvents(new Unbekannt(), this);
		Bukkit.getPluginManager().registerEvents(new NPCListener(), this);
		Bukkit.getPluginManager().registerEvents(new RideonMe(), this);
		Bukkit.getPluginManager().registerEvents(new Chat(), this);
		Bukkit.getPluginManager().registerEvents(new AFKListener(), this);
		Bukkit.getPluginManager().registerEvents(new BuildListener(), this);

		this.getCommand("walkspeed").setExecutor(new WFCMD());
		this.getCommand("flyspeed").setExecutor(new WFCMD());
		this.getCommand("npc").setExecutor(new ServerNPC());
		this.getCommand("chat").setExecutor(new Chat());
		this.getCommand("setafkspawn").setExecutor(new AFKLobby());
		this.getCommand("build").setExecutor(new BuildCMD());

		final AFK w = new AFK();
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, w, 1, 1);

		setupPermissions();
		loadLocations();

		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		instance = this;
		
		MySQLLoader sqll = new MySQLLoader(plugin);
		sqll.loadMySQL();

		Bukkit.getConsoleSender().sendMessage(pr + "§awurde erfolgreich geladen und aktiviert!");

		manageFiles();
		this.sql = new MySQL(this.cfg);
		if (!this.sql.openConnection(true)) {
			return;
		}
		this.util = new DataUtil();

	}

	public void onDisable() {

		for (Player all : Bukkit.getOnlinePlayers()) {
			all.kickPlayer(
					pr + "\n §eDer Server startet nun neu!\n§9Bitte warte kurz bevor du dich wieder verbindest!");
		}

		Bukkit.getConsoleSender().sendMessage(pr + "§ewird deaktiviert...");
		log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));

	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}

	public void loadLocations() {
		File file = new File("plugins//GGLobby//spawns.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		double x = cfg.getDouble("Spawn.X");
		double y = cfg.getDouble("Spawn.Y");
		double z = cfg.getDouble("Spawn.Z");
		double yaw = cfg.getDouble("Spawn.Yaw");
		double pitch = cfg.getDouble("Spawn.Pitch");
		Location loc = new Location(Bukkit.getWorld(cfg.getString("Spawn.WeltName")), x, y, z);
		loc.setYaw((float) yaw);
		loc.setPitch((float) pitch);
		Data.spawn = loc;

	}

	public void load1() {
		File file = new File("plugins//GGLobby//spawns.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		double x = cfg.getDouble("1.X");
		double y = cfg.getDouble("1.Y");
		double z = cfg.getDouble("1.Z");
		double yaw = cfg.getDouble("1.Yaw");
		double pitch = cfg.getDouble("1.Pitch");
		Location loc = new Location(Bukkit.getWorld(cfg.getString("Spawn.WeltName")), x, y, z);
		loc.setYaw((float) yaw);
		loc.setPitch((float) pitch);
		Data.spawn1 = loc;
	}

	public void load2() {
		File file = new File("plugins//GGLobby//spawns.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		double x = cfg.getDouble("2.X");
		double y = cfg.getDouble("2.Y");
		double z = cfg.getDouble("2.Z");
		double yaw = cfg.getDouble("2.Yaw");
		double pitch = cfg.getDouble("2.Pitch");
		Location loc = new Location(Bukkit.getWorld(cfg.getString("Spawn.WeltName")), x, y, z);
		loc.setYaw((float) yaw);
		loc.setPitch((float) pitch);
		Data.spawn2 = loc;
	}

	public void load3() {
		File file = new File("plugins//GGLobby//spawns.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		double x = cfg.getDouble("3.X");
		double y = cfg.getDouble("3.Y");
		double z = cfg.getDouble("3.Z");
		double yaw = cfg.getDouble("3.Yaw");
		double pitch = cfg.getDouble("3.Pitch");
		Location loc = new Location(Bukkit.getWorld(cfg.getString("Spawn.WeltName")), x, y, z);
		loc.setYaw((float) yaw);
		loc.setPitch((float) pitch);
		Data.spawn3 = loc;
	}

	public void load4() {
		File file = new File("plugins//GGLobby//spawns.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		double x = cfg.getDouble("4.X");
		double y = cfg.getDouble("4.Y");
		double z = cfg.getDouble("4.Z");
		double yaw = cfg.getDouble("4.Yaw");
		double pitch = cfg.getDouble("4.Pitch");
		Location loc = new Location(Bukkit.getWorld(cfg.getString("Spawn.WeltName")), x, y, z);
		loc.setYaw((float) yaw);
		loc.setPitch((float) pitch);
		Data.spawn4 = loc;
	}

	public void load5() {
		File file = new File("plugins//GGLobby//spawns.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		double x = cfg.getDouble("5.X");
		double y = cfg.getDouble("5.Y");
		double z = cfg.getDouble("5.Z");
		double pitch = cfg.getDouble("5.Pitch");
		double yaw = cfg.getDouble("5.Yaw");
		Location loc = new Location(Bukkit.getWorld(cfg.getString("Spawn.WeltName")), x, y, z);
		loc.setYaw((float) yaw);
		loc.setPitch((float) pitch);
		Data.spawn5 = loc;
	}

	public void load6() {
		File file = new File("plugins//GGLobby//spawns.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		double x = cfg.getDouble("6.X");
		double y = cfg.getDouble("6.Y");
		double z = cfg.getDouble("6.Z");
		double yaw = cfg.getDouble("6.Yaw");
		double pitch = cfg.getDouble("6.Pitch");
		Location loc = new Location(Bukkit.getWorld(cfg.getString("Spawn.WeltName")), x, y, z);
		loc.setYaw((float) yaw);
		loc.setPitch((float) pitch);
		Data.spawn6 = loc;
	}

	public static Permission getPermissions() {
		return perms;
	}

	public static Main getInstance() {
		return (Main) instance;
	}

	private void manageFiles() {
		this.configFile = new File(getDataFolder().getPath(), "config.yml");
		if (!this.configFile.exists()) {
			saveResource("config.yml", true);
		}
		this.cfg = new UTF8YamlConfiguration(this.configFile);
	}

	public DataUtil getUtil() {
		return this.util;
	}

	public String getString(String path) {
		String get = ChatColor.translateAlternateColorCodes('&', this.cfg.getString(path));
		return get;
	}

	public MySQL getSQL() {
		return this.sql;
	}

	public String getMessage(String path) {
		String get = ChatColor.translateAlternateColorCodes('&', this.cfg.getString("ChatMessages." + path));
		return get;
	}
}
