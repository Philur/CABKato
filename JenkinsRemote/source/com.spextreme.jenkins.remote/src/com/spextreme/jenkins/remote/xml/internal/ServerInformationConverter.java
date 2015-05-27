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

package com.spextreme.jenkins.remote.xml.internal;

import org.jdom.Element;

import com.spextreme.jenkins.remote.Utilities;
import com.spextreme.jenkins.remote.model.IServerInformation;
import com.spextreme.jenkins.remote.model.internal.ServerInformation;
import com.spextreme.jenkins.remote.xml.IXMLProcessing;

/**
 * 
 */
public class ServerInformationConverter implements IXMLProcessing
{
	/**
	 * The attribute for the the address.
	 */
	public static final String	ADDRESS_ATTRIBUTE			= "address";
	/**
	 * The attribute for the username.
	 */
	public static final String	USERNAME_ATTRIBUTE			= "username";
	/**
	 * The attribute for the password.
	 */
	public static final String	PASSWORD_ATTRIBUTE			= "password";
	/**
	 * The attribute for the query rate.
	 */
	public static final String	QUERY_RATE_ATTRIBUTE		= "queryRate";
	/**
	 * The attribute for the allow increase.
	 */
	public static final String	ALLOW_INCREASE_ATTRIBUTE	= "allowIncrease";
	/**
	 * The attribute for the is primary flag.
	 */
	public static final String	IS_PRIMARY_ATTRIBUTE		= "isPrimary";
	/**
	 * The server element.
	 */
	public static final String	ELEMENT_SERVER				= "server";

	/**
	 * @see com.spextreme.jenkins.remote.xml.IXMLProcessing#fromElement(org.jdom.Element)
	 * @param element The element to process.
	 * @return The {@link IServerInformation} instance or <code>null</code> if not a valid element.
	 */
	@Override
	public Object fromElement( final Element element )
	{
		IServerInformation server = null;

		if( ( element != null ) && element.getName( ).equals( ELEMENT_SERVER )
				&& ( element.getAttributeValue( ADDRESS_ATTRIBUTE ) != null )
				&& !element.getAttributeValue( ADDRESS_ATTRIBUTE ).isEmpty( ) )
		{
			server = new ServerInformation( element.getAttributeValue( ADDRESS_ATTRIBUTE ) );

			server.setUserName( element.getAttributeValue( USERNAME_ATTRIBUTE ) );
			server.setPassword( element.getAttributeValue( PASSWORD_ATTRIBUTE ) );
			server.setQueryPeriodic( Utilities.parseInt( element.getAttributeValue( QUERY_RATE_ATTRIBUTE ), 10000 ) );
			server.setAllowIncrease( Boolean.parseBoolean( element.getAttributeValue( ALLOW_INCREASE_ATTRIBUTE ) ) );
			server.setPrimary( Boolean.parseBoolean( element.getAttributeValue( IS_PRIMARY_ATTRIBUTE ) ) );
		}

		return server;
	}

	/**
	 * @see com.spextreme.jenkins.remote.xml.IXMLProcessing#toElement(java.lang.Object)
	 * @param object An {@link IServerInformation} object to convert.
	 * @return The element representing the {@link IServerInformation} or <code>null</code> if not
	 *         an {@link IServerInformation} object.
	 */
	@Override
	public Element toElement( final Object object )
	{
		Element element = null;

		if( object instanceof IServerInformation )
		{
			final IServerInformation server = (IServerInformation)object;

			element = new Element( ELEMENT_SERVER );
			element.setAttribute( ADDRESS_ATTRIBUTE, server.getAddress( ) );
			element.setAttribute( USERNAME_ATTRIBUTE, server.getUserName( ) );
			element.setAttribute( PASSWORD_ATTRIBUTE, server.getPassword( ) );
			element.setAttribute( QUERY_RATE_ATTRIBUTE, Integer.toString( server.getQueryPeriodic( ) ) );
			element.setAttribute( ALLOW_INCREASE_ATTRIBUTE, Boolean.toString( server.isAllowIncrease( ) ) );
			element.setAttribute( IS_PRIMARY_ATTRIBUTE, Boolean.toString( server.isPrimary( ) ) );
		}

		return element;
	}
}
