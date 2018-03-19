package eu.mj.gg.lobby.methods;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.entity.Player;

import eu.mj.gg.lobby.main.Main;

public class BungeeUtil {
	public static void sendPlayerToServer(Player p, String server) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);

		try {
			out.writeUTF("Connect");
			out.writeUTF(server);
		} catch (IOException eee) {
			// Fehler
		}

		p.sendPluginMessage(Main.plugin, "BungeeCord", b.toByteArray());
	}
}
