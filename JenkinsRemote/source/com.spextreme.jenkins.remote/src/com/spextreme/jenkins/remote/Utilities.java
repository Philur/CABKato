/*
 * JenkinsRemote - A tool that sits in the tray and provide Jenkins information/control.
 *
 * Copyright 2009 SP extreme (http://www.spextreme.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.spextreme.jenkins.remote;


/**
 * A collection of useful utilities methods. This is a collection of all static methods.
 */
public final class Utilities
{
	/**
	 * Represents the number of milliseconds in a second.
	 */
	public static final int	MILLISECONDS	= 1000;
	/**
	 * Represents the number of minutes in a hour.
	 */
	public static final int	MINUTES			= 60;
	/**
	 * Represents the number of seconds in a minute.
	 */
	public static final int	SECONDS			= 60;

	/**
	 * Takes a duration and formats it as h:m:s:ms. If hours minutes or seconds are 0 then they will
	 * not be displayed if the previous item is not set.
	 * 
	 * @param duration The duration to set
	 * @return The formatted output.
	 */
	public static String formatBuildDuration( final long duration )
	{
		final StringBuilder formattedDuration = new StringBuilder( );

		final int hours = (int)Math.floor( duration / ( MILLISECONDS * MINUTES * SECONDS ) );
		final int minutes = (int)Math.floor( duration % ( MILLISECONDS * MINUTES * SECONDS ) / ( MILLISECONDS * MINUTES ) );
		final int seconds = (int)Math.floor( duration % ( MILLISECONDS * MINUTES * SECONDS ) % ( MILLISECONDS * MINUTES )
				/ MILLISECONDS );

		// Format the hours if needed
		boolean hoursAdded = false;
		if( hours != 0 )
		{
			formattedDuration.append( hours );
			formattedDuration.append( hours == 1 ? " hr " : " hrs " );
			hoursAdded = true;
		}

		// Format minutes if needed
		boolean minutesAdded = false;
		if( ( minutes != 0 ) || hoursAdded )
		{
			formattedDuration.append( minutes );
			formattedDuration.append( minutes == 1 ? " min " : " mins " );
			minutesAdded = true;
		}

		// Format seconds if needed
		if( ( seconds != 0 ) || minutesAdded )
		{
			formattedDuration.append( seconds );
			formattedDuration.append( seconds == 1 ? " sec " : " secs " );
		}

		return formattedDuration.toString( );
	}

	/**
	 * Gets the int out of the value.
	 * 
	 * @param value The value to get the int of.
	 * @param defaultValue The default value to use if the value can't be parsed.
	 * @return The int value.
	 */
	public static int parseInt( final String value, final int defaultValue )
	{
		int result = defaultValue;

		try
		{
			result = Integer.parseInt( value );
		}
		catch( final NumberFormatException e )
		{
			// Do nothing.
		}

		return result;
	}

	/**
	 * Gets the long out of the value.
	 * 
	 * @param value The value to get the long of.
	 * @param defaultValue The default value to use if the value can't be parsed.
	 * @return The long value.
	 */
	public static long parseLong( final String value, final long defaultValue )
	{
		long result = defaultValue;

		try
		{
			result = Long.parseLong( value );
		}
		catch( final NumberFormatException e )
		{
			// Do nothing.
		}

		return result;
	}

	/**
	 * Makes sure that the passed in string ends with a slash.
	 * 
	 * @param value The string to verify.
	 * @return The string that was passed in and ends with a slash.
	 */
	public static String verifyEndingSlash( final String value )
	{
		String result = value;
		if( !value.endsWith( "/" ) )
		{
			result = value + "/";
		}

		return result;
	}

	/**
	 * Private since it can not be constructed.
	 */
	private Utilities( )
	{
	}

}
