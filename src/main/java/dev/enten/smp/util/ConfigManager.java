package dev.enten.smp.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.bukkit.ChatColor;

import dev.enten.smp.Main;

public class ConfigManager {
	
	public static String lang = "en";
	public static String chatformat = "%player% >> %message%";
	public static String base_perm = "perm.";
	public static String prefix = "";
	public static String loglevel = "info";
	public static String wildcard = "*";
	public static String enable_op = "all";
	public static String log_type = "Console";
	
	public static String playerlist_header = "test";
	public static String playerlist_footer = "";
	
	public static void init() {
		genConfig();
		readConfig();
		
		
		
	}
	
	public static void readConfig() {
		FileBuilder FileBuilder = new FileBuilder("config.yml");
		if(!FileBuilder.exist()) {
			genConfig();
			Logger.log("error", "Config not found.");
			readConfig();
			return;
		}
		
		String confLang = FileBuilder.getString("lang");
		String confChatformat = FileBuilder.getString("chat_format");
		String confBasePerm = FileBuilder.getString("base_permission");
		String confPrefix = FileBuilder.getString("prefix");
		String confLoglevel = FileBuilder.getString("log.level");
		String confWildcard = FileBuilder.getString("perms.wildcard");
		String confEnableop = FileBuilder.getString("perms.enable_op");
		String confLogType = FileBuilder.getString("log.type");
		String confPLHeader = FileBuilder.getString("playerlist.header");
		String confPLFooter = FileBuilder.getString("playerlist.footer");
		

		
		if(confLang != null && !confLang.equalsIgnoreCase("")) {
			lang = confLang;
		}
		
		if(confChatformat != null && !confChatformat.equalsIgnoreCase("")) {
			chatformat = ChatColor.translateAlternateColorCodes('&', confChatformat);
		}
		
		if(confBasePerm != null && !confBasePerm.equalsIgnoreCase("")) {
			base_perm = confBasePerm;
		}
		
		if(confPrefix != null && !confPrefix.equalsIgnoreCase("")) {
			prefix = ChatColor.translateAlternateColorCodes('&', confPrefix);
		}
		
		if(confLogType != null && !confLogType.equalsIgnoreCase("")) {
			if(confLogType.equals("File") || confLogType.equals("Console")) {
				log_type = confLogType;
			}
		}
		
		if(confWildcard != null && !confWildcard.equalsIgnoreCase("")) {
			wildcard = confWildcard;
		}
		
		if(confPLHeader != null && !confPLHeader.equalsIgnoreCase("")) {
			playerlist_header = confPLHeader;
		}
		
		if(confPLFooter != null && !confPLFooter.equalsIgnoreCase("")) {
			playerlist_footer = confPLFooter;
		}
		
		if(confLoglevel != null && !confLoglevel.equalsIgnoreCase("")) {
			if(Logger.Levels.valueOf(confLoglevel) != null) {
				loglevel = confLoglevel;
			}
		}
		
		if(confEnableop != null && !confEnableop.equalsIgnoreCase("")) {
			confEnableop = confEnableop.toLowerCase();
			if(confEnableop.equalsIgnoreCase("all") || confEnableop.equalsIgnoreCase("normal") || confEnableop.equalsIgnoreCase("none")) {
				enable_op = confEnableop;
			}
		}
		
	}
	
	public static String getRawValue(String path) {
		FileBuilder FileBuilder = new FileBuilder("config.yml");
		if(!FileBuilder.exist()) {
			genConfig();
			Logger.log("error", "Config not found.");
			readConfig();
			return "";
		}
		return FileBuilder.getString(path);
	}
	
	public static String getEnableOP() {
		return enable_op;
	}
	
	private static void genConfig() {

		File folder = new File(Main.BASE_DIR_PATH);
		if(!folder.exists()) {
			folder.mkdir();
		}
		
        File configFile = new File(folder, "config.yml");
        if (!configFile.exists()) {
            Logger.log("debug", "Generating config....");
            try (InputStream inputStream = Main.getInstance().getResource("config.yml")) {
                Files.copy(inputStream, configFile.toPath());
                Logger.log("debug", "Generated config");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
}
