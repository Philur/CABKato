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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Vector;

import org.jdom.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spextreme.jenkins.remote.model.IConfigurationManager;
import com.spextreme.jenkins.remote.model.internal.ConfigurationData;
import com.spextreme.jenkins.remote.model.internal.ServerInformation;
import com.spextreme.jenkins.remote.xml.internal.ConfigurationFileConverter;
import com.spextreme.jenkins.remote.xml.internal.JobInformationConverter;
import com.spextreme.jenkins.remote.xml.internal.ServerInformationConverter;
import com.spextreme.jenkins.remote.xml.internal.ShowInformationConverter;

/**
 * Tests the {@link ConfigurationFileConverter} object.
 */
public class ConfigurationFileConverterTest
{
	/**
	 * The object under test.
	 */
	private ConfigurationFileConverter	mConverter	= null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp( ) throws Exception
	{
		final List<ShowInformationConverter> showConverters = new Vector<ShowInformationConverter>( );
		showConverters.add( new ShowInformationConverter( "health" ) );
		showConverters.add( new ShowInformationConverter( "test" ) );
		showConverters.add( new ShowInformationConverter( "download" ) );

		mConverter = new ConfigurationFileConverter( showConverters );
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown( ) throws Exception
	{
		mConverter = null;
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.xml.internal.ConfigurationFileConverter#fromElement(org.jdom.Element)}
	 * .
	 */
	@Test
	public void testFromElement( )
	{
		final Element jenkinsRemote = new Element( ConfigurationFileConverter.ELEMENT_HUDSON_REMOTE );
		final Element servers = new Element( ConfigurationFileConverter.ELEMENT_SERVERS );
		final Element server1 = new Element( ServerInformationConverter.ELEMENT_SERVER );
		server1.setAttribute( ServerInformationConverter.ADDRESS_ATTRIBUTE, "http://localhost/jenkins/" );
		server1.setAttribute( ServerInformationConverter.USERNAME_ATTRIBUTE, "" );
		server1.setAttribute( ServerInformationConverter.PASSWORD_ATTRIBUTE, "" );
		server1.setAttribute( ServerInformationConverter.QUERY_RATE_ATTRIBUTE, "2000" );
		server1.setAttribute( ServerInformationConverter.ALLOW_INCREASE_ATTRIBUTE, "false" );
		server1.setAttribute( ServerInformationConverter.IS_PRIMARY_ATTRIBUTE, "true" );
		servers.addContent( server1 );
		final Element server2 = new Element( ServerInformationConverter.ELEMENT_SERVER );
		server2.setAttribute( ServerInformationConverter.ADDRESS_ATTRIBUTE, "http://123.1.2.3/jenkins/" );
		server2.setAttribute( ServerInformationConverter.USERNAME_ATTRIBUTE, "user" );
		server2.setAttribute( ServerInformationConverter.PASSWORD_ATTRIBUTE, "password" );
		server2.setAttribute( ServerInformationConverter.QUERY_RATE_ATTRIBUTE, "10000" );
		server2.setAttribute( ServerInformationConverter.ALLOW_INCREASE_ATTRIBUTE, "true" );
		server2.setAttribute( ServerInformationConverter.IS_PRIMARY_ATTRIBUTE, "false" );
		servers.addContent( server2 );

		final Element show = new Element( ConfigurationFileConverter.ELEMENT_SHOW );
		final Element show1 = new Element( "health" );
		show1.setText( "true" );
		show.addContent( show1 );
		final Element show2 = new Element( "test" );
		show2.setText( "false" );
		show.addContent( show2 );
		final Element show3 = new Element( "download" );
		show3.setText( "true" );
		show.addContent( show3 );

		final Element jobs = new Element( ConfigurationFileConverter.ELEMENT_JOBS );
		final Element job1 = new Element( JobInformationConverter.ELEMENT_JOB );
		job1.setAttribute( JobInformationConverter.NAME_ATTRIBUTE, "JenkinsRemote" );
		job1.setAttribute( JobInformationConverter.SERVER_ATTRIBUTE, "http://localhost/jenkins/" );
		job1.setAttribute( JobInformationConverter.VISIBLE_ATTRIBUTE, "true" );
		jobs.addContent( job1 );
		final Element job2 = new Element( JobInformationConverter.ELEMENT_JOB );
		job2.setAttribute( JobInformationConverter.NAME_ATTRIBUTE, "JenkinsRemote2" );
		job2.setAttribute( JobInformationConverter.SERVER_ATTRIBUTE, "http://123.1.2.3/jenkins/" );
		job2.setAttribute( JobInformationConverter.VISIBLE_ATTRIBUTE, "false" );
		jobs.addContent( job2 );

		jenkinsRemote.addContent( servers );
		jenkinsRemote.addContent( show );
		jenkinsRemote.addContent( jobs );

		final IConfigurationManager config = (IConfigurationManager)mConverter.fromElement( jenkinsRemote );

		assertEquals( 2, config.getJobs( ).size( ) );
		assertEquals( "JenkinsRemote", config.getJobs( ).get( 0 ).getName( ) );
		assertEquals( "http://localhost/jenkins/", config.getJobs( ).get( 0 ).getServerReference( ) );
		assertTrue( config.getJobs( ).get( 0 ).isVisible( ) );
		assertEquals( "JenkinsRemote2", config.getJobs( ).get( 1 ).getName( ) );
		assertEquals( "http://123.1.2.3/jenkins/", config.getJobs( ).get( 1 ).getServerReference( ) );
		assertFalse( config.getJobs( ).get( 1 ).isVisible( ) );

		assertEquals( 2, config.getServers( ).size( ) );
		assertEquals( "http://localhost/jenkins/", config.getServers( ).get( 0 ).getAddress( ) );
		assertEquals( "", config.getServers( ).get( 0 ).getUserName( ) );
		assertEquals( "", config.getServers( ).get( 0 ).getPassword( ) );
		assertEquals( 2000, config.getServers( ).get( 0 ).getQueryPeriodic( ) );
		assertFalse( config.getServers( ).get( 0 ).isAllowIncrease( ) );
		assertTrue( config.getServers( ).get( 0 ).isPrimary( ) );
		assertEquals( "http://123.1.2.3/jenkins/", config.getServers( ).get( 1 ).getAddress( ) );
		assertEquals( "user", config.getServers( ).get( 1 ).getUserName( ) );
		assertEquals( "password", config.getServers( ).get( 1 ).getPassword( ) );
		assertEquals( 10000, config.getServers( ).get( 1 ).getQueryPeriodic( ) );
		assertTrue( config.getServers( ).get( 1 ).isAllowIncrease( ) );
		assertFalse( config.getServers( ).get( 1 ).isPrimary( ) );

		assertEquals( 3, config.getShowInfo( ).size( ) );
		assertEquals( "health", config.getShowInfo( ).get( 0 ).getName( ) );
		assertTrue( config.getShowInfo( ).get( 0 ).isVisible( ) );
		assertEquals( "test", config.getShowInfo( ).get( 1 ).getName( ) );
		assertFalse( config.getShowInfo( ).get( 1 ).isVisible( ) );
		assertEquals( "download", config.getShowInfo( ).get( 2 ).getName( ) );
		assertTrue( config.getShowInfo( ).get( 2 ).isVisible( ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.xml.internal.ConfigurationFileConverter#toElement(java.lang.Object)}
	 * .
	 */
	@Test
	public void testToElement( )
	{
		final IConfigurationManager config = new ConfigurationData( );
		config.addServer( new ServerInformation( "http://localhost/jenkins/", "", "", 1000, true, true ) );
		config.addServer( new ServerInformation( "http://123.1.2.3/jenkins/", "", "", 2000, false, false ) );

		config.setJobShowState( "JenkinsRemote", true );
		config.setJobShowState( "JenkinsRemote2", false );

		config.setItemShowState( "test", true );
		config.setItemShowState( "health", false );
		config.setItemShowState( "downloads", true );

		final Element element = mConverter.toElement( config );

		assertEquals( ConfigurationFileConverter.ELEMENT_HUDSON_REMOTE, element.getName( ) );

		Element child = element.getChild( ConfigurationFileConverter.ELEMENT_SERVERS );
		final List<?> servers = child.getChildren( );
		assertEquals( 2, servers.size( ) );
		assertEquals( "http://localhost/jenkins/", ( (Element)servers.get( 0 ) )
				.getAttributeValue( ServerInformationConverter.ADDRESS_ATTRIBUTE ) );
		assertEquals( "http://123.1.2.3/jenkins/", ( (Element)servers.get( 1 ) )
				.getAttributeValue( ServerInformationConverter.ADDRESS_ATTRIBUTE ) );

		child = element.getChild( ConfigurationFileConverter.ELEMENT_JOBS );
		final List<?> jobs = child.getChildren( );
		assertEquals( 2, jobs.size( ) );
		assertEquals( "JenkinsRemote", ( (Element)jobs.get( 0 ) ).getAttributeValue( JobInformationConverter.NAME_ATTRIBUTE ) );
		assertEquals( "JenkinsRemote2", ( (Element)jobs.get( 1 ) ).getAttributeValue( JobInformationConverter.NAME_ATTRIBUTE ) );

		child = element.getChild( ConfigurationFileConverter.ELEMENT_SHOW );
		final List<?> shows = child.getChildren( );
		assertEquals( 3, shows.size( ) );
		assertEquals( "test", ( (Element)shows.get( 0 ) ).getName( ) );
		assertEquals( "health", ( (Element)shows.get( 1 ) ).getName( ) );
		assertEquals( "downloads", ( (Element)shows.get( 2 ) ).getName( ) );
	}
}
