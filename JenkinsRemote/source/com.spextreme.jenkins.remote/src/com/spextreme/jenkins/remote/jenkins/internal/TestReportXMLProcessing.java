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

import com.spextreme.jenkins.remote.Utilities;
import com.spextreme.jenkins.remote.model.ITestResult;
import com.spextreme.jenkins.remote.model.internal.TestResult;
import com.spextreme.jenkins.remote.xml.IXMLProcessing;

/**
 * This processes the 'action' element of the Jenkins Remote API XML data and parses it into a
 * {@link ITestResult}. This has a secondary check to make sure the action is a test report by
 * looking at the URL field of the action.
 */
public class TestReportXMLProcessing implements IXMLProcessing
{
	/**
	 * Constructs the processor.
	 */
	public TestReportXMLProcessing( )
	{
		// do nothing.
	}

	/**
	 * Gets the test results from the action element as long as the passed in element is a 'action'
	 * and there is a URL child element with the work 'testResult'.
	 * 
	 * @param element The element to parse. This should be the action element from the Jenkins XML
	 *            tree.
	 * @return An {@link ITestResult} item or <code>null</code> if unable to parse.
	 */
	public Object fromElement( final Element element )
	{
		ITestResult result = null;

		if( ( element != null ) && element.getName( ).equals( "action" ) )
		{
			final String urlString = element.getChildText( "urlName" );
			if( ( urlString != null ) && urlString.equals( "testReport" ) )
			{
				result = new TestResult( Utilities.parseInt( element.getChildText( "failCount" ), 0 ), Utilities.parseInt(
						element.getChildText( "skipCount" ), 0 ), Utilities.parseInt( element.getChildText( "totalCount" ),
						0 ) );
			}
		}

		return result;
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
