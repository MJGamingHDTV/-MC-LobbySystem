package eu.mj.gg.lobby.commands;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.mj.gg.lobby.main.Main;

public class BuildCMD implements CommandExecutor {
	public static ArrayList<Player> allow = new ArrayList<>();
	HashMap<String, ItemStack[]> inventory = new HashMap<>();
	HashMap<String, ItemStack[]> armor = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("build")) {
				if (p.hasPermission("gg.build")) {
					if (allow.contains(p)) {
						allow.remove(p);
						p.setGameMode(GameMode.SURVIVAL);
						p.setAllowFlight(false);
						p.getInventory().clear();
						p.sendMessage(Main.pr + "§cDu kannst nun nicht mehr bauen");
						ItemStack[] contents = inventory.get(p.getName());
						p.getInventory().setContents(contents);
						ItemStack[] armorcon = armor.get(p.getName());
						p.getInventory().setArmorContents(armorcon);
					} else {
						allow.add(p);
						p.setGameMode(GameMode.CREATIVE);
						p.setAllowFlight(true);
						inventory.put(p.getName(), p.getInventory().getContents());
						armor.put(p.getName(), p.getInventory().getArmorContents());
						p.getInventory().clear();
						p.sendMessage(Main.pr + "§aDu kannst nun bauen!");
					}
				} else {
					p.sendMessage(Main.pr + "§cDazu hast du keine Rechte!");
				}

				return true;
			}
		} else {
			sender.sendMessage(Main.pr + "§cDu musst ein Spieler sein um diesen Befehl nutzen zu können!");
		}
		return true;
	}

}
