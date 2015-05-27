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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.jdom.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spextreme.jenkins.remote.model.IServerInformation;
import com.spextreme.jenkins.remote.model.internal.ServerInformation;
import com.spextreme.jenkins.remote.xml.internal.ServerInformationConverter;

/**
 * Tests the {@link ServerInformationConverter} object.
 */
public class ServerInformationConverterTest
{
	/**
	 * The object under test.
	 */
	private ServerInformationConverter	mConverter	= null;

	/**
	 * @throws java.lang.Exception Thrown if an error occurs.
	 */
	@Before
	public void setUp( ) throws Exception
	{
		mConverter = new ServerInformationConverter( );
	}

	/**
	 * @throws java.lang.Exception Thrown if an error occurs.
	 */
	@After
	public void tearDown( ) throws Exception
	{
		mConverter = null;
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.xml.internal.ServerInformationConverter#fromElement(org.jdom.Element)}
	 * .
	 */
	@Test
	public void testFromElement( )
	{
		final Element element = new Element( ServerInformationConverter.ELEMENT_SERVER );
		element.setAttribute( ServerInformationConverter.ADDRESS_ATTRIBUTE, "http://localhost/jenkins/" );
		element.setAttribute( ServerInformationConverter.USERNAME_ATTRIBUTE, "username" );
		element.setAttribute( ServerInformationConverter.PASSWORD_ATTRIBUTE, "password" );
		element.setAttribute( ServerInformationConverter.QUERY_RATE_ATTRIBUTE, "2000" );
		element.setAttribute( ServerInformationConverter.ALLOW_INCREASE_ATTRIBUTE, "true" );
		element.setAttribute( ServerInformationConverter.IS_PRIMARY_ATTRIBUTE, "true" );

		final IServerInformation server = (IServerInformation)mConverter.fromElement( element );

		assertNotNull( server );
		assertEquals( "http://localhost/jenkins/", server.getAddress( ) );
		assertEquals( "username", server.getUserName( ) );
		assertEquals( "password", server.getPassword( ) );
		assertEquals( 2000, server.getQueryPeriodic( ) );
		assertTrue( server.isAllowIncrease( ) );
		assertTrue( server.isPrimary( ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.xml.internal.ServerInformationConverter#fromElement(org.jdom.Element)}
	 * .
	 */
	@Test
	public void testFromElementBadValues( )
	{
		final Element element = new Element( ServerInformationConverter.ELEMENT_SERVER );
		element.setAttribute( ServerInformationConverter.ADDRESS_ATTRIBUTE, "http://localhost/jenkins/" );
		element.setAttribute( ServerInformationConverter.QUERY_RATE_ATTRIBUTE, "abc" );
		element.setAttribute( ServerInformationConverter.ALLOW_INCREASE_ATTRIBUTE, "bad" );
		element.setAttribute( ServerInformationConverter.IS_PRIMARY_ATTRIBUTE, "bad" );

		final IServerInformation server = (IServerInformation)mConverter.fromElement( element );

		assertNotNull( server );
		assertEquals( "http://localhost/jenkins/", server.getAddress( ) );
		assertEquals( "", server.getUserName( ) );
		assertEquals( "", server.getPassword( ) );
		assertEquals( 10000, server.getQueryPeriodic( ) );
		assertFalse( server.isAllowIncrease( ) );
		assertFalse( server.isPrimary( ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.xml.internal.ServerInformationConverter#fromElement(org.jdom.Element)}
	 * .
	 */
	@Test
	public void testFromElementNotServer( )
	{
		final Element element = new Element( "notRight" );

		assertNull( mConverter.fromElement( element ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.xml.internal.ServerInformationConverter#fromElement(org.jdom.Element)}
	 * .
	 */
	@Test
	public void testFromElementNull( )
	{
		assertNull( mConverter.fromElement( null ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.xml.internal.ServerInformationConverter#toElement(java.lang.Object)}
	 * .
	 */
	@Test
	public void testToElement( )
	{
		final IServerInformation server = new ServerInformation( "http://localhost/jenkins/", "username", "password", 1000,
				true, false );

		final Element element = mConverter.toElement( server );

		assertEquals( "http://localhost/jenkins/", element.getAttributeValue( ServerInformationConverter.ADDRESS_ATTRIBUTE ) );
		assertEquals( "username", element.getAttributeValue( ServerInformationConverter.USERNAME_ATTRIBUTE ) );
		assertEquals( "password", element.getAttributeValue( ServerInformationConverter.PASSWORD_ATTRIBUTE ) );
		assertEquals( "1000", element.getAttributeValue( ServerInformationConverter.QUERY_RATE_ATTRIBUTE ) );
		assertEquals( "true", element.getAttributeValue( ServerInformationConverter.ALLOW_INCREASE_ATTRIBUTE ) );
		assertEquals( "false", element.getAttributeValue( ServerInformationConverter.IS_PRIMARY_ATTRIBUTE ) );
	}
}
