package dev.enten.smp.util.permissions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import dev.enten.smp.Main;
import dev.enten.smp.util.ConfigManager;
import dev.enten.smp.util.FileBuilder;


public class PermPlayer {
	
	private UUID uuid;
	private FileBuilder FileBuilder;
	private String chatprefix;
	private String tabprefix ;
	private String displayprefix;
	private String chatcolor;
	private String group;
	private Integer prio;
	private LocalDateTime temporary_until;
	private String temporary_previous_rank;
	private String discord_id;
	
	
	public PermPlayer(UUID uuid) {
		this.uuid = uuid;
		this.FileBuilder =  new FileBuilder("permissions.yml");
		this.fileCheck();
		
		
		this.chatprefix = FileBuilder.getString("User." + this.uuid + ".chatprefix");
		this.tabprefix = FileBuilder.getString("User." + this.uuid + ".tabprefix");
		this.displayprefix = FileBuilder.getString("User." + this.uuid + ".displayprefix");
		this.chatcolor = FileBuilder.getString("User." + this.uuid + ".chatcolor");
		this.group = FileBuilder.getString("User." + this.uuid + ".group");
		
		if(this.group == "") {
			this.group = PermissionManager.getDefaultGroup();
		}
		
		if(FileBuilder.getObject("User." + this.uuid + ".prio") != null) {
			this.prio = FileBuilder.getInt("User." + this.uuid + ".prio");
		}
		
		String tempStr = FileBuilder.getString("User." + this.uuid + ".temporary_until");
		if(tempStr != null) {
			this.temporary_until = LocalDateTime.parse(tempStr);
		}
		this.temporary_previous_rank = FileBuilder.getString("User." + this.uuid + ".temporary_previous_rank");
		
		
		
	}
	
	private void fileCheck() {
		if(FileBuilder.getObject("User." + this.uuid.toString()) == null) {
			FileBuilder.setValue("User." + this.uuid.toString() + ".permissions", "");
			FileBuilder.setValue("User." + this.uuid.toString() + ".group", "");
			FileBuilder.save();
		}
	}
	
	//	Setters
	
	public void setChatPrefix(String prefix) {
		FileBuilder.setValue("User." + this.uuid.toString() + ".chatprefix", prefix);
		this.saveAndReload();
		this.chatprefix = prefix;
	}
	
	public void setTabPrefix(String prefix) {
		FileBuilder.setValue("User." + this.uuid.toString() + ".tabprefix", prefix);
		this.saveAndReload();
		this.tabprefix = prefix;
	}
	
	public void setDisplayPrefix(String prefix) {
		FileBuilder.setValue("User." + this.uuid.toString() + ".displayprefix", prefix);
		this.saveAndReload();
		this.displayprefix = prefix;
	}
	
	public void setChatColor(String color) {
		FileBuilder.setValue("User." + this.uuid.toString() + ".chatcolor", color);
		this.saveAndReload();
		this.chatcolor = color;
	}
	
	public void setGroup(String group) {
		
//		if(ConfigManager.enable_discord) {
//			PlayerData pdata = new PlayerData(this.uuid);
//			String discordAcc = pdata.getDiscordAccount();
//
//			Logger.log("debug", "Discord ID: " + discordAcc);
//			if(!discordAcc.equalsIgnoreCase("")) {
//
//				PermGroup priorGroup = new PermGroup(this.group);
//				Logger.log("debug", "Old Group Discord ID: " + priorGroup.getDiscordRole());
//				if(!priorGroup.getDiscordRole().equals("")) {
//					Main.discord.removeRole(discordAcc, priorGroup.getDiscordRole());
//				}
//				PermGroup newGroup = new PermGroup(group);
//				Logger.log("debug", "New Group Discord ID: " + newGroup.getDiscordRole());
//				if(!newGroup.getDiscordRole().equals("")) {
//					Main.discord.assignRole(discordAcc, newGroup.getDiscordRole());
//				}
//			}
//		}
		
		
		this.removeTemporary();
		FileBuilder.setValue("User." + this.uuid.toString() + ".group", group);
		this.saveAndReload();
		this.group = group;
	}
	
	public void setPrio(Integer prio) {
		FileBuilder.setValue("User." + this.uuid.toString() + ".prio", prio);
		this.saveAndReload();
		this.prio = prio;
	}
	
	public void setTemporary(LocalDateTime until) {
		FileBuilder.setValue("User." + this.uuid + ".temporary_until", until.toString());
		this.saveAndReload();
		this.temporary_until = until;
	}
	
	public void setTemporaryPreviousRank(String group) {
		FileBuilder.setValue("User." + this.uuid + ".temporary_previous_rank", group);
		this.saveAndReload();
		this.temporary_previous_rank = group;
	}
	
	public void setDiscordId(String discord_id) {
		FileBuilder.setValue("User." + this.uuid + ".discord_id", discord_id);
		this.saveAndReload();
		this.discord_id = discord_id;
	}
	
	// Remover
	
	public void removeChatPrefix() {
		FileBuilder.setValue("User." + this.uuid.toString() + ".chatprefix", null);
		this.saveAndReload();
		this.chatprefix = null;
	}
	
	public void removeTabPrefix() {
		FileBuilder.setValue("User." + this.uuid.toString() + ".tabprefix", null);
		this.saveAndReload();
		this.tabprefix = null;
	}
	
	public void removeDisplayPrefix() {
		FileBuilder.setValue("User." + this.uuid.toString() + ".displayprefix", null);
		this.saveAndReload();
		this.displayprefix = null;
	}
	
	public void removeChatColor() {
		FileBuilder.setValue("User." + this.uuid.toString() + ".chatcolor", null);
		this.saveAndReload();
		this.chatcolor = null;
	}
	
	public void removeGroup() {
		this.removeTemporary();
		FileBuilder.setValue("User." + this.uuid.toString() + ".group", "");
		this.saveAndReload();
		this.group = null;
	}
	
	public void removePrio() {
		FileBuilder.setValue("User." + this.uuid.toString() + ".prio", null);
		this.saveAndReload();
		this.prio = null;
	}
	
	public void removeTemporary() {
		FileBuilder.setValue("User." + this.uuid + ".temporary_until", null);
		FileBuilder.setValue("User." + this.uuid + ".temporary_previous_rank", null);
		this.saveAndReload();
		this.temporary_previous_rank = null;
		this.temporary_until = null;
	}
	
	public void removeDiscordId() {
		FileBuilder.setValue("User." + this.uuid + ".discord_id", null);
		this.saveAndReload();
		this.discord_id = null;
	}
	
	// Getters
	
	public String getChatPrefix() {
		return this.chatprefix;
	}
	
	public String getTabPrefix() {
		return this.tabprefix;
	}
	
	public String getDisplayPrefix() {
		return this.displayprefix;
	}
	
	public String getChatColor() {
		return this.chatcolor;
	}
	
	public String getGroup() {
		return this.group;
	}
	
	public Integer getPrio() {
		return this.prio;
	}
	
	public LocalDateTime getTemporaryUntil() {
		return this.temporary_until;
	}
	
	public String getTemporaryPreviousGroup() {
		return this.temporary_previous_rank;
	}
	
	public String getDiscordId() {
		return this.discord_id;
	}
	
	
	// Util
	
	public Player getPlayer() {
		return Bukkit.getPlayer(uuid);
	}
	
	public String getPlayerDisplay() {
		return "&ctest x ";
	}
	
	private String getUserGroup() {
		if(this.group != null)return this.group;
		return PermissionManager.getDefaultGroup();
	}	
	
	public Boolean hasRankTemporary() {
		if(this.temporary_until != null) return true;
		return false;
	}
	
	public void setRankTemporary(String group, String duration) {
		LocalDateTime until = extractTimeValues(duration);	
		
		if(this.temporary_previous_rank == null) {
			this.setTemporaryPreviousRank(this.group);
		}else {
			this.setTemporaryPreviousRank(this.temporary_previous_rank);
		}
		
		this.setTemporary(until);
		FileBuilder.setValue("User." + this.uuid.toString() + ".group", group);
		this.saveAndReload();
		this.group = group;
		Player player = getPlayer();
		if(player != null) {
			if(Main.temp_ranks.contains(player)) return;
			Main.temp_ranks.add(player);
		}
	}
	
	public void tempRankOver() {
		String prevRank = this.getTemporaryPreviousGroup();
		this.removeTemporary();
		this.setGroup(prevRank);
//		Main.TablistManager.registerTeam(getPlayer(), getUserPrefix("tab"), getUserPrio());
	}
	
	
	private LocalDateTime extractTimeValues(String input) {
        // Define the regular expression pattern
        String pattern = "(\\d+mo)?(\\d+d)?(\\d+h)?(\\d+m)?(\\d+s)?";

        // Create a pattern object
        Pattern regex = Pattern.compile(pattern);

        // Match the pattern against the input string
        Matcher matcher = regex.matcher(input);

        // Initialize the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Extract and add the different time values
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                int months = Integer.parseInt(matcher.group(1).replace("mo", ""));
                now = now.plusDays(months * 30);
            }
            if (matcher.group(2) != null) {
                int days = Integer.parseInt(matcher.group(2).replace("d", ""));
                now = now.plusDays(days);
            }
            if (matcher.group(3) != null) {
                int hours = Integer.parseInt(matcher.group(3).replace("h", ""));
                now = now.plusHours(hours);
            }
            if (matcher.group(4) != null) {
                int minutes = Integer.parseInt(matcher.group(4).replace("m", ""));
                now = now.plusMinutes(minutes);
            }
            if (matcher.group(5) != null) {
                int seconds = Integer.parseInt(matcher.group(5).replace("s", ""));
                now = now.plusSeconds(seconds);
            }
        }

        // Return the resulting date and time
        return now;
    }
	
	
	public Integer getUserPrio() {
		if(getPrio() == null) {
			return new PermGroup(getUserGroup()).getPrio();
		}
		return getPrio();
	}
	
	public String getUserChatColor() {
		if(getChatColor() == null) {
			return new PermGroup(getUserGroup()).getChatColor();
		}
		return getChatColor();
	}
	
	public String getUserPrefix(String type) {
		if(type.equalsIgnoreCase("chat")) {
			String userChatPrefix = getChatPrefix();
			if(userChatPrefix == null) {
				userChatPrefix = new PermGroup(getUserGroup()).getChatPrefix();
			}
			return userChatPrefix;
		}
		
		if(type.equalsIgnoreCase("tab")) {
			String userTabPrefix = getTabPrefix();
			if(userTabPrefix == null) {
				userTabPrefix = new PermGroup(getUserGroup()).getTabPrefix();
			}
			return userTabPrefix;
		}
		return "";
	}
	
	public void saveAndReload() {
		FileBuilder.save();
		this.FileBuilder = new FileBuilder("permissions.yml");
	}
	
	public List<String> getPermissions() {
		return FileBuilder.getStringList("User." + this.uuid.toString() + ".permissions");
	}
	
	public Boolean addPermission(String permission) {
		List<String> permissions = FileBuilder.getStringList("User." + this.uuid.toString() + ".permissions");
		if(permissions.contains(permission)) {
			return false;
		}
		permissions.add(permission);
		FileBuilder.setValue("User." + this.uuid.toString() + ".permissions", permissions);
		this.saveAndReload();
		return true;
	}
	
	public Boolean removePermission(String permission) {
		List<String> permissions = FileBuilder.getStringList("User." + this.uuid.toString() + ".permissions");
		if(!permissions.contains(permission)) {
			return false;
		}
		permissions.remove(permission);
		FileBuilder.setValue("User." + this.uuid.toString() + ".permissions", permissions);
		this.saveAndReload();
		return true;
	}
	
	
	public Boolean hasPermission(String permission) {
		
		List<String> permissions = FileBuilder.getStringList("User." + this.uuid.toString() + ".permissions");
		
		String group = FileBuilder.getString("User." + this.uuid.toString() + ".group");
		Boolean hasGroup = !group.equals("");
		if(hasGroup) {
			PermGroup PermGroup = new PermGroup(group);
			if(!PermGroup.groupExists()) {
				
			}else {
				List<String> grouppermissions = new PermGroup(group).getAllPermissions();
				permissions = Stream.concat(permissions.stream(), grouppermissions.stream()).collect(Collectors.toList());
			}
		}
		
		if(permissions.indexOf(ConfigManager.wildcard) != -1) {
			return true;
		}
				
			
		if(permissions.indexOf(permission) != -1) {
			return true;
		}
		if(permission.contains(".")) {
			String[] parts = permission.split("\\.");
			
			
			String permPart = "";
			for(String part : parts) {
				permPart += permPart == "" ? part : "." + part;
				if(permissions.indexOf(permPart + "." + ConfigManager.wildcard) != -1) {
					return true;
				}
			}
		}
		
		
		return false;
	}
	
	
	
	
	
}
