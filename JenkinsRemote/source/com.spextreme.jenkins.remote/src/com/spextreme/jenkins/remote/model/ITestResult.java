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

package com.spextreme.jenkins.remote.model;

/**
 * The interface for the test results.
 */
public interface ITestResult
{
	/**
	 * Gets the number of tests that failed.
	 * 
	 * @return The failed count.
	 */
	public int getFailedCount( );

	/**
	 * Gets the number of skipped tests.
	 * 
	 * @return The skipped count.
	 */
	public int getSkipCount( );

	/**
	 * Gets the total number of tests.
	 * 
	 * @return The total number of tests.
	 */
	public int getTotalCount( );

	/**
	 * The total number of test that failed.
	 * 
	 * @param count The total failed count.
	 */
	public void setFailedCount( final int count );

	/**
	 * The total number of tests that were skipped.
	 * 
	 * @param count The total skipped count.
	 */
	public void setSkipCount( final int count );

	/**
	 * Sets the total number of tests.
	 * 
	 * @param count The total number of tests.
	 */
	public void setTotalCount( final int count );
}
