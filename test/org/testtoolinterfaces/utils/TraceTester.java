/**
 * 
 */
package org.testtoolinterfaces.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testtoolinterfaces.utils.Trace;

/**
 * @author Arjan Kranenburg
 *
 */
public class TraceTester extends TestCase
{
	private final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
	private final PrintStream myOrigOut = System.out;
	private final String myNewline = System.getProperty( "line.separator" );

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		System.out.println("==========================================================================");
		System.out.println(this.getName() + ":");

		System.setOut(new PrintStream(myOut));
	}

	@After
	public void tearDown()
	{
	    System.setOut(myOrigOut);
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.Trace#addBaseClass(java.lang.String)}.
	 * 
	 * This test must runfirst as it adds a base class and since Trace is a global object, the
	 * added baseclass remains and cannot be removed.
	 */
	@Test
	public void testAddBaseClass()
	{
		Trace traceObj = Trace.getInstance();
		traceObj.setEnabled(true);
		traceObj.setDepth(3);
		traceObj.setTraceClass("org.testtoolinterfaces.utils.TraceTester");
		traceObj.setTraceLevel(Trace.ALL);

myOrigOut.println( "Once" );
		Trace.print(Trace.EXEC, "testAddBaseClass");
		myOrigOut.println( myOut.toString() );
		assertEquals("Incorrect printout", "", myOut.toString());
		myOut.reset();

		traceObj.addBaseClass("org.testtoolinterfaces");

myOrigOut.println( "Twice" );
		Trace.print(Trace.EXEC, "testAddBaseClass", true);
		myOrigOut.println( myOut.toString() );
		assertEquals("Incorrect printout", " utils.TraceTester.testAddBaseClass", myOut.toString());
		myOut.reset();
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.Trace#setDepth(int)}.
	 */
	@Test
	public void testSetDepth()
	{
		Trace traceObj = Trace.getInstance();
		traceObj.setEnabled(true);
		traceObj.setDepth(0);
		traceObj.setTraceClass("org.testtoolinterfaces.utils.TraceTester");
		traceObj.setTraceLevel(Trace.ALL);

		Trace.println(Trace.EXEC, "testSetDepth");
		depthTestHelper();
		myOrigOut.println( myOut.toString() );
		assertEquals("Incorrect printout", " testSetDepth" + myNewline + " depthTestHelper" + myNewline, myOut.toString());
		myOut.reset();

		traceObj.setDepth(1);
		depthTestHelper();
		myOrigOut.println( myOut.toString() );
		assertEquals("Incorrect printout", " depthTestHelper" + myNewline + "+ depthTestHelper1" + myNewline, myOut.toString());
		myOut.reset();

		traceObj.setDepth(2);
		depthTestHelper();
		myOrigOut.println( myOut.toString() );
		assertEquals("Incorrect printout", " depthTestHelper" + myNewline + "+ depthTestHelper1" + myNewline + "++ depthTestHelper2" + myNewline, myOut.toString());
		myOut.reset();
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.Trace#setEnabled(boolean)}.
	 */
	@Test
	public void testSetEnabled()
	{
		Trace traceObj = Trace.getInstance();
		traceObj.setEnabled(false);
		traceObj.setDepth(3);
		traceObj.setTraceClass("org.testtoolinterfaces.utils.TraceTester");
		traceObj.setTraceLevel(Trace.ALL);

		Trace.print(Trace.EXEC, "testSetEnabled");
		myOrigOut.println( myOut.toString() );
		assertEquals("Incorrect printout", "", myOut.toString());
		myOut.reset();

		traceObj.setEnabled(true);

		Trace.print(Trace.EXEC, "testSetEnabled");
		myOrigOut.println( myOut.toString() );
		assertEquals("Incorrect printout", " testSetEnabled", myOut.toString());
		myOut.reset();
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.Trace#setTraceClass(java.lang.String)}.
	 */
	@Test
	public void testSetTraceClass()
	{
		Trace traceObj = Trace.getInstance();
		traceObj.setEnabled(true);
		traceObj.setDepth(3);
		traceObj.setTraceClass("org.testtoolinterfaces.utils");
		traceObj.setTraceLevel(Trace.ALL);

		Trace.print(Trace.EXEC, "testSetTraceClass");
		myOrigOut.println( myOut.toString() );
		assertEquals("Incorrect printout", "", myOut.toString());
		myOut.reset();

		traceObj.setTraceClass("org.testtoolinterfaces.utils.TraceTester");

		Trace.print(Trace.EXEC, "testSetTraceClass");
		myOrigOut.println( myOut.toString() );
		assertEquals("Incorrect printout", " testSetTraceClass", myOut.toString());
		myOut.reset();

		traceObj.setTraceClass("org.testtoolinterfaces.utils.TraceTester2");

		Trace.print(Trace.EXEC, "testSetTraceClass");
		myOrigOut.println( myOut.toString() );
		assertEquals("Incorrect printout", "", myOut.toString());
		myOut.reset();
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.Trace#setTraceLevel(org.testtoolinterfaces.utils.Trace.LEVEL)}.
	 */
	@Test
	public void testSetTraceLevel()
	{
		Trace traceObj = Trace.getInstance();
		traceObj.setEnabled(true);
		traceObj.setDepth(3);
		traceObj.setTraceClass("org.testtoolinterfaces.utils.TraceTester");
		traceObj.setTraceLevel(Trace.HIGH);

		Trace.print(Trace.EXEC, "testSetTraceLevel");
		myOrigOut.println( myOut.toString() );
		assertEquals("Incorrect printout", "", myOut.toString());
		myOut.reset();

		traceObj.setTraceLevel(Trace.EXEC);

		Trace.print(Trace.EXEC, "testSetTraceLevel");
		myOrigOut.println( myOut.toString() );
		assertEquals("Incorrect printout", " testSetTraceLevel", myOut.toString());
		myOut.reset();

		traceObj.setTraceLevel(Trace.EXEC_PLUS);

		Trace.print(Trace.EXEC, "testSetTraceLevel2");
		Trace.print(Trace.EXEC_UTIL, "testSetTraceLevel3");
		myOrigOut.println( myOut.toString() );
		assertEquals("Incorrect printout", " testSetTraceLevel2", myOut.toString());
		myOut.reset();
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.Trace#println(org.testtoolinterfaces.utils.Trace.LEVEL)}.
	 */
	@Test
	public void testPrintlnLEVEL()
	{
		Trace traceObj = Trace.getInstance();
		traceObj.setEnabled(true);
		traceObj.setDepth(3);
		traceObj.setTraceClass("org.testtoolinterfaces.utils.TraceTester");
		traceObj.setTraceLevel(Trace.ALL);

		Trace.println(Trace.EXEC);
		myOrigOut.println( myOut.toString() );
		assertEquals("Incorrect printout", " utils.TraceTester.testPrintlnLEVEL" + myNewline, myOut.toString());
		myOut.reset();

	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.Trace#append(org.testtoolinterfaces.utils.Trace.LEVEL, java.lang.String)}.
	 */
	@Test
	public void testAppend()
	{
		Trace traceObj = Trace.getInstance();
		traceObj.setEnabled(true);
		traceObj.setDepth(3);
		traceObj.setTraceClass("org.testtoolinterfaces.utils.TraceTester");
		traceObj.setTraceLevel(Trace.ALL);

		Trace.println(Trace.EXEC);
		Trace.append(Trace.EXEC, "This is appended");
		myOrigOut.println( myOut.toString() );
		assertEquals("Incorrect printout", " utils.TraceTester.testAppend" + myNewline + "This is appended", myOut.toString());
		myOut.reset();

	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.Trace#print(org.testtoolinterfaces.utils.Trace.LEVEL, java.lang.Throwable)}.
	 */
	@Test
	public void testPrintThrowable()
	{
		Trace traceObj = Trace.getInstance();
		traceObj.setEnabled(true);
		traceObj.setDepth(3);
		traceObj.setTraceClass("org.testtoolinterfaces.utils.TraceTester");
		traceObj.setTraceLevel(Trace.ALL);

		Trace.print( Trace.EXEC, new Exception("Throwable test") );
		String output = myOut.toString();
		int secondNewLinePos = output.indexOf(myNewline, output.indexOf(myNewline)+1);
		String firstTwoLines = output.substring(0, secondNewLinePos);
		myOrigOut.println( myOut.toString() );
		assertEquals("Incorrect printout", " utils.TraceTester.testPrintThrowable" + myNewline + "java.lang.Exception: Throwable test", firstTwoLines);

		myOut.reset();
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.Trace#dumpSettingst()}.
	 */
	@Test
	public void testDumpSettings()
	{
		Trace traceObj = Trace.getInstance();
		traceObj.setEnabled(true);
		traceObj.setDepth(3);
		traceObj.setTraceClass("org.testtoolinterfaces.utils.TraceTester");
		traceObj.setTraceLevel(Trace.SUITE);

		traceObj.dumpSettings();
		myOrigOut.println( myOut.toString() );
		assertEquals( "Incorrect printout", "Enabled:    true" + myNewline + 
											"Depth:      3" + myNewline +
											"TraceClass: org.testtoolinterfaces.utils.TraceTester" + myNewline +
											"TraceLevel: SUITE" + myNewline + 
											"BaseClass:  org.testtoolinterfaces" + myNewline,
					 myOut.toString());

		myOut.reset();
	}
	
	public void testUnknownClass()
	{
		Trace traceObj = Trace.getInstance();
		traceObj.setEnabled(true);
		traceObj.setDepth(3);
		traceObj.setTraceClass("org.testtoolinterfaces.utils.OtherClass");
		traceObj.setTraceLevel(Trace.ALL);

		Trace.print(Trace.GETTER, "method", true);
		myOrigOut.println( myOut.toString() );
		assertEquals( "Incorrect printout", "", myOut.toString());

		myOut.reset();
	}
	
	private void depthTestHelper()
	{
		Trace.println(Trace.EXEC, "depthTestHelper");
		TraceTesterHelper helper = new TraceTesterHelper();
		helper.depthTestHelper1();
	}
}
