package eu.mj.gg.lobby.listener;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import eu.mj.gg.lobby.data.Functions;

public class AFKListener implements Listener{
	public Functions functions = new Functions();
	public static HashMap<Player, Location> radius = new HashMap<Player, Location>();
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e){
		AFK.time.put(e.getPlayer(), 0);
		if(functions.isAFK(e.getPlayer())){
			functions.AFK(e.getPlayer(), false);
		}
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		AFK.time.put(e.getPlayer(), 0);
	}
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e){
		AFK.time.remove(e.getPlayer());
	}
	@EventHandler
	public void onPlayerLeave2(PlayerKickEvent e){
		AFK.time.remove(e.getPlayer());
	}
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		Location l = e.getPlayer().getLocation();
		int radius1 = 5;
		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();
		Location loc2 = null;
		int x2 = 0;
		int y2 = 0;
		int z2 = 0;
		try{
			loc2 = radius.get(e.getPlayer());
			x2 = loc2.getBlockX() + radius1; 
			y2 = loc2.getBlockY() + radius1;
			z2 = loc2.getBlockZ() + radius1;
		}catch(Exception er){
			
		}
		if(loc2 != null){
			if(x >= x2 || y >= y2 || z >= z2){
				AFK.time.put(e.getPlayer(), 0);
				if(functions.isAFK(e.getPlayer())){
					functions.AFK(e.getPlayer(), false);
				}
			}
		}
	}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		AFK.time.put(e.getPlayer(), 0);
		if(functions.isAFK(e.getPlayer())){
			functions.AFK(e.getPlayer(), false);
		}
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e){
		AFK.time.put(e.getPlayer(), 0);
		if(functions.isAFK(e.getPlayer())){
			functions.AFK(e.getPlayer(), false);
		}
	}
	
	@EventHandler
	public void onItemHeldChange(PlayerItemHeldEvent e){
		AFK.time.put(e.getPlayer(), 0);
		if(functions.isAFK(e.getPlayer())){
			functions.AFK(e.getPlayer(), false);
		}
	}
	
	@EventHandler
	public void onPlayerBedLeave(PlayerBedLeaveEvent e){
		AFK.time.put(e.getPlayer(), 0);
		if(functions.isAFK(e.getPlayer())){
			functions.AFK(e.getPlayer(), false);
		}
	}
	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent e){
		AFK.time.put(e.getPlayer(), 0);
		if(functions.isAFK(e.getPlayer())){
			functions.AFK(e.getPlayer(), false);
		}
	}
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e){
		AFK.time.put(e.getPlayer(), 0);
		if(functions.isAFK(e.getPlayer())){
			functions.AFK(e.getPlayer(), false);
		}
	}
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent e){
		AFK.time.put(e.getPlayer(), 0);
		if(functions.isAFK(e.getPlayer())){
			functions.AFK(e.getPlayer(), false);
		}
	}
	
	
}
