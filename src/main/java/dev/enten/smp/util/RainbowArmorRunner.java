package dev.enten.smp.util;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import dev.enten.smp.Main;

public class RainbowArmorRunner implements Listener{

static String rbarmor = Main.perm + "rainbowarmor";

public static int r, g, b = 20;
public static int time = 59;
	
	public static void makeClockAndChangingTimers() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			public void run() {
				switch(time) {
				case 59:
					r = 255;
					g = 0;
					b = 0;
					time-=1;
					break;
				case 58:
					r = 255;
					g = 68;
					b = 0;
					time-=1;
					break;
				case 57:
					r = 255;
					g = 111;
					b = 0;
					time-=1;
					break;
				case 56:
					r = 255;
					g = 171;
					b = 0;
					time-=1;
					break;
				case 55:
					r = 255;
					g = 255;
					b = 0;
					time-=1;
					break;
				case 54:
					r = 188;
					g = 255;
					b = 0;
					time-=1;
					break;
				case 53:
					r = 128;
					g = 255;
					b = 0;
					time-=1;
					break;
				case 52:
					r = 43;
					g = 255;
					b = 0;
					time-=1;
					break;
				case 51:
					r = 0;
					g = 255;
					b = 9;
					time-=1;
					break;
				case 50:
					r = 0;
					g = 255;
					b = 51;
					time-=1;
					break;
				case 49:
					r = 0;
					g = 255;
					b = 111;
					time-=1;
					break;
				case 48:
					r = 0;
					g = 255;
					b = 162;
					time-=1;
					break;
				case 47:
					r = 0;
					g = 255;
					b = 230;
					time-=1;
					break;
				case 46:
					r = 0;
					g = 239;
					b = 255;
					time-=1;
					break;
				case 45:
					r = 0;
					g = 196;
					b = 255;
					time-=1;
					break;
				case 44:
					r = 0;
					g = 173;
					b = 255;
					time-=1;
					break;
				case 43:
					r = 0;
					g = 162;
					b = 255;
					time-=1;
					break;
				case 42:
					r = 0;
					g = 137;
					b = 255;
					time-=1;
					break;
				case 41:
					r = 0;
					g = 100;
					b = 255;
					time-=1;
					break;
				case 40:
					r = 0;
					g = 77;
					b = 255;
					time-=1;
					break;
				case 39:
					r = 0;
					g = 34;
					b = 255;
					time-=1;
					break;
				case 38:
					r = 17;
					g = 0;
					b = 255;
					time-=1;
					break;
				case 37:
					r = 37;
					g = 0;
					b = 255;
					time-=1;
					break;
				case 36:
					r = 68;
					g = 0;
					b = 255;
					time-=1;
					break;
				case 35:
					r = 89;
					g = 0;
					b = 255;
					time-=1;
					break;
				case 34:
					r = 102;
					g = 0;
					b = 255;
					time-=1;
					break;
				case 33:
					r = 124;
					g = 0;
					b = 255;
					time-=1;
					break;
				case 32:
					r = 154;
					g = 0;
					b = 255;
					time-=1;
					break;
				case 31:
					r = 222;
					g = 0;
					b = 255;
					time-=1;
					break;
				case 30:
					r = 255;
					g = 0;
					b = 247;
					time-=1;
					break;
				case 29:
					r = 255;
					g = 0;
					b = 179;
					time-=1;
					break;
				case 28:
					r = 255;
					g = 0;
					b = 128;
					time = 59;
					break;
				}	
				Color c = Color.fromRGB(r, g, b);
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
//					Location loc = p.getLocation();
					try {
		            if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getType() == Material.LEATHER_HELMET && p.getInventory().getHelmet().getItemMeta().getLore().contains("§fRainbow :)")) {

		            	p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));

		            }
		           
		            if (p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getType() == Material.LEATHER_CHESTPLATE && p.getInventory().getChestplate().getItemMeta().getLore().contains("§fRainbow :)")) {
		                    p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
		             
		            }
		           
		            if (p.getInventory().getLeggings() != null && p.getInventory().getLeggings().getType() == Material.LEATHER_LEGGINGS && p.getInventory().getLeggings().getItemMeta().getLore().contains("§fRainbow :)")) {
		                    p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
		            
		            }
		           
		            if (p.getInventory().getBoots() != null && p.getInventory().getBoots().getType() == Material.LEATHER_BOOTS && p.getInventory().getBoots().getItemMeta().getLore().contains("§fRainbow :)")) {
		                    p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
		                 
		            }
		    }catch(NullPointerException d) {
		    }
				}
				}
		},0, 1);
	}
	static ArrayList<String> lore = new ArrayList<String>();
	public static ItemStack getColorArmor(Material m, Color c) {
		ItemStack i = new ItemStack(m, 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
		lore.add("§fRainbow :)");
		meta.setLore(lore);
		meta.setColor(c);
		lore.clear();
		i.setItemMeta(meta);
		return i;
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		ItemStack item = e.getItemDrop().getItemStack();
		try {
		if(item.getItemMeta() != null && item.getItemMeta().getLore().contains("§fRainbow :)")) {
			e.setCancelled(true);
		}
		}catch(NullPointerException d) {
		}
	}
	
}
