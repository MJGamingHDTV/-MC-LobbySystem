package eu.mj.gg.lobby.nick;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import eu.mj.gg.lobby.main.Main;

public class MySQL
{
  private String user;
  private String passwd;
  private String database;
  private String host;
  private int port;
  private Connection conn;
  private Logger logger;
  private int sched;
  
  public MySQL(YamlConfiguration file)
  {
    this.user = file.getString("MySQL.User");
    this.passwd = file.getString("MySQL.Password");
    this.database = file.getString("MySQL.Database");
    this.host = file.getString("MySQL.Host");
    this.port = file.getInt("MySQL.Port");
    this.logger = Bukkit.getLogger();
    this.sched = -1;
  }
  
  public boolean nickActive(UUID u)
  {
    try
    {
      PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM `autonick_players` WHERE `uuid` = ?");
      ps.setString(1, u.toString());
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return rs.getBoolean("status");
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return false;
  }
  
  public void setNick(UUID u, boolean active)
  {
    try
    {
      PreparedStatement ps = this.conn.prepareStatement("UPDATE `autonick_players` SET `status` = ? WHERE `uuid` = ?");
      ps.setBoolean(1, active);
      ps.setString(2, u.toString());
      ps.executeUpdate();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
  
  public void createAccount(UUID u)
  {
    try
    {
      PreparedStatement ps = this.conn.prepareStatement("INSERT INTO `autonick_players` ( `uuid`, `status` ) VALUES ( ?, ? )");
      ps.setString(1, u.toString());
      ps.setBoolean(2, false);
      ps.executeUpdate();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
  
  public boolean accountExists(UUID u)
  {
    try
    {
      PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM `autonick_players` WHERE `UUID` = ?");
      ps.setString(1, u.toString());
      ResultSet rs = ps.executeQuery();
      return rs.next();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return false;
  }
  
  public boolean openConnection(boolean shutdown)
  {
    try
    {
      this.conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.user, this.passwd);
      PreparedStatement ps = this.conn.prepareStatement("CREATE TABLE IF NOT EXISTS autonick_database( uuid TEXT, name TEXT, signature TEXT, value TEXT )");
      PreparedStatement p2 = this.conn.prepareStatement("CREATE TABLE IF NOT EXISTS autonick_players( uuid TEXT, status BOOLEAN )");
      ps.executeUpdate();
      ps.close();
      p2.executeUpdate();
      ps.close();
      this.logger.info(Main.pr + "§aMySQL Connection success");
      checkConnection();
      return true;
    }
    catch (SQLException e)
    {
      if (shutdown)
      {
        this.logger.warning(Main.pr + "§cMySQL Connection failed");
        this.logger.warning(Main.pr + "§cShutting down Plugin!");
        e.printStackTrace();
        Bukkit.getPluginManager().disablePlugin(Main.getInstance());
        return false;
      }
      openConnection(false);
    }
    return false;
  }
  
  @SuppressWarnings("deprecation")
private void checkConnection()
  {
    if (Bukkit.getScheduler().isQueued(this.sched)) {
      return;
    }
    this.sched = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getInstance(), new Runnable()
    {
      public void run()
      {
        try
        {
          MySQL.this.conn.prepareStatement("SHOW TABLES LIKE 'autonick_database'").execute();
        }
        catch (Exception e)
        {
          MySQL.this.openConnection(false);
        }
      }
    }, 20L, 20L);
  }
  
  public void closeConnection()
  {
    if (this.conn == null) {
      return;
    }
    try
    {
      this.conn.close();
      this.logger.info(Main.pr + "§eMySQL disconnected");
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
}

