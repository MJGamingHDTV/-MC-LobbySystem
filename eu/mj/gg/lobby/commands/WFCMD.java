package eu.mj.gg.lobby.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.mj.gg.lobby.main.Main;

public class WFCMD implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(sender instanceof Player){
			float big = Float.parseFloat(args[0]);
			Player p = (Player) sender;
			try {
			if (cmd.getName().equalsIgnoreCase("flyspeed")){
		    if (p.hasPermission("gg.wandf")){
		    	try{
		    	if(args.length == 0){
		    		p.sendMessage(Main.pr + "§cBitte gib eine Zahl an §a(§9/flyspeed 1-10§a)");
		    	}else if(args[0].toString() != null && big <= 10){
		    		float flieg = Float.parseFloat(args[0]);
		    		float fly = flieg/10;
		    		p.setFlySpeed(fly);
		    		p.sendMessage(Main.pr + "§aDu hast nun einen FlySpeed von §6" + flieg);	    	
		    	}else if(big > 10){
		    		p.sendMessage(Main.pr + "§cBitte gib eine Zahl zwischen 1 und 10 an!");
		    	}
		    	}catch(ArrayIndexOutOfBoundsException ex){
		    		p.sendMessage(Main.pr + "§cBitte gib eine Zahl an §a(§9/flyspeed 1-10§a)");
		    	}
		    }else {
		    	p.sendMessage(Main.pr + "§cDazu hast du keine Rechte!");
		    }
		}else if(cmd.getName().equalsIgnoreCase("walkspeed")) {
			if (p.hasPermission("gg.wandf")){
				try {
		    	if(args.length == 0){
		    		p.sendMessage(Main.pr + "§cBitte gib eine Zahl an (/walkspeed 1-10)");
		    	}else if(args[0].toString() != null && big <= 10){
		    		float lauf = Float.parseFloat(args[0]);
		    		float walk = lauf/10;
		    		p.setWalkSpeed(walk);
		    		p.sendMessage(Main.pr + "§aDu hast nun einen WalkSpeed von §6" + lauf);	    	
		    	}else if(big > 10){
		    		p.sendMessage(Main.pr + "§cBitte gib eine Zahl zwischen 1 und 10 an!");
	    	}
				}catch(ArrayIndexOutOfBoundsException ex){
					ex.getMessage();
					p.sendMessage(Main.pr + "§cBitte gib eine Zahl an §a(§9/walkspeed 1-10§a)");
				}
		    }else {
		    	p.sendMessage(Main.pr + "§cDazu hast du keine Rechte!");
		    }
		}else {
			sender.sendMessage(Main.pr + "§cDu musst ein Spieler sein um diesen Befehl nutzen zu können!");
		}
			}catch(NumberFormatException ex){
				p.sendMessage(Main.pr + "§cBitte gib nur Zahlen an!");
			}
		}else {
			sender.sendMessage(Main.pr + "Du musst ein Spieler sein um diesen Befehl nutzen zu können!");
		}
		return true;
		
	}
}
