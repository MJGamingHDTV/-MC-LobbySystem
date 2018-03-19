package eu.mj.gg.lobby.nick;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataUtil
{
  private Map<String, String> uuid_nodashes;
  private Map<String, String> name;
  private Map<String, UUID> uuid;
  
  public DataUtil()
  {
    this.uuid_nodashes = new HashMap<String, String>();
    this.name = new HashMap<String, String>();
    this.uuid = new HashMap<String, UUID>();
  }
  
  public UUID getUUID(String name)
  {
    if (this.uuid.containsKey(name)) {
      return (UUID)this.uuid.get(name);
    }
    try
    {
      URL api = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
      BufferedReader reader = new BufferedReader(new InputStreamReader(api.openStream()));
      Object obj = new JSONParser().parse(reader);
      JSONObject object = (JSONObject)obj;
      reader.close();
      UUID output = UUID.fromString(((String)object.get("id")).replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5"));
      this.uuid.put(name, output);
      return output;
    }
    catch (Exception e) {}
    return null;
  }
  
  public String getName(String uuid)
  {
    if (this.name.containsKey(uuid)) {
      return (String)this.name.get(uuid);
    }
    try
    {
      URL api = new URL("https://api.mojang.com/user/profiles/UUID/names".replace("UUID", uuid.replace("-", "")));
      BufferedReader reader = new BufferedReader(new InputStreamReader(api.openStream()));
      Object obj = new JSONParser().parse(reader);
      JSONArray array = (JSONArray)obj;
      JSONObject object = (JSONObject)array.get(array.size() - 1);
      reader.close();
      String output = (String)object.get("name");
      this.name.put(uuid, output);
      return output;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public String getUUID_NoDashes(String name)
  {
    if (this.uuid_nodashes.containsKey(name)) {
      return (String)this.uuid_nodashes.get(name);
    }
    try
    {
      URL api = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
      BufferedReader reader = new BufferedReader(new InputStreamReader(api.openStream()));
      Object obj = new JSONParser().parse(reader);
      JSONObject object = (JSONObject)obj;
      reader.close();
      String output = (String)object.get("id");
      this.uuid_nodashes.put(name, output);
      return output;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
