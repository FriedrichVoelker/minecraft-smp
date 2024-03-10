package dev.enten.smp.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import dev.enten.smp.Main;



public class FileBuilder {
	 
	protected File file;
	protected FileConfiguration cfg;
 
	public FileBuilder(String FileName){
		String FilePath = Main.BASE_DIR_PATH;
		this.file = new File(FilePath, FileName);
		if(!this.file.exists()) {
			
			File folder = new File(FilePath);
			if(!folder.exists()) {
				folder.mkdir();
			}
			try {
				this.file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
//		try {
			this.cfg = YamlConfiguration.loadConfiguration(this.file);
//		}catch(Exception ex) {}
	}
	public FileBuilder setValue(String ValuePath, Object Value){
		cfg.set(ValuePath, Value);
		return this;
	}
	public boolean exist(){
		return this.file.exists();
	}
	public Object getObject(String ValuePath){
		return cfg.get(ValuePath);
	}
	public int getInt(String ValuePath){
		return cfg.getInt(ValuePath);
	}
	public double getDouble(String ValuePath){
		return cfg.getDouble(ValuePath);
	}
	public long getLong(String ValuePath){
		return cfg.getLong(ValuePath);
	}
	public String getString(String ValuePath){
		return cfg.getString(ValuePath);
	}
	public boolean getBoolean(String ValuePath){
		return cfg.getBoolean(ValuePath);
	}
	public List<String> getStringList(String ValuePath){
		return cfg.getStringList(ValuePath);
	}
	public Set<String> getKeys(boolean deep){
		return cfg.getKeys(deep);
	}
	public ConfigurationSection getConfigurationSection(String Section){
		return cfg.getConfigurationSection(Section);
	}
	public void delete(){
		file.delete();
	}
	public FileBuilder save(){
		try {
			this.cfg.save(this.file);
		}catch (IOException e){
			e.printStackTrace();
		}
		return this;
	}
	public FileBuilder load(){
		try {
			this.cfg.load(this.file);
		}catch (IOException | InvalidConfigurationException e){
			e.printStackTrace();
		}
		return this;
	}
 
}
