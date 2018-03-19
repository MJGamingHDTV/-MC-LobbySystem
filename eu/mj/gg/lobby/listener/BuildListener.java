package eu.mj.gg.lobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import eu.mj.gg.lobby.commands.BuildCMD;

public class BuildListener implements Listener {
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (!BuildCMD.allow.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBuild(BlockPlaceEvent e) {
		if (!BuildCMD.allow.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
}
