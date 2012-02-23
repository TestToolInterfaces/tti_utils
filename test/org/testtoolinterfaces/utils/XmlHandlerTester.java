package org.testtoolinterfaces.utils;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;
import org.testtoolinterfaces.utils.helper.SubXmlHandler;
import org.xml.sax.XMLReader;

public class XmlHandlerTester extends TestCase
{
	XMLReader myXmlReader;
	
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
	}

	/**
	 * Test Cases
	 */
	public void testCase_generalChecks()
	{
		SubXmlHandler handler = new SubXmlHandler( myXmlReader, "string" );
		Assert.assertEquals("Incorrect Reader", myXmlReader, handler.getXmlReader());
		Assert.assertEquals("Incorrect toString", "string", handler.toString());
	}

	/**
	 * Test Cases
	 */
	public void testCase_readString_inline()
	{
		SubXmlHandler handler = new SubXmlHandler( myXmlReader, "start" );
		parseFile("xmlHandler_start.xml", handler);
		Assert.assertEquals("Incorrect tag", "start", handler.getStartElement());
		Assert.assertEquals("Incorrect attribute", "a1", handler.getAttribute());
		Assert.assertEquals("Incorrect sub-tag", "Some text here.", handler.getTag());
		Assert.assertTrue("Incorrect sub-boolean", handler.getBool());
	}

	/**
	 * Test Cases
	 */
	public void testCase_removeElements()
	{
		SubXmlHandler handler = new SubXmlHandler( myXmlReader, "start" );
		handler.removeElementHandler(SubXmlHandler.BOOL_ELEMENT);
		try
		{
			parseFile_withExc("xmlHandler_start.xml", handler);
			fail( "No Exception thrown " );
		}
		catch (Exception e)
		{
			Assert.assertEquals("Incorrect exception", "No (child) XmlHandler defined for bool", e.getMessage());
		}
	}

	private void parseFile(String aFileName, XmlHandler aHandler)
	{
		try
		{
			parseFile_withExc(aFileName, aHandler);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail( "Exception thrown " + e.getMessage() );
		}
	}

	private void parseFile_withExc(String aFileName, XmlHandler aHandler) throws Exception
	{
		File testXmlFilesDir = new File ( "test" + File.separator +
		                                  "org" + File.separator +
		                                  "testtoolinterfaces" + File.separator +
		                                  "utils" + File.separator +
		                                  "testXmlFiles" );

		File xmlTestFile = new File ( testXmlFilesDir, aFileName);

		// parse the document
		myXmlReader.setContentHandler(aHandler);
		myXmlReader.parse(xmlTestFile.getAbsolutePath());
	}
}
