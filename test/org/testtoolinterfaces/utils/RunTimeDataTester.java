package org.testtoolinterfaces.utils;

import java.io.File;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.testtoolinterfaces.utils.RunTimeData;
import org.testtoolinterfaces.utils.RunTimeVariable;

public class RunTimeDataTester
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
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeData#substituteVars(java.lang.String)}.
	 * 
	 */
	@Test
	public void testSubstituteVarInBeginning()
	{
		RunTimeData rtData = new RunTimeData();
		
		RunTimeVariable rtVar = new RunTimeVariable("testvar", "substituted variable");
		rtData.add(rtVar);
		
		String testString = "{testvar} is the start of this string.";
		String returnedString = rtData.substituteVars(testString);
		
    	Assert.assertEquals( "Incorrect substitution",
    			"substituted variable is the start of this string.",
    			returnedString );
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeData#substituteVars(java.lang.String)}.
	 * 
	 */
	@Test
	public void testSubstituteVarInMiddle()
	{
		RunTimeData rtData = new RunTimeData();
		
		RunTimeVariable rtVar = new RunTimeVariable("testvar", "substituted variable");
		rtData.add(rtVar);
		
		String testString = "This string has a {testvar} in the middle.";
		String returnedString = rtData.substituteVars(testString);
		
    	Assert.assertEquals( "Incorrect substitution",
    			"This string has a substituted variable in the middle.",
    			returnedString );
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeData#substituteVars(java.lang.String)}.
	 * 
	 */
	@Test
	public void testSubstituteVarInEnd()
	{
		RunTimeData rtData = new RunTimeData();
		
		RunTimeVariable rtVar = new RunTimeVariable("testvar", "substituted variable");
		rtData.add(rtVar);
		
		String testString = "This string ends with a {testvar}";
		String returnedString = rtData.substituteVars(testString);
		
    	Assert.assertEquals( "Incorrect substitution",
    			"This string ends with a substituted variable",
    			returnedString );
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeData#substituteVars(java.lang.String)}.
	 * 
	 */
	@Test
	public void testSubstituteVarNoClosingParenthesis()
	{
		RunTimeData rtData = new RunTimeData();
		
		RunTimeVariable rtVar = new RunTimeVariable("testvar", "substituted variable");
		rtData.add(rtVar);
		
		String testString = "This string has a {testvar without a closing parenthesis";
		String returnedString = rtData.substituteVars(testString);
		
    	Assert.assertEquals( "Incorrect substitution",
    			"This string has a {testvar without a closing parenthesis",
    			returnedString );
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeData#substituteVars(java.lang.String)}.
	 * 
	 */
	@Test
	public void testSubstituteVarNoOpeningParenthesis()
	{
		RunTimeData rtData = new RunTimeData();
		
		RunTimeVariable rtVar = new RunTimeVariable("testvar", "substituted variable");
		rtData.add(rtVar);
		
		String testString = "This string has a testvar} without an opening parenthesis";
		String returnedString = rtData.substituteVars(testString);
		
    	Assert.assertEquals( "Incorrect substitution",
    			"This string has a testvar} without an opening parenthesis",
    			returnedString );
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeData#substituteVars(java.lang.String)}.
	 * 
	 */
	@Test
	public void testSubstituteVarWithEscapedParenthesis()
	{
		RunTimeData rtData = new RunTimeData();
		
		RunTimeVariable rtVar = new RunTimeVariable("testvar", "substituted variable");
		rtData.add(rtVar);
		
		String testString = "This string has a \\{testvar} that will not be substituted";
		String returnedString = rtData.substituteVars(testString);
		
    	Assert.assertEquals( "Incorrect substitution",
    			"This string has a {testvar} that will not be substituted",
    			returnedString );
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeData#substituteVars(java.lang.String)}.
	 * 
	 */
	@Test
	public void testSubstituteVarWithEscapedEscapeChar()
	{
		RunTimeData rtData = new RunTimeData();
		
		RunTimeVariable rtVar = new RunTimeVariable("testvar", "substituted variable");
		rtData.add(rtVar);
		
		String testString = "This string has a \\\\{testvar} after a double escape character";
		String returnedString = rtData.substituteVars(testString);
		
    	Assert.assertEquals( "Incorrect substitution",
    			"This string has a \\substituted variable after a double escape character",
    			returnedString );
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeData#substituteVars(java.lang.String)}.
	 * 
	 */
	@Test
	public void testSubstituteVarFileType()
	{
		RunTimeData rtData = new RunTimeData();

		String dirName = File.separator + "substituted" + File.separator + "variable";
		RunTimeVariable rtVar = new RunTimeVariable("testvar", new File( dirName ));
		rtData.add(rtVar);
		
		String testString = "/this/path/has/a{testvar}/and/more";
		String returnedString = rtData.substituteVars(testString);
		
    	Assert.assertEquals( "Incorrect substitution",
    			"/this/path/has/a" + dirName + "/and/more",
    			returnedString );
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeData#get(java.lang.String)}.
	 * 
	 */
	@Test
	public void testGet()
	{
		RunTimeData rtData = new RunTimeData( );

		RunTimeVariable rtVar = new RunTimeVariable("testvar", "value");
		rtData.add(rtVar);
		
    	Assert.assertEquals( "Incorrect value", rtVar, rtData.get("testvar") );
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeData#get(java.lang.String)}.
	 * 
	 */
	@Test
	public void testGetParent()
	{
		RunTimeData parentRtData = new RunTimeData();
		RunTimeData rtData = new RunTimeData( parentRtData );

		RunTimeVariable rtVar = new RunTimeVariable("testvar", "value");
		parentRtData.add(rtVar);
		
    	Assert.assertEquals( "Incorrect value", rtVar, rtData.get("testvar") );
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeData#getValue(java.lang.String)}.
	 * 
	 */
	@Test
	public void testGetValue()
	{
		RunTimeData rtData = new RunTimeData();

		RunTimeVariable rtVar = new RunTimeVariable("testvar", "value");
		rtData.add(rtVar);
		
    	Assert.assertEquals( "Incorrect value", "value", rtData.getValue("testvar") );
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeData#getValue(java.lang.String)}.
	 * 
	 */
	@Test
	public void testGetValueParent()
	{
		RunTimeData parentRtData = new RunTimeData();
		RunTimeData rtData = new RunTimeData( parentRtData );

		RunTimeVariable rtVar = new RunTimeVariable("testvar", "value");
		parentRtData.add(rtVar);
		
    	Assert.assertEquals( "Incorrect value", "value", rtData.getValue("testvar") );
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeData#getValue(java.lang.String)}.
	 * 
	 */
	@Test
	public void testGetValueNone()
	{
		RunTimeData parentRtData = new RunTimeData();
		RunTimeData rtData = new RunTimeData( parentRtData );

		RunTimeVariable rtVar = new RunTimeVariable("testvar", "value");
		parentRtData.add(rtVar);
		
    	Assert.assertEquals( "Incorrect value", null, rtData.getValue("nonExisting") );
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeData#getType(java.lang.String)}.
	 * 
	 */
	@Test
	public void testGetType()
	{
		RunTimeData rtData = new RunTimeData( );

		RunTimeVariable rtVar = new RunTimeVariable("testvar", "value");
		rtData.add(rtVar);
		
    	Assert.assertEquals( "Incorrect value", java.lang.String.class, rtData.getType("testvar") );
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeData#getType(java.lang.String)}.
	 * 
	 */
	@Test
	public void testGetTypeParent()
	{
		RunTimeData parentRtData = new RunTimeData();
		RunTimeData rtData = new RunTimeData( parentRtData );

		RunTimeVariable rtVar = new RunTimeVariable("testvar", "value");
		parentRtData.add(rtVar);
		
    	Assert.assertEquals( "Incorrect value", java.lang.String.class, rtData.getType("testvar") );
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeData#getType(java.lang.String)}.
	 * 
	 */
	@Test
	public void testGetTypeNone()
	{
		RunTimeData parentRtData = new RunTimeData();
		RunTimeData rtData = new RunTimeData( parentRtData );

		RunTimeVariable rtVar = new RunTimeVariable("testvar", "value");
		parentRtData.add(rtVar);
		
    	Assert.assertEquals( "Incorrect value", null, rtData.getType("nonExisting") );
	}
}
