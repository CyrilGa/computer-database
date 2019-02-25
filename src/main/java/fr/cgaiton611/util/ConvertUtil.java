package fr.cgaiton611.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class ConvertUtil {
	public Optional<Long> stringToLong(Optional<String> s) {
		if (!s.isPresent())
			return Optional.empty();
		return Optional.of(Long.parseLong(s.get()));
	}

	public Optional<Long> stringToLong(String s) {
		if (s == null)
			return Optional.empty();
		return stringToLong(Optional.of(s));
	}

	public Optional<Date> stringToDate(Optional<String> s) {
		if (!s.isPresent())
			return Optional.empty();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(s.get());
		} catch (ParseException e) {
			return Optional.empty();
		}
		return Optional.of(new Date(parsedDate.getTime()));
	}

	public String dateToString(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return dateFormat.format(date);
	}

	public Optional<Date> stringToDate(String s) {
		if (s == null || "".equals(s))
			return Optional.empty();
		return stringToDate(Optional.of(s));
	}

	public Timestamp dateToTimestamp(Date date) {
		if (date == null)
			return null;
		return new Timestamp(date.getTime());
	}
}
