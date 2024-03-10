package dev.enten.smp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatColor;

public class ColorManager {
	
//	public static String getColorString(String input) {
//		
//		Pattern pattern = Pattern.compile("<#([0-9a-fA-F]{6})>");
//        Matcher matcher = pattern.matcher(input);
//
//        while (matcher.find()) {
//            input = input.replace(matcher.group(0), ChatColor.of("#"+matcher.group(1)) + "");
//        }
//		
//		input = ChatColor.translateAlternateColorCodes('&', input);
//		return input;
//	}
	
	private static String RESET = ChatColor.RESET.toString();
	
	public static String getColorString(String input) {
        input = processExtras(input);
        input = processGradient(input);
        input = processColorCodes(input);
        return input;
    }

	
	
    private static String processColorCodes(String input) {
        Pattern pattern = Pattern.compile("(<#([0-9a-fA-F]{6})>(.+?)</#>)|(<#([0-9a-fA-F]{6})>(.+?))");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
        	
        	if(matcher.group(2) != null) {
                input = input.replace(matcher.group(0), ChatColor.of("#" + matcher.group(2)) + matcher.group(3) + RESET);
        	}else {
                input = input.replace(matcher.group(0), ChatColor.of("#" +matcher.group(5))  + matcher.group(6));
        	}
        	
        }
        input = ChatColor.translateAlternateColorCodes('&', input);
        return input;
    }

    private static String processGradient(String input) {
        Pattern pattern = Pattern.compile("((?i)<gradient:(#[0-9a-fA-F]{6}),(#[0-9a-fA-F]{6})>(.+?)(?i)</gradient>)|((?i)<gradient:(#[0-9A-Fa-f]{6}),(#[0-9A-Fa-f]{6})>((?:(?!§).)*))");
        Matcher matcher = pattern.matcher(input);
        
        while (matcher.find()) {
        	
        	if(matcher.group(1) != null) {
	        	
	            String startColor = matcher.group(2);
	            String endColor = matcher.group(3);
	            String text = matcher.group(4);
	
	            String gradientText = applyGradient(startColor, endColor, text);
	            input = input.replace(matcher.group(0), gradientText);
        	}else {
	            String startColor = matcher.group(6);
	            String endColor = matcher.group(7);
	            String text = matcher.group(8);
	
	            String gradientText = applyGradient(startColor, endColor, text);
	            input = input.replace(matcher.group(0), gradientText);
        	}
        }

        return input;
    }
    
    private static String processExtras(String input) {
    	
    	input = input.replaceAll("(?i)<bold>", ChatColor.BOLD.toString())
    				 .replaceAll("(?i)<reset>", RESET)
    				 .replaceAll("(?i)<underline>", ChatColor.UNDERLINE.toString())
    				 .replaceAll("(?i)<strikethrough>", ChatColor.STRIKETHROUGH.toString())
    				 .replaceAll("(?i)<italic>", ChatColor.ITALIC.toString())
    				 .replaceAll("(?i)<br>", " ")
    				 .replaceAll("(?i)<magic>", ChatColor.MAGIC.toString());
    				
    	
    	return input;
    }
    

    private static String applyGradient(String startColor, String endColor, String text) {
        StringBuilder result = new StringBuilder();
        
        Boolean lastWasColorChar = false;
        String customColorString = "";
        
        int length = text.length();
        double r1 = Integer.parseInt(startColor.substring(1, 3), 16);
        double g1 = Integer.parseInt(startColor.substring(3, 5), 16);
        double b1 = Integer.parseInt(startColor.substring(5, 7), 16);
        double r2 = Integer.parseInt(endColor.substring(1, 3), 16);
        double g2 = Integer.parseInt(endColor.substring(3, 5), 16);
        double b2 = Integer.parseInt(endColor.substring(5, 7), 16);

        for (int i = 0; i < length; i++) {
        	if(text.charAt(i) != ChatColor.COLOR_CHAR && !lastWasColorChar) {
                double ratio = (double) i / (length - 1);
                int r = (int) (r1 + (ratio * (r2 - r1)));
                int g = (int) (g1 + (ratio * (g2 - g1)));
                int b = (int) (b1 + (ratio * (b2 - b1)));

                String hexCode = String.format("#%02x%02x%02x", r, g, b);
                result.append(ChatColor.of(hexCode)).append(customColorString + text.charAt(i));
        	}
        	if(text.charAt(i) == ChatColor.COLOR_CHAR) {
        		lastWasColorChar = true;
        	}
        	if(lastWasColorChar) {
        		lastWasColorChar = false;
        		customColorString = "§" + text.charAt(i); 
        	}
        }

        return result.toString() + RESET;
    }
	
}
