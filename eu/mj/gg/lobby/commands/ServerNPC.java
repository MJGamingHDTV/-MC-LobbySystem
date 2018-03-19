package eu.mj.gg.lobby.commands;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.util.Vector;

import eu.mj.gg.lobby.main.Main;

public class ServerNPC implements CommandExecutor {

	private static File file = new File("plugins/GGLobby/", "servernpc.yml");
	public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	private Entity v;
	private ArmorStand as;
	private Location loc;
	private Location locas;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("npc")) {
				if (args[0].equalsIgnoreCase("create") && args.length == 3) {
					this.loc = p.getLocation();
					this.locas = p.getLocation().add(0, 0.5, 0);
					Vector dir = p.getEyeLocation().getDirection();
					this.v = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
					this.as = (ArmorStand) locas.getWorld().spawnEntity(locas, EntityType.ARMOR_STAND);
					this.as.setCustomName(args[1].replace("&", "§"));
					this.as.setVisible(false);
					this.as.setCustomNameVisible(true);
					this.v.getLocation().setDirection(dir);
					ServerNPC.setNoAITag(v, true);
					UUID villager = this.v.getUniqueId();
					cfg.set("Villagers." + villager + ".Server", args[2]);
					cfg.set("Villagers." + villager + ".Name", args[1].replace("&", "§"));
					p.sendMessage(Main.pr + "§aNPC " + args[1] + " gesetzt!");
					try {
						cfg.save(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (args[0].equalsIgnoreCase("remove")) {
					try {
						p.performCommand("kill @e[type=Villager,r=1]");
						p.performCommand("kill @e[type=ArmorStand,r=1]");
						p.sendMessage(Main.pr + "§cNPC wurde entfernt!");
					} catch (NullPointerException e) {
						System.out.println("");
					}
				}
			} else {
				p.sendMessage(Main.pr + "§cBitte verwende §e/npc <name> <server> §c!");
			}
		}
		return false;
	}

	public static void setNoAITag(Entity ent, boolean noAI) {
		try {
			String pack = Bukkit.getServer().getClass().getPackage().getName();
			String version = pack.substring(pack.lastIndexOf(".") + 1);

			Method k = Class.forName("net.minecraft.server." + version + ".EntityInsentient").getMethod("k",
					boolean.class);
			Object entity = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftEntity")
					.getMethod("getHandle").invoke(ent);

			k.invoke(entity, noAI);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// private void EntityType(String[] args, Player p) {
	// if (args[3].equalsIgnoreCase("villager")) {
	// this.v = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
	// } else if (args[3].equalsIgnoreCase("witch")) {
	// this.v = loc.getWorld().spawnEntity(loc, EntityType.WITCH);
	// } else if (args[3].equalsIgnoreCase("wither")) {
	// this.v = loc.getWorld().spawnEntity(loc, EntityType.WITHER);
	// } else if (args[3].equalsIgnoreCase("zombie")) {
	// this.v = loc.getWorld().spawnEntity(null, EntityType.ZOMBIE);
	// } else if (args[3].equalsIgnoreCase("blaze")) {
	// this.v = loc.getWorld().spawnEntity(null, EntityType.BLAZE);
	// } else if (args[3].equalsIgnoreCase("creeper")) {
	// this.v = loc.getWorld().spawnEntity(null, EntityType.CREEPER);
	// } else if (args[3].equalsIgnoreCase("enderman")) {
	// this.v = loc.getWorld().spawnEntity(null, EntityType.ENDERMAN);
	// } else if (args[3].equalsIgnoreCase("golem")) {
	// this.v = loc.getWorld().spawnEntity(null, EntityType.IRON_GOLEM);
	// } else if (args[3].equalsIgnoreCase("magmacube")) {
	// this.v = loc.getWorld().spawnEntity(null, EntityType.MAGMA_CUBE);
	// MagmaCube m = (MagmaCube) this.v;
	// int i = Integer.parseInt(args[4]);
	// m.setSize(i);
	// } else if (args[3].equalsIgnoreCase("slime")) {
	// this.v = loc.getWorld().spawnEntity(null, EntityType.SLIME);
	// Slime m = (Slime) this.v;
	// int i = Integer.parseInt(args[4]);
	// m.setSize(i);
	// } else if (args[3].equalsIgnoreCase("skeleton")) {
	// this.v = loc.getWorld().spawnEntity(null, EntityType.SKELETON);
	// } else if (args[3].equalsIgnoreCase("pigzombie")) {
	// this.v = loc.getWorld().spawnEntity(null, EntityType.PIG_ZOMBIE);
	// } else if (args[3].equalsIgnoreCase("guardian")) {
	// this.v = loc.getWorld().spawnEntity(loc, EntityType.GUARDIAN);
	// } else {
	// p.sendMessage(Main.pr + "§cBitte nutze: §e/npc <server> <villager, witch,
	// wither, zombie, blaze,"
	// + " creeper, enderman, golem, magmacube, slime, skeleton, pigzombie,
	// guardian> <size> §c!");
	// }
	// }
}
