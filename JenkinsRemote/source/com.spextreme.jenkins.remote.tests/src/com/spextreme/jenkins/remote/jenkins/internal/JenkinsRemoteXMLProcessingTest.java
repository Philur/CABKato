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
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.List;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.junit.Test;

import com.spextreme.jenkins.remote.jenkins.JenkinsCLITest;
import com.spextreme.jenkins.remote.jenkins.internal.JenkinsRemoteXMLProcessing;
import com.spextreme.jenkins.remote.model.IJob;
import com.spextreme.jenkins.remote.model.IServerInformation;
import com.spextreme.jenkins.remote.model.internal.ServerInformation;
import com.spextreme.jenkins.remote.xml.IXMLProcessing;

/**
 * Test the remote XML processing from Jenkins.
 */
public class JenkinsRemoteXMLProcessingTest
{
	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.jenkins.internal.JenkinsRemoteXMLProcessing#loadDataFromJenkinsServer(java.lang.String)}
	 * .
	 */
	@Test
	public void testLoadDataFromJenkinsServer( )
	{
		final IServerInformation serverInformation = new ServerInformation(
				System.getProperty( JenkinsCLITest.JENKINS_URL_PROPERTY_KEY ), "", "", 1000, true, true );

		final List<IJob> jobs = JenkinsRemoteXMLProcessing.loadDataFromJenkinsServer( serverInformation );

		assertNotNull( jobs );
		assertTrue( jobs.size( ) > 0 );
	}

	/**
	 * Tests when the URL is invalid.
	 */
	@Test
	public void testLoadDataFromInvalidJenkinsServer( )
	{
		final IServerInformation serverInformation = new ServerInformation( "http://bad.address.com/jenkins/", "", "", 2000,
				false, true );
		final List<IJob> jobs = JenkinsRemoteXMLProcessing.loadDataFromJenkinsServer( serverInformation );

		assertNotNull( jobs );
		assertEquals( 0, jobs.size( ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.jenkins.internal.JenkinsRemoteXMLProcessing#processJobElement(org.jdom.Element)}
	 * .
	 */
	@Test
	public void testProcessJobElement( ) throws Exception
	{
		final IServerInformation serverInformation = new ServerInformation( "", "", "", 1000, false, true );
		final IXMLProcessing processor = new JenkinsRemoteXMLProcessing( serverInformation );

		final IJob job = (IJob)processor.fromElement( getXMLElement( false ) );

		assertNotNull( job );
		assertEquals( "blue", job.getColor( ) );
		assertEquals( "JenkinsRemote", job.getName( ) );
		assertEquals( "http://192.168.1.2:8080/jenkins/job/JenkinsRemote/", job.getURL( ) );

		assertEquals( 57, job.getLastCompletedBuild( ).getNumber( ) );
		assertNotNull( job.getLastCompletedBuild( ) );
		assertEquals( "JenkinsRemote #57", job.getLastCompletedBuild( ).getDisplayName( ) );
		assertNull( job.getCurrentBuild( ) );

		assertNotNull( job.getHealthReports( ) );
		assertEquals( 4, job.getHealthReports( ).size( ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.jenkins.internal.JenkinsRemoteXMLProcessing#processJobElement(org.jdom.Element)}
	 * .
	 */
	@Test
	public void testProcessJobWhenBuildingElement( ) throws Exception
	{
		final IServerInformation serverInformation = new ServerInformation( "", "", "", 1000, false, true );
		final IXMLProcessing processor = new JenkinsRemoteXMLProcessing( serverInformation );

		final IJob job = (IJob)processor.fromElement( getXMLElement( true ) );

		assertNotNull( job );
		assertEquals( "blue", job.getColor( ) );
		assertEquals( "JenkinsRemote", job.getName( ) );
		assertEquals( "http://192.168.1.2:8080/jenkins/job/JenkinsRemote/", job.getURL( ) );

		assertEquals( 57, job.getLastCompletedBuild( ).getNumber( ) );
		assertNotNull( job.getLastCompletedBuild( ) );
		assertEquals( "JenkinsRemote #57", job.getLastCompletedBuild( ).getDisplayName( ) );
		assertNotNull( job.getCurrentBuild( ) );

		assertNotNull( job.getHealthReports( ) );
		assertEquals( 4, job.getHealthReports( ).size( ) );
	}

	/**
	 * Tests the to element method returns <code>null</code>.
	 */
	@Test
	public void testToElement( )
	{
		final IServerInformation serverInformation = new ServerInformation(
				System.getProperty( JenkinsCLITest.JENKINS_URL_PROPERTY_KEY ), "", "", 1000, true, true );

		assertNull( new JenkinsRemoteXMLProcessing( serverInformation ).toElement( new Object( ) ) );
	}

	/**
	 * Gets an XML String as sent back by a jenkins server when there are no builds going on.
	 * 
	 * @param isBuilding The building flag.
	 * @return The XML string.
	 */
	private String getBuildCompleted( final boolean isBuilding )
	{
		final StringBuilder xmlString = new StringBuilder( );

		xmlString.append( "<job>" );
		xmlString
				.append( "<description>A windows tray application that monitors Jenkins and provides some control over it.</description>" );
		xmlString.append( "<displayName>JenkinsRemote</displayName>" );
		xmlString.append( "<name>JenkinsRemote</name>" );
		xmlString.append( "<url>http://192.168.1.2:8080/jenkins/job/JenkinsRemote/</url>" );
		xmlString.append( "<buildable>true</buildable>" );
		xmlString.append( "<color>blue</color>" );
		xmlString.append( "<healthReport>" );
		xmlString
				.append( "<description>Coverage: Classes 7/8 (88%). Blocks 723/867 (83%). Lines 214.6/246 (87%). </description>" );
		xmlString.append( "<iconUrl>health-20to39.gif</iconUrl>" );
		xmlString.append( "<score>30</score>" );
		xmlString.append( "</healthReport>" );
		xmlString.append( "<healthReport>" );
		xmlString.append( "<description>Test Result: 0 tests in total.</description>" );
		xmlString.append( "<iconUrl>health-80plus.gif</iconUrl>" );
		xmlString.append( "<score>100</score>" );
		xmlString.append( "</healthReport>" );
		xmlString.append( "<healthReport>" );
		xmlString.append( "<description>Test Result: 0 tests failing out of a total of 24 tests.</description>" );
		xmlString.append( "<iconUrl>health-80plus.gif</iconUrl>" );
		xmlString.append( "<score>100</score>" );
		xmlString.append( "</healthReport>" );
		xmlString.append( "<healthReport>" );
		xmlString.append( "<description>Build stability: No recent builds failed.</description>" );
		xmlString.append( "<iconUrl>health-80plus.gif</iconUrl>" );
		xmlString.append( "<score>100</score>" );
		xmlString.append( "</healthReport>" );
		xmlString.append( "<inQueue>false</inQueue>" );
		xmlString.append( "<keepDependencies>false</keepDependencies>" );
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
		xmlString.append( "<failCount>0</failCount>" );
		xmlString.append( "<skipCount>0</skipCount>" );
		xmlString.append( "<totalCount>24</totalCount>" );
		xmlString.append( "<urlName>testReport</urlName>" );
		xmlString.append( "</action>" );
		xmlString.append( "<artifact>" );
		xmlString.append( "<displayPath>jenkinsremote_linux.1.0.0.57.zip</displayPath>" );
		xmlString.append( "<fileName>jenkinsremote_linux.1.0.0.57.zip</fileName>" );
		xmlString
				.append( "<relativePath>jenkinsremote/com.spextreme.jenkins.remote.builder/build/jenkinsremote_linux.1.0.0.57.zip" );
		xmlString.append( "</relativePath>" );
		xmlString.append( "</artifact>" );
		xmlString.append( "<artifact>" );
		xmlString.append( "<displayPath>jenkinsremote_macosx.1.0.0.57.zip</displayPath>" );
		xmlString.append( "<fileName>jenkinsremote_macosx.1.0.0.57.zip</fileName>" );
		xmlString
				.append( "<relativePath>jenkinsremote/com.spextreme.jenkins.remote.builder/build/jenkinsremote_macosx.1.0.0.57.zip</relativePath>" );
		xmlString.append( "</artifact>" );
		xmlString.append( "<artifact>" );
		xmlString.append( "<displayPath>jenkinsremote_win32.1.0.0.57.zip</displayPath>" );
		xmlString.append( "<fileName>jenkinsremote_win32.1.0.0.57.zip</fileName>" );
		xmlString
				.append( "<relativePath>jenkinsremote/com.spextreme.jenkins.remote.builder/build/jenkinsremote_win32.1.0.0.57.zip</relativePath>" );
		xmlString.append( "</artifact>" );
		xmlString.append( "<building>" + isBuilding + "</building>" );
		xmlString.append( "<duration>95236</duration>" );
		xmlString.append( "<fullDisplayName>JenkinsRemote #57</fullDisplayName>" );
		xmlString.append( "<id>2009-10-15_16-59-03</id>" );
		xmlString.append( "<keepLog>false</keepLog>" );
		xmlString.append( "<number>57</number>" );
		xmlString.append( "<result>SUCCESS</result>" );
		xmlString.append( "<timestamp>1255640343824</timestamp>" );
		xmlString.append( "<url>http://192.168.1.2:8080/jenkins/job/JenkinsRemote/57/</url>" );
		xmlString.append( "<builtOn></builtOn>" );
		xmlString.append( "</lastBuild>" );
		xmlString.append( "<lastCompletedBuild>" );
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
		xmlString.append( "<failCount>0</failCount>" );
		xmlString.append( "<skipCount>0</skipCount>" );
		xmlString.append( "<totalCount>24</totalCount>" );
		xmlString.append( "<urlName>testReport</urlName>" );
		xmlString.append( "</action>" );
		xmlString.append( "<artifact>" );
		xmlString.append( "<displayPath>jenkinsremote_linux.1.0.0.57.zip</displayPath>" );
		xmlString.append( "<fileName>jenkinsremote_linux.1.0.0.57.zip</fileName>" );
		xmlString
				.append( "<relativePath>jenkinsremote/com.spextreme.jenkins.remote.builder/build/jenkinsremote_linux.1.0.0.57.zip</relativePath>" );
		xmlString.append( "</artifact>" );
		xmlString.append( "<artifact>" );
		xmlString.append( "<displayPath>jenkinsremote_macosx.1.0.0.57.zip</displayPath>" );
		xmlString.append( "<fileName>jenkinsremote_macosx.1.0.0.57.zip</fileName>" );
		xmlString
				.append( "<relativePath>jenkinsremote/com.spextreme.jenkins.remote.builder/build/jenkinsremote_macosx.1.0.0.57.zip</relativePath>" );
		xmlString.append( "</artifact>" );
		xmlString.append( "<artifact>" );
		xmlString.append( "<displayPath>jenkinsremote_win32.1.0.0.57.zip</displayPath>" );
		xmlString.append( "<fileName>jenkinsremote_win32.1.0.0.57.zip</fileName>" );
		xmlString
				.append( "<relativePath>jenkinsremote/com.spextreme.jenkins.remote.builder/build/jenkinsremote_win32.1.0.0.57.zip</relativePath>" );
		xmlString.append( "</artifact>" );
		xmlString.append( "<building>false</building>" );
		xmlString.append( "<duration>95236</duration>" );
		xmlString.append( "<fullDisplayName>JenkinsRemote #57</fullDisplayName>" );
		xmlString.append( "<id>2009-10-15_16-59-03</id>" );
		xmlString.append( "<keepLog>false</keepLog>" );
		xmlString.append( "<number>57</number>" );
		xmlString.append( "<result>SUCCESS</result>" );
		xmlString.append( "<timestamp>1255640343824</timestamp>" );
		xmlString.append( "<url>http://192.168.1.2:8080/jenkins/job/JenkinsRemote/57/</url>" );
		xmlString.append( "<builtOn></builtOn>" );
		xmlString.append( "</lastCompletedBuild>" );
		xmlString.append( "<nextBuildNumber>58</nextBuildNumber>" );
		xmlString.append( "<property></property>" );
		xmlString.append( "</job>" );

		return xmlString.toString( );
	}

	/**
	 * Gets the element Job.
	 * 
	 * @param isBuilding The building flag.
	 * @return The Job {@link Element}.
	 * @throws Exception Thrown if an error occurs.
	 */
	private Element getXMLElement( final boolean isBuilding ) throws Exception
	{
		return new SAXBuilder( ).build( new StringReader( getBuildCompleted( isBuilding ) ) ).getRootElement( );
	}
}
