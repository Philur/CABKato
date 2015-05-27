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

package com.spextreme.jenkins.remote.jenkins.internal;

import org.jdom.Element;

import com.spextreme.jenkins.remote.Utilities;
import com.spextreme.jenkins.remote.model.IHealthReport;
import com.spextreme.jenkins.remote.model.internal.HealthReport;
import com.spextreme.jenkins.remote.xml.IXMLProcessing;

/**
 * This processes the 'healthReport' element of the Jenkins Remote API XML data and parses it into a
 * {@link IHealthReport}.
 */
public class HealthReportXMLProcessing implements IXMLProcessing
{
	/**
	 * Constructs the processor.
	 */
	public HealthReportXMLProcessing( )
	{
		// do nothing.
	}

	/**
	 * Gets the health report item from the element as long as the passed in element is a
	 * 'healthReport'.
	 * 
	 * @param element The element to parse. This should be the healthReport element from the Jenkins
	 *            XML tree.
	 * @return An {@link IHealthReport} item or <code>null</code> if unable to parse.
	 */
	public Object fromElement( final Element element )
	{
		IHealthReport report = null;

		if( ( element != null ) && element.getName( ).equals( "healthReport" ) )
		{
			report = new HealthReport( element.getChildText( "description" ), Utilities.parseInt( element
					.getChildText( "score" ), 0 ) );
			report.setIconName( element.getChildText( "iconUrl" ) );
		}

		return report;
	}

	/**
	 * Not implemented in this implementation.
	 * 
	 * @see com.spextreme.jenkins.remote.xml.IXMLProcessing#toElement(java.lang.Object)
	 * @param object The object.
	 * @return The element.
	 */
	@Override
	public Element toElement( final Object object )
	{
		return null;
	}
}
