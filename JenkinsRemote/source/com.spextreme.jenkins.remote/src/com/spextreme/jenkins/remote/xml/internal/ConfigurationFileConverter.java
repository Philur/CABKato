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

import java.util.List;

import org.jdom.Element;

import com.spextreme.jenkins.remote.model.IConfigurationManager;
import com.spextreme.jenkins.remote.model.IJobInformation;
import com.spextreme.jenkins.remote.model.IServerInformation;
import com.spextreme.jenkins.remote.model.IShowInformation;
import com.spextreme.jenkins.remote.model.internal.ConfigurationData;
import com.spextreme.jenkins.remote.xml.IXMLProcessing;

/**
 * This is responsible for parsing the configuration file that is used by Jenkins Remote to control
 * the different aspects of it.
 */
public class ConfigurationFileConverter implements IXMLProcessing
{
	/**
	 * The jenkins remote root element.
	 */
	public static final String				ELEMENT_HUDSON_REMOTE	= "jenkinsremote";
	/**
	 * The server element.
	 */
	public static final String				ELEMENT_SERVERS			= "servers";
	/**
	 * The jobs element.
	 */
	public static final String				ELEMENT_JOBS			= "jobs";
	/**
	 * The show element.
	 */
	public static final String				ELEMENT_SHOW			= "show";

	/**
	 * The job information converter instance.
	 */
	private JobInformationConverter			mJobConverter			= null;
	/**
	 * The server information converter.
	 */
	private ServerInformationConverter		mServerConverter		= null;
	/**
	 * The list of show information converters.
	 */
	private List<ShowInformationConverter>	mShowConverters			= null;

	/**
	 * Constructs the configuration file converter.
	 * 
	 * @param showConverters The list of show item converters.
	 */
	public ConfigurationFileConverter( final List<ShowInformationConverter> showConverters )
	{
		mJobConverter = new JobInformationConverter( );
		mServerConverter = new ServerInformationConverter( );
		mShowConverters = showConverters;
	}

	/**
	 * @see com.spextreme.jenkins.remote.xml.IXMLProcessing#fromElement(org.jdom.Element)
	 * @param element The XML element which should be the root of the XML file (jenkinsRemote).
	 * @return The {@link IConfigurationManager} instance or <code>null</code> if not a valid XML
	 *         file.
	 */
	@Override
	public Object fromElement( final Element element )
	{
		IConfigurationManager configFile = null;

		if( element.getName( ).equals( ELEMENT_HUDSON_REMOTE ) )
		{
			configFile = new ConfigurationData( );

			Element child = element.getChild( ELEMENT_JOBS );
			List<?> children = child.getChildren( );

			for( final Object obj : children )
			{
				final Object item = getJobConverter( ).fromElement( (Element)obj );

				if( item != null )
				{
					configFile.getJobs( ).add( (IJobInformation)item );
				}
			}

			child = element.getChild( ELEMENT_SERVERS );
			children = child.getChildren( );

			for( final Object obj : children )
			{
				final Object item = getServerConverter( ).fromElement( (Element)obj );
				if( item != null )
				{
					configFile.addServer( (IServerInformation)item );
				}
			}

			child = element.getChild( ELEMENT_SHOW );
			children = child.getChildren( );

			for( final Object obj : children )
			{
				for( final ShowInformationConverter converter : getShowConverters( ) )
				{
					final Object item = converter.fromElement( (Element)obj );
					if( item != null )
					{
						configFile.getShowInfo( ).add( (IShowInformation)item );
						break;
					}
				}
			}
		}

		return configFile;
	}

	/**
	 * Gets the job information converter.
	 * 
	 * @return Returns the {@link JobInformationConverter}.
	 */
	public JobInformationConverter getJobConverter( )
	{
		return mJobConverter;
	}

	/**
	 * Gets the server information converter.
	 * 
	 * @return Returns the {@link ServerInformationConverter}.
	 */
	public ServerInformationConverter getServerConverter( )
	{
		return mServerConverter;
	}

	/**
	 * Gets the List<ShowInformationConverter> for this object.
	 * 
	 * @return Returns the mShowConverters.
	 */
	public List<ShowInformationConverter> getShowConverters( )
	{
		return mShowConverters;
	}

	/**
	 * @see com.spextreme.jenkins.remote.xml.IXMLProcessing#toElement(java.lang.Object)
	 * @param object The configuration data item.
	 * @return The XML {@link Element} created from the object or null if not a valid type.
	 */
	@Override
	public Element toElement( final Object object )
	{
		Element element = null;

		if( object instanceof IConfigurationManager )
		{
			final IConfigurationManager config = (IConfigurationManager)object;

			element = new Element( ELEMENT_HUDSON_REMOTE );

			final Element servers = new Element( ELEMENT_SERVERS );
			for( final IServerInformation server : config.getServers( ) )
			{
				final Element serverElement = getServerConverter( ).toElement( server );
				servers.addContent( serverElement );
			}
			element.addContent( servers );

			final Element jobs = new Element( ELEMENT_JOBS );
			for( final IJobInformation job : config.getJobs( ) )
			{
				final Element jobElement = getJobConverter( ).toElement( job );
				jobs.addContent( jobElement );
			}
			element.addContent( jobs );

			final Element showElementSet = new Element( ELEMENT_SHOW );
			for( final IShowInformation show : config.getShowInfo( ) )
			{
				final Element showElement = getShowElement( show );
				if( showElement != null )
				{
					showElementSet.addContent( showElement );
				}
			}
			element.addContent( showElementSet );
		}

		return element;
	}

	/**
	 * Attempts to create an element for the given item based on the registered set of show
	 * information converters.
	 * 
	 * @param item The item to convert.
	 * @return The show item converted or <code>null</code> if no converter is found.
	 */
	private Element getShowElement( final IShowInformation item )
	{
		Element element = null;

		for( final ShowInformationConverter converter : getShowConverters( ) )
		{
			element = converter.toElement( item );

			if( element != null )
			{
				break;
			}
		}

		return element;
	}
}
