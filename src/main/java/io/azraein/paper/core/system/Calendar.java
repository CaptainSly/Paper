package io.azraein.paper.core.system;

import org.tinylog.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Calendar {

	private final String[] months = { "Frost Mouth", "Rains Hand", "Suns Fury", "Leaf Harvest" };
	private final String[] days = { "Foresday", "Tinsday", "Middas", "Laosday" };

	private final StringProperty timeProperty = new SimpleStringProperty("00:00 AM");
	private final StringProperty dateProperty = new SimpleStringProperty();

	private int hours, minutes;
	private int day, week, month, year;

	private String era = "RE";

	public Calendar() {
		this.hours = 0;
		this.minutes = 0;
		this.day = 1;
		this.week = 1;
		this.month = 1;
		this.year = 164;
	}

	public void update(float delta) {
		minutes++;
		if (minutes >= 60) {
			minutes = 0;
			hours++;
			if (hours >= 24) {
				hours = 0;
				day++;
				if (day > 20) {
					day = 1;
					week++;
					if (week > 5) {
						week = 1;
						month++;
						if (month > 4) {
							month = 1;
							year++;
						}
					}
				}
			}
		}

		timeProperty.set(getTimeAsString());
		dateProperty.set(getDateAsString());
	}
	
	public StringProperty timeProperty() {
		return timeProperty;
	}
	
	public StringProperty dateProperty() {
		return dateProperty;
	}

	public String getTimeAsString() {
		String timeFormat = String.format("%02d:%02d %s", (hours == 0 ? 12 : hours), minutes, getAmOrPm());
		return timeFormat;
	}

	public String getDateAsString() {
		String date = String.format("%d%s The %s of %s, %s", year, era, Utils.getNumberSuffix(day), months[month - 1],
				days[day - 1]);
		return date;
	}

	public String getAmOrPm() {
		return hours < 12 ? "AM" : "PM";
	}

	public int getYear() {
		return year;
	}

	public String getYearEra() {
		return year + era;
	}

	public String getDayString() {
		return days[day - 1];
	}

	public String getMonthString() {
		return months[month - 1];
	}

	public int getHours() {
		return hours;
	}

	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}

	public void setTime(int hours, int minutes, int day, int week, int month) {
		if (isValidTime(hours, minutes) && isValidDate(day, week, month)) {
			this.hours = hours;
			this.minutes = minutes;
			this.day = day;
			this.week = week;
			this.month = month;
		} else {
			Logger.error("Invalid time or date values provided.");
		}
	}

	public void setTime(int hours, int minutes) {
		if (isValidTime(hours, minutes)) {
			this.hours = hours;
			this.minutes = minutes;
		} else
			Logger.error("Invalid time value provided");
	}

	public void setDate(int day, int week, int month) {
		if (isValidDate(day, week, month)) {
			this.day = day;
			this.week = week;
			this.month = month;
		} else
			Logger.error("Invalid date value provided");
	}

	private boolean isValidTime(int hours, int minutes) {
		return (hours >= 0 && hours < 12) && (minutes >= 0 && minutes < 60);
	}

	private boolean isValidDate(int day, int week, int month) {
		return (day >= 1 && day <= 20) && (week >= 1 && week <= 5) && (month >= 1 && month <= 4);
	}

}