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
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.spextreme.jenkins.remote.model.IBuild;
import com.spextreme.jenkins.remote.model.IHealthReport;
import com.spextreme.jenkins.remote.model.IJob;
import com.spextreme.jenkins.remote.model.IServerInformation;
import com.spextreme.jenkins.remote.model.internal.Job;
import com.spextreme.jenkins.remote.xml.IXMLProcessing;

/**
 * This processes the Jenkins Remote API XML data and parses it into a {@link IJob}. The data is then
 * made available as a list of jobs that were parsed out of the retrieved XML.
 */
public class JenkinsRemoteXMLProcessing implements IXMLProcessing
{
	/**
	 * Loads the data from the jenkins server.
	 * 
	 * @param serverInformation The server to get the information from.
	 * @return The list of jobs processed from the returned data or an empty list.
	 */
	@SuppressWarnings( "unchecked" )
	public static List<IJob> loadDataFromJenkinsServer( final IServerInformation serverInformation )
	{
		final List<IJob> jobs = new Vector<IJob>( );
		final JenkinsRemoteXMLProcessing processor = new JenkinsRemoteXMLProcessing( serverInformation );

		try
		{
			final Document dom = new SAXBuilder( ).build( serverInformation.getAddress( ) + "api/xml?depth=2" );

			// scan through the job list and print its status
			for( final Element elementJob : (List<Element>)dom.getRootElement( ).getChildren( "job" ) )
			{
				final IJob job = (IJob)processor.fromElement( elementJob );
				if( job != null )
				{
					jobs.add( job );
				}
			}
		}
		catch( final Exception e )
		{
			// do nothing.
			e.printStackTrace( );
		}

		return jobs;
	}

	/**
	 * The server instance.
	 */
	private IServerInformation	mServer	= null;

	/**
	 * Constructs the processor.
	 * 
	 * @param serverInformation The server instance. This can not be <code>null</code>.
	 */
	public JenkinsRemoteXMLProcessing( final IServerInformation serverInformation )
	{
		mServer = serverInformation;
	}

	/**
	 * @see com.spextreme.jenkins.remote.xml.IXMLProcessing#fromElement(org.jdom.Element)
	 * @param element The Job element to process.
	 * @return The job parsed from the element or null if not a job element.
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public Object fromElement( final Element element )
	{
		IJob job = null;

		if( element.getName( ).equals( "job" ) )
		{
			job = new Job( mServer, element.getChildText( "name" ), element.getChildText( "url" ),
					element.getChildText( "color" ) );

			processHealthReports( job, element.getChildren( "healthReport" ) );

			Element buildElement = element.getChild( "lastCompletedBuild" );
			final IBuild lastCompletedBuild = processBuilds( "lastCompletedBuild", buildElement );
			if( lastCompletedBuild != null )
			{
				job.setLastCompletedBuild( lastCompletedBuild );
			}

			buildElement = element.getChild( "lastBuild" );
			final IBuild currentBuild = processBuilds( "lastBuild", buildElement );

			if( currentBuild != null )
			{
				if( currentBuild.isBuilding( ) )
				{
					job.setCurrentBuild( currentBuild );
				}
			}
		}
		return job;
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

	/**
	 * Process the builds nodes. The string tells which build item to process.
	 * 
	 * @param buildTypes The string of the build type to get.
	 * @param element The element to process.
	 * @return The {@link IBuild} or <code>null</code> if not valid.
	 */
	protected IBuild processBuilds( final String buildTypes, final Element element )
	{
		return (IBuild)new BuildXMLProcessing( buildTypes ).fromElement( element );
	}

	/**
	 * Gets the average of the health reports. It uses the scores of each health report element and
	 * averages it.
	 * 
	 * @param job The job to put the reports into.
	 * @param healthReports The list of health reports.
	 */
	protected void processHealthReports( final IJob job, final List<Element> healthReports )
	{
		if( healthReports != null )
		{
			final IXMLProcessing processor = new HealthReportXMLProcessing( );

			for( final Element item : healthReports )
			{
				final IHealthReport report = (IHealthReport)processor.fromElement( item );

				if( report != null )
				{
					job.addHealthReport( report );
				}
			}
		}
	}
}
