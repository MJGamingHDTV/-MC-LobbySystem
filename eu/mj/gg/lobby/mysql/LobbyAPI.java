package eu.mj.gg.lobby.mysql;

import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.mj.gg.lobby.functions.Settings;
import eu.mj.gg.lobby.main.Main;
import eu.mj.gg.lobby.mysql.AsyncMySQL.MySQL;

public class LobbyAPI {
	static AsyncMySQL amsql = new AsyncMySQL(Main.plugin);
	static MySQL msql = new AsyncMySQL.MySQL();

	public static void createPlayer(String uuid) throws SQLException {
		AsyncMySQL.update("INSERT INTO LobbyConf (UUID, COLOR, WJUMP, PJUMP, SILENT, RIDE, ITEMCOL) SELECT '" + uuid + "', '0', '0', '0', '0', '0', 'f' FROM DUAL WHERE NOT EXISTS (SELECT '*' FROM LobbyConf WHERE UUID = '" + uuid + "');");
	}

	public static void getColor(String uuid) {
		Player p = Bukkit.getPlayer(UUID.fromString(uuid));
		amsql.query("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'", rs -> {
			try {
				if (rs.next()) {
					String c = String.valueOf(rs.getInt("COLOR"));
					Settings.Color.put(p, c);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	public static void getItemCol(String uuid) {
		Player p = Bukkit.getPlayer(UUID.fromString(uuid));
		amsql.query("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'", rs -> {
			try {
				if (rs.next()) {
					Integer c = Integer.valueOf(rs.getInt("ITEMCOL"));
					Settings.ItemCol.put(p, c);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public static void getPjump(String uuid) {
		Player p = Bukkit.getPlayer(UUID.fromString(uuid));
		amsql.query("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'", rs -> {
			try {
				if (rs.next()) {
					Integer.valueOf(rs.getInt("PJUMP"));
				}
				int b = 0;
				b = rs.getInt("PJUMP");
				if (b == 1) {
					Settings.platejump.add(p.getName());
				} else {
					Settings.platejump.remove(p.getName());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public static void getSilent(String uuid) {
		Player p = Bukkit.getPlayer(UUID.fromString(uuid));
		amsql.query("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'", rs -> {
			try {
				if (rs.next()) {
					Integer.valueOf(rs.getInt("SILENT"));
				}
				int b = rs.getInt("SILENT");
				if (b == 1) {
					Settings.SilentLobby.add(p.getName());
				} else {
					Settings.SilentLobby.remove(p.getName());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public static void getWjump(String uuid) {
		Player p = Bukkit.getPlayer(UUID.fromString(uuid));
		amsql.query("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'", rs -> {
			try {
				if (rs.next()) {
					Integer.valueOf(rs.getInt("WJUMP"));
				}
				int b = rs.getInt("WJUMP");
				if (b == 1) {
					Settings.waterjump.add(p.getName());
				} else {
					Settings.waterjump.remove(p.getName());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public static void getRide(String uuid) {
		Player p = Bukkit.getPlayer(UUID.fromString(uuid));
		amsql.query("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'", rs -> {
			try {
				if (rs.next()) {
					Integer.valueOf(rs.getInt("RIDE"));
				}
				int b = rs.getInt("RIDE");
				if (b == 1) {
					Settings.Ride.add(p.getName());
				} else {
					Settings.Ride.remove(p.getName());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public static void setColor(String uuid, String string) {
		AsyncMySQL.update("UPDATE LobbyConf SET COLOR='" + string + "' WHERE UUID='" + uuid + "'");
	}
	
	public static void setItemCol(String uuid, Integer itemcol) {
		AsyncMySQL.update("UPDATE LobbyConf SET ITEMCOL='" + itemcol + "' WHERE UUID='" + uuid + "'");
	}

	public static void setPjump(String uuid, boolean b) throws SQLException {
		int i;
		if (b == true) {
			i = 1;
		} else {
			i = 0;
		}
		AsyncMySQL.update("UPDATE LobbyConf SET ='" + i + "' WHERE UUID='" + uuid + "'");
	}

	public static void setSilent(String uuid, boolean silent) throws SQLException {
		int b = 0;
		if (silent == true) {
			b = 1;
		} else {
			b = 0;
		}
		AsyncMySQL.update("UPDATE LobbyConf SET SILENT='" + b + "' WHERE UUID='" + uuid + "'");
	}

	public static void setWjump(String uuid, boolean wjump) throws SQLException {
		int b = 0;
		if (wjump == true) {
			b = 1;
		} else {
			b = 0;
		}
		AsyncMySQL.update("UPDATE LobbyConf SET WJUMP='" + b + "' WHERE UUID='" + uuid + "'");
	}

	public static void setRide(String uuid, boolean ride) throws SQLException {
		int b = 0;
		if (ride == true) {
			b = 1;
		} else {
			b = 0;
		}
		AsyncMySQL.update("UPDATE LobbyConf SET RIDE='" + b + "' WHERE UUID='" + uuid + "'");
	}
}
