package dev.enten.smp.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
	
	
	public static enum Levels{
		NONE(0),
		INFO(1),
		WARN(2),
		ERROR(3),
		DEBUG(4);
		
		public final Integer level;
		
		private Levels(Integer level) {
			this.level = level;
		}
	}
	
	
	public static void log(String level, String message) {
		level = level.toUpperCase();
		Integer intLvl = Levels.valueOf(level.toUpperCase()).level;
		Integer intConfLvl = Levels.valueOf(ConfigManager.loglevel.toUpperCase()).level;
		
		if(intLvl <= intConfLvl) {
			if(ConfigManager.log_type.equals("Console")) {
				System.out.println("[SMP] [" + level + "]: " + message);
			}else if(ConfigManager.log_type.equals("File")) {
				String filePath = "plugins/smp/log.log";
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
		            writer.write("[" + level + "]: " + message);
		            writer.newLine(); // Write a new line separator
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			}
		}
	}
	
	
}
