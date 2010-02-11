/**
 * 
 */
package org.testtoolinterfaces.utils;

import java.util.Calendar;

/**
 * @author Arjan Kranenburg
 *
 */
public class DateFormat
{
	/**
	 * @param aCalendar
	 * @return the date_time in YYYYMMDD_HHMMSS format, zero padded where needed
	 */
	public static String getDateTimeStamp(Calendar aCalendar)
	{
		return getDateTimeStamp( aCalendar, '_' );
	}

	/**
	 * @param aCalendar
	 * @param aSeparationCharacter character that will be used to separate the date from
	 *  the time.
	 * @return the date_time in YYYYMMDD%aSeparationCharacter%HHMMSS format, zero padded
	 *  where needed.
	 */
	public static String getDateTimeStamp( Calendar aCalendar, char aSeparationCharacter )
	{
		return getDateStamp( aCalendar ) + aSeparationCharacter + getTimeStamp( aCalendar );
	}

	/**
	 * @param aCalendar
	 * @return the date in YYYYMMDD format
	 */
	public static String getDateStamp( Calendar aCalendar )
	{
		String dateStamp = "" + aCalendar.get(Calendar.YEAR)
							  + getMonths(aCalendar)
							  + getDays(aCalendar);
		
		return dateStamp;
	}

	/**
	 * @param aCalendar
	 * @return the date in HHMMSS format, zero padded where needed
	 */
	public static String getTimeStamp( Calendar aCalendar )
	{
		String timeStamp = getHours(aCalendar) + getMinutes( aCalendar ) + getSeconds( aCalendar );
		
		return timeStamp;
	}
	
	/**
	 * @param aCalendar
	 * @return the month in MM format, zero padded if needed
	 */
	public static String getMonths( Calendar aCalendar )
	{
		String dateStamp = "";
		int months = aCalendar.get(Calendar.MONTH) + 1;
		if ( months < 10 )
		{
			dateStamp += "0" + months;
		}
		else
		{
			dateStamp += months;
		}
		
		return dateStamp;
	}

	/**
	 * @param aCalendar
	 * @return the days in DD format, zero padded if needed
	 */
	public static String getDays( Calendar aCalendar )
	{
		String dateStamp = "";
		int days = aCalendar.get(Calendar.DAY_OF_MONTH);
		if ( days < 10 )
		{
			dateStamp += "0" + days;
		}
		else
		{
			dateStamp += days;
		}
		
		return dateStamp;
	}

	/**
	 * @param aCalendar
	 * @return the hour in HH format, zero padded if needed
	 */
	public static String getHours( Calendar aCalendar )
	{
		String timeStamp = "";
		int hours = aCalendar.get(Calendar.HOUR_OF_DAY);
		if ( hours < 10 )
		{
			timeStamp += "0" + hours;
		}
		else
		{
			timeStamp += hours;
		}
		
		return timeStamp;
	}

	/**
	 * @param aCalendar
	 * @return the minutes in MM format, zero padded if needed
	 */
	public static String getMinutes( Calendar aCalendar )
	{
		String timeStamp = "";
		int minutes = aCalendar.get(Calendar.MINUTE);
		if ( minutes < 10 )
		{
			timeStamp += "0" + minutes;
		}
		else
		{
			timeStamp += minutes;
		}
		
		return timeStamp;
	}

	/**
	 * @param aCalendar
	 * @return the seconds in SS format, zero padded if needed
	 */
	public static String getSeconds( Calendar aCalendar )
	{
		String timeStamp = "";
		int seconds = aCalendar.get(Calendar.SECOND);
		if ( seconds < 10 )
		{
			timeStamp += "0" + seconds;
		}
		else
		{
			timeStamp += seconds;
		}
		
		return timeStamp;
	}
}
