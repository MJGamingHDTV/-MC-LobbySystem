package eu.mj.gg.lobby.listener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iDev!
 */
public class BungeeCord{
    
    private final String motd;
    private static int playersOnline;
    private final int maxPlayers;
    
    
    private BungeeCord(String motd, int playersOnline, int maxPlayers){
        this.motd = motd;
        BungeeCord.playersOnline = playersOnline;
        this.maxPlayers = maxPlayers;
    }
    
    public static BungeeCord getServerInfo(String host, int port){
        try {
            @SuppressWarnings("resource")
			Socket socket = new Socket(host, port); //Verbinden
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            
            out.write(0xFE); //Ping Anfrage
            
            byte[] answereBytes = new byte[256];
            in.read(answereBytes);
            
            byte[] newBytes = new byte[128];
            
            //Remove the other bytes
            int remove = 0;
            
            for(int i = 0; i < 128; i++){
                if((answereBytes[2*i] <= 0x1f && answereBytes[2*i] >= 0x00)){
                    remove++;
                }else{
                    newBytes[i-remove] = answereBytes[2*i];
                }
            }
            //End!
            
            String answere = new String(newBytes, "Cp1252");
            answere = answere.substring(1);
            
            if(answereBytes[0] == 0xFF) return null;
            
            String splitByte = String.valueOf((char)0xA7);
            String[] info = answere.split(splitByte);
            if(info.length != 3) return null;
            return new BungeeCord(info[0], new Integer(info[1].trim()), new Integer(info[2].trim()));
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(BungeeCord.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BungeeCord.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    //GETTER!
    public String getMOTD(){
        return motd;
    }
    
    public static Integer getOnlinePlayers(){
        return playersOnline;
    }
    
    public Integer getMaxPlayers(){
        return maxPlayers;
    }
    
    //ToString()-Override
    @Override
    public String toString(){
        return String.format("%s (%d/%d)", motd, playersOnline, maxPlayers);
    }
}
