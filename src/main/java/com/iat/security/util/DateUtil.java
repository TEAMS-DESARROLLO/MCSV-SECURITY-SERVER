package com.iat.security.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	
	private DateUtil() {}
	
	private static final String DATE_FORMAT_DD_MM_YYYY = "dd-MM-yyyy";

	public static LocalDate  convertStringToLocalDate(String dateString) {
		try {			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_DD_MM_YYYY);
			return LocalDate.parse(dateString,formatter);			
		} catch (Exception e) {
			throw new IllegalArgumentException(" invalid date format. Please use dd-MM-yyyy");
		}
	}
	
}
