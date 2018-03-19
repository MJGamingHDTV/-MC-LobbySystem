package eu.mj.gg.lobby.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

import eu.mj.gg.lobby.mysql.AsyncMySQL.MySQL;

public class LobbyAPI {
	static MySQL msql = new AsyncMySQL.MySQL();

	public static boolean playerExists(String uuid) {
		try {
			ResultSet rs = msql.getResult("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'");
			if (rs.next()) {
				if (rs.getString("UUID") != null) {
					return true;
				}
				return false;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void createPlayer(String uuid) {
		if (!LobbyAPI.playerExists(uuid)) {
			AsyncMySQL.update("INSERT INTO LobbyConf (UUID, COLOR, WJUMP, PJUMP, SILENT, RIDE) VALUES ('" + uuid
					+ "', '0', '0', '0', '0', '0');");
		}
	}

	public static Integer getColor(String uuid) {
		Integer i = 0;
		if (LobbyAPI.playerExists(uuid)) {
			try {
				ResultSet rs = msql.getResult("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'");
				if (rs.next()) {
					Integer.valueOf(rs.getInt("COLOR"));
				}
				i = rs.getInt("COLOR");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			LobbyAPI.createPlayer(uuid);
			LobbyAPI.getColor(uuid);
		}
		return i;
	}

	public static boolean getPjump(String uuid) {
		boolean i = false;
		if (LobbyAPI.playerExists(uuid)) {
			try {
				ResultSet rs = msql.getResult("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'");
				if (rs.next()) {
					Integer.valueOf(rs.getInt("PJUMP"));
				}
				int b = rs.getInt("PJUMP");
				if (b == 1) {
					i = true;
				} else {
					i = false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			LobbyAPI.createPlayer(uuid);
			LobbyAPI.getPjump(uuid);
		}
		return i;
	}

	public static boolean getSilent(String uuid) {
		boolean i = false;
		if (LobbyAPI.playerExists(uuid)) {
			try {
				ResultSet rs = msql.getResult("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'");
				if (rs.next()) {
					Integer.valueOf(rs.getInt("SILENT"));
				}
				int b = rs.getInt("SILENT");
				if (b == 1) {
					i = true;
				} else {
					i = false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return i;
	}

	public static boolean getWjump(String uuid) {
		boolean i = false;
		if (LobbyAPI.playerExists(uuid)) {
			try {
				ResultSet rs = msql.getResult("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'");
				if (rs.next()) {
					Integer.valueOf(rs.getInt("WJUMP"));
				}
				int b = rs.getInt("WJUMP");
				if (b == 1) {
					i = true;
				} else {
					i = false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return i;
	}

	public static boolean getRide(String uuid) {
		boolean i = false;
		if (LobbyAPI.playerExists(uuid)) {
			try {
				ResultSet rs = msql.getResult("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'");
				if (rs.next()) {
					Integer.valueOf(rs.getInt("RIDE"));
				}
				int b = rs.getInt("RIDE");
				if (b == 1) {
					i = true;
				} else {
					i = false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return i;
	}

	public static String getPlayer(String name) {
		String s = "a";
		try {
			ResultSet rs = msql.getResult("SELECT * FROM LobbyConf WHERE PLAYER='" + name + "'");
			if (rs.next()) {
				String.valueOf(rs.getString("UUID"));
			}
			s = rs.getString("UUID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String getallPlayer() {
		String s = "a";
		try {
			ResultSet rs = msql.getResult("SELECT PLAYER FROM LobbyConf");
			s = rs.getString("UUID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}

	public static void setColor(String uuid, Integer color) {
		if (LobbyAPI.playerExists(uuid)) {
			AsyncMySQL.update("UPDATE LobbyConf SET COLOR='" + color + "' WHERE UUID='" + uuid + "'");
		} else {
			LobbyAPI.createPlayer(uuid);
			LobbyAPI.setColor(uuid, color);
		}
	}

	public static void setPjump(String uuid, boolean b) throws SQLException {
		int i;
		if (LobbyAPI.playerExists(uuid)) {
			if (b == true) {
				i = 1;
			} else {
				i = 0;
			}
			AsyncMySQL.update("UPDATE LobbyConf SET ='" + i + "' WHERE UUID='" + uuid + "'");
		} else {
			LobbyAPI.createPlayer(uuid);
			LobbyAPI.setPjump(uuid, b);
		}
	}

	public static void setSilent(String uuid, boolean silent) throws SQLException {
		int b = 0;
		if (LobbyAPI.playerExists(uuid)) {
			if (silent == true) {
				b = 1;
			} else {
				b = 0;
			}
			AsyncMySQL.update("UPDATE LobbyConf SET SILENT='" + b + "' WHERE UUID='" + uuid + "'");
		} else {
			LobbyAPI.createPlayer(uuid);
			LobbyAPI.setSilent(uuid, silent);
		}
	}

	public static void setWjump(String uuid, boolean wjump) throws SQLException {
		int b = 0;
		if (LobbyAPI.playerExists(uuid)) {
			if (wjump == true) {
				b = 1;
			} else {
				b = 0;
			}
			AsyncMySQL.update("UPDATE LobbyConf SET WJUMP='" + b + "' WHERE UUID='" + uuid + "'");
		} else {
			LobbyAPI.createPlayer(uuid);
			LobbyAPI.setWjump(uuid, wjump);
		}
	}

	public static void setRide(String uuid, boolean ride) throws SQLException {
		int b = 0;
		if (LobbyAPI.playerExists(uuid)) {
			if (ride == true) {
				b = 1;
			} else {
				b = 0;
			}
			AsyncMySQL.update("UPDATE LobbyConf SET RIDE='" + b + "' WHERE UUID='" + uuid + "'");
		} else {
			LobbyAPI.createPlayer(uuid);
			LobbyAPI.setRide(uuid, ride);
		}
	}

	public static void setPlayer(String uuid, String name) {
		if (LobbyAPI.playerExists(uuid)) {
			AsyncMySQL.update("UPDATE LobbyConf SET PLAYER='" + name + "' WHERE UUID='" + uuid + "'");
		} else {
			LobbyAPI.createPlayer(uuid);
			LobbyAPI.setPlayer(uuid, name);
		}
	}
}
