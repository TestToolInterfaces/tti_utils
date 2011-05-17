package org.testtoolinterfaces.utils;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.testtoolinterfaces.utils.RunTimeVariable;

public class RunTimeVariableTester
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
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeValue#getName()}.
	 * 
	 */
	@Test
	public void testGetName()
	{
		RunTimeVariable rtVar = new RunTimeVariable( "name", "value" );
		
    	Assert.assertEquals( "Incorrect name",
    			"name",
    			rtVar.getName() );
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeValue#getValue()}.
	 * 
	 */
	@Test
	public void testGetValue()
	{
		RunTimeVariable rtVar = new RunTimeVariable( "name", "value" );
		
    	Assert.assertEquals( "Incorrect value",
    			"value",
    			rtVar.getValue() );
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeValue#getType()}.
	 * 
	 */
	@Test
	public void testGetType()
	{
		RunTimeVariable rtVar = new RunTimeVariable( "name", "value" );
		
    	Assert.assertEquals( "Incorrect type",
    			String.class,
    			rtVar.getType() );
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeValue#RunTimeValue( java.lang.String, class, java.lang.Object )}.
	 * 
	 */
	@Test
	public void testConstructor_3args()
	{
		RunTimeVariable rtVar = new RunTimeVariable( "name", RunTimeVariable.class, "value" );
		
    	Assert.assertEquals( "Incorrect name",
    			"name",
    			rtVar.getName() );

    	Assert.assertEquals( "Incorrect value",
    			"value",
    			rtVar.getValue() );

    	Assert.assertEquals( "Incorrect type",
    			RunTimeVariable.class,
    			rtVar.getType() );
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeValue#RunTimeValue( java.lang.String, class )}.
	 * 
	 */
	@Test
	public void testConstructor_name_type()
	{
		RunTimeVariable rtVar = new RunTimeVariable( "name", RunTimeVariable.class );
		
    	Assert.assertEquals( "Incorrect name",
    			"name",
    			rtVar.getName() );

    	Assert.assertEquals( "Incorrect value",
    			null,
    			rtVar.getValue() );

    	Assert.assertEquals( "Incorrect type",
    			RunTimeVariable.class,
    			rtVar.getType() );
	}

	/**
	 * Test method for {@link org.testtoolinterfaces.utils.RunTimeValue#setValue()}.
	 * 
	 */
	@Test
	public void testSetValue()
	{
		RunTimeVariable rtVar = new RunTimeVariable( "name", "value" );

		rtVar.setValue("other value");
    	Assert.assertEquals( "Incorrect value",
    			"other value",
    			rtVar.getValue() );

		RunTimeVariable rtVar2 = new RunTimeVariable( "name", RunTimeVariable.class );
		rtVar2.setValue("Another value");
		
    	Assert.assertEquals( "Incorrect value",
    			"Another value",
    			rtVar2.getValue() );

    	Assert.assertEquals( "Incorrect type",
    			String.class,
    			rtVar2.getType() );
	}
}
