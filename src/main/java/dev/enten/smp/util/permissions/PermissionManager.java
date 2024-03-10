package dev.enten.smp.util.permissions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import dev.enten.smp.util.FileBuilder;

public class PermissionManager {
	
	public static String getDefaultGroup() {
		
		FileBuilder FileBuilder = new FileBuilder("permissions.yml");
		ConfigurationSection groupscgs = FileBuilder.getConfigurationSection("Groups");
		
		if(groupscgs == null) {
			return "";
		}
		
		for(String key : groupscgs.getKeys(false)) {
			if(new PermGroup(key).getDefault()) {
				return key;
			}
		}
		return "";
	}
	
	public static List<String> getAllGroups(){
		FileBuilder FileBuilder = new FileBuilder("permissions.yml");
		ConfigurationSection groupscgs = FileBuilder.getConfigurationSection("Groups");
		
		if(groupscgs == null) {
			return List.of();
		}
		
		
		List<String>groups = new ArrayList<>();
		
		for(String key : groupscgs.getKeys(false)) {
			groups.add(key);
		}
		
		return groups;
	}
	
	public static List<String> getAllGroupsSorted(){
		FileBuilder FileBuilder = new FileBuilder("permissions.yml");
		ConfigurationSection groupscgs = FileBuilder.getConfigurationSection("Groups");
		
		if(groupscgs == null) {
			return List.of();
		}
		List<String>groups = new ArrayList<>();
		
		for(String key : groupscgs.getKeys(false)) {
			
			
			groups.add(key);
		}
		
		groups.sort(Comparator.comparingInt(PermissionManager::returnNumb));
		return groups;
	}
	
	private static Integer returnNumb(String str) {
		return new PermGroup(str).getPrio();
	}
	
}
