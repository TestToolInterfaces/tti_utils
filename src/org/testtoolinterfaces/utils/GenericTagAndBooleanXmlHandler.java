package org.testtoolinterfaces.utils;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;

/**
 * Handles XML lines like:
 *  <Tag>true|false</Tag>
 * 
 * @author Arjan Kranenburg 
 * 
 */
public final class GenericTagAndBooleanXmlHandler extends XmlHandler
{
	public GenericTagAndBooleanXmlHandler( XMLReader anXmlReader, String aTag )
	{
		super(anXmlReader, aTag);
		Trace.println(Trace.LEVEL.CONSTRUCTOR, "GenericTagAndBooleanXmlHandler( anXmlreader, " + aTag + " )", true);
	}

	@Override
	public void handleStartElement(String aQualifiedName)
	{
   		//nop;
    }

	@Override
	public void handleCharacters(String aValue)
	{
		if ( aValue.equalsIgnoreCase("true") || aValue.equalsIgnoreCase("false"))
		{
			this.setValue(aValue.toLowerCase());
		}
		else
		{
			Warning.println("Wrong value for " + this.getStartElement() + ": " + aValue);
			Warning.println("Allowed values are: true, false");
		}
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
