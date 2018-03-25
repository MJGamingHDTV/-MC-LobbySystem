package eu.mj.gg.lobby.functions;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.mj.gg.lobby.main.Main;

public class Compass implements Listener {
	static ArrayList<Player> incd = new ArrayList<Player>();
	static int cd;
	static int cdc;
	private eu.mj.gg.lobby.main.Main pl;

	public Compass(eu.mj.gg.lobby.main.Main Main) {
		this.pl = Main;
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		try {

			if (e.getItem().getType() == Material.COMPASS) {
				if (incd.isEmpty()) {
					if (!Bukkit.getScheduler().isCurrentlyRunning(cd)) {
						incd.add(p);
						cdc = 0;
						Inventory inv = Bukkit.createInventory(null, 3 * 9, "§a\u25B8 §9§lNAVIGATOR §a\u25C2");
						ItemStack a = new ItemStack(Material.EXP_BOTTLE);
						ItemMeta am = a.getItemMeta();
						am.setDisplayName("§aEvent");
						a.setItemMeta(am);

						ItemStack a1 = new ItemStack(Material.DIAMOND_SWORD);
						ItemMeta am1 = a1.getItemMeta();
						am1.setDisplayName("§3FreeBuild");
						a1.setItemMeta(am1);

						ItemStack a2 = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
						ItemMeta am2 = a2.getItemMeta();
						am2.setDisplayName("§eSurvivalGames");
						a2.setItemMeta(am2);

						ItemStack a3 = new ItemStack(Material.ENDER_PEARL);
						ItemMeta am3 = a3.getItemMeta();
						am3.setDisplayName("§cComming Soon...");
						a3.setItemMeta(am3);

						ItemStack a4 = new ItemStack(Material.GOLDEN_APPLE);
						ItemMeta am4 = a4.getItemMeta();
						am4.setDisplayName("§6UHC");
						a4.setItemMeta(am4);

						ItemStack a5 = new ItemStack(Material.FISHING_ROD);
						ItemMeta am5 = a5.getItemMeta();
						am5.setDisplayName("§b1vs1");
						a5.setItemMeta(am5);

						ItemStack a51 = new ItemStack(Material.NETHER_STAR);
						ItemMeta am51 = a51.getItemMeta();
						am51.setDisplayName("§6Spawn");
						a51.setItemMeta(am51);

						ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) Settings.ItemCol.get(p).shortValue());
						ItemMeta glasss = glass.getItemMeta();
						glasss.setDisplayName("§a\u25B8 §9§lNAVIGATOR §a\u25C2");
						glass.setItemMeta(glasss);

						int i = 26;
						while (i >= 0) {
							inv.setItem(i, glass);
							--i;
						}

						p.openInventory(inv);

						cd = Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {

							@Override
							public void run() {
								cdc++;
								p.playSound(p.getLocation(), Sound.NOTE_STICKS, 1, 1);
								if (cdc == 1) {
									int i = 25;
									while (i >= 0) {
										inv.setItem(i, glass);
										--i;
									}
									inv.setItem(9, a4);
									inv.setItem(19, glass);
									inv.setItem(23, glass);
									inv.setItem(20, glass);
									inv.setItem(23, glass);
									inv.setItem(24, glass);
									inv.setItem(25, glass);
									inv.setItem(26, glass);
									return;
								}
								if (cdc == 2) {
									inv.clear();
									int i = 23;
									while (i >= 0) {
										inv.setItem(i, glass);
										--i;
									}
									inv.setItem(10, a4);
									inv.setItem(0, a5);
									inv.setItem(18, a5);
									inv.setItem(23, glass);
									inv.setItem(19, glass);
									inv.setItem(20, glass);
									inv.setItem(24, glass);
									inv.setItem(25, glass);
									inv.setItem(26, glass);
									return;
								}
								if (cdc == 3) {
									inv.clear();
									int i = 22;
									while (i >= 0) {
										inv.setItem(i, glass);
										--i;
									}
									inv.setItem(11, a4);
									inv.setItem(1, a5);
									inv.setItem(19, a3);
									inv.setItem(9, a51);
									inv.setItem(23, glass);
									inv.setItem(18, glass);
									inv.setItem(20, glass);
									inv.setItem(24, glass);
									inv.setItem(25, glass);
									inv.setItem(26, glass);
									return;
								}
								if (cdc == 4) {
									inv.clear();
									int i = 20;
									while (i >= 0) {
										inv.setItem(i, glass);
										--i;
									}
									inv.setItem(12, a4);
									inv.setItem(2, a5);
									inv.setItem(20, a3);
									inv.setItem(10, a51);
									inv.setItem(0, a);
									inv.setItem(18, a2);
									inv.setItem(23, glass);
									inv.setItem(19, glass);
									inv.setItem(20, glass);
									inv.setItem(24, glass);
									inv.setItem(25, glass);
									inv.setItem(26, glass);
									return;
								}
								if (cdc == 5) {

									inv.clear();
									int i = 19;
									while (i >= 0) {
										inv.setItem(i, glass);
										--i;
									}
									inv.setItem(13, a4);
									inv.setItem(3, a5);
									inv.setItem(21, a3);
									inv.setItem(11, a51);
									inv.setItem(1, a);
									inv.setItem(19, a2);
									inv.setItem(9, a1);
									inv.setItem(23, glass);
									inv.setItem(19, glass);
									inv.setItem(20, glass);
									inv.setItem(24, glass);
									inv.setItem(25, glass);
									inv.setItem(26, glass);
									return;
								}
								if (cdc == 6) {
									inv.clear();
									int i = 19;
									while (i >= 0) {
										inv.setItem(i, glass);
										--i;
									}
									inv.setItem(14, a4);
									inv.setItem(4, a5);
									inv.setItem(22, a3);
									inv.setItem(12, a51);
									inv.setItem(2, a);
									inv.setItem(20, a2);
									inv.setItem(10, a1);
									inv.setItem(23, glass);
									inv.setItem(19, glass);
									inv.setItem(21, glass);
									inv.setItem(24, glass);
									inv.setItem(25, glass);
									inv.setItem(26, glass);
									return;
								}
								if (cdc == 7) {
									inv.clear();
									int i = 19;
									while (i >= 0) {
										inv.setItem(i, glass);
										--i;
									}
									inv.setItem(15, a4);
									inv.setItem(5, a5);
									inv.setItem(23, a3);
									inv.setItem(13, a51);
									inv.setItem(3, a);
									inv.setItem(21, a2);
									inv.setItem(11, a1);
									inv.setItem(20, glass);
									inv.setItem(22, glass);
									inv.setItem(24, glass);
									inv.setItem(25, glass);
									inv.setItem(26, glass);
									Bukkit.getScheduler().cancelTask(cd);
									incd.clear();

								}
								if (cdc == 8) {
									Bukkit.getScheduler().cancelTask(cd);
									incd.clear();

								}

							}
						}, 1, 1);
					} else {
						Bukkit.getScheduler().cancelTask(cd);
						p.sendMessage(Main.pr + "§cEs ist ein Fehler aufgetreten! Bitte öffne den Navigator erneut!");
						p.closeInventory();
					}
				} else {
					p.sendMessage(Main.pr
							+ "§cZu viele Spieler verwenden den Navigator gleichzeitig, bitte warte einen Moment!");
				}
			}

		} catch (Exception e1) {
		}

	}
}
