package org.testtoolinterfaces.utils.helper;

import org.testtoolinterfaces.utils.GenericTagAndBooleanXmlHandler;
import org.testtoolinterfaces.utils.GenericTagAndStringXmlHandler;
import org.testtoolinterfaces.utils.TTIException;
import org.testtoolinterfaces.utils.XmlHandler;
import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;

/**
 * <{tag} attribute="...">
 *  <tag>
 *    ...
 *  </tag>
 *  <bool>
 *    ...
 *  </bool>
 * </{tag}>
 * 
 * @author Arjan Kranenburg 
 * @see http://www.testtoolinterfaces.org
 * 
 */
public class SubXmlHandler extends XmlHandler
{
	public static final String TAG_ELEMENT = "tag";
	public static final String BOOL_ELEMENT = "bool";

	private static final String ATTRIBUTE_ATTRIBUTE = "attribute";

	// The sub-handlers
    private GenericTagAndStringXmlHandler myTagXmlHandler;
    private GenericTagAndBooleanXmlHandler myBoolXmlHandler;

	// Needed to create the TestStep
	private String myAttribute;
	private String myTagValue;
	private boolean myBoolValue;
	
	/**
	 * Creates the XML Handler
	 * 
	 * @param anXmlReader			The xmlReader
	 */
	public SubXmlHandler( XMLReader anXmlReader, String aStartTag )
	{
		super(anXmlReader, aStartTag);

		myTagXmlHandler = new GenericTagAndStringXmlHandler(anXmlReader, TAG_ELEMENT);
		this.addElementHandler(myTagXmlHandler);

		myBoolXmlHandler = new GenericTagAndBooleanXmlHandler(anXmlReader, BOOL_ELEMENT);
		this.addElementHandler(myBoolXmlHandler);

		reset();
	}

	@Override
    public void processElementAttributes(String aQualifiedName, Attributes att) throws TTIException
    {
    	if (aQualifiedName.equalsIgnoreCase(this.getStartElement()))
    	{
		    for (int i = 0; i < att.getLength(); i++)
		    {
		    	if (att.getQName(i).equalsIgnoreCase(ATTRIBUTE_ATTRIBUTE))
		    	{
		    		myAttribute = att.getValue(i);
    	    	}
		    }
    	}
    }

	@Override
	public void handleStartElement(String aQualifiedName)
	{
    	//nop
	}

	@Override
	public void handleCharacters(String aValue)
	{
		//nop
	}

	@Override
	public void handleEndElement(String aQualifiedName)
	{
		//nop
	}
	
	@Override
	public void handleGoToChildElement(String aQualifiedName)
	{
		//nop
	}

	@Override
	public void handleReturnFromChildElement(String aQualifiedName, XmlHandler aChildXmlHandler)
	{
    	if (aQualifiedName.equalsIgnoreCase(TAG_ELEMENT))
    	{
    		myTagValue  = myTagXmlHandler.getValue();
    		myTagXmlHandler.reset();
    	}
    	else if (aQualifiedName.equalsIgnoreCase(BOOL_ELEMENT))
    	{
    		myBoolValue = myBoolXmlHandler.getBoolean();
    		myBoolXmlHandler.reset();
    	}
		// else nothing (ignored)
	}

	public String getTag()
	{
		return myTagValue;
	}
	
	public boolean getBool()
	{
		return myBoolValue;
	}
	
	public String getAttribute()
	{
		return myAttribute;
	}
	
	@Override
	public void reset()
	{
		myTagValue = "";
		myBoolValue = false;
		myAttribute = "";
	}
}
