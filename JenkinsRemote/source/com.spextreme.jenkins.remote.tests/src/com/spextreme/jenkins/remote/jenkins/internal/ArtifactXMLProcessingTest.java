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

import com.spextreme.jenkins.remote.jenkins.internal.ArtifactsXMLProcessing;
import com.spextreme.jenkins.remote.model.IArtifact;

/**
 * Tests the artifact XML processing.
 */
public class ArtifactXMLProcessingTest extends AbstractXMLProcessingHelper
{
	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.jenkins.internal.ArtifactsXMLProcessing#fromElement(org.jdom.Element)}
	 * .
	 */
	@Test
	public void testProcessElementInvalid( ) throws Exception
	{
		assertNull( new ArtifactsXMLProcessing( ).fromElement( getXMLElement( "<testInvalid />" ) ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.jenkins.internal.ArtifactsXMLProcessing#fromElement(org.jdom.Element)}
	 * .
	 */
	@Test
	public void testProcessElementValid( ) throws Exception
	{
		final ArtifactsXMLProcessing processor = new ArtifactsXMLProcessing( );

		final IArtifact artifact = (IArtifact)processor.fromElement( getXMLElement( getXMLString( ) ) );

		assertNotNull( artifact );
		assertEquals( "jenkinsremote_win32.1.0.0.35.zip", artifact.getDisplayName( ) );
		assertEquals( "jenkinsremote/com.spextreme.jenkins.remote.builder/build/jenkinsremote_win32.1.0.0.35.zip", artifact
				.getRelativePath( ) );
	}

	/**
	 * Tests the to element method returns <code>null</code>.
	 */
	@Test
	public void testToElement( )
	{
		assertNull( new ArtifactsXMLProcessing( ).toElement( new Object( ) ) );
	}

	/**
	 * Gets a string for a valid artifact element.
	 * 
	 * @return The artifact XML string.
	 */
	protected String getXMLString( )
	{
		final StringBuilder xmlString = new StringBuilder( );

		xmlString.append( "<artifact>" );
		xmlString.append( "<displayPath>jenkinsremote_win32.1.0.0.35.zip</displayPath>" );
		xmlString.append( "<fileName>jenkinsremote_win32.1.0.0.35.zip</fileName>" );
		xmlString
				.append( "<relativePath>jenkinsremote/com.spextreme.jenkins.remote.builder/build/jenkinsremote_win32.1.0.0.35.zip</relativePath>" );
		xmlString.append( "</artifact>" );

		return xmlString.toString( );
	}
}
