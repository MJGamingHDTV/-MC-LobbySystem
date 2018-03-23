package eu.mj.gg.lobby.methods;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import eu.mj.gg.lobby.main.Main;

public class BungeeUtil {
    public static void sendPlayerToServer(Player p, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        p.sendPluginMessage(Main.plugin, "BungeeCord", out.toByteArray());
    }
}
