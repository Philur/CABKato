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

import com.spextreme.jenkins.remote.model.IJobInformation;
import com.spextreme.jenkins.remote.model.internal.JobInformation;
import com.spextreme.jenkins.remote.xml.IXMLProcessing;

/**
 * This parses the Job item in the configuration file.
 */
public class JobInformationConverter implements IXMLProcessing
{
	/**
	 * The job element label.
	 */
	public static final String	ELEMENT_JOB			= "job";
	/**
	 * The attribute for the name.
	 */
	public static final String	NAME_ATTRIBUTE		= "name";
	/**
	 * The attribute for the visibility state.
	 */
	public static final String	VISIBLE_ATTRIBUTE	= "visible";
	/**
	 * The attribute for the server.
	 */
	public static final String	SERVER_ATTRIBUTE	= "server";

	/**
	 * Constructs the converter.
	 */
	public JobInformationConverter( )
	{
		// Do nothing
	}

	/**
	 * @see com.spextreme.jenkins.remote.xml.IXMLProcessing#fromElement(org.jdom.Element)
	 * @param element The {@link Element} to processes.
	 * @return The {@link IJobInformation} instance or <code>null</code> if the object is not a
	 *         valid type.
	 */
	@Override
	public Object fromElement( final Element element )
	{
		IJobInformation info = null;

		if( element.getName( ).equals( ELEMENT_JOB ) )
		{
			info = new JobInformation( );

			info.setName( element.getAttributeValue( NAME_ATTRIBUTE ) );
			info.setServerReference( element.getAttributeValue( SERVER_ATTRIBUTE ) );
			info.setVisible( Boolean.parseBoolean( element.getAttributeValue( VISIBLE_ATTRIBUTE ) ) );
		}

		return info;
	}

	/**
	 * @see com.spextreme.jenkins.remote.xml.IXMLProcessing#toElement(java.lang.Object)
	 * @param object The {@link IJobInformation} item to convert.
	 * @return The XML {@link Element} or null if not an {@link IJobInformation}.
	 */
	@Override
	public Element toElement( final Object object )
	{
		Element element = null;

		if( object instanceof IJobInformation )
		{
			final IJobInformation info = (IJobInformation)object;

			element = new Element( ELEMENT_JOB );
			element.setAttribute( NAME_ATTRIBUTE, info.getName( ) );
			element.setAttribute( VISIBLE_ATTRIBUTE, Boolean.toString( info.isVisible( ) ) );
			element.setAttribute( SERVER_ATTRIBUTE, info.getServerReference( ) );
		}

		return element;
	}
}
