package org.testtoolinterfaces.utils;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;


/**
 * @author Arjan Kranenburg 
 * 
 *  <Tag>
 *    ...
 *  </Tag>
 * 
 * The Tag-name is configurable and the Text is read and returned as string.
 * 
 * No parameters and no sub-tags are supported.
 * If parameters and/or sub-tags are needed, a specefic XmlHandler must be created.
 */
public final class GenericTagAndStringXmlHandler extends XmlHandler
{
	public GenericTagAndStringXmlHandler( XMLReader anXmlReader, String aTag )
	{
		super(anXmlReader, aTag);
		Trace.println(Trace.LEVEL.CONSTRUCTOR, "GenericTagAndStringXmlHandler( anXmlreader, " + aTag + " )", true);
	}

	@Override
	public void handleStartElement(String aQualifiedName)
	{
   		//nop;
    }

	@Override
	public void handleCharacters(String aValue)
	{
		Trace.println(Trace.LEVEL.SUITE, "handleCharacters( " + aValue.trim() + " )", true);
		this.appendValue(aValue.trim());
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
}
