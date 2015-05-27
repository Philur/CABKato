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

package com.spextreme.jenkins.remote.model.internal;

import com.spextreme.jenkins.remote.model.IServerInformation;

/**
 * This is the backing for a Server instance. A server is represented as the item Jenkins Remote
 * contacts to query for information. Since it is possible to support multiple servers for
 * monitoring this represents a single server instance for use around the different parts.
 */
public class ServerInformation implements IServerInformation
{
	/**
	 * The flag to know if this is the primary instance.
	 */
	private boolean	mPrimary		= false;
	/**
	 * The address of the server.
	 */
	private String	mAddress		= "";
	/**
	 * The query periodic to poll the server for information.
	 */
	private int		mQueryPeriodic	= 5000;
	/**
	 * A flag used to know if increase of polling is allowed.
	 */
	private boolean	mAllowIncrease	= false;
	/**
	 * The user name for the server.
	 */
	private String	mUserName		= "";
	/**
	 * The password for the server.
	 */
	private String	mPassword		= "";

	/**
	 * Constructs an instance of this object.
	 * 
	 * @param address The address (can not be <code>null</code>).
	 */
	public ServerInformation( final String address )
	{
		this( address, "", "", 5000, true, false );
	}

	/**
	 * Constructs an instance of this object.
	 * 
	 * @param address The address (can not be <code>null</code>).
	 * @param username The user name (empty string if not needed).
	 * @param password The password (empty string if not needed).
	 * @param queryPeriodic The query periodic in seconds (value is multiply by 1000).
	 * @param allowIncrease <code>true</code> if query periodic is allowed to be increased.
	 * @param primary <code>true</code> if this is the primary server. <code>false</code> in all
	 *            other cases.
	 */
	public ServerInformation( final String address, final String username, final String password, final int queryPeriodic,
			final boolean allowIncrease, final boolean primary )
	{
		setAddress( address );
		setUserName( username );
		setPassword( password );
		setQueryPeriodic( queryPeriodic );
		setAllowIncrease( allowIncrease );
		setPrimary( primary );
	}

	/**
	 * @see java.lang.Object#clone()
	 * @return The new server instance as an exact match.
	 * @throws CloneNotSupportedException Thrown if not supported. Never will happen.
	 */
	@Override
	public Object clone( ) throws CloneNotSupportedException
	{
		final IServerInformation server = new ServerInformation( getAddress( ), getUserName( ), getPassword( ),
				getQueryPeriodic( ), isAllowIncrease( ), isPrimary( ) );

		return server;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @param obj The object to compare to.
	 * @return <code>true</code> if the object is equal. <code>false</code> in all other cases.
	 */
	@Override
	public boolean equals( final Object obj )
	{
		boolean result = false;

		if( ( obj != null ) && ( this == obj ) )
		{
			result = true;
		}
		else if( ( obj != null ) && ( obj instanceof ServerInformation ) )
		{
			final IServerInformation other = (IServerInformation)obj;

			if( getAddress( ).equals( other.getAddress( ) ) )
			{
				result = true;
			}
		}

		return result;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IServerInformation#getAddress()
	 * @return The address.
	 */
	public String getAddress( )
	{
		return mAddress;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IServerInformation#getPassword()
	 * @return The password or an empty string.
	 */
	public String getPassword( )
	{
		return mPassword;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IServerInformation#getQueryPeriodic()
	 * @return The periodic rate for polling.
	 */
	public int getQueryPeriodic( )
	{
		return mQueryPeriodic;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IServerInformation#getUserName()
	 * @return The user name or an empty string.
	 */
	public String getUserName( )
	{
		return mUserName;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 * @return The hash code value.
	 */
	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int result = 1;

		result = prime * result + getAddress( ).hashCode( );

		return result;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IServerInformation#isAllowIncrease()
	 * @return <code>true</code> if the polling frequency can be increased when a build is detected.
	 */
	public boolean isAllowIncrease( )
	{
		return mAllowIncrease;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IServerInformation#isPrimary()
	 * @return <code>true</code> if this is the primary server. <code>false</code> otherwise
	 */
	@Override
	public boolean isPrimary( )
	{
		return mPrimary;
	}

	/**
	 * Sets the address for this object.
	 * 
	 * @param address The address of the jenkins server.
	 */
	public void setAddress( final String address )
	{
		if( address == null )
		{
			throw new NullPointerException( "The Jenkins server addresss can not be null." );
		}

		mAddress = address;
	}

	/**
	 * Sets the allow increase for this object.
	 * 
	 * @param allowIncrease The allow increase to set.
	 */
	public void setAllowIncrease( final boolean allowIncrease )
	{
		mAllowIncrease = allowIncrease;
	}

	/**
	 * Sets the password for this object.
	 * 
	 * @param password The password to use.
	 */
	public void setPassword( String password )
	{
		if( password == null )
		{
			password = "";
		}

		mPassword = password;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IServerInformation#setPrimary(boolean)
	 * @param value <code>true</code> if this is the primary server. <code>false</code> in all other
	 *            cases.
	 */
	public void setPrimary( final boolean value )
	{
		mPrimary = value;
	}

	/**
	 * Sets the query periodic for this object. The minimum value is 250.
	 * 
	 * @param queryPeriodic The query periodic to use.
	 */
	public void setQueryPeriodic( int queryPeriodic )
	{
		if( queryPeriodic < 250 )
		{
			queryPeriodic = 10000;
		}

		mQueryPeriodic = queryPeriodic;
	}

	/**
	 * Sets the username for this object.
	 * 
	 * @param userName The user name to use.
	 */
	public void setUserName( String userName )
	{
		if( userName == null )
		{
			userName = "";
		}

		mUserName = userName;
	}
}
