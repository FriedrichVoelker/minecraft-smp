package dev.enten.smp.commands;

import org.bukkit.entity.Player;

import dev.enten.smp.util.commands.RCommand;

public class AboutCommand extends RCommand{

	public AboutCommand() {
        super("about", "", "?", "plugins", "pl", "bukkit", "bukkit:?", "help");
	}

	@Override
	public void execute(Player player, String[] args, boolean server) {
		// TODO Auto-generated method stub
		player.sendMessage("Nope :)");
	}

}
