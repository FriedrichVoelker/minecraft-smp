package dev.enten.smp.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import dev.enten.smp.Main;
import dev.enten.smp.util.ColorManager;
import dev.enten.smp.util.ConfigManager;
import dev.enten.smp.util.permissions.PermPlayer;

public class ChatListener implements Listener{
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
//		if(GlobalMuteCommand.enabled && !(player.hasPermission(Main.perm + "globalmute.bypass") || player.hasPermission(Main.perm + "globalmute"))) {
//			ResponseManager.respondError(player, "errors.globalmute");
//			event.setCancelled(true);
//			return;
//		}
//		
		
		PermPlayer PermPlayer = new PermPlayer(player.getUniqueId());
		
		String format = ConfigManager.chatformat;
		String chatPrefix = PermPlayer.getUserPrefix("chat");
		String chatcolor = ColorManager.getColorString(PermPlayer.getUserChatColor());
		String message = event.getMessage();
		
		format = format.replaceAll("%prefix%", chatPrefix).replaceAll("%player%", player.getName());
		format = ColorManager.getColorString(format);
		if(player.hasPermission(Main.perm + "chatcolor")) {
			message = ColorManager.getColorString(message);
		}
		format = format.replaceAll("%message%", chatcolor + message);
		event.setFormat(format);
	}
	
	
}
