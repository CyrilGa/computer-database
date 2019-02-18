package fr.cgaiton611.cli.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class ConvertUtil {
	public Optional<Long> stringToLong(Optional<String> s) {
		if (! s.isPresent())
			return Optional.empty();
		return Optional.of(Long.parseLong(s.get()));
	}

	public Optional<Date> stringToDate(Optional<String> s) {
		if (! s.isPresent())
			return Optional.empty();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(s.get()+":00:000");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Optional.of(new Date(parsedDate.getTime()));
	}
	
	public Timestamp DateToTimestamp(Date date) {
		if (date == null) return null;
		return new Timestamp(date.getTime());
	}
}
