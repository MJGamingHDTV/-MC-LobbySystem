package eu.mj.gg.lobby.listener;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.comphenix.protocol.ProtocolLibrary;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import eu.mj.gg.lobby.commands.ServerNPC;
import eu.mj.gg.lobby.main.Main;
import eu.mj.gg.lobby.methods.BungeeUtil;
import eu.mj.gg.lobby.methods.LobbyScore;

public class NPCListener implements Listener {

	@EventHandler
	public void RightClick(PlayerInteractEntityEvent e) {
		e.setCancelled(true);
		Player p = e.getPlayer();
		try {
			Villager v = (Villager) e.getRightClicked();
			UUID villager = v.getUniqueId();
			String server = ServerNPC.cfg.getString("Villagers." + villager + ".Server");
			if (server == "Survival") {
				if (getPVersion(p) == true) {
					String state = TimoCloudAPI.getUniversalInstance().getServer(server).getState();
					if (state == "OFFLINE") {
						p.sendMessage(Main.pr + "§cDer Server ist derzeit offline!");
					} else {
						LobbyScore.sendTabTitle(p, null, null);
						BungeeUtil.sendPlayerToServer(p, server);
					}
				} else {
					p.sendMessage(Main.pr
							+ "§cDieser Server ist in der neusten Minecraft-Version, bitte update dein Minecraft!");
				}
			} else {
				String state = TimoCloudAPI.getUniversalInstance().getServer(server).getState();
				if (state == "OFFLINE") {
					p.sendMessage(Main.pr + "§cDer Server ist derzeit offline!");
				} else {
					LobbyScore.sendTabTitle(p, null, null);
					BungeeUtil.sendPlayerToServer(p, server);
				}
			}
		} catch (ClassCastException c) {

		}
	}

	@EventHandler
	public void LeftClick(EntityDamageByEntityEvent e) {
		e.setCancelled(true);
		Player p = (Player) e.getDamager();
		try {
			Villager v = (Villager) e.getEntity();
			UUID villager = v.getUniqueId();
			String server = ServerNPC.cfg.getString("Villagers." + villager + ".Server");
			if (server == "Survival") {
				if (getPVersion(p) == true) {
					String state = TimoCloudAPI.getUniversalInstance().getServer(server).getState();
					if (state == "OFFLINE") {
						p.sendMessage(Main.pr + "§cDer Server ist derzeit offline!");
					} else {
						LobbyScore.sendTabTitle(p, null, null);
						BungeeUtil.sendPlayerToServer(p, server);
					}
				} else {
					p.sendMessage(Main.pr
							+ "§cDer Server ist in der neusten Minecraft-Version, bitte update dein Minecraft!");
				}
			} else {
				String state = TimoCloudAPI.getUniversalInstance().getServer(server).getState();
				if (state == "OFFLINE") {
					p.sendMessage(Main.pr + "§cDer Server ist derzeit offline!");
				} else {
					LobbyScore.sendTabTitle(p, null, null);
					BungeeUtil.sendPlayerToServer(p, server);
				}
			}
		} catch (ClassCastException c) {

		}
	}

	public boolean getPVersion(Player p) {
		if (ProtocolLibrary.getProtocolManager().getProtocolVersion(p) >= 335) {
			return true;
		} else {
			return false;
		}
	}
}
