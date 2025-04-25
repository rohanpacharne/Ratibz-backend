package com.lonar.master.a2zmaster.common;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import io.lamma.Date;
import io.lamma.Dates;
import io.lamma.DayOfWeek;

/*import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;*/

public class CalculateDate {

    public static void main(String[] args) throws ParseException {
    	CalculateDate calculateDate=new CalculateDate();
    	
    	//System.out.println(calculateDate.findLast("2020-03-31"));
    	//System.out.println(calculateDate.findNextMonthEndDate("2020-03-31"));
    //	calculateDate.utilToYYYYMMDD("Sat Dec 01 00:00:00 GMT 2012");
    	//yyyy-mm-dd
    	//System.out.println(calculateDate.findLastSub("2019-11-18", "2019-12-25"));;

    	//calculateDate.getDaysInDate("2019-11-1","2019-12-31","Y","y","y","y","y","y","Y");
    //	calculateDate.getDaysInDate("2019-11-1","2019-12-31","Y","y","n","n","n","n","n");
    }
	
    
    public static String findNextMonthEndDate(String startDate){
		Date calEndDate=Date.apply(startDate).nextLastDayOfMonth();
		return calEndDate.yyyy()+"-"+calEndDate.mm()+"-"+calEndDate.dd();
	}
    
    
	public static String utilToYYYYMMDD(java.util.Date utilDate) {
		//System.out.println("utilDate "+utilDate);
		//java.util.Date date = new java.util.Date(utilDate);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format = formatter.format(utilDate);
		//System.out.println(format);
		return format;
	}
	
	public static String getNextDate(String  curDate) throws ParseException {
		  final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		  final java.util.Date date = format.parse(curDate);
		  final Calendar calendar = Calendar.getInstance();
		  calendar.setTime(date);
		  calendar.add(Calendar.DAY_OF_YEAR, 1);
		  return format.format(calendar.getTime()); 
		}
	
	public static String findLast(String startDate){
		Date calEndDate=Date.apply(startDate).plusMonths(1).nextLastDayOfMonth();
		return calEndDate.yyyy()+"-"+calEndDate.mm()+"-"+calEndDate.dd();
	}
	
	
	
	public static String findLastSub(String startDate, String endDate) {
		
		String subCalEndDate = findLast(startDate);
		if(Date.apply(endDate).$greater$eq(Date.apply(subCalEndDate))){
			return subCalEndDate;
		}
		else{
			return endDate;
		}
			
	}
	
	public static String lastMondayDate(String weekDate) {
		Date lastMonday=Dates.newDate(weekDate).previousOrSame(DayOfWeek.MONDAY).previous(DayOfWeek.MONDAY);
		return lastMonday.yyyy()+"-"+lastMonday.mm()+"-"+lastMonday.dd();
	}
	
	public static String lastSundayDate(String weekDate) {
		Date lastSunday=Dates.newDate(weekDate).previous(DayOfWeek.SUNDAY);
		return lastSunday.yyyy()+"-"+lastSunday.mm()+"-"+lastSunday.dd();
	}

	
    
    
    public static List<java.util.Date> getDaysInDate(String startDate,String endDate,String mondayFlag,String tuesdayFlag,
    		String wednesdayFlag,String thursdayFlag,String fridayFlag,String saturdayFalg,String sundayFlag) throws ParseException{
    	List<java.util.Date> utilDateList=new ArrayList<java.util.Date>();
    	List<Date> dayList=new ArrayList<Date>();
    	if(mondayFlag.equalsIgnoreCase("y") && tuesdayFlag.equalsIgnoreCase("y") && wednesdayFlag.equalsIgnoreCase("y") && thursdayFlag.equalsIgnoreCase("y")
    			&& fridayFlag.equalsIgnoreCase("y") && saturdayFalg.equalsIgnoreCase("y") && sundayFlag.equalsIgnoreCase("y")){
    		List<Date> allDaysList=Dates.from(startDate).to(endDate).byDay().build();
    		dayList.addAll(allDaysList);
    	}
    	else{  
	    		if(mondayFlag.equalsIgnoreCase("y")){
	    			List<Date> mondayList=Dates.from(startDate).to(endDate).byWeek().on(DayOfWeek.MONDAY).build();
	    			dayList.addAll(mondayList);
	    		}
	    		
	    		if(tuesdayFlag.equalsIgnoreCase("y")){
	    			List<Date> tuesdayList=Dates.from(startDate).to(endDate).byWeek().on(DayOfWeek.TUESDAY).build();
	    			dayList.addAll(tuesdayList);
	    		}
	    		
	    		if(wednesdayFlag.equalsIgnoreCase("y")){
	    			List<Date> wednesdayList=Dates.from(startDate).to(endDate).byWeek().on(DayOfWeek.WEDNESDAY).build();
	    			dayList.addAll(wednesdayList);
	    		}
	    		
	    		if(thursdayFlag.equalsIgnoreCase("y")){
	    			List<Date> thursdayList=Dates.from(startDate).to(endDate).byWeek().on(DayOfWeek.THURSDAY).build();
	    			dayList.addAll(thursdayList);
	    		}
	    		
	    		if(fridayFlag.equalsIgnoreCase("y")){
	    			List<Date> fridayList=Dates.from(startDate).to(endDate).byWeek().on(DayOfWeek.FRIDAY).build();
	    			dayList.addAll(fridayList);
	    		}
	    		
	    		if(saturdayFalg.equalsIgnoreCase("y")){
	    			List<Date> saturdayList=Dates.from(startDate).to(endDate).byWeek().on(DayOfWeek.SATURDAY).build();
	    			dayList.addAll(saturdayList);
	    		}
	    		
	    		if(sundayFlag.equalsIgnoreCase("y")){
	    			List<Date> sundayList=Dates.from(startDate).to(endDate).byWeek().on(DayOfWeek.SUNDAY).build();
	    			dayList.addAll(sundayList);
	    		}
	    		
    	}
    	Collections.sort(dayList);
    	for (Iterator iterator = dayList.iterator(); iterator.hasNext();) {
			Date date = (Date) iterator.next();
			 String sDate1=date.dd()+"/"+date.mm()+"/"+date.yyyy();
			 java.util.Date utilDate=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
			 utilDateList.add(utilDate);
			// System.out.println(utilDate.toString());
    	}
    	 
    	return utilDateList;
    }

	

}