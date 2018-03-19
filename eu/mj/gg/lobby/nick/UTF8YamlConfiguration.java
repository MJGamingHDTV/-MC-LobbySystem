package eu.mj.gg.lobby.nick;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class UTF8YamlConfiguration
  extends YamlConfiguration
{
  public UTF8YamlConfiguration(File file)
  {
    try
    {
      load(file);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void save(File file)
    throws IOException
  {
    Validate.notNull(file, "File cannot be null");
    Files.createParentDirs(file);
    String data = saveToString();
    Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);
    try
    {
      writer.write(data);
    }
    finally
    {
      writer.close();
    }
  }
  
  public void load(File file)
    throws FileNotFoundException, IOException, InvalidConfigurationException
  {
    Validate.notNull(file, "File cannot be null");
    load(new InputStreamReader(new FileInputStream(file), Charsets.UTF_8));
  }
}