package captainsly.paper.utils;

import captainsly.paper.entities.stats.Stat;

public class Utils {

	public static final String VERSION = "0.0.2";
	
	public static String toNormalCase(String string) {
		String firstLetter = string.substring(0, 1);
		firstLetter = firstLetter.toUpperCase();

		String rest = string.substring(1).toLowerCase();
		return firstLetter + rest;
	}

	public static String writeStatString(Stat stat, int statValue) {
		return stat.name() + ": " + statValue;
	}

}
