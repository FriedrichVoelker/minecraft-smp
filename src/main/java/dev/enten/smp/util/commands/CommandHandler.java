package dev.enten.smp.util.commands;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;

public class CommandHandler {


    private final List<RCommand> commands = new ArrayList<>();
	
	public void register(RCommand... commands) {
		try {
			   final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

		   		bukkitCommandMap.setAccessible(true);
		   		CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
			   
			   	final Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
		        knownCommandsField.setAccessible(true);
		        Map<String, Command> knownCommands = (Map<String, Command>) knownCommandsField.get(commandMap);

		        Arrays.stream(commands)
	                .forEach(rCommand -> {
	                    this.commands.add(rCommand);
	                    
	                    // Unregister any existing command with the same name
	                    knownCommands.remove(rCommand.getCommand());
	
	                    commandMap.register(rCommand.getCommand(), rCommand);
	                    
	                    // Remove the prefix:command entry from the knownCommands map
	                    knownCommands.remove(rCommand.getCommand() + ":" + rCommand.getCommand());

                });
			} catch(Exception e) {
			   e.printStackTrace();
			}
		
	}

    public RCommand get(String start) {
        return commands.stream()
                .filter(rCommand -> rCommand.getCommand().toLowerCase().startsWith(start.toLowerCase()) |
                        rCommand.getAlias().stream().anyMatch(s -> s.toLowerCase().startsWith(start.toLowerCase())))
                .findAny().orElse(null);
    }

    public RCommand getCommand(String command) {
        return commands.stream()
                .filter(rCommand -> rCommand.getCommand().equalsIgnoreCase(command) |
                        rCommand.getAlias().stream().anyMatch(s -> s.equalsIgnoreCase(command)))
                .findAny().orElse(null);
    }

    public void executeCommand(Player player, String commandString) {
        final String[] args = commandString.replace("/", "").split(" ");

        final RCommand command = getCommand(args[0]);

        if (player == null)
            return;

        if (command == null) {
        	player.sendMessage("§cUnknown Command");
            return;
        }

        if (!(command.getPermission().isEmpty() | player.hasPermission(command.getPermission()))) {
        	player.sendMessage("Nope");
            return;
        }

        command.execute(player, args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : new String[]{},
                false);
    }

    public void executeCommand(String command, String... args) {
        final RCommand cmd = getCommand(command);

        if (cmd == null)
            return;

        cmd.execute(null, args, true);
    }

    public void removeCommands(String... commands) {
		try {
			   final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

			   bukkitCommandMap.setAccessible(true);
			   SimpleCommandMap commandMap = (SimpleCommandMap) bukkitCommandMap.get(Bukkit.getServer());
			   
		        Arrays.stream(commands).forEach(s -> {
		            final Command command = commandMap.getCommands().stream()
		                    .filter(command1 -> command1.getName().equalsIgnoreCase(s) |
		                            command1.getAliases().stream().anyMatch(s::equalsIgnoreCase))
		                    .findAny().orElse(null);

		            if (command == null)
		                return;

		            commandMap.getCommands().remove(command);

            });
			} catch(Exception e) {
			   e.printStackTrace();
			}

    }
	
}
