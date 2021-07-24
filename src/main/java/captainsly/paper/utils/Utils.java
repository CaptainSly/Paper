package captainsly.paper.utils;

public class Utils {

	public static String toNormalCase(String string) {
		String firstLetter = string.substring(0, 1);
		firstLetter = firstLetter.toUpperCase();
		
		String rest = string.substring(1).toLowerCase();
		return firstLetter + rest;
	}
	
}
