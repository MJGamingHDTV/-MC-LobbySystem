package eu.mj.gg.lobby.methods;

import java.lang.reflect.Field;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import de.NeonnBukkit.CoinsAPI.API.CoinsAPI;
import eu.mj.gg.lobby.main.Main;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class LobbyScore {

	// Sidebar

	public static void setSidebar(Player p, String color) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("test", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName("§" + color + "§lGalaxyofGames");

		Score score1 = objective.getScore("§8§m----------------");
		score1.setScore(Integer.valueOf(15).intValue());
		Score scorep = objective.getScore("§1 ");
		scorep.setScore(Integer.valueOf(14).intValue());

		Score score5 = objective.getScore("§a§lCoins:");
		score5.setScore(Integer.valueOf(13).intValue());
		try {
			Score scorekss = objective.getScore("§a" + CoinsAPI.getCoins(p.getUniqueId().toString()));
			scorekss.setScore(Integer.valueOf(12).intValue());
		} catch (NullPointerException e) {

		}

		Score scorepb = objective.getScore("§2 ");
		scorepb.setScore(Integer.valueOf(11).intValue());

		Score score2 = objective.getScore("§b§lTeamSpeak:");
		score2.setScore(Integer.valueOf(10).intValue());
		Score scoreks = objective.getScore("§bts.galaxyofgames.eu");
		scoreks.setScore(Integer.valueOf(9).intValue());

		Score scorep2 = objective.getScore("§3 ");
		scorep2.setScore(Integer.valueOf(8).intValue());

		Score score3 = objective.getScore("§f§lOnline Spieler:");
		score3.setScore(Integer.valueOf(7).intValue());
		Score scoret = objective.getScore("§f" + TimoCloudAPI.getUniversalInstance().getProxy("Proxy").getOnlinePlayerCount());
		scoret.setScore(Integer.valueOf(6).intValue());

		Score scorep3 = objective.getScore("§4 ");
		scorep3.setScore(Integer.valueOf(5).intValue());

		Score score4 = objective.getScore("§e§lDein Rang:");
		score4.setScore(Integer.valueOf(4).intValue());
		Score scoreL = objective.getScore("§e" + Main.getPermissions().getPrimaryGroup(p));
		scoreL.setScore(Integer.valueOf(3).intValue());
		Score scorep4 = objective.getScore("§5 ");
		scorep4.setScore(Integer.valueOf(2).intValue());

		Score scoreC = objective.getScore("§8§m---------------");
		scoreC.setScore(Integer.valueOf(1));

		p.setScoreboard(board);
	}

	// TabList-Title
	@SuppressWarnings("rawtypes")
	public static void sendTabTitle(Player player, String header, String footer) {
		if (header == null) {
			header = "";
		}
		header = ChatColor.translateAlternateColorCodes((char) '&', (String) header);
		if (footer == null) {
			footer = "";
		}
		footer = ChatColor.translateAlternateColorCodes((char) '&', (String) footer);
		header = header.replaceAll("%player%", player.getDisplayName());
		footer = footer.replaceAll("%player%", player.getDisplayName());
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a((String) ("{\"text\": \"" + header + "\"}"));
		IChatBaseComponent tabFoot = IChatBaseComponent.ChatSerializer.a((String) ("{\"text\": \"" + footer + "\"}"));
		PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);
		try {
			try {
				Field field = headerPacket.getClass().getDeclaredField("b");
				field.setAccessible(true);
				field.set((Object) headerPacket, (Object) tabFoot);
			} catch (Exception e) {
				e.printStackTrace();
				connection.sendPacket((Packet) headerPacket);
			}
		} finally {
			connection.sendPacket((Packet) headerPacket);
		}
	}
}
