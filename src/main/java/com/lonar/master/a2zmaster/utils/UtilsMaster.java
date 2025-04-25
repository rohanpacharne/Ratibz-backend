package com.lonar.master.a2zmaster.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.apache.commons.text.WordUtils;

import jakarta.servlet.http.HttpServletRequest;

public class UtilsMaster {

	public static Date getCurrentDateTime() {
		 Instant instant = Instant.now();
		 ZonedDateTime jpTime = instant.atZone(ZoneId.of("Asia/Calcutta"));
		 try {
			 Calendar calendar = new GregorianCalendar();
			 calendar.set(jpTime.getYear(), jpTime.getMonthValue()-1, jpTime.getDayOfMonth(), jpTime.getHour(), jpTime.getMinute(), jpTime.getSecond());
			 return calendar.getTime();
		 } catch (Exception e1) {
			e1.printStackTrace();
		 }
		 return new Date();
	}
	
	public static Date addTimeInMinutes(Date date, int minute) {
		try {
			Calendar calender = Calendar.getInstance();
			calender.setTime(date);
			calender.add(Calendar.MINUTE, minute);
			return calender.getTime();
		}catch(Exception e ) {
			e.printStackTrace();
		}
		return null;  
	}
	
	public static Date getConvertedTime( String time  ) {
		int hour = 0;
		int minutes = 0;
		if(time != null) {
			String[] times =  time.split(":");
			hour = Integer.parseInt(times[0]);
			minutes = Integer.parseInt(times[1]);
			
		}		
		 Instant instant = Instant.now();
		 ZonedDateTime jpTime = instant.atZone(ZoneId.of("Asia/Calcutta"));
		 try {
			 Calendar calendar = new GregorianCalendar();
			 calendar.set(jpTime.getYear(), jpTime.getMonthValue()-1, jpTime.getDayOfMonth(), hour, minutes, 0);
			 return calendar.getTime();
		 } catch (Exception e1) {
			e1.printStackTrace();
		 }
		 return new Date();
	}
	
	
	public static Date addDays(Date date, int day) {
		try {
			Calendar calender = Calendar.getInstance();
			calender.setTime(date);
			calender.add(Calendar.DAY_OF_YEAR, day);
			return calender.getTime();
		}catch(Exception e ) {
			e.printStackTrace();
		}
		return null;  
	}
	
	
	public static String capitalizeSentence(String value) {
		StringBuilder newWord = new StringBuilder();
		try {
		String words[] = value.split("\\.");  
		    for(int i = 0; i < words.length; i++ ) {
			   String firstStr = words[i].trim().substring(0, 1);
			   String str =  words[i].trim().replaceFirst(firstStr.toLowerCase(), firstStr.toUpperCase()); 
			   if(i > 0) {
				  newWord.append(". "+ str);
			   }else {
				  newWord.append( str );
			   }		 
		    }
		    return  newWord.toString();
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return value;
	}
	
	public static DateFormat dateForyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

	public static DateFormat dateFormatdMMMyyyy = new SimpleDateFormat("d MMM yyyy");

	public static Date getCurrentDate() {
		 Instant instant = Instant.now();
		 ZonedDateTime jpTime = instant.atZone(ZoneId.of("Asia/Calcutta"));
		 try {
			 Calendar calendar = new GregorianCalendar();
			 calendar.set(jpTime.getYear(), jpTime.getMonthValue()-1, jpTime.getDayOfMonth());
			 return calendar.getTime();
		 } catch (Exception e1) {
			e1.printStackTrace();
		 }
		 return new Date();
	}
	
	public static String setCapitalizeFully(String value) {
		try {
		StringBuilder newWord = new StringBuilder();
		String words[] = value.split("\\(");
		  for(int i = 0; i < words.length; i++ ) {
			  if(i == 0) {
				  newWord.append( WordUtils.capitalizeFully( words[i]  ));
			  }else {
				  newWord.append("("+ WordUtils.capitalizeFully( words[i]  ));
			  }
		  }
		 return  newWord.toString();
		}catch(Exception e) {
			//e.printStackTrace();
		}
		return WordUtils.capitalizeFully(value);
	}
	
	public static int getRandomNumberInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
		//return 8866;
		//return 1234;
	}
	
	public static String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }
	
	public static String generateSupplierCode( Long supplierId, String supplierName ) {
		try {
			StringBuilder  code = new StringBuilder();
			String[] strArr = null;
			if(supplierName != null) {
				strArr = supplierName.split(" ");
			}
			
			for(int i = 0; i < strArr.length; i++ ){
				if(strArr[i].trim().length() > 0  ) {
					code.append(strArr[i].charAt(0));	
				}
			}
			return "L"+supplierId+code.toString().toUpperCase(); 
		}catch(Exception e) {
			//e.printStackTrace();
		}
		return null;
	}
	
}


/*TimeZone.setDefault(TimeZone.getTimeZone("GMT+5:30"));
Calendar cal_Two = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
//System.out.println(cal_Two.getTimeZone());
return cal_Two.getTime();*/