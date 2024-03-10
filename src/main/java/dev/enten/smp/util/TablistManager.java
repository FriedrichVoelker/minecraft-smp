package dev.enten.smp.util;

import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import dev.enten.smp.Main;


public class TablistManager {
	
//	private static TablistManager instance;
	private Scoreboard scoreboard;
	private HashMap<UUID, String>teams;
	
	private double tps;
	
	
	public TablistManager() {
		teams = new HashMap<>();
		this.scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		runTPSTimer();
		
	}
	
	@SuppressWarnings("deprecation")
	public void registerTeam(Player p, String prefix, int priority) {
		String prioString = StringUtils.leftPad(priority + "", 5, "0");
		
//		String prioString = (priority == 999 ? "a" : "b");
		
		String s = prioString + "_" +  p.getName().substring(0, 5);
		
		if(teams.containsKey(p.getUniqueId())) {
			scoreboard.getTeam(teams.get(p.getUniqueId())).unregister();
			teams.remove(p.getUniqueId());
		}
		
		
		if(scoreboard.getTeam(s) != null) {
			scoreboard.getTeam(s).unregister();
		}
		scoreboard.registerNewTeam(s);
		scoreboard.getTeam(s).addPlayer(p);
		teams.put(p.getUniqueId(), s);
		
//		p.setPlayerListHeaderFooter(ColorManager.getColorString(ConfigManager.playerlist_header), ColorManager.getColorString(ConfigManager.playerlist_footer));
		setPlayerTab(p);
		p.setPlayerListName(ColorManager.getColorString(prefix + p.getName()));
	}
	
	public void updateTabData() {
		
		new BukkitRunnable() {

			@Override
			public void run() {
				for(Player all : Bukkit.getOnlinePlayers()) {
					
					setPlayerTab(all);
				}
				
			}
			
		}.runTaskTimer(Main.getInstance(), 0, 20 * 5);
		
	}
	private void setPlayerTab(Player player) {
		

		
		
		String header = ConfigManager.playerlist_header;
		String footer = ConfigManager.playerlist_footer;
		
		
        Pattern pattern = Pattern.compile("(?i)%(.*?)%");
        Matcher matcher = pattern.matcher(header);

        while (matcher.find()) {
        	header = header.replace(matcher.group(0), headerFooterParam("%"+matcher.group(1)+"%", player));
        }
        Matcher newmatcher = pattern.matcher(footer);

        while (newmatcher.find()) {
        	footer = footer.replace(newmatcher.group(0), headerFooterParam("%"+newmatcher.group(1)+"%", player));
        }
		
		
		footer = ColorManager.getColorString(footer);
		
		header = ColorManager.getColorString(header);
		
		player.setPlayerListHeaderFooter(header, footer);
	}
	
	private String headerFooterParam(String key, Player player) {
		
		if(key.equalsIgnoreCase("%ping%")) {
			key = player.getPing() + "";
		}
		if(key.equalsIgnoreCase("%tps%")) {
			key = tps + "";
		}
		
		
		
		return key;
	}
	
	private void runTPSTimer() {
		Bukkit.getServer().getScheduler().runTaskTimer(Main.getInstance(), new Runnable(){
	
	        long secstart;
	        long secend;
	     
	        int ticks;
	     
	        @Override
	        public void run(){
	            secstart = (System.currentTimeMillis() / 1000);
	         
	            if(secstart == secend){
	                ticks++;
	            }else{
	                secend = secstart;
	                tps = (tps == 0) ? ticks : ((tps + ticks) / 2);
	                tps = Math.round(tps);
	                if(tps > 20) {
	                	tps = 20.0;
	                }
	                ticks = 0;
	            }
	        }
	     
	    }, 0, 1);
	}
	
}
