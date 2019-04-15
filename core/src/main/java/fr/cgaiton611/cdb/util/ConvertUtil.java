package fr.cgaiton611.cdb.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.cgaiton611.cdb.exception.MappingStringToDateException;
import fr.cgaiton611.cdb.exception.MappingStringToIntegerException;
import fr.cgaiton611.cdb.exception.MappingStringToLongException;

public class ConvertUtil {
	private final Logger logger = LoggerFactory.getLogger(ConvertUtil.class);

	public Optional<Long> stringToLong(Optional<String> s) throws MappingStringToLongException {
		if (!s.isPresent()) {
			return Optional.empty();
		}
		Long lon;
		try {
			lon = Long.parseLong(s.get());
		} catch (NumberFormatException e) {
			throw new MappingStringToLongException();
		}
		return Optional.of(lon);
	}

	public Optional<Long> stringToLong(String s) throws MappingStringToLongException {
		if (s == null)
			return Optional.empty();
		return stringToLong(Optional.of(s));
	}

	public Optional<Date> stringToDate(Optional<String> s) throws MappingStringToDateException {
		if (!s.isPresent())
			return Optional.empty();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(s.get());
		} catch (ParseException e) {
			throw new MappingStringToDateException();
		}
		return Optional.of(new Date(parsedDate.getTime()));
	}

	public String dateToString(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return dateFormat.format(date);
	}

	public Optional<Date> stringToDate(String s) throws MappingStringToDateException {
		if (s == null || "".equals(s))
			return Optional.empty();
		return stringToDate(Optional.of(s));
	}

	public Timestamp dateToTimestamp(Date date) {
		if (date == null)
			return null;
		return new Timestamp(date.getTime());
	}

	public Optional<Integer> stringToInteger(String s) throws MappingStringToIntegerException {
		if (s == null)
			return Optional.empty();
		Integer i = null;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			logger.error("Cannot convert string to int");
			throw new MappingStringToIntegerException();
		}
		return Optional.ofNullable(i);
	}
}
