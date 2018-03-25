package eu.mj.gg.lobby.functions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import cloud.timo.TimoCloud.api.objects.ServerObject;
import de.slikey.effectlib.effect.SkyRocketEffect;
import eu.mj.gg.lobby.commands.BuildCMD;
import eu.mj.gg.lobby.data.Data;
import eu.mj.gg.lobby.functions.Settings;
import eu.mj.gg.lobby.listener.BungeeCord;
import eu.mj.gg.lobby.main.Main;
import eu.mj.gg.lobby.methods.BungeeUtil;
import eu.mj.gg.lobby.methods.LobbyScore;
import eu.mj.gg.lobby.mysql.LobbyAPI;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Settings implements Listener {

	private int jump = 1;

	public static ArrayList<String> waterjump = new ArrayList<String>();

	public static ArrayList<String> platejump = new ArrayList<String>();

	public static ArrayList<String> Speed5 = new ArrayList<String>();
	public static ArrayList<String> Speed10 = new ArrayList<String>();
	private static ArrayList<String> Speed1 = new ArrayList<String>();

	public static ArrayList<String> SilentLobby = new ArrayList<String>();
	
	public static ArrayList<String> Ride = new ArrayList<String>();
	
	public static HashMap<Player, String> Color = new HashMap<Player, String>();
	
	public static HashMap<Player, Integer> ItemCol = new HashMap<Player, Integer>();

	private Inventory inv1 = Bukkit.createInventory(null, 54, "§a\u25B8 §9§lKONFIGURATIONSMENÜ §a\u25C2");
	private Inventory inv2 = Bukkit.createInventory(null, 54, "§a\u25B8 §9§lKONFIGURATIONSMENÜ §a\u25C2");
	private Inventory inv3 = Bukkit.createInventory(null, 54, "§a\u25B8 §9§lKONFIGURATIONSMENÜ §a\u25C2");
	private Inventory invfarbe = Bukkit.createInventory(null, 18, "§a\u25B8 §9§lInventarfarbe §a\u25C2");
	private Inventory inv = Bukkit.createInventory(null, 9, "§a\u25B8 §9§lLOBBYWECHSLER §a\u25C2");

	@EventHandler (priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e) {
		BungeeCord.getServerInfo("127.0.0.1", 25565);
		e.setJoinMessage("");
		Player p = e.getPlayer();
		String pexprefix = PermissionsEx.getUser(p).getPrefix();
		p.setDisplayName(pexprefix + p.getName());
		try {
			LobbyAPI.createPlayer(p.getUniqueId().toString());
		} catch (SQLException e1) {
			System.out.println(e1);
		}
		LobbyAPI.getWjump(p.getUniqueId().toString());
		LobbyAPI.getColor(p.getUniqueId().toString());
		LobbyAPI.getPjump(p.getUniqueId().toString());
		LobbyAPI.getRide(p.getUniqueId().toString());
		LobbyAPI.getSilent(p.getUniqueId().toString());
		LobbyAPI.getItemCol(p.getUniqueId().toString());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		PermissionUser user = PermissionsEx.getUser(p);
		String prefix1 = user.getPrefix("world");
		String prefix = prefix1.replace("&", "§");
		p.setDisplayName(prefix + " " + p.getName());
		p.setPlayerListName(prefix + " " + p.getName());
		p.setCustomName(prefix + " " + p.getName());
		p.getInventory().clear();
		p.setGameMode(GameMode.SURVIVAL);
		ItemStack is = new ItemStack(Material.REDSTONE_COMPARATOR);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("§a\u25B8 §9§lKONFIGURATIONSMENÜ §a\u25C2");
		is.setItemMeta(im);
		Speed10.remove(p.getName());
		Speed5.remove(p.getName());
		Speed1.add(p.getName());
		p.setWalkSpeed((float) 0.2);
		p.getInventory().setItem(8, is);
		if (p.hasPermission("player.vip")) {
			p.setAllowFlight(true);
			p.setFlying(false);
		}
		
		if (p.getFirstPlayed() == 0) {
			Settings.waterjump.add(p.getName());
			Settings.platejump.add(p.getName());
		}
		if (BuildCMD.allow.contains(p)) {
			BuildCMD.allow.remove(p);
		}
		if (!Color.containsKey(p)) {
			Color.put(p, "f");
		}
		if (!ItemCol.containsKey(p)) {
			ItemCol.put(p, 0);
		}
		p.setHealthScale(20);
		p.setHealth(20);
		p.setFoodLevel(20);
		String servername = TimoCloudAPI.getBukkitInstance().getThisServer().getName();
		LobbyScore.sendTabTitle(p, "§aWillkommen auf §9GalaxyofGames\n §bViel Spaß beim Spielen!",
				"§eDu befindest dich auf:\n §6" + servername);
		for (Player all : Bukkit.getOnlinePlayers()) {
			try {
				String c = Settings.Color.get(all);
				LobbyScore.setSidebar(all, c);
			} catch (NullPointerException ex) {
				LobbyScore.setSidebar(all, "0");
			}
		}
		ItemStack ep = new ItemStack(Material.NETHER_STAR);
		ItemMeta epm = ep.getItemMeta();
		epm.setDisplayName("§a\u25B8 §9§lLOBBYWECHSLER §a\u25C2");
		ep.setItemMeta(epm);
		p.getInventory().setItem(6, ep);
		ItemStack ec = new ItemStack(Material.ENDER_CHEST);
		ItemMeta ecm = ec.getItemMeta();
		ecm.setDisplayName("§a\u25B8 §9§lSHOP §a\u25C2");
		ec.setItemMeta(ecm);
		p.getInventory().setItem(4, ec);
		ItemStack Head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		ItemMeta HeadM = Head.getItemMeta();
		HeadM.setDisplayName("§a\u25B8 §9§lFREUNDE §a\u25C2");
		Head.setItemMeta(HeadM);
		SkullMeta SM1 = (SkullMeta) Head.getItemMeta();
		SM1.setOwner(p.getName());
		Head.setItemMeta(SM1);
		ItemStack co = new ItemStack(Material.COMPASS);
		ItemMeta com = co.getItemMeta();
		com.setDisplayName("§a\u25B8 §9§lNAVIGATOR §a\u25C2");
		co.setItemMeta(com);
		p.getInventory().setItem(0, co);
		p.getInventory().setItem(7, Head);
		if (p.hasPlayedBefore() == true) {
			p.teleport(Data.spawn);
		} else {
			p.teleport(Data.spawn);
			p.sendMessage(Main.pr + "§aWillkommen auf GalaxyofGames!");
			p.sendMessage(Main.pr + "§9Viel Spaß beim Spielen!");
		}
		if (!Main.getInstance().getSQL().accountExists(Main.getInstance().getUtil().getUUID(e.getPlayer().getName()))) {
			Main.getInstance().getSQL().createAccount(Main.getInstance().getUtil().getUUID(e.getPlayer().getName()));
		}
		if (e.getPlayer().hasPermission("autonickerlobby.receiveitem")) {
			Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
				public void run() {
					e.getPlayer().getInventory().setItem(Main.getInstance().cfg.getInt("NickItem.Slot") - 1,
							Settings.this.getTool(e.getPlayer()));
				}
			}, 25L);
		}
		if (e.getPlayer().hasPermission("ranks.hadmin")) {
			e.setJoinMessage("§8[§a+§8] §4" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.admin")) {
			e.setJoinMessage("§8[§a+§8] §c" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.dev")) {
			e.setJoinMessage("§8[§a+§8] §3" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.mod")) {
			e.setJoinMessage("§8[§a+§8] §a" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.build")) {
			e.setJoinMessage("§8[§a+§8] §2" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.sup")) {
			e.setJoinMessage("§8[§a+§8] §9" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.elite")) {
			e.setJoinMessage("§8[§a+§8] §1" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.yt")) {
			e.setJoinMessage("§8[§a+§8] §5" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.mvp")) {
			e.setJoinMessage("§8[§a+§8] §b" + e.getPlayer().getName());
		} else if (e.getPlayer().hasPermission("ranks.vip")) {
			e.setJoinMessage("§8[§a+§8] §6" + e.getPlayer().getName());
		} else {
			e.setJoinMessage("§8[§a+§8] §7" + e.getPlayer().getName());
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (BuildCMD.allow.contains(p)) {
			e.setCancelled(false);
		} else {
			if (e.getAction() == Action.RIGHT_CLICK_AIR | e.getAction() == Action.RIGHT_CLICK_BLOCK
					| e.getAction() == Action.LEFT_CLICK_AIR | e.getAction() == Action.LEFT_CLICK_BLOCK) {
				if (e.getMaterial() == Material.REDSTONE_COMPARATOR) {
					p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 1);
					this.Invent1(p);
					p.openInventory(inv1);
					p.updateInventory();
				} else if (e.getMaterial() == Material.NETHER_STAR) {
					p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 1);
					ServerGroupObject go = TimoCloudAPI.getUniversalInstance().getServerGroup("Lobby");
					int i = go.getServers().size();
					int size = i;
					if (size == 1) {
						p.sendMessage(Main.pr + "§eDerzeit sind keine weiteren Lobby-Server online!");
					} else {
						String curServer = p.getServer().getServerName();
						while (size > 0) {
							ServerObject so = TimoCloudAPI.getUniversalInstance().getServer("Lobby-" + size);
							int c = so.getOnlinePlayerCount();
							this.Inv("Lobby-" + size, c, size, curServer);
							size--;
							p.openInventory(inv);
							p.updateInventory();
						}
					}
				} else if (e.getMaterial() == Material.ENDER_CHEST) {
					p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 1);
					p.performCommand("uc menu main");
				} else if (e.getMaterial() == Material.SKULL_ITEM) {
					p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 1);
					p.performCommand("friendsgui");
				}
			}
			if (e.getItem() == null) {
				return;
			}
			if (isTool(e.getItem(), e.getPlayer())) {
				boolean active = Main.getInstance().getSQL()
						.nickActive(Main.getInstance().getUtil().getUUID(e.getPlayer().getName()));
				Main.getInstance().getSQL().setNick(Main.getInstance().getUtil().getUUID(e.getPlayer().getName()),
						!active);
				e.getPlayer().getInventory().setItem(Main.getInstance().cfg.getInt("NickItem.Slot") - 1,
						getTool(e.getPlayer()));
				e.getPlayer().sendMessage(active ? Main.getInstance().getMessage("AutoNickDeactivated")
						: Main.getInstance().getMessage("AutoNickActivated"));
			}
		}
	}

	// Klick Config
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (BuildCMD.allow.contains(p)) {
			e.setCancelled(false);
		} else {
			e.setCancelled(true);
		}
		try {
			if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Lobby")
					&& !e.getCurrentItem().getItemMeta().getDisplayName().contains("Silent")
					&& !e.getCurrentItem().getItemMeta().getDisplayName().contains("Speed")) {
				String[] lobby = e.getCurrentItem().getItemMeta().getDisplayName().split("-");
				System.out.println(lobby[1]);
				String server = "Lobby-" + lobby[1];
				String[] splitserver = p.getServer().getServerName().split("-");
				int serverid = Integer.parseInt(splitserver[1]);
				int lobbyid = Integer.parseInt(lobby[1]);
				if (lobbyid == serverid) {
					p.sendMessage(Main.pr + "§cDu bist bereits mit diesem Server verbunden!");
				} else {
					p.sendMessage(Main.pr + "§aDu betrittst nun den Server §9Lobby-" + lobby[1]);
					p.closeInventory();
					BungeeUtil.sendPlayerToServer(p, server);
				}
			} else {
				if (e.getCurrentItem().getItemMeta().getDisplayName() == "§9§lJump on Liquid") {
					if (Settings.waterjump.contains(p.getName())) {
						Settings.waterjump.remove(p.getName());
						try {
							LobbyAPI.setWjump(p.getUniqueId().toString(), false);
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
						p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1, 1);
						this.deaktiv(17, inv1);
						p.updateInventory();
					} else {
						Settings.waterjump.add(p.getName());
						try {
							LobbyAPI.setWjump(p.getUniqueId().toString(), true);
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
						p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1, 1);
						this.aktiv(17, inv1);
						p.updateInventory();
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§9§lJump on Plate") {
					if (Settings.platejump.contains(p.getName())) {
						Settings.platejump.remove(p.getName());
						try {
							LobbyAPI.setPjump(p.getUniqueId().toString(), false);
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
						p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1, 1);
						this.deaktiv(26, inv1);
						p.updateInventory();
					} else {
						Settings.platejump.add(p.getName());
						try {
							LobbyAPI.setPjump(p.getUniqueId().toString(), true);
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
						p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1, 1);
						this.aktiv(26, inv1);
						p.updateInventory();
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Speed")) {
					if (Speed5.contains(p.getName()) && p.hasPermission("player.mvp")) {
						p.playSound(p.getLocation(), Sound.DRINK, 1, 1);
						Speed5.remove(p.getName());
						Speed10.add(p.getName());
						this.yellow(35, 10);
						p.setWalkSpeed(1);
						p.updateInventory();
					} else if (p.hasPermission("player.vip") && Speed1.contains(p.getName())) {
						p.playSound(p.getLocation(), Sound.DRINK, 1, 1);
						Speed1.remove(p.getName());
						Speed5.add(p.getName());
						this.yellow(35, 5);
						p.setWalkSpeed((float) 0.5);
						p.updateInventory();
					} else if (Speed10.contains(p.getName())) {
						p.playSound(p.getLocation(), Sound.DRINK, 1, 1);
						Speed10.remove(p.getName());
						Speed1.add(p.getName());
						this.yellow(35, 1);
						p.setWalkSpeed((float) 0.2);
						p.updateInventory();
					} else {
						p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
						p.sendMessage(Main.pr
								+ "§cKaufe dir zuerst den §6§lVIP §coder §d§lMVP §cRang, um dieses Feature nutzen zu können!");
						p.updateInventory();
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§aNächste Seite") {
					if (p.getOpenInventory().getItem(49).getItemMeta().getDisplayName() == "§9Seite §a§l1§6§l|§a§l3") {
						this.Invent2(p);
						p.openInventory(inv2);
					} else if (p.getOpenInventory().getItem(49).getItemMeta()
							.getDisplayName() == "§9Seite §a§l2§6§l|§a§l3") {
						this.Invent3(p);
						p.openInventory(inv3);
					}

				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§aVorherige Seite") {
					if (p.getOpenInventory().getItem(49).getItemMeta().getDisplayName() == "§9Seite §a§l2§6§l|§a§l3") {
						p.openInventory(inv1);
					} else if (p.getOpenInventory().getItem(49).getItemMeta()
							.getDisplayName() == "§9Seite §a§l3§6§l|§a§l3") {
						p.openInventory(inv2);
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§9§lDesign") {
					p.playSound(p.getLocation(), Sound.GLASS, 1, 1);
					this.ConfItems(p, 1, 1, (short) 0, "§fWeiß", Material.STAINED_GLASS, invfarbe);
					this.ConfItems(p, 1, 2, (short) 1, "§6Orange", Material.STAINED_GLASS, invfarbe);
					this.ConfItems(p, 1, 3, (short) 2, "§dMagenta", Material.STAINED_GLASS, invfarbe);
					this.ConfItems(p, 1, 4, (short) 3, "§bHellblau", Material.STAINED_GLASS, invfarbe);
					this.ConfItems(p, 1, 5, (short) 4, "§eGelb", Material.STAINED_GLASS, invfarbe);
					this.ConfItems(p, 1, 6, (short) 5, "§aHellgrün", Material.STAINED_GLASS, invfarbe);
					this.ConfItems(p, 1, 7, (short) 6, "§dPink", Material.STAINED_GLASS, invfarbe);
					this.ConfItems(p, 1, 9, (short) 7, "§8Grau", Material.STAINED_GLASS, invfarbe);
					this.ConfItems(p, 1, 10, (short) 8, "§7Hellgrau", Material.STAINED_GLASS, invfarbe);
					this.ConfItems(p, 1, 11, (short) 9, "§3Aquablau", Material.STAINED_GLASS, invfarbe);
					this.ConfItems(p, 1, 12, (short) 10, "§5Lila", Material.STAINED_GLASS, invfarbe);
					this.ConfItems(p, 1, 13, (short) 11, "§1Blau", Material.STAINED_GLASS, invfarbe);
					this.ConfItems(p, 1, 14, (short) 12, "Braun", Material.STAINED_GLASS, invfarbe);
					this.ConfItems(p, 1, 15, (short) 13, "§2Grün", Material.STAINED_GLASS, invfarbe);
					this.ConfItems(p, 1, 16, (short) 14, "§4Rot", Material.STAINED_GLASS, invfarbe);
					this.ConfItems(p, 1, 17, (short) 15, "§0Schwarz", Material.STAINED_GLASS, invfarbe);
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§fWeiß") {
					Settings.Color.remove(p);
					Settings.Color.put(p, "f");
					Settings.ItemCol.remove(p);
					Settings.ItemCol.put(p, 0);
					LobbyAPI.setColor(p.getUniqueId().toString(), "f");
					this.Invent1(p);
					this.Invent2(p);
					p.openInventory(inv2);
					LobbyScore.setSidebar(p, "f");
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§6Orange") {
					Settings.Color.remove(p);
					Settings.Color.put(p, "6");
					Settings.ItemCol.remove(p);
					Settings.ItemCol.put(p, 1);
					LobbyAPI.setColor(p.getUniqueId().toString(), "6");
					this.Invent1(p);
					this.Invent2(p);
					p.openInventory(inv2);
					LobbyScore.setSidebar(p, "6");
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§dMagenta") {
					Settings.Color.remove(p);
					Settings.Color.put(p, "d");
					Settings.ItemCol.remove(p);
					Settings.ItemCol.put(p, 2);
					LobbyAPI.setColor(p.getUniqueId().toString(), "d");
					this.Invent1(p);
					this.Invent2(p);
					p.openInventory(inv2);
					LobbyScore.setSidebar(p, "d");
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§bHellblau") {
					Settings.Color.remove(p);
					Settings.Color.put(p, "b");
					Settings.ItemCol.remove(p);
					Settings.ItemCol.put(p, 3);
					LobbyAPI.setColor(p.getUniqueId().toString(), "b");
					this.Invent1(p);
					this.Invent2(p);
					p.openInventory(inv2);
					LobbyScore.setSidebar(p, "b");
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§eGelb") {
					Settings.Color.remove(p);
					Settings.Color.put(p, "e");
					Settings.ItemCol.remove(p);
					Settings.ItemCol.put(p, 4);
					LobbyAPI.setColor(p.getUniqueId().toString(), "e");
					this.Invent1(p);
					this.Invent2(p);
					p.openInventory(inv2);
					LobbyScore.setSidebar(p, "e");
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§aHellgrün") {
					Settings.Color.remove(p);
					Settings.Color.put(p, "a");
					Settings.ItemCol.remove(p);
					Settings.ItemCol.put(p, 5);
					LobbyAPI.setColor(p.getUniqueId().toString(), "a");
					this.Invent1(p);
					this.Invent2(p);
					p.openInventory(inv2);
					LobbyScore.setSidebar(p, "a");
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§dPink") {
					Settings.Color.remove(p);
					Settings.Color.put(p, "d");
					Settings.ItemCol.remove(p);
					Settings.ItemCol.put(p, 6);
					LobbyAPI.setColor(p.getUniqueId().toString(), "d");
					this.Invent1(p);
					this.Invent2(p);
					p.openInventory(inv2);
					LobbyScore.setSidebar(p, "d");
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§8Grau") {
					Settings.Color.remove(p);
					Settings.Color.put(p, "8");
					Settings.ItemCol.remove(p);
					Settings.ItemCol.put(p, 7);
					LobbyAPI.setColor(p.getUniqueId().toString(), "8");
					this.Invent1(p);
					this.Invent2(p);
					p.openInventory(inv2);
					LobbyScore.setSidebar(p, "8");
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§7Hellgrau") {
					Settings.Color.remove(p);
					Settings.Color.put(p, "7");
					Settings.ItemCol.remove(p);
					Settings.ItemCol.put(p, 8);
					LobbyAPI.setColor(p.getUniqueId().toString(), "7");
					this.Invent1(p);
					this.Invent2(p);
					p.openInventory(inv2);
					LobbyScore.setSidebar(p, "7");
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§3Aquablau") {
					Settings.Color.remove(p);
					Settings.Color.put(p, "3");
					Settings.ItemCol.remove(p);
					Settings.ItemCol.put(p, 9);
					LobbyAPI.setColor(p.getUniqueId().toString(), "3");
					this.Invent1(p);
					this.Invent2(p);
					p.openInventory(inv2);
					LobbyScore.setSidebar(p, "3");
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§5Lila") {
					Settings.Color.remove(p);
					Settings.Color.put(p, "5");
					Settings.ItemCol.remove(p);
					Settings.ItemCol.put(p, 10);
					LobbyAPI.setColor(p.getUniqueId().toString(), "5");
					this.Invent1(p);
					this.Invent2(p);
					p.openInventory(inv2);
					LobbyScore.setSidebar(p, "5");
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§1Blau") {
					Settings.Color.remove(p);
					Settings.Color.put(p, "1");
					Settings.ItemCol.remove(p);
					Settings.ItemCol.put(p, 11);
					LobbyAPI.setColor(p.getUniqueId().toString(), "1");
					this.Invent1(p);
					this.Invent2(p);
					p.openInventory(inv2);
					LobbyScore.setSidebar(p, "1");
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "Braun") {
					Settings.Color.remove(p);
					Settings.Color.put(p, "c");
					Settings.ItemCol.remove(p);
					Settings.ItemCol.put(p, 12);
					LobbyAPI.setColor(p.getUniqueId().toString(), "c");
					this.Invent1(p);
					this.Invent2(p);
					p.openInventory(inv2);
					LobbyScore.setSidebar(p, "c");
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§2Grün") {
					Settings.Color.remove(p);
					Settings.Color.put(p, "2");
					Settings.ItemCol.remove(p);
					Settings.ItemCol.put(p, 13);
					LobbyAPI.setColor(p.getUniqueId().toString(), "2");
					this.Invent1(p);
					this.Invent2(p);
					p.openInventory(inv2);
					LobbyScore.setSidebar(p, "2");
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§4Rot") {
					Settings.Color.remove(p);
					Settings.Color.put(p, "4");
					Settings.ItemCol.remove(p);
					Settings.ItemCol.put(p, 14);
					LobbyAPI.setColor(p.getUniqueId().toString(), "4");
					this.Invent1(p);
					this.Invent2(p);
					p.openInventory(inv2);
					LobbyScore.setSidebar(p, "4");
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§0Schwarz") {
					Settings.Color.remove(p);
					Settings.Color.put(p, "0");
					Settings.ItemCol.remove(p);
					Settings.ItemCol.put(p, 15);
					LobbyAPI.setColor(p.getUniqueId().toString(), "0");
					this.Invent1(p);
					this.Invent2(p);
					p.openInventory(inv2);
					LobbyScore.setSidebar(p, "0");
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§aVorherige Seite") {
					if (p.getInventory() == inv2) {
						p.openInventory(inv1);
					} else if (p.getInventory() == inv3) {
						p.openInventory(inv2);
					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§9§lSilent-Lobby") {
					SkyRocketEffect ae = new SkyRocketEffect(Main.effectManager);
					ae.setEntity(p);
					ae.iterations = 5 * 20;
					ae.start();
					if (SilentLobby.contains(p.getName())) {
						SilentLobby.remove(p.getName());
						try {
							LobbyAPI.setSilent(p.getUniqueId().toString(), false);
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
						for (Player all : Bukkit.getOnlinePlayers()) {
							p.showPlayer(all);
							all.showPlayer(p);
							this.deaktiv(26, inv2);
							p.updateInventory();
							p.sendMessage(Main.pr + "§aDu hast die Silent-Lobby verlassen!");
						}
					} else {
						if (p.hasPermission("gg.silentlobby")) {
							SilentLobby.add(p.getName());
							try {
								LobbyAPI.setSilent(p.getUniqueId().toString(), true);
							} catch (SQLException e2) {
								e2.printStackTrace();
							}
							for (Player all : Bukkit.getOnlinePlayers()) {
								p.hidePlayer(all);
								all.hidePlayer(p);
								this.aktiv(26, inv2);
								p.sendMessage(Main.pr + "§aDu hast die Silent-Lobby betreten!");
								p.updateInventory();
							}
						} else {
							p.sendMessage(Main.pr
									+ "§cKaufe dir zuerst den §6§lVIP §coder §d§lMVP §cRang, um dieses Feature nutzen zu können!");
						}

					}
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§9§lSpieler Verstecken") {
					p.performCommand("hide");
				} else if (e.getCurrentItem().getItemMeta().getDisplayName() == "§9§lLet player ride on you") {
					if (Settings.Ride.contains(p.getName())) {
						try {
							LobbyAPI.setRide(p.getUniqueId().toString(), false);
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
						this.deaktiv(17, inv3);
						p.updateInventory();
					} else {
						try {
							LobbyAPI.setRide(p.getUniqueId().toString(), true);
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
						this.aktiv(17, inv3);
						p.updateInventory();
					}
				} else if (e.getInventory().getName().equalsIgnoreCase("§a\u25B8 §9§lNAVIGATOR §a\u25C2")) {
					if (e.getCurrentItem().getType() == Material.EXP_BOTTLE) {
						p.teleport(Data.spawn1);
						p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);
						p.sendMessage(Main.pr + "§7Du hast dich zum §eEvent §7teleportiert!");
						p.closeInventory();
					}
					if (e.getCurrentItem().getType() == Material.FISHING_ROD) {
						p.teleport(Data.spawn2);
						p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);
						p.sendMessage(Main.pr + "§7Du hast dich zu §e1vs1 §7teleportiert!");
						p.closeInventory();
					}
					if (e.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
						p.teleport(Data.spawn3);
						p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);
						p.sendMessage(Main.pr + "§7Du hast dich zu §eFreeBuild §7teleportiert!");
						p.closeInventory();
					}
					if (e.getCurrentItem().getType() == Material.GOLDEN_APPLE) {
						p.teleport(Data.spawn4);
						p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);
						p.sendMessage(Main.pr + "§7Du hast dich zu §eUHC §7teleportiert!");
						p.closeInventory();
					}
					if (e.getCurrentItem().getType() == Material.CHAINMAIL_CHESTPLATE) {
						p.teleport(Data.spawn5);
						p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);
						p.sendMessage(Main.pr + "§7Du hast dich zu §eSurvivalGames §7teleportiert!");
						p.closeInventory();
					}
					if (e.getCurrentItem().getType() == Material.ENDER_PEARL) {
						p.teleport(Data.spawn6);
						p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);
						p.sendMessage(Main.pr + "§cDieser Spielmodus ist bislang nicht verfügbar!");
						p.closeInventory();
					}
					if (e.getCurrentItem().getType() == Material.NETHER_STAR) {
						p.teleport(Data.spawn);
						p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);
						p.sendMessage(Main.pr + "§7Du hast dich zum §eSpawn §7teleportiert!");
						p.closeInventory();
					}
				}
			}
		} catch (NullPointerException ex) {

		} catch (ArrayIndexOutOfBoundsException ex1) {

		}
	}

	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {
		this.jump = 2;
	}

	@EventHandler
	public void onDJ(PlayerToggleFlightEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("player.vip")) {
			if (p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE) {
				e.setCancelled(true);
				p.setAllowFlight(false);
				p.setFlying(false);
				p.setVelocity(p.getLocation().getDirection().multiply(2).add(new Vector(0, this.jump, 0)));
				this.jump = 1;
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		Block block = loc.getBlock();
		if (p.hasPermission("player.vip")) {
			if (p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE) {
				if (p.getLocation().add(0, -1, 0).getBlock().getType() != Material.AIR) {
					p.setAllowFlight(true);
				}
			}
		}
		if (waterjump.contains(p.getName())) {
			if (block.isLiquid()) {
				p.setAllowFlight(true);
				p.setVelocity(p.getLocation().getDirection().setY(2));
				if (p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE) {
					p.setAllowFlight(false);
				}
			}
		}
		if (platejump.contains(p.getName())) {
			if (p.getLocation().getBlock().getType() == Material.GOLD_PLATE
					|| p.getLocation().getBlock().getType() == Material.IRON_PLATE
					|| p.getLocation().getBlock().getType() == Material.STONE_PLATE
					|| p.getLocation().getBlock().getType() == Material.WOOD_PLATE) {
				p.setAllowFlight(true);
				p.setVelocity(p.getLocation().getDirection().multiply(3).setY(2));
				if (p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE) {
					p.setAllowFlight(false);
				}
			}
		}
	}

	private void aktiv(int i, Inventory inv) {
		ItemStack clayad = new ItemStack(Material.STAINED_CLAY);
		ItemMeta clayadm = clayad.getItemMeta();
		clayad.setDurability((short) 5);
		clayadm.setDisplayName("§a§lAKTIVIERT!");
		clayad.setItemMeta(clayadm);
		inv.setItem(i, clayad);
	}

	private void deaktiv(int i, Inventory inv) {
		ItemStack clayad = new ItemStack(Material.STAINED_CLAY);
		ItemMeta clayadm = clayad.getItemMeta();
		clayad.setDurability((short) 14);
		clayadm.setDisplayName("§c§lDEAKTIVIERT!");
		clayad.setItemMeta(clayadm);
		inv.setItem(i, clayad);
	}

	private void yellow(int i, int amount) {
		ItemStack SpeedClay = new ItemStack(Material.STAINED_CLAY);
		ItemMeta SpeedCM = SpeedClay.getItemMeta();
		SpeedClay.setDurability((short) 4);
		SpeedCM.setDisplayName("§a§l" + amount);
		SpeedClay.setItemMeta(SpeedCM);
		SpeedClay.setAmount(amount);
		inv1.setItem(i, SpeedClay);
	}

	private void ConfItems(Player p, int amount, int where, short durab, String dname, Material mat, Inventory inv) {
		ItemStack itemstack = new ItemStack(mat);
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemstack.setDurability((short) durab);
		itemstack.setAmount(amount);
		itemmeta.setDisplayName(dname);
		itemstack.setItemMeta(itemmeta);
		inv.setItem(where, itemstack);
		p.openInventory(inv);
	}

	private void Invent3(Player p) {

		int i = 8;
		while (i >= 0) {
			ItemStack GlasPane = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemMeta gpm = GlasPane.getItemMeta();
			int color =  Settings.ItemCol.get(p);
			GlasPane.setDurability((short) color);
			gpm.setDisplayName("§a\u25B8 §9§lKONFIGURATIONSMENÜ §a\u25C2");
			GlasPane.setItemMeta(gpm);
			inv3.setItem(i, GlasPane);
			--i;
		}

		int color = Settings.ItemCol.get(p);

		ItemStack GlasPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) color);
		ItemMeta gpm = GlasPane.getItemMeta();
		gpm.setDisplayName("§a\u25B8 §9§lKONFIGURATIONSMENÜ §a\u25C2");
		GlasPane.setItemMeta(gpm);

		inv3.setItem(36, GlasPane);
		inv3.setItem(37, GlasPane);
		inv3.setItem(38, GlasPane);
		inv3.setItem(39, GlasPane);
		inv3.setItem(40, GlasPane);
		inv3.setItem(41, GlasPane);
		inv3.setItem(42, GlasPane);
		inv3.setItem(43, GlasPane);
		inv3.setItem(44, GlasPane);

		this.ConfItems(p, 1, 49, (short) 0, "§9Seite §a§l3§6§l|§a§l3", Material.PAPER, inv3);

		ItemStack ArrowL = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		ItemMeta ArrowLM = ArrowL.getItemMeta();
		ArrowLM.setDisplayName("§aVorherige Seite");
		ArrowL.setItemMeta(ArrowLM);
		SkullMeta SM = (SkullMeta) ArrowL.getItemMeta();
		SM.setOwner("MHF_ArrowLeft");
		ArrowL.setItemMeta(SM);
		inv3.setItem(45, ArrowL);
		p.openInventory(inv3);

		ItemStack ride = new ItemStack(Material.TRIPWIRE_HOOK, 1);
		ItemMeta ridemeta = ride.getItemMeta();
		ridemeta.setDisplayName("§9§lLet player ride on you");
		ride.setItemMeta(ridemeta);
		inv3.setItem(9, ride);

		if (Settings.Ride.contains(p.getName())) {
			this.aktiv(17, inv3);
		} else {
			this.deaktiv(17, inv3);
		}

	}

	private void Invent2(Player p) {
		int color = Settings.ItemCol.get(p);

		int v = 8;
		while (v >= 0) {
			ItemStack GlasPane = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemMeta gpm = GlasPane.getItemMeta();
			color = Settings.ItemCol.get(p);
			GlasPane.setDurability((short) color);
			gpm.setDisplayName("§a\u25B8 §9§lKONFIGURATIONSMENÜ §a\u25C2");
			GlasPane.setItemMeta(gpm);
			inv2.setItem(v, GlasPane);
			--v;
		}

		this.ConfItems(p, 1, 9, (short) 0, "§9§lDesign", Material.STAINED_GLASS, inv2);

		this.ConfItems(p, 1, 17, (short) color, "", Material.STAINED_CLAY, inv2);
		ItemStack GlasPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) color);
		ItemMeta gpm = GlasPane.getItemMeta();
		gpm.setDisplayName("§a\u25B8 §9§lKONFIGURATIONSMENÜ §a\u25C2");
		GlasPane.setItemMeta(gpm);

		inv2.setItem(36, GlasPane);
		inv2.setItem(37, GlasPane);
		inv2.setItem(38, GlasPane);
		inv2.setItem(39, GlasPane);
		inv2.setItem(40, GlasPane);
		inv2.setItem(41, GlasPane);
		inv2.setItem(42, GlasPane);
		inv2.setItem(43, GlasPane);
		inv2.setItem(44, GlasPane);

		this.ConfItems(p, 1, 49, (short) 0, "§9Seite §a§l2§6§l|§a§l3", Material.PAPER, inv2);

		ItemStack ArrowL = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		ItemMeta ArrowLM = ArrowL.getItemMeta();
		ArrowLM.setDisplayName("§aVorherige Seite");
		ArrowL.setItemMeta(ArrowLM);
		SkullMeta SM1 = (SkullMeta) ArrowL.getItemMeta();
		SM1.setOwner("MHF_ArrowLeft");
		ArrowL.setItemMeta(SM1);
		inv2.setItem(45, ArrowL);

		ItemStack ArrowR = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		ItemMeta ArrowRM = ArrowR.getItemMeta();
		ArrowRM.setDisplayName("§aNächste Seite");
		ArrowR.setItemMeta(ArrowRM);
		SkullMeta ASM = (SkullMeta) ArrowR.getItemMeta();
		ASM.setOwner("MHF_ArrowRight");
		ArrowR.setItemMeta(ASM);
		inv2.setItem(53, ArrowR);

		ItemStack Silent = new ItemStack(Material.TNT);
		ItemMeta SilentM = Silent.getItemMeta();
		SilentM.setDisplayName("§9§lSilent-Lobby");
		Silent.setItemMeta(SilentM);
		inv2.setItem(18, Silent);

		ItemStack Hide = new ItemStack(Material.BLAZE_ROD);
		ItemMeta HideM = Silent.getItemMeta();
		HideM.setDisplayName("§9§lSpieler Verstecken");
		Hide.setItemMeta(HideM);
		inv2.setItem(27, Hide);

		if (SilentLobby.contains(p.getName())) {
			this.aktiv(26, inv2);
		} else {
			this.deaktiv(26, inv2);
		}
	}

	private void Invent1(Player p) {
		ItemStack ArrowR = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		ItemMeta ArrowRM = ArrowR.getItemMeta();
		ArrowRM.setDisplayName("§aNächste Seite");
		ArrowR.setItemMeta(ArrowRM);
		SkullMeta SM = (SkullMeta) ArrowR.getItemMeta();
		SM.setOwner("MHF_ArrowRight");
		ArrowR.setItemMeta(SM);
		int i = 8;
		this.ConfItems(p, 1, 9, (short) 0, "§9§lJump on Liquid", Material.WATER_BUCKET, inv1);
		this.ConfItems(p, 1, 18, (short) 0, "§9§lJump on Plate", Material.GOLD_PLATE, inv1);
		this.ConfItems(p, 1, 27, (short) 8226, "§9§lLobby-Speed", Material.POTION, inv1);
		this.ConfItems(p, 1, 49, (short) 0, "§9Seite §a§l1§6§l|§a§l3", Material.PAPER, inv1);
		while (i >= 0) {
			int color = Settings.ItemCol.get(p);
			ItemStack GlasPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) color);
			ItemMeta gpm = GlasPane.getItemMeta();
			gpm.setDisplayName("§a\u25B8 §9§lKONFIGURATIONSMENÜ §a\u25C2");
			GlasPane.setItemMeta(gpm);
			inv1.setItem(i, GlasPane);
			--i;
		}
		inv1.setItem(53, ArrowR);
		if (Settings.waterjump.contains(p.getName())) {
			this.aktiv(17, inv1);
		} else {
			this.deaktiv(17, inv1);
		}
		if (Settings.platejump.contains(p.getName())) {
			this.aktiv(26, inv1);
		} else {
			this.deaktiv(26, inv1);
		}
		if (Speed5.contains(p.getName())) {
			this.yellow(35, 5);
		} else if (Speed10.contains(p.getName())) {
			this.yellow(35, 10);
		} else {
			this.yellow(35, 1);
		}
		int color = Settings.ItemCol.get(p);
		ItemStack GlasPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) color);
		ItemMeta gpm = GlasPane.getItemMeta();
		gpm.setDisplayName("§a\u25B8 §9§lKONFIGURATIONSMENÜ §a\u25C2");
		GlasPane.setItemMeta(gpm);
		inv1.setItem(36, GlasPane);
		inv1.setItem(37, GlasPane);
		inv1.setItem(38, GlasPane);
		inv1.setItem(39, GlasPane);
		inv1.setItem(40, GlasPane);
		inv1.setItem(41, GlasPane);
		inv1.setItem(42, GlasPane);
		inv1.setItem(43, GlasPane);
		inv1.setItem(44, GlasPane);
	}

	private void Inv(String name, int online, int where, String curServer) {
		String[] name2 = curServer.split("-");
		int size2 = Integer.parseInt(name2[1]);
		ItemStack emer = new ItemStack(Material.EMERALD);
		ItemMeta emerm = emer.getItemMeta();
		emerm.setDisplayName("§a" + name);
		String o = "§e" + online + " §6Spieler online!";
		ArrayList<String> list = new ArrayList<String>();
		list.add(o);
		if (size2 == where) {
			list.add("§cDerzeit verbunden!");
		} else {
			list.add("§aKlicke zum verbinden!");
		}
		emerm.setLore(list);
		emer.setItemMeta(emerm);
		where--;
		inv.setItem(where, emer);
	}

	public boolean isTool(ItemStack item, Player p) {
		if (!item.getType().equals(Material.valueOf(Main.getInstance().getString("NickItem.Type")))) {
			return false;
		}
		if (item.getItemMeta() == null) {
			return false;
		}
		if (item.getItemMeta().getDisplayName() == null) {
			return false;
		}
		String name = Main.getInstance().getSQL().nickActive(Main.getInstance().getUtil().getUUID(p.getName()))
				? Main.getInstance().getString("NickItem.NameOn")
				: Main.getInstance().getString("NickItem.NameOff");
		if (item.getItemMeta().getDisplayName().equals(name)) {
			return true;
		}
		return false;
	}

	public ItemStack getTool(Player p) {
		ItemStack nicktool = new ItemStack(Material.valueOf(Main.getInstance().getString("NickItem.Type")));
		ItemMeta nicktoolm = nicktool.getItemMeta();
		nicktoolm.setDisplayName(
				Main.getInstance().getSQL().nickActive(Main.getInstance().getUtil().getUUID(p.getName()))
						? Main.getInstance().getString("NickItem.NameOn")
						: Main.getInstance().getString("NickItem.NameOff"));
		if (Main.getInstance().cfg.getBoolean("NickItem.Enchanted")) {
			nicktoolm.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
			nicktoolm.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
			nicktoolm.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
		}
		List<String> lore = new ArrayList<String>();
		for (String s : Main.getInstance().cfg.getStringList("NickItem.Desc")) {
			lore.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		nicktoolm.setLore(lore);
		nicktool.setItemMeta(nicktoolm);
		return nicktool;
	}
}
