package org.testtoolinterfaces.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StreamGobblerTester extends TestCase
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
		System.out.println("==========================================================================");
		System.out.println(this.getName() + ":");
	}

	/**
	 * Test method to test regular input to stdout.
	 * 
	 */
	@Test
	public void testInputToStdOut()
	{
		String inputString = "Input String\nSecond Line\n";
		ByteArrayInputStream input = new ByteArrayInputStream( inputString.getBytes() );
		
		StreamGobbler strGobbler = new StreamGobbler( input, System.out );
		strGobbler.start();
		
		sleep(2);
	}

	@Test
	public void testInputToString()
	{
		String inputString = "Input String\nSecond Line\n";
		ByteArrayInputStream input = new ByteArrayInputStream( inputString.getBytes() );
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PrintStream outputStream  = new PrintStream(output, true);
		StreamGobbler strGobbler = new StreamGobbler( input, outputStream );  

		strGobbler.start();
		sleep(2);
		Assert.assertEquals("Output size", 27, output.size());
		Assert.assertEquals("First Line", inputString, output.toString());
	}

	//	/**
//	 * Test method to test regular input to a file.
//	 * 
//	 */
//	@Test
//	public void testInputToFile()
//	{
//		RunTimeData rtData = new RunTimeData();
//		
//		RunTimeVariable rtVar = new RunTimeVariable("testvar", "substituted variable");
//		rtData.add(rtVar);
//		
//		String testString = "This string has a {testvar} in the middle.";
//		String returnedString = rtData.substituteVars(testString);
//		
//    	Assert.assertEquals( "Incorrect substitution",
//    			"This string has a substituted variable in the middle.",
//    			returnedString );
//	}

	private void sleep( long aTime )
	{
		try
		{
			Thread.sleep( aTime );
		}
		catch (InterruptedException e)
		{
			throw new Error( e );
		}
	}
}
