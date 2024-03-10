package dev.enten.smp.commands;

import org.bukkit.entity.Player;

import dev.enten.smp.util.commands.RCommand;

public class DevCommand extends RCommand{

	public DevCommand() {
        super("dev", "dev");
	}

	@Override
	public void execute(Player player, String[] args, boolean server) {
		// TODO Auto-generated method stub
		player.sendMessage("Hallo " + player.getName());
	}

}
