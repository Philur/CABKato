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

import java.util.List;

import org.jdom.Element;

import com.spextreme.jenkins.remote.Utilities;
import com.spextreme.jenkins.remote.model.IArtifact;
import com.spextreme.jenkins.remote.model.IBuild;
import com.spextreme.jenkins.remote.model.ITestResult;
import com.spextreme.jenkins.remote.model.internal.Build;
import com.spextreme.jenkins.remote.model.internal.TestResult;
import com.spextreme.jenkins.remote.xml.IXMLProcessing;

/**
 * This processes build information from the Jenkins Remote API XML data. If parsing the element is
 * successful an {@link IBuild} item will be returned.
 */
public class BuildXMLProcessing implements IXMLProcessing
{
	/**
	 * The build element to support.
	 */
	private String	mBuildElementTag	= "";

	/**
	 * Constructs the processor.
	 * 
	 * @param elementTagName The name of the element tag to parse. Possible values are:
	 *            <ul>
	 *            <li>build</li>
	 *            <li>lastBuild</li>
	 *            <li>lastCompletedBuild</li>
	 *            <li>lastStableBuild</li>
	 *            <li>lastSuccessfulBuild</li>
	 *            </ul>
	 */
	public BuildXMLProcessing( final String elementTagName )
	{
		mBuildElementTag = elementTagName;
	}

	/**
	 * @see com.spextreme.jenkins.remote.xml.IXMLProcessing#fromElement(org.jdom.Element)
	 * @param element The build element to process.
	 * @return The {@link IBuild} parsed from the element or <code>null</code> if it wasn't a build
	 *         element.
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public Object fromElement( final Element element )
	{
		IBuild build = null;
		if( ( element != null ) && element.getName( ).equalsIgnoreCase( mBuildElementTag ) )
		{
			build = new Build( element.getChildText( "id" ), element.getChildText( "fullDisplayName" ), Utilities.parseInt(
					element.getChildText( "number" ), 0 ), Boolean.parseBoolean( element.getChildText( "building" ) ) );

			build.setURL( element.getChildText( "url" ) );
			build.setResult( element.getChildText( "result" ) );
			build.setDuration( Utilities.parseLong( element.getChildText( "duration" ), 0L ) );
			build.setTimestamp( Utilities.parseLong( element.getChildText( "timestamp" ), 0L ) );

			IXMLProcessing subProcessor = new TestReportXMLProcessing( );

			for( final Element childElement : (List<Element>)element.getChildren( "action" ) )
			{
				final ITestResult item = (ITestResult)subProcessor.fromElement( childElement );
				if( item != null )
				{
					build
							.setTestResults( new TestResult( item.getFailedCount( ), item.getSkipCount( ), item
									.getTotalCount( ) ) );
					// done since there should be only one test results.
					break;
				}
			}

			subProcessor = new ArtifactsXMLProcessing( );

			for( final Element childElement : (List<Element>)element.getChildren( "artifact" ) )
			{
				final IArtifact item = (IArtifact)subProcessor.fromElement( childElement );
				if( item != null )
				{
					build.addArtifacts( item );
				}
			}
		}
		return build;
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
