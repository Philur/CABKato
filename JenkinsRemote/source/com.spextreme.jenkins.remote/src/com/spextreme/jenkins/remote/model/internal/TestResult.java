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

import com.spextreme.jenkins.remote.model.ITestResult;

/**
 * This holds the test results data. It is pulled from the action (with a URL of 'testReport')
 */
public class TestResult implements ITestResult
{
	/**
	 * Failed data item.
	 */
	private int	mFailedCount	= 0;
	/**
	 * The skipped test data item.
	 */
	private int	mSkipCount		= 0;
	/**
	 * The total number of tests.
	 */
	private int	mTotalCount		= 0;

	/**
	 * Constructs the test results.
	 * 
	 * @param failed The failed count.
	 * @param skipped The skipped count.
	 * @param total The total count.
	 */
	public TestResult( final int failed, final int skipped, final int total )
	{
		setFailedCount( failed );
		setSkipCount( skipped );
		setTotalCount( total );
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @param obj The object to compare to.
	 * @return <code>true</code> if the two are equal. Otherwise <code>false</code>.
	 */
	@Override
	public boolean equals( final Object obj )
	{
		boolean result = this == obj;

		if( ( obj != null ) && !result )
		{
			if( obj instanceof ITestResult )
			{
				final ITestResult tr = (ITestResult)obj;

				result = ( getTotalCount( ) == tr.getTotalCount( ) ) && ( getFailedCount( ) == tr.getFailedCount( ) )
						&& ( getSkipCount( ) == tr.getSkipCount( ) );
			}
		}

		return result;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.ITestResult#getFailedCount()
	 * @return The failed count.
	 */
	public int getFailedCount( )
	{
		return mFailedCount;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.ITestResult#getSkipCount()
	 * @return The skipped count.
	 */
	public int getSkipCount( )
	{
		return mSkipCount;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.ITestResult#getTotalCount()
	 * @return The total number of tests.
	 */
	public int getTotalCount( )
	{
		return mTotalCount;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 * @return The hash code value.
	 */
	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + getTotalCount( );
		result = prime * result + getFailedCount( );
		result = prime * result + getSkipCount( );

		return result;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.ITestResult#setFailedCount(int)
	 * @param count The total failed count.
	 */
	public void setFailedCount( final int count )
	{
		mFailedCount = count;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.ITestResult#setSkipCount(int)
	 * @param count The total skipped count.
	 */
	public void setSkipCount( final int count )
	{
		mSkipCount = count;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.ITestResult#setTotalCount(int)
	 * @param count The total number of tests.
	 */
	public void setTotalCount( final int count )
	{
		mTotalCount = count;
	}
}
