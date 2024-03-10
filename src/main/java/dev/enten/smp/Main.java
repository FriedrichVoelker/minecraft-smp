package dev.enten.smp;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import dev.enten.smp.commands.AdminCommand;
import dev.enten.smp.commands.DevCommand;
import dev.enten.smp.commands.SitCommand;
import dev.enten.smp.listener.ChatListener;
import dev.enten.smp.util.ConfigManager;
import dev.enten.smp.util.RainbowArmorRunner;
import dev.enten.smp.util.TablistManager;
import dev.enten.smp.util.commands.CommandHandler;
import dev.enten.smp.util.permissions.PermBase;
import dev.enten.smp.util.permissions.PermPlayer;


public class Main extends JavaPlugin{
	
//	Base Config Variables
	public static String BASE_DIR_PATH = "plugins/SMP";
	public static String perm = "dev.";
	public static TablistManager TablistManager;
	public static CommandHandler CommandHandler = new CommandHandler();
	
//	Global Helper Variables
	public static ArrayList<Player> temp_ranks = new ArrayList<>();
	
	public static Main instance;
	
	@Override
	public void onDisable() {
		System.out.println("[SMP] Stopped");
	}
	
	@Override
	public void onEnable() {
		instance = this;
		
		RainbowArmorRunner.makeClockAndChangingTimers();
		ConfigManager.init();
		Main.TablistManager  = new TablistManager();
		TablistManager.updateTabData();
		
		for(Player all : Bukkit.getOnlinePlayers()) {
			setupPlayer(all);
		}
		
		
		System.out.println("[SMP] Started");
		
//		CommandHandler.removeCommands("?", "help");
		CommandHandler.register(/*new AboutCommand(),*/ new DevCommand(), new AdminCommand(), new SitCommand());
		
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new ChatListener(), this);
		pm.registerEvents(new SitCommand(), this);
	}
	
	
	
	public static void setupPlayer(Player player) {
		setPlayerPermissions(player);
	}
	
	public static void setPlayerPermissions(Player player) {
		final String version = Bukkit.getServer().getClass().getPackage().getName().substring(23);
		try {
			final Field perm = Class.forName("org.bukkit.craftbukkit."+version+".entity.CraftHumanEntity").getDeclaredField("perm");
			perm.setAccessible(true);
			final Object base = perm.get(player);
			if (base instanceof PermBase == false) {
				perm.set(player, new PermBase(player));
			}
				} catch (final Exception e) {
			}
		
		PermPlayer PermPlayer = new PermPlayer(player.getUniqueId());
		
		TablistManager.registerTeam(player, PermPlayer.getUserPrefix("tab"), PermPlayer.getUserPrio());
	}
	
	
	public static Main getInstance() {
		return instance;
	}
}
