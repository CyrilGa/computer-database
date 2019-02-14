package fr.cgaiton611.cli.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertUtil {
	public Integer stringToInt(String s) {
		if (s == null) return null;
		return Integer.parseInt(s);
	}
	
	public Timestamp stringToTimastamp(String s) {
		if (s == null) return null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
		Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(s+":00:000");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Timestamp(parsedDate.getTime());
	}
}
