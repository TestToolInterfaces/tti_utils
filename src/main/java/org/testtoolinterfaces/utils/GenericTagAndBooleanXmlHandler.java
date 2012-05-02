package org.testtoolinterfaces.utils;

import org.xml.sax.XMLReader;

/**
 * Handles XML lines like:
 *  <{tag}>true|false</{tag}>
 * 
 * @author Arjan Kranenburg 
 * 
 */
public final class GenericTagAndBooleanXmlHandler extends GenericTagAndStringXmlHandler
{
	public GenericTagAndBooleanXmlHandler( XMLReader anXmlReader, String aTag )
	{
		super(anXmlReader, aTag);
		Trace.println(Trace.CONSTRUCTOR, "GenericTagAndBooleanXmlHandler( anXmlreader, " + aTag + " )", true);
	}

	/**
	 * @return 	The boolean meaning of the value in the XML field, i.e. if
	 * 			the xml field was equal to "true", ignoring case and whitespaces.
	 */
	public boolean getBoolean()
	{
			return Boolean.parseBoolean(getValue());
	}
}
