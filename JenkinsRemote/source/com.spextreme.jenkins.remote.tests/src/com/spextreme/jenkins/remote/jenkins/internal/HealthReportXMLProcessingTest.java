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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.spextreme.jenkins.remote.jenkins.internal.HealthReportXMLProcessing;
import com.spextreme.jenkins.remote.model.IHealthReport;

/**
 * Tests the health report XML processing.
 */
public class HealthReportXMLProcessingTest extends AbstractXMLProcessingHelper
{
	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.jenkins.internal.HealthReportXMLProcessing#fromElement(org.jdom.Element)}
	 * .
	 */
	@Test
	public void testProcessElementInvalid( ) throws Exception
	{
		assertNull( new HealthReportXMLProcessing( ).fromElement( getXMLElement( "<testInvalid />" ) ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.jenkins.internal.HealthReportXMLProcessing#fromElement(org.jdom.Element)}
	 * .
	 */
	@Test
	public void testProcessElementValid( ) throws Exception
	{
		final HealthReportXMLProcessing processor = new HealthReportXMLProcessing( );

		final IHealthReport report = (IHealthReport)processor.fromElement( getXMLElement( getXMLString( ) ) );

		assertNotNull( report );
		assertEquals( "Build stability: No recent builds failed.", report.getDescription( ) );
		assertEquals( "health-80plus.gif", report.getIconName( ) );
		assertEquals( 100, report.getScore( ) );
	}

	/**
	 * Tests the to element method returns <code>null</code>.
	 */
	@Test
	public void testToElement( )
	{
		assertNull( new HealthReportXMLProcessing( ).toElement( new Object( ) ) );
	}

	/**
	 * Gets a string for a valid health report.
	 * 
	 * @return The health report XML string.
	 */
	protected String getXMLString( )
	{
		final StringBuilder xmlString = new StringBuilder( );

		xmlString.append( "<healthReport>" );
		xmlString.append( "<description>Build stability: No recent builds failed.</description>" );
		xmlString.append( "<iconUrl>health-80plus.gif</iconUrl>" );
		xmlString.append( "<score>100</score>" );
		xmlString.append( "</healthReport>" );

		return xmlString.toString( );
	}
}
