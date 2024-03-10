package dev.enten.smp.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.entity.EntityDismountEvent;

import dev.enten.smp.Main;
import dev.enten.smp.util.commands.RCommand;

public class SitCommand extends RCommand implements Listener{

	public SitCommand() {
        super("sit", "");
	}

	@Override
	public void execute(Player player, String[] args, boolean server) {
		// TODO Auto-generated method stub
		if(server) return;
		
		if(player.getLocation().add(0,-1,0).getBlock().getType() == Material.AIR) return;
		spawnChair("is_chair", player.getLocation().add(0, -0.5, 0), player);
		
	}

	@EventHandler
	public void onUnSit(EntityDismountEvent event) {
		Entity ent = event.getDismounted();
		if(ent.getScoreboardTags().contains("is_chair")) {
			ent.remove();
			Entity dismounted = event.getEntity();
			if(dismounted != null) {
				dismounted.teleport(dismounted.getLocation().add(0, 0.5, 0));
			}
		}
		if(ent.getScoreboardTags().contains("is_stair_chair")) {
			ent.remove();
			Entity dismounted = event.getEntity();
			if(dismounted != null) {
				dismounted.teleport(dismounted.getLocation().add(0, 0.6, 0));
			}
		}
	}
	
	@EventHandler
	public void onRightClickChair(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && (event.getPlayer().getInventory().getItemInMainHand() == null || event.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR )) {
			String block = event.getClickedBlock().getType().toString();
			if(block.endsWith("STAIRS")) {
				Player player = event.getPlayer();
				Location loc = event.getClickedBlock().getLocation().add(0.5, 0, 0.5);
				Location ploc = player.getLocation();
				float newYaw = (ploc.getYaw() + 180) % 360;
				ploc.setYaw(newYaw);
				player.teleport(ploc);
				spawnChair("is_stair_chair", loc, player);
				
			}
		}
	}

	
	private void spawnChair(String tag, Location loc, Player player) {
		loc.setPitch(0.0F);
		loc.setYaw(player.getLocation().getYaw());
		
		Arrow chair = (Arrow) loc.getWorld().spawnEntity(loc, EntityType.ARROW);
		
		chair.setPersistent(true);
		chair.setPickupStatus(PickupStatus.DISALLOWED);
		chair.setInvulnerable(true);

		chair.addScoreboardTag(tag);
		chair.addPassenger(player);
		
	    new BukkitRunnable() {
	        @Override
	        public void run() {
	            if (chair == null || chair.isDead()) {
	            	this.cancel();
	            }
	            chair.setTicksLived(1);
	        }
	    }.runTaskTimer(Main.getInstance(), 0L, 20L);
	}
	
}
