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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.jdom.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spextreme.jenkins.remote.model.IShowInformation;
import com.spextreme.jenkins.remote.model.internal.ShowInformation;
import com.spextreme.jenkins.remote.xml.internal.ShowInformationConverter;

/**
 * Tests the {@link ShowInformationConverter} object.
 */
public class ShowInformationConverterTest
{
	/**
	 * The object under test.
	 */
	private ShowInformationConverter	mConverter	= null;

	/**
	 * @throws java.lang.Exception Thrown if an error occurs.
	 */
	@Before
	public void setUp( ) throws Exception
	{
		mConverter = new ShowInformationConverter( "test" );
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
	 * {@link com.spextreme.jenkins.remote.xml.internal.ShowInformationConverter#fromElement(org.jdom.Element)}
	 * .
	 */
	@Test
	public void testFromElement( )
	{
		Element element = new Element( "test" );
		element.setText( "true" );

		final Object item = mConverter.fromElement( element );

		assertNotNull( item );
		assertTrue( item instanceof IShowInformation );
		assertEquals( "test", ( (IShowInformation)item ).getName( ) );
		assertTrue( ( (IShowInformation)item ).isVisible( ) );

		element.setText( "" );
		assertNull( mConverter.fromElement( element ) );

		element = new Element( "InvalidName" );
		assertNull( mConverter.fromElement( element ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.xml.internal.ShowInformationConverter#toElement(java.lang.Object)}
	 * .
	 */
	@Test
	public void testToElement( )
	{
		final IShowInformation show = new ShowInformation( );
		show.setName( "health" );
		show.setVisible( true );

		final Element element = mConverter.toElement( show );

		assertEquals( "health", element.getName( ) );
		assertEquals( "true", element.getText( ) );

		assertNull( mConverter.toElement( new Object( ) ) );
	}
}
