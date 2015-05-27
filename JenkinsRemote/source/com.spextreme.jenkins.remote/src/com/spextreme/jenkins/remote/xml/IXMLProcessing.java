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

package com.spextreme.jenkins.remote.xml;

import org.jdom.Element;

/**
 * This is the interface used for XML processing.
 */
public interface IXMLProcessing
{
	/**
	 * Process the element. This is a JDOM {@link Element} object.
	 * 
	 * @param element The element to process.
	 * @return The parsed object from the element.
	 */
	public Object fromElement( final Element element );

	/**
	 * Process the object and converts it to an XML element. This is a JDOM {@link Element} that get
	 * created.
	 * 
	 * @param object The object to process.
	 * @return The JDOM {@link Element}
	 */
	public Element toElement( Object object );
}
