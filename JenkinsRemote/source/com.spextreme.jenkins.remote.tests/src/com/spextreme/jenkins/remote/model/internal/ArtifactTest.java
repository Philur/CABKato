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

import com.spextreme.jenkins.remote.model.IArtifact;
import com.spextreme.jenkins.remote.model.internal.Artifact;

/**
 * Test the artifact object.
 */
public class ArtifactTest
{
	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.internal.Artifact#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject( )
	{
		final IArtifact a1 = new Artifact( "Test1", "path/to/file/test1.zip" );
		final IArtifact a2 = new Artifact( "Test2", "path/to/file/test2.zip" );
		final IArtifact a3 = new Artifact( "Test1", "path/to/file/test1.zip" );

		assertTrue( a1.equals( a1 ) );
		assertFalse( a1.equals( a2 ) );
		assertTrue( a1.equals( a3 ) );
		assertFalse( a1.equals( null ) );
		assertFalse( a1.equals( new Object( ) ) );
	}

	/**
	 * Tests the get/set methods.
	 */
	@Test
	public void testGetSetMethods( )
	{
		final IArtifact artifact = new Artifact( "DisplayName.zip", "path/to/artifact/DisplayName.zip" );

		assertEquals( "DisplayName.zip", artifact.getDisplayName( ) );
		assertEquals( "path/to/artifact/DisplayName.zip", artifact.getRelativePath( ) );

		artifact.setDisplayName( "test.zip" );
		assertEquals( "test.zip", artifact.getDisplayName( ) );

		artifact.setRelativePath( "new/path/to/file/test.zip" );
		assertEquals( "new/path/to/file/test.zip", artifact.getRelativePath( ) );
	}

	/**
	 * Test method for {@link com.spextreme.jenkins.remote.model.internal.Artifact#hashCode()}.
	 */
	@Test
	public void testHashCode( )
	{
		final IArtifact a1 = new Artifact( "Test1", "path/to/file/test1.zip" );
		final IArtifact a2 = new Artifact( "Test2", "path/to/file/test2.zip" );
		final IArtifact a3 = new Artifact( "Test1", "path/to/file/test1.zip" );

		assertTrue( a1.hashCode( ) == a1.hashCode( ) );
		assertFalse( a1.hashCode( ) == a2.hashCode( ) );
		assertTrue( a1.hashCode( ) == a3.hashCode( ) );
		assertFalse( a1.hashCode( ) == new Object( ).hashCode( ) );
	}

}
