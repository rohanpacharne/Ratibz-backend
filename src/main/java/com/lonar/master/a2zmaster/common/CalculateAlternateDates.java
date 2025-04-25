package com.lonar.master.a2zmaster.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CalculateAlternateDates {
	
	public static void main(String args[]) {
		CalculateAlternateDates CalculateAlternateDates = new CalculateAlternateDates();
		try {
			CalculateAlternateDates.getAlternateDaysInDate("2020-02-28","2020-03-31","Y");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<Date> getAlternateDaysInDate(String startDate, String endDate, String alternateDay) throws ParseException {
		
		
		Date start = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);  
		Date end = new SimpleDateFormat("yyyy-MM-dd").parse(endDate); 
		
		Date dayAfter = new Date(end.getTime() + TimeUnit.DAYS.toMillis( 1 ));
		
		//System.out.println("start "+start+" end "+dayAfter);
		//Date start = new Date(2020, 01, 25);
		//Date end = new Date(2020, 02, 25);
		
		List<Date> dates = new ArrayList<Date>();
	    Calendar calendar = new GregorianCalendar();
	    calendar.setTime(start);
	    while (calendar.getTime().before(dayAfter)) {
	        Date result = calendar.getTime();
	        dates.add(result);
	       // System.out.println(result);
	        calendar.add(Calendar.DATE, 2);
	    }
	    //System.out.println(dates);
	    return dates;
	}

}
