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

package com.spextreme.jenkins.remote.model.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.spextreme.jenkins.remote.model.ITestResult;
import com.spextreme.jenkins.remote.model.internal.TestResult;

/**
 * Tests the Test Results object.
 */
public class TestResultTest
{
	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.internal.TestResult#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject( )
	{
		final TestResult r1 = new TestResult( 1, 2, 3 );
		final TestResult r2 = new TestResult( 2, 2, 3 );
		final TestResult r3 = new TestResult( 1, 3, 3 );
		final TestResult r4 = new TestResult( 1, 2, 4 );
		final TestResult r5 = new TestResult( 1, 2, 3 );

		assertTrue( r1.equals( r1 ) );
		assertFalse( r1.equals( r2 ) );
		assertFalse( r1.equals( r3 ) );
		assertFalse( r1.equals( r4 ) );
		assertTrue( r1.equals( r5 ) );
		assertFalse( r1.equals( null ) );
		assertFalse( r1.equals( new Object( ) ) );
	}

	/**
	 * Tests the get/set methods.
	 */
	@Test
	public void testGetSetMethods( )
	{
		final ITestResult result = new TestResult( 0, 1, 20 );

		assertEquals( 0, result.getFailedCount( ) );
		assertEquals( 1, result.getSkipCount( ) );
		assertEquals( 20, result.getTotalCount( ) );

		result.setFailedCount( 4 );
		assertEquals( 4, result.getFailedCount( ) );

		result.setSkipCount( 2 );
		assertEquals( 2, result.getSkipCount( ) );

		result.setTotalCount( 40 );
		assertEquals( 40, result.getTotalCount( ) );
	}

	/**
	 * Test method for {@link com.spextreme.jenkins.remote.model.internal.TestResult#hashCode()}.
	 */
	@Test
	public void testHashCode( )
	{
		final TestResult r1 = new TestResult( 1, 2, 3 );
		final TestResult r2 = new TestResult( 2, 2, 3 );
		final TestResult r3 = new TestResult( 1, 3, 3 );
		final TestResult r4 = new TestResult( 1, 2, 4 );
		final TestResult r5 = new TestResult( 1, 2, 3 );

		assertTrue( r1.hashCode( ) == r1.hashCode( ) );
		assertFalse( r1.hashCode( ) == r2.hashCode( ) );
		assertFalse( r1.hashCode( ) == r3.hashCode( ) );
		assertFalse( r1.hashCode( ) == r4.hashCode( ) );
		assertTrue( r1.hashCode( ) == r5.hashCode( ) );
		assertFalse( r1.hashCode( ) == new Object( ).hashCode( ) );
	}
}
