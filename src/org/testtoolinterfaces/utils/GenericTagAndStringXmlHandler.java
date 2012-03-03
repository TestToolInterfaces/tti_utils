package org.testtoolinterfaces.utils;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;


/**
 * Handles XML lines like:
 *  <{tag}>
 *    ...
 *  </{tag}>
 * 
 * The Tag-name is configurable and the Text is read and returned as string.
 * 
 * No parameters and no sub-tags are supported.
 * If parameters and/or sub-tags are needed, a specefic XmlHandler must be created.
 * 
 * @author Arjan Kranenburg 
 * 
 */
public class GenericTagAndStringXmlHandler extends XmlHandler
{
	boolean myPreserveWhites;
	private String myValue = "";
	
	/**
	 * Constructor
	 * 
	 * @param anXmlReader		The XMLReader
	 * @param aTag				The XML-element name
	 * @param aPreserveWhites	Flag to indicate if white-spaces must be reserved (default false)
	 */
	public GenericTagAndStringXmlHandler( XMLReader anXmlReader, String aTag, boolean aPreserveWhites )
	{
		super(anXmlReader, aTag);
		Trace.println(Trace.CONSTRUCTOR, "GenericTagAndStringXmlHandler( anXmlreader, " + aTag + " )", true);
		
		myPreserveWhites = aPreserveWhites;
	}

	/**
	 * Constructor
	 * 
	 * @param anXmlReader	The XMLReader
	 * @param aTag			The XML-element name
	 */
	public GenericTagAndStringXmlHandler( XMLReader anXmlReader, String aTag )
	{
		this( anXmlReader, aTag, false );
	}

	@Override
	public void handleStartElement(String aQualifiedName) throws TTIException
	{
   		//nop;
    }

	@Override
	public void handleCharacters(String aValue) throws TTIException
	{
		Trace.println(Trace.SUITE, "handleCharacters( " + aValue + " )", true);
		String value = aValue;
		if ( ! myPreserveWhites )
		{
			value = removeExtraWhites( aValue );
		}
		myValue += value;
	}

	@Override
	public void handleEndElement(String aQualifiedName) throws TTIException
	{
		//nop
    }

	@Override
    public void processElementAttributes(String aQualifiedName, Attributes att) throws TTIException
    {
		//nop. Attributes will be ignored.
    }

	@Override
	public void handleGoToChildElement(String aQualifiedName) throws TTIException
	{
		//nop
	}

	@Override
	public void handleReturnFromChildElement(String aQualifiedName, XmlHandler aChildXmlHandler) throws TTIException
	{
		//nop
	}
	
	/**
	 * @return the value read by this handler
	 */
	public String getValue()
	{
		Trace.println(Trace.GETTER);
		String value = myValue;
		if ( ! myPreserveWhites )
		{
			value = value.trim();
		}
		
		return value;
	}

	protected String removeExtraWhites( String aString )
	{
		String string_trimmed = aString.replaceAll("\n", " ");
		string_trimmed = string_trimmed.replaceAll("\t", " ");
		string_trimmed = string_trimmed.replaceAll(" +", " ");
		return string_trimmed;
	}
	
	@Override
	public void reset( )
	{
		Trace.println(Trace.UTIL);
		myValue = "";
	}

	@Override
	public String toString()
	{
		return getStartElement() + (this.myValue == "" ? "" : "=" + this.myValue);		
	}
}
