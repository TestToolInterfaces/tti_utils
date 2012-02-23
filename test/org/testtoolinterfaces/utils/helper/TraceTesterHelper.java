/**
 * 
 */
package org.testtoolinterfaces.utils.helper;

import org.testtoolinterfaces.utils.Trace;

/**
 * @author Arjan Kranenburg
 *
 */
public class TraceTesterHelper
{
	public void depthTestHelper1()
	{
		Trace.println(Trace.EXEC, "depthTestHelper1");
		depthTestHelper2();
	}

	private void depthTestHelper2()
	{
		Trace.println(Trace.EXEC, "depthTestHelper2");
	}
}
