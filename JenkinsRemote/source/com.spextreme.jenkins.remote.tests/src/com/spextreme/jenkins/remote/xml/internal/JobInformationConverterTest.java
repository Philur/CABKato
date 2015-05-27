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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.jdom.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spextreme.jenkins.remote.model.IJobInformation;
import com.spextreme.jenkins.remote.model.internal.JobInformation;
import com.spextreme.jenkins.remote.xml.internal.JobInformationConverter;

/**
 * Test the {@link JobInformationConverter} object.
 */
public class JobInformationConverterTest
{
	/**
	 * The object under test.
	 */
	private JobInformationConverter	mConverter	= null;

	/**
	 * @throws java.lang.Exception Thrown if an error occurs.
	 */
	@Before
	public void setUp( ) throws Exception
	{
		mConverter = new JobInformationConverter( );
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
	 * {@link com.spextreme.jenkins.remote.xml.internal.JobInformationConverter#fromElement(org.jdom.Element)}
	 * .
	 */
	@Test
	public void testFromElement( )
	{
		final Element element = new Element( JobInformationConverter.ELEMENT_JOB );

		element.setAttribute( JobInformationConverter.NAME_ATTRIBUTE, "JenkinsRemote" );
		element.setAttribute( JobInformationConverter.SERVER_ATTRIBUTE, "http://localhost/jenkins/" );
		element.setAttribute( JobInformationConverter.VISIBLE_ATTRIBUTE, "true" );

		final IJobInformation info = (IJobInformation)mConverter.fromElement( element );

		assertEquals( "JenkinsRemote", info.getName( ) );
		assertEquals( "http://localhost/jenkins/", info.getServerReference( ) );
		assertNull( info.getServerInstance( ) );
		assertTrue( info.isVisible( ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.xml.internal.JobInformationConverter#toElement(java.lang.Object)}
	 * .
	 */
	@Test
	public void testToElement( )
	{
		final IJobInformation job = new JobInformation( );
		job.setName( "JenkinsRemote" );
		job.setServerReference( "http://localhost/jenkins/" );
		job.setVisible( true );

		final Element element = mConverter.toElement( job );

		assertEquals( "JenkinsRemote", element.getAttributeValue( JobInformationConverter.NAME_ATTRIBUTE ) );
		assertEquals( "http://localhost/jenkins/", element.getAttributeValue( JobInformationConverter.SERVER_ATTRIBUTE ) );
		assertEquals( "true", element.getAttributeValue( JobInformationConverter.VISIBLE_ATTRIBUTE ) );
	}
}
