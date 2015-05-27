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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.spextreme.jenkins.remote.jenkins.internal.BuildXMLProcessing;
import com.spextreme.jenkins.remote.model.IBuild;

/**
 * Tests the health report XML processing.
 */
public class BuildXMLProcessingTest extends AbstractXMLProcessingHelper
{
	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.jenkins.internal.BuildXMLProcessing#fromElement(org.jdom.Element)}
	 * .
	 */
	@Test
	public void testProcessElementInvalid( ) throws Exception
	{
		assertNull( new BuildXMLProcessing( "build" ).fromElement( getXMLElement( "<testInvalid />" ) ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.jenkins.internal.BuildXMLProcessing#fromElement(org.jdom.Element)}
	 * .
	 */
	@Test
	public void testProcessElementValid( ) throws Exception
	{
		final BuildXMLProcessing processor = new BuildXMLProcessing( "lastBuild" );

		final IBuild build = (IBuild)processor.fromElement( getXMLElement( getXMLString( ) ) );

		assertNotNull( build );
		assertNotNull( build.getArtifacts( ) );
		assertEquals( 3, build.getArtifacts( ).size( ) );
		assertEquals( "jenkinsremote_win32.1.0.0.35.zip", build.getArtifacts( ).get( 0 ).getDisplayName( ) );
		assertEquals( "jenkinsremote/com.spextreme.jenkins.remote.builder/build/jenkinsremote_win32.1.0.0.35.zip", build
				.getArtifacts( ).get( 0 ).getRelativePath( ) );
		assertEquals( "JenkinsRemote #35", build.getDisplayName( ) );
		assertEquals( 91328L, build.getDuration( ) );
		assertEquals( "2009-10-11_15-28-39", build.getID( ) );
		assertEquals( 35, build.getNumber( ) );
		assertEquals( "SUCCESS", build.getResult( ) );
		assertNotNull( build.getTestResults( ) );
		assertEquals( 2, build.getTestResults( ).getFailedCount( ) );
		assertEquals( 1, build.getTestResults( ).getSkipCount( ) );
		assertEquals( 12, build.getTestResults( ).getTotalCount( ) );
		assertEquals( 1255289319970L, build.getTimestamp( ) );
		assertEquals( "http://192.1.1.100/jenkins/job/JenkinsRemote/35/", build.getURL( ) );
		assertFalse( build.isBuilding( ) );
	}

	/**
	 * Tests the to element method returns <code>null</code>.
	 */
	@Test
	public void testToElement( )
	{
		assertNull( new BuildXMLProcessing( "test" ).toElement( new Object( ) ) );
	}

	/**
	 * Gets a string for a valid health report.
	 * 
	 * @return The health report XML string.
	 */
	protected String getXMLString( )
	{
		final StringBuilder xmlString = new StringBuilder( );

		xmlString.append( "<lastBuild>" );
		xmlString.append( "<action>" );
		xmlString.append( "<cause>" );
		xmlString.append( "<shortDescription>Started by user anonymous</shortDescription>" );
		xmlString.append( "</cause>" );
		xmlString.append( "</action>" );
		xmlString.append( "<action>" );
		xmlString.append( "<failCount>0</failCount>" );
		xmlString.append( "<skipCount>0</skipCount>" );
		xmlString.append( "<totalCount>0</totalCount>" );
		xmlString.append( "<urlName>aggregatedTestReport</urlName>" );
		xmlString.append( "</action>" );
		xmlString.append( "<action>" );
		xmlString.append( "<failCount>2</failCount>" );
		xmlString.append( "<skipCount>1</skipCount>" );
		xmlString.append( "<totalCount>12</totalCount>" );
		xmlString.append( "<urlName>testReport</urlName>" );
		xmlString.append( "</action>" );
		xmlString.append( "<artifact>" );
		xmlString.append( "<displayPath>jenkinsremote_win32.1.0.0.35.zip</displayPath>" );
		xmlString.append( "<fileName>jenkinsremote_win32.1.0.0.35.zip</fileName>" );
		xmlString
				.append( "<relativePath>jenkinsremote/com.spextreme.jenkins.remote.builder/build/jenkinsremote_win32.1.0.0.35.zip</relativePath>" );
		xmlString.append( "</artifact>" );
		xmlString.append( "<artifact>" );
		xmlString.append( "<displayPath>jenkinsremote_linux.1.0.0.35.zip</displayPath>" );
		xmlString.append( "<fileName>jenkinsremote_linux.1.0.0.35.zip</fileName>" );
		xmlString
				.append( "<relativePath>jenkinsremote/com.spextreme.jenkins.remote.builder/build/jenkinsremote_linux.1.0.0.35.zip" );
		xmlString.append( "</relativePath>" );
		xmlString.append( "</artifact>" );
		xmlString.append( "<artifact>" );
		xmlString.append( "<displayPath>jenkinsremote_macosx.1.0.0.35.zip</displayPath>" );
		xmlString.append( "<fileName>jenkinsremote_macosx.1.0.0.35.zip</fileName>" );
		xmlString
				.append( "<relativePath>jenkinsremote/com.spextreme.jenkins.remote.builder/build/jenkinsremote_macosx.1.0.0.35.zip" );
		xmlString.append( "</relativePath>" );
		xmlString.append( "</artifact>" );
		xmlString.append( "<building>false</building>" );
		xmlString.append( "<duration>91328</duration>" );
		xmlString.append( "<fullDisplayName>JenkinsRemote #35</fullDisplayName>" );
		xmlString.append( "<id>2009-10-11_15-28-39</id>" );
		xmlString.append( "<keepLog>false</keepLog>" );
		xmlString.append( "<number>35</number>" );
		xmlString.append( "<result>SUCCESS</result>" );
		xmlString.append( "<timestamp>1255289319970</timestamp>" );
		xmlString.append( "<url>http://192.1.1.100/jenkins/job/JenkinsRemote/35/</url>" );
		xmlString.append( "</lastBuild>" );

		return xmlString.toString( );
	}
}
