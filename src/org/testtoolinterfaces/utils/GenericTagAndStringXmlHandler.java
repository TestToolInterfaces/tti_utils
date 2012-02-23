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
	public void handleStartElement(String aQualifiedName)
	{
   		//nop;
    }

	@Override
	public void handleCharacters(String aValue)
	{
		Trace.println(Trace.SUITE, "handleCharacters( " + aValue + " )", true);
		String value = aValue;
		if ( ! myPreserveWhites )
		{
			value = removeExtraWhites( aValue );
		}
		this.appendValue( value );
	}

	@Override
	public void handleEndElement(String aQualifiedName)
	{
		//nop
    }

	@Override
    public void processElementAttributes(String aQualifiedName, Attributes att)
    {
		//nop. Attributes will be ignored.
    }

	@Override
	public void handleGoToChildElement(String aQualifiedName)
	{
		//nop
	}

	@Override
	public void handleReturnFromChildElement(String aQualifiedName, XmlHandler aChildXmlHandler)
	{
		//nop
	}
	
	@Override
	public String getValue()
	{
		Trace.println(Trace.GETTER);
		String value = super.getValue();
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
}
