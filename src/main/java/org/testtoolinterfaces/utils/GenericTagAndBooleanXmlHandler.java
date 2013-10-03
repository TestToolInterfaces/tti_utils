package org.testtoolinterfaces.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOG = LoggerFactory.getLogger(GenericTagAndBooleanXmlHandler.class);

    public GenericTagAndBooleanXmlHandler( XMLReader anXmlReader, String aTag )
	{
		super(anXmlReader, aTag);
		LOG.trace(Mark.CONSTRUCTOR, "{}, {}, {}", anXmlReader, aTag);
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
