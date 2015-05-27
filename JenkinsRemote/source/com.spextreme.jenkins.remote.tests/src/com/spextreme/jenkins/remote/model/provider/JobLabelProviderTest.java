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

package com.spextreme.jenkins.remote.model.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spextreme.jenkins.remote.model.internal.Job;
import com.spextreme.jenkins.remote.model.internal.ServerInformation;
import com.spextreme.jenkins.remote.model.provider.JobLabelProvider;

/**
 * Test the job label provider.
 */
public class JobLabelProviderTest
{
	/**
	 * The object under test.
	 */
	JobLabelProvider	mProvider	= null;

	/**
	 * @throws java.lang.Exception Thrown if an error occurs.
	 */
	@Before
	public void setUp( ) throws Exception
	{
		mProvider = new JobLabelProvider( );
	}

	/**
	 * @throws java.lang.Exception Thrown if an error occurs.
	 */
	@After
	public void tearDown( ) throws Exception
	{
		mProvider.dispose( );
		mProvider = null;
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.provider.JobLabelProvider#getColumnImage(java.lang.Object, int)}
	 * .
	 */
	@Test
	public void testGetColumnImage( )
	{
		assertNull( mProvider.getColumnImage( null, 0 ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.provider.JobLabelProvider#getColumnText(java.lang.Object, int)}
	 * .
	 */
	@Test
	public void testGetColumnText( )
	{
		assertNull( mProvider.getColumnText( "", 0 ) );
		assertEquals( "Test1", mProvider.getColumnText( new Job( new ServerInformation( "address", "", "", 1000, false,
				false ), "Test1", "", "disabled" ), 0 ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.provider.JobLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)}
	 * .
	 */
	@Test
	public void testIsLabelProperty( )
	{
		assertFalse( mProvider.isLabelProperty( null, "" ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.provider.JobLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)}
	 * .
	 */
	@Test
	public void testListener( )
	{
		// These are not implemented so just call for coverage.
		mProvider.addListener( null );
		mProvider.removeListener( null );
	}

}
