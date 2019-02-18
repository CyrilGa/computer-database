package fr.cgaiton611.cli.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class ConvertUtil {
	public Optional<Integer> stringToInt(Optional<String> s) {
		if (! s.isPresent())
			return null;
		return Optional.of(Integer.parseInt(s.get()));
	}

	public Optional<Timestamp> stringToTimastamp(Optional<String> s) {
		if (! s.isPresent())
			return Optional.empty();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
		Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(s + ":00:000");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Optional.of(new Timestamp(parsedDate.getTime()));
	}
}
