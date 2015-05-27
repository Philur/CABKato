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

import com.spextreme.jenkins.remote.model.IShowInformation;
import com.spextreme.jenkins.remote.model.internal.ShowInformation;
import com.spextreme.jenkins.remote.xml.IXMLProcessing;

/**
 * This parses items for the show block of the XML. This may be used also as a very generic parser
 * that uses the name of it's element as the passed in value and pulls the text value as a boolean.
 */
public class ShowInformationConverter implements IXMLProcessing
{
	/**
	 * The admin XML label.
	 */
	public static final String		ADMIN			= "admin";
	/**
	 * The archives XML label.
	 */
	public static final String		ARCHIVES		= "archives";
	/**
	 * The duration status XML label.
	 */
	public static final String		DURATION		= "duration";
	/**
	 * The health report XML label.
	 */
	public static final String		HEALTH_REPORTS	= "healthReports";
	/**
	 * The pop-ups XML label.
	 */
	public static final String		POPUPS			= "popups";
	/**
	 * The test status XML label.
	 */
	public static final String		TESTS			= "tests";
	/**
	 * The list of items used to know what should be shown and hidden.
	 */
	public static final String[]	SHOW_TYPES		= new String[] { ADMIN, ARCHIVES, DURATION, HEALTH_REPORTS, TESTS };

	/**
	 * The element name to parse out.
	 */
	private String					mElementName	= "";

	/**
	 * Constructs the converter to parse for the given element.
	 * 
	 * @param name The name of the element.
	 */
	public ShowInformationConverter( final String name )
	{
		mElementName = name;
	}

	/**
	 * @see com.spextreme.jenkins.remote.xml.IXMLProcessing#fromElement(org.jdom.Element)
	 * @param element The Element to processes.
	 * @return The {@link IShowInformation} instance or <code>null</code> if the text couldn't be
	 *         parsed out and the name of the element doesn't match the name given to this
	 *         converter.
	 */
	@Override
	public Object fromElement( final Element element )
	{
		final String name = element.getName( );
		final String text = element.getText( );
		IShowInformation info = null;

		if( name.equals( getElementName( ) ) && ( text != null ) && !text.isEmpty( ) )
		{
			info = new ShowInformation( );
			info.setName( name );
			info.setVisible( Boolean.parseBoolean( text ) );
		}

		return info;
	}

	/**
	 * Gets the name of the element this converts.
	 * 
	 * @return Returns the element name.
	 */
	public String getElementName( )
	{
		return mElementName;
	}

	/**
	 * @see com.spextreme.jenkins.remote.xml.IXMLProcessing#toElement(java.lang.Object)
	 * @param object The {@link IShowInformation} item to convert.
	 * @return The XML {@link Element} or null if not an {@link IShowInformation}.
	 */
	@Override
	public Element toElement( final Object object )
	{
		Element element = null;

		if( object instanceof IShowInformation )
		{
			final IShowInformation info = (IShowInformation)object;

			element = new Element( info.getName( ) );
			element.setText( Boolean.toString( info.isVisible( ) ) );
		}

		return element;
	}
}
