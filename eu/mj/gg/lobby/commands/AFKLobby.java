package eu.mj.gg.lobby.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import eu.mj.gg.lobby.main.Main;

public class AFKLobby implements CommandExecutor {

	public File f = new File("plugins/GGLobby/", "afk.yml");
	public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName() == "setafklobby") {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("set.afk")) {
					Location loc = p.getLocation();
					double y = loc.getY();
					double x = loc.getX();
					double z = loc.getZ();
					float yaw = loc.getYaw();
					float pitch = loc.getPitch();
					String World = loc.getWorld().getName();
					cfg.set("AFK.Y", y);
					cfg.set("AFK.X", x);
					cfg.set("AFK.Z", z);
					cfg.set("AFK.Yaw", yaw);
					cfg.set("AFK.Pitch", pitch);
					cfg.set("AFK.World", World);
					try {
						cfg.save(f);
						p.sendMessage(Main.pr + "§aDer AFK Spawn wurde erfolgreich gesetzt!");
					} catch (IOException e) {
						e.printStackTrace();
						p.sendMessage(Main.pr
								+ "§cEs ist ein Fehler aufgetreten, sieh in der Konsole nach für mehr Informationen!");
					}
				}
			}
		}
		return false;
	}

}
