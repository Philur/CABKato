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

import org.jdom.Element;

import com.spextreme.jenkins.remote.model.IArtifact;
import com.spextreme.jenkins.remote.model.internal.Artifact;
import com.spextreme.jenkins.remote.xml.IXMLProcessing;

/**
 * This processes the 'artifact' element of the Jenkins Remote API XML data and parses it into a
 * {@link IArtifact}.
 */
public class ArtifactsXMLProcessing implements IXMLProcessing
{
	/**
	 * Constructs the processor.
	 */
	public ArtifactsXMLProcessing( )
	{
		// do nothing.
	}

	/**
	 * Gets the artifact item from the element as long as the passed in element is an 'artifact'.
	 * 
	 * @param element The element to parse. This should be the artifact element from the Jenkins XML
	 *            tree.
	 * @return An {@link IArtifact} item or <code>null</code> if unable to parse.
	 */
	public Object fromElement( final Element element )
	{
		IArtifact report = null;

		if( ( element != null ) && element.getName( ).equals( "artifact" ) )
		{
			report = new Artifact( element.getChildText( "displayPath" ), element.getChildText( "relativePath" ) );
		}

		return report;
	}

	/**
	 * Not implemented in this implementation.
	 * 
	 * @see com.spextreme.jenkins.remote.xml.IXMLProcessing#toElement(java.lang.Object)
	 * @param object The object.
	 * @return The element.
	 */
	@Override
	public Element toElement( final Object object )
	{
		return null;
	}
}
