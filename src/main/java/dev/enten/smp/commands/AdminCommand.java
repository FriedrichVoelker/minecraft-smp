package dev.enten.smp.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import dev.enten.smp.Main;
import dev.enten.smp.util.ConfigManager;
import dev.enten.smp.util.RainbowArmorRunner;
import dev.enten.smp.util.commands.RCommand;

public class AdminCommand extends RCommand{

	public AdminCommand() {
		super("admin", "admin");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Player player, String[] args, boolean server) {
		
		if(args.length > 0) {
			
			if(args[0].equalsIgnoreCase("rainbow")) {
				player.getInventory().addItem(RainbowArmorRunner.getColorArmor(Material.LEATHER_HELMET, Color.RED));
				player.getInventory().addItem(RainbowArmorRunner.getColorArmor(Material.LEATHER_CHESTPLATE, Color.RED));
				player.getInventory().addItem(RainbowArmorRunner.getColorArmor(Material.LEATHER_LEGGINGS, Color.RED));
				player.getInventory().addItem(RainbowArmorRunner.getColorArmor(Material.LEATHER_BOOTS, Color.RED));
			}
			
			if(args.length > 1 && args[0].equalsIgnoreCase("config") && args[1].equalsIgnoreCase("reload")) {
				ConfigManager.readConfig();
				Main.TablistManager.updateTabData();
			}
			
			if(args.length > 1 && args[0].equalsIgnoreCase("permissions") && args[1].equalsIgnoreCase("reload")) {
				for(Player all : Bukkit.getOnlinePlayers()) {
					Main.setupPlayer(all);
				}
				Main.TablistManager.updateTabData();
			}
		}
		
	}
	
	
    @Override
    public List<String> getTab(Player player, String[] args) {
        if (args.length == 1)
            return Arrays.asList("config", "permissions", "rainbow");

        if (args.length == 2 && args[0].equalsIgnoreCase("config"))
            return Arrays.asList("reload");

        if (args.length == 2 && args[0].equalsIgnoreCase("permissions"))
            return Arrays.asList("reload");

        return Collections.emptyList();
    }

}
