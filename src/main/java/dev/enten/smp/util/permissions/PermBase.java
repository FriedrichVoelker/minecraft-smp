package dev.enten.smp.util.permissions;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;

import dev.enten.smp.util.ConfigManager;
import dev.enten.smp.util.Logger;



public class PermBase extends PermissibleBase{
	private Player player;
    public PermBase(final Player player) {
        super(player);
        this.player = player;
    }
    
    @Override
    public boolean hasPermission(final String permission) {
        if (permission != null) {
        	
        	if(this.player.isOp()) {
        		if(ConfigManager.getEnableOP().equals("all")) return true;
        		if(ConfigManager.getEnableOP().equals("normal") && !permission.startsWith(ConfigManager.base_perm)) return true;
        	}
        	PermPlayer PermPlayer = new PermPlayer(player.getUniqueId());
        	Boolean hasPerm = PermPlayer.hasPermission(permission);
        	
        	if(!hasPerm) {
        		Logger.log("debug","Missing permission: " + permission + " for user " + player.getName());
        	}
        	
        	return hasPerm;
        }
		return false;
    }
	
}
