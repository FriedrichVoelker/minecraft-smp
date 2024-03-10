package dev.enten.smp.util.permissions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.ChatColor;

import dev.enten.smp.util.FileBuilder;

public class PermGroup {

	private String group;
	private FileBuilder FileBuilder;
	private Boolean isDefault;
	private String chatprefix;
	private String tabprefix ;
	private String displayprefix;
	private String chatcolor;
	private Integer prio;
	private String display;
	private String item_material;
	private List<String> parents;
	private String discord_role;
	
	public PermGroup(String group) {
		this.group = group;
		this.FileBuilder =  new FileBuilder( "permissions.yml");
		
		
		this.chatprefix = FileBuilder.getString("Groups." + this.group + ".chatprefix");
		this.tabprefix = FileBuilder.getString("Groups." + this.group + ".tabprefix");
		this.displayprefix = FileBuilder.getString("Groups." + this.group + ".displayprefix");
		this.chatcolor = FileBuilder.getString("Groups." + this.group + ".chatcolor");
		this.prio = FileBuilder.getInt("Groups." + this.group + ".prio");
		this.isDefault = FileBuilder.getBoolean("Groups." + this.group + ".default");
		this.parents = FileBuilder.getStringList("Groups." + this.group + ".parents");
		this.display = FileBuilder.getString("Groups." + this.group + ".display");
		this.item_material = FileBuilder.getString("Groups." + this.group + ".item_material");
		this.discord_role = FileBuilder.getString("Groups." + this.group + ".discord_role");
	}
	
	//	Setters
	
	public void setChatPrefix(String prefix) {
		FileBuilder.setValue("Groups." + this.group.toString() + ".chatprefix", prefix);
		this.saveAndReload();
		this.chatprefix = prefix;
	}
	
	public void setTabPrefix(String prefix) {
		FileBuilder.setValue("Groups." + this.group.toString() + ".tabprefix", prefix);
		this.saveAndReload();
		this.tabprefix = prefix;
	}
	
	public void setDisplayPrefix(String prefix) {
		FileBuilder.setValue("Groups." + this.group.toString() + ".displayprefix", prefix);
		this.saveAndReload();
		this.displayprefix = prefix;
	}
	
	public void setChatColor(String color) {
		FileBuilder.setValue("Groups." + this.group.toString() + ".chatcolor", color);
		this.saveAndReload();
		this.chatcolor = color;
	}
	public void setPrio(Integer prio) {
		FileBuilder.setValue("Groups." + this.group.toString() + ".prio", prio);
		this.saveAndReload();
		this.prio = prio;
	}
	public void setDefault() {
		FileBuilder.setValue("Groups." + this.group + ".default", true);
		this.saveAndReload();
		this.isDefault = true;
	}
	public void setDisplay(String display) {
		FileBuilder.setValue("Groups." + this.group + ".display", display);
		this.saveAndReload();
		this.display = display;
	}
	public void setItemMaterial(String item_material) {
		FileBuilder.setValue("Groups." + this.group + ".item_material", item_material);
		this.saveAndReload();
		this.item_material = item_material;
	}
	public void setDiscordRole(String discord_role) {
		FileBuilder.setValue("Groups." + this.group + ".discord_role", discord_role);
		this.saveAndReload();
		this.discord_role = discord_role;
	}
	
	// Remover
	
	public void removeChatPrefix() {
		FileBuilder.setValue("Groups." + this.group.toString() + ".chatprefix", null);
		this.saveAndReload();
		this.chatprefix = null;
	}
	
	public void removeTabPrefix() {
		FileBuilder.setValue("Groups." + this.group.toString() + ".tabprefix", null);
		this.saveAndReload();
		this.tabprefix = null;
	}
	
	public void removeDisplayPrefix() {
		FileBuilder.setValue("Groups." + this.group.toString() + ".displayprefix", null);
		this.saveAndReload();
		this.displayprefix = null;
	}
	
	public void removeChatColor() {
		FileBuilder.setValue("Groups." + this.group.toString() + ".chatcolor", null);
		this.saveAndReload();
		this.chatcolor = null;
	}
	public void removePrio() {
		FileBuilder.setValue("Groups." + this.group.toString() + ".prio", null);
		this.saveAndReload();
		this.prio = null;
	}
	public void removeDefault() {
		FileBuilder.setValue("Groups." + this.group + ".default", null);
		this.saveAndReload();
		this.isDefault = null;
	}
	public void removeDisplay() {
		FileBuilder.setValue("Groups." + this.group + ".display", null);
		this.saveAndReload();
		this.display = null;
	}
	public void removeItemMaterial() {
		FileBuilder.setValue("Groups." + this.group + ".item_material", null);
		this.saveAndReload();
		this.item_material = null;
	}
	public void removeDiscordRole() {
		FileBuilder.setValue("Groups." + this.group + ".discord_role", null);
		this.saveAndReload();
		this.discord_role = null;
	}
	
	// Getters
	
	public String getChatPrefix() {
		return this.chatprefix != null ? this.chatprefix : "";
	}
	
	public String getTabPrefix() {
		return this.tabprefix != null ? this.tabprefix : "";
	}
	
	public String getDisplayPrefix() {
		return this.displayprefix != null ? this.displayprefix : "";
	}
	
	public String getChatColor() {
		return this.chatcolor != null ? this.chatcolor : "";
	}
	
	public Integer getPrio() {
		return this.prio;
	}
	
	public Boolean getDefault() {
		return this.isDefault;
	}
	
	public List<String> getParents() {
		return this.parents;
	}
	
	public String getDisplay() {
		return this.display != null ? this.display : "";
	}
	
	public String getItemMaterial() {
		return this.item_material;
	}
	
	public String getDiscordRole() {
		return this.discord_role;
	}
	
	
	// Util
	
	public void saveAndReload() {
		FileBuilder.save();
		this.FileBuilder = new FileBuilder("permissions.yml");
	}
	
	public String getItemString() {
		if(this.item_material != null) {
				return this.item_material.toUpperCase();
		}
		return "-/-";
	}
	
	public String returnDisplayName() {
		if(this.display != null) {
			return ChatColor.translateAlternateColorCodes('&', this.display);
		}
		return this.group;
	}
	
//	public ItemStack getItem() {
//		if(this.item_material != null) {
//			try {
//				return new ItemStack(Material.getMaterial(this.item_material.toUpperCase()));
//			}catch(Exception ex) {
//				Logger.log("error", "Material " + this.item_material + " is invalid");
//				try {
//					return new ItemStack(Material.getMaterial(ConfigManager.default_item));
//				}catch(Exception ex2) {
//					Logger.log("error", "Config default item is broken, reverting to IRON_HELMET");
//					return new ItemStack(Material.IRON_HELMET);
//				}
//			}
//		}
//		try {
//			return new ItemStack(Material.getMaterial(ConfigManager.default_item));
//		}catch(Exception ex) {
//			Logger.log("error", "Config default item is broken, reverting to IRON_HELMET");
//			return new ItemStack(Material.IRON_HELMET);
//		}
//	}
	
	
	public Boolean groupExists() {
		return FileBuilder.getObject("Groups." + group) != null;
	}
	
	public Boolean create() {
		if(!groupExists()) {
			FileBuilder.setValue("Groups." + group, "");
			FileBuilder.setValue("Groups." + group+ ".permissions", "");
			this.saveAndReload();
			return true;
		}
		return false;
	}
	
	public Boolean delete() {
		if(!groupExists()) {
			return false;
		}
		FileBuilder.setValue("Groups." + this.group, null);
		this.saveAndReload();
		return true;
	}
	
	public Boolean addParent(String parent) {
		if(this.parents.contains(parent)) {
			return false;
		}
		
		this.parents.add(parent);
		FileBuilder.setValue("Groups." + this.group + ".parents", parents);
		this.saveAndReload();
		return true;
	}
	
	public Boolean removeParent(String parent) {
		if(!this.parents.contains(parent)) {
			return false;
		}
		
		this.parents.remove(parent);
		FileBuilder.setValue("Groups." + this.group + ".parents", parents);
		this.saveAndReload();
		return true;
	}
	
	public List<String> getPermissions(){
		List<String>permissions = FileBuilder.getStringList("Groups." + this.group + ".permissions");
		return permissions;
	}
	
	public List<String> getAllPermissions(){
		List<String>permissions = FileBuilder.getStringList("Groups." + this.group + ".permissions");
		for(String parent : parents) {
			List<String>parentpermissions = new PermGroup(parent).getAllPermissions();
			permissions = Stream.concat(permissions.stream(), parentpermissions.stream()).collect(Collectors.toList());
		}
		return permissions;
	}
	
	public Boolean addPermission(String permission) {
		List<String> permissions = FileBuilder.getStringList("Groups." + this.group + ".permissions");
		if(permissions.contains(permission)) {
			return false;
		}
		permissions.add(permission);
		FileBuilder.setValue("Groups." + this.group + ".permissions", permissions);
		this.saveAndReload();
		return true;
	}
	
	public Boolean removePermission(String permission) {
		List<String> permissions = FileBuilder.getStringList("Groups." + this.group + ".permissions");
		if(!permissions.contains(permission)) {
			return false;
		}
		permissions.remove(permission);
		FileBuilder.setValue("Groups." + this.group + ".permissions", permissions);
		this.saveAndReload();
		return true;
	}
	
	
}
