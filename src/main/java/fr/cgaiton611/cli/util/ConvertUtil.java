package fr.cgaiton611.cli.util;

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

	public Optional<Date> stringToDate(Optional<String> s) {
		if (! s.isPresent())
			return Optional.empty();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(s.get());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Optional.of(new Date(parsedDate.getTime()));
	}
}
