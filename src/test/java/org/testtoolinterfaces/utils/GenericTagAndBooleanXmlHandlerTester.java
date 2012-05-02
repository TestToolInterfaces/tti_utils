package org.testtoolinterfaces.utils;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;
import org.xml.sax.XMLReader;

public class GenericTagAndBooleanXmlHandlerTester extends TestCase
{
	GenericTagAndBooleanXmlHandler myGenericTagAndBooleanXmlHandler;
	XMLReader myXmlReader;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		System.out.println("==========================================================================");
		System.out.println(this.getName() + ":");

		if ( myGenericTagAndBooleanXmlHandler == null )
		{
			SAXParserFactory spf = SAXParserFactory.newInstance();
	        spf.setNamespaceAware(false);

	        SAXParser saxParser = spf.newSAXParser();
	        myXmlReader = saxParser.getXMLReader();

			// create a handler
	        myGenericTagAndBooleanXmlHandler = new GenericTagAndBooleanXmlHandler( myXmlReader, "boolean" );

	        // assign the handler to the parser
			myXmlReader.setContentHandler(myGenericTagAndBooleanXmlHandler);
		}

	}

	/**
	 * Test Cases
	 */
	public void testCase_readBoolean_true()
	{
		parseFile("boolean_true.xml");
		Assert.assertEquals("Incorrect value", "true", myGenericTagAndBooleanXmlHandler.getValue());
    	Assert.assertTrue("Incorrect boolean", myGenericTagAndBooleanXmlHandler.getBoolean());

		myGenericTagAndBooleanXmlHandler.reset();
	}
		
	/**
	 * Test Cases
	 */
	public void testCase_readBoolean_false()
	{
		parseFile("boolean_false.xml");
		Assert.assertEquals("Incorrect value", "false", myGenericTagAndBooleanXmlHandler.getValue());
    	Assert.assertFalse("Incorrect boolean", myGenericTagAndBooleanXmlHandler.getBoolean());

		myGenericTagAndBooleanXmlHandler.reset();
	}
	
	/**
	 * Test Cases
	 */
	public void testCase_readBoolean_invalid()
	{
		parseFile("boolean_invalid.xml");
		Assert.assertEquals("Incorrect value", "OK", myGenericTagAndBooleanXmlHandler.getValue());
    	Assert.assertFalse("Incorrect boolean", myGenericTagAndBooleanXmlHandler.getBoolean());

		myGenericTagAndBooleanXmlHandler.reset();
	}
	
	/**
	 * Test Cases
	 */
	public void testCase_readBoolean_extraWhitespaces()
	{
		parseFile("boolean_extraWhites.xml");
		Assert.assertEquals("Incorrect value", "true", myGenericTagAndBooleanXmlHandler.getValue());
    	Assert.assertTrue("Incorrect boolean", myGenericTagAndBooleanXmlHandler.getBoolean());

		myGenericTagAndBooleanXmlHandler.reset();
	}
	
	private void parseFile(String aFileName)
	{
		File testXmlFilesDir = new File ( "test" + File.separator +
		                                  "org" + File.separator +
		                                  "testtoolinterfaces" + File.separator +
		                                  "utils" + File.separator +
		                                  "testXmlFiles" );

		File xmlTestFile = new File ( testXmlFilesDir, aFileName);

		// parse the document
		try
		{
			myXmlReader.parse(xmlTestFile.getAbsolutePath());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail( "Exception thrown " + e.getMessage() );
		}
	}
}
