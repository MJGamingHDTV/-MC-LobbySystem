package eu.mj.gg.lobby.listener;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import eu.mj.gg.lobby.main.Main;

public class Chat implements Listener, CommandExecutor {
	private String su = " §8•§f ";
	private String hadmin = "§4[HeadAdmin] ";
	private String admin = "§c[Admin] ";
	private String des = "§c[Designer] ";
	private String dev = "§3[Developer] ";
	private String mod = "§a[Moderator] ";
	private String build = "§2[Builder] ";
	private String sup = "§9[Supporter] ";
	private String elite = "§1[Elite] ";
	private String yt = "§5[YouTube] ";
	private String vip = "§6[VIP] ";
	private String player = "§7";

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String msg = e.getMessage();
		for (Player all : Bukkit.getOnlinePlayers()) {
			String name = all.getName();
			String nmsg1 = msg.replace("@" + name, "§9" + name + "§f");
			String nmsg = nmsg1.replaceAll("&", "§");
			if (p.hasPermission("ranks.hadmin")) {
				e.setFormat(hadmin + p.getName() + su + nmsg);
			} else if (p.hasPermission("ranks.admin")) {
				e.setFormat(admin + p.getName() + su + nmsg);
			} else if (p.hasPermission("ranks.des")) {
				e.setFormat(des + p.getName() + su + nmsg);
			} else if (p.hasPermission("ranks.dev")) {
				e.setFormat(dev + p.getName() + su + nmsg);
			} else if (p.hasPermission("ranks.mod")) {
				e.setFormat(mod + p.getName() + su + nmsg);
			} else if (p.hasPermission("ranks.build")) {
				e.setFormat(build + p.getName() + su + nmsg);
			} else if (p.hasPermission("ranks.sup")) {
				e.setFormat(sup + p.getName() + su + nmsg);
			} else if (p.hasPermission("ranks.elite")) {
				e.setFormat(elite + p.getName() + su + nmsg);
			} else if (p.hasPermission("ranks.yt")) {
				e.setFormat(yt + p.getName() + su + nmsg);
			} else if (p.hasPermission("ranks.vip")) {
				e.setFormat(vip + p.getName() + su + nmsg);
			} else {
				e.setFormat(player + p.getName() + su + nmsg);
			}
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("ranks.mod")) {
			if (args.length == 0) {
				sender.sendMessage(Main.pr + "§6§lMögliche Befehle sind:");
				sender.sendMessage(Main.pr + "§a/chat clear");
			} else if (args[0].equalsIgnoreCase("clear")) {
				for (int i = 0; i < 100; ++i) {
					Bukkit.broadcastMessage((String) "\n");
				}
				sender.sendMessage(Main.pr + "§aDu hast den Chat erfolgreich geleert!");
			} else {
				sender.sendMessage(Main.pr + "§6§lMögliche Befehle sind:");
				sender.sendMessage(Main.pr + "§a/chat clear");
			}
		}
		return true;
	}
}

