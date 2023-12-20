package io.azraein.paper.core.system;

public class Utils {

	public static String toNormalCase(String str) {
		
		if (str.contains("_"))
			str.replace("_", " ");
		
		return str.substring(0, 1).toUpperCase() + str.substring(1, str.length()).toLowerCase();
	}

	public static String getNumberSuffix(int number) {
		if (number >= 11 && number <= 13) {
			return "th";
		} else {
			int lastDigit = number % 10;
			switch (lastDigit) {
			case 1:
				return number + "st";
			case 2:
				return number + "nd";
			case 3:
				return number + "rd";
			default:
				return number + "th";
			}
		}
	}

}
