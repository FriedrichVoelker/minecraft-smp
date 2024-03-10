package dev.enten.smp.util.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.enten.smp.Main;
import net.dv8tion.jda.api.entities.User;

public abstract class RCommand extends Command{    
	private final Main plugin = Main.getInstance();
	private final String command;
	private final String permission;
	private final List<String> alias;
	
	public RCommand(String command, String permission, String... alias) {
	    super(command, "", "", Arrays.asList(alias));
	
	
	    this.command = command;
	    this.permission = permission;
	    this.alias = new ArrayList<>(getAliases());
	}
	
	
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		// TODO Auto-generated method stub
		
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			if(!(permission.isEmpty() || player.hasPermission(permission))) {
				player.sendMessage("No permissions");
				return true;
			}
			
			
			execute(player, args, false);
			return false;
		}

        execute(null, args, true);
		return false;
	}

    public abstract void execute(Player player, String[] args, boolean server);
    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (!(sender instanceof Player))
            return Collections.emptyList();

        final List<String> tab = new ArrayList<>();

        tab.addAll(getTab((Player) sender, args));

        if (tab.isEmpty()) {
            if (args.length == 0)
                tab.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
            else
                tab.addAll(Bukkit.getOnlinePlayers().stream().filter(o -> o.getName().toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                        .map(Player::getName)
                        .collect(Collectors.toList()));
        }
        return tab;
    }

    public List<String> getTab(Player player, String[] args) {
        return Collections.emptyList();
    }
    
    public String getCommand() {
    	return this.command;
    }
    
    public String getPermission() {
    	return this.permission;
    }
    
    public List<String> getAlias(){
    	return this.alias;
    }
}
