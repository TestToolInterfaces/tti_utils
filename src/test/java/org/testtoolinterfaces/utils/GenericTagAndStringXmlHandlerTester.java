package org.testtoolinterfaces.utils;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;
import org.xml.sax.XMLReader;

public class GenericTagAndStringXmlHandlerTester extends TestCase
{
	XMLReader myXmlReader;
	GenericTagAndStringXmlHandler myHandler_removeWhites;
    GenericTagAndStringXmlHandler myHandler_preserveWhites;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		System.out.println("==========================================================================");
		System.out.println(this.getName() + ":");

		if ( myXmlReader == null )
		{
			SAXParserFactory spf = SAXParserFactory.newInstance();
	        spf.setNamespaceAware(false);

	        SAXParser saxParser = spf.newSAXParser();
	        myXmlReader = saxParser.getXMLReader();
		}

		if ( myHandler_removeWhites == null )
		{
			// create a handler
	        myHandler_removeWhites = new GenericTagAndStringXmlHandler( myXmlReader, "string" );
		}
		if ( myHandler_preserveWhites == null )
		{
			// create a handler
	        myHandler_preserveWhites = new GenericTagAndStringXmlHandler( myXmlReader, "string", true );
		}
	}

	/**
	 * Test Cases
	 */
	public void testCase_readString_inline()
	{
		parseFile("string_inline.xml", myHandler_removeWhites);
		Assert.assertEquals("Incorrect tag", "string", myHandler_removeWhites.getStartElement());
		Assert.assertEquals("Incorrect value", "value", myHandler_removeWhites.getValue());

		myHandler_removeWhites.reset();

		parseFile("string_inline.xml", myHandler_removeWhites);
		Assert.assertEquals("Incorrect value", "value", myHandler_removeWhites.getValue());
	}
		
	/**
	 * Test Cases
	 */
	public void testCase_readString_wrongTag()
	{
		File testXmlFilesDir = new File ( "test" + File.separator +
		                                  "org" + File.separator +
		                                  "testtoolinterfaces" + File.separator +
		                                  "utils" + File.separator +
		                                  "testXmlFiles" );

		File xmlTestFile = new File ( testXmlFilesDir, "string_wrongTag.xml");

		// parse the document
		try
		{
			myXmlReader.setContentHandler(myHandler_removeWhites);
			myXmlReader.parse(xmlTestFile.getAbsolutePath());
			fail( "No Exception thrown" );
		}
		catch (Exception e)
		{
			Assert.assertEquals( "Incorrect exception",
			                     "No (child) XmlHandler defined for wrong",
			                     e.getMessage() );
		}
		Assert.assertEquals("Incorrect value", "", myHandler_removeWhites.getValue());

		myHandler_removeWhites.reset();
	}
	
	/**
	 * Test Cases
	 */
	public void testCase_readString_multiline_removeWhites()
	{
		parseFile("string_multiline.xml", myHandler_removeWhites);
		Assert.assertEquals("Incorrect value", "This is a multi line sentence.", myHandler_removeWhites.getValue());

		myHandler_removeWhites.reset();
	}
	
	/**
	 * Test Cases
	 */
	public void testCase_readString_extraWhitespaces()
	{
		parseFile("string_extraWhites.xml", myHandler_removeWhites);
		Assert.assertEquals( "Incorrect value",
		                     "string over more lines and with extra white-spaces.",
		                     myHandler_removeWhites.getValue());

		myHandler_removeWhites.reset();
	}
	
	/**
	 * Test Cases
	 */
	public void testCase_readString_multiline_preserveWhites()
	{
		parseFile("string_multiline.xml", myHandler_preserveWhites);
		Assert.assertEquals("Incorrect value", "\nThis is a multi line \nsentence.", myHandler_preserveWhites.getValue());

		myHandler_preserveWhites.reset();
	}
	
	/**
	 * Test Cases
	 */
	public void testCase_readString_extraWhitespaces_preserveWhites()
	{
		parseFile("string_extraWhites.xml", myHandler_preserveWhites);
		Assert.assertEquals( "Incorrect value",
		                     "\n\t    string\n\t    \tover\n\t   more lines\n\t   \tand with extra white-spaces.        \n\t             ",
		                     myHandler_preserveWhites.getValue());

		myHandler_preserveWhites.reset();
	}
	
	private void parseFile(String aFileName, GenericTagAndStringXmlHandler aHandler)
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
			myXmlReader.setContentHandler(aHandler);
			myXmlReader.parse(xmlTestFile.getAbsolutePath());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail( "Exception thrown " + e.getMessage() );
		}
	}
}
