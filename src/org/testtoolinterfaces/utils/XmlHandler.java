/**
 * 
 */
package org.testtoolinterfaces.utils;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Abstract Base Class for Handling XML Elements
 * 
 * @author Arjan Kranenburg
 */
public abstract class XmlHandler extends DefaultHandler
{
	private String myStartElement = "";
	private String myValue = "";

	public abstract void handleStartElement( String aQualifiedName ) throws TTIException; 
    public abstract void processElementAttributes( String qualifiedName, Attributes att ) throws TTIException;
    public abstract void handleGoToChildElement( String aQualifiedName ) throws TTIException;
    public abstract void handleCharacters( String aValue ) throws TTIException; 
    public abstract void handleEndElement( String aQualifiedName ) throws TTIException;
    public abstract void handleReturnFromChildElement( String aQualifiedName, XmlHandler aChildXmlHandler ) throws TTIException;

	private Hashtable<String, XmlHandler> myStartElementHandlers;
    private Hashtable<String, XmlHandler> myEndElementHandlers;
	private XMLReader myXmlReader;
	
	private Locator myLocator;

	/**
	 * @param anXmlReader The XML Reader
	 * @param aTag The XML element used to indicate this XML part
	 */
	public XmlHandler( XMLReader anXmlReader, String aTag )
	{
		Trace.println(Trace.CONSTRUCTOR);

    	myStartElementHandlers = new Hashtable<String, XmlHandler>();
		myEndElementHandlers = new Hashtable<String, XmlHandler>();
        myXmlReader = anXmlReader;

		myStartElement = aTag;
	}

	/**
	 * @param aTag The XML element used to indicate this XML part
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public XmlHandler( String aTag ) throws ParserConfigurationException, SAXException
	{
		Trace.println(Trace.CONSTRUCTOR);

	    SAXParserFactory spf = SAXParserFactory.newInstance();
	    spf.setNamespaceAware(false);

		SAXParser saxParser = spf.newSAXParser();
		myXmlReader = saxParser.getXMLReader();

    	myStartElementHandlers = new Hashtable<String, XmlHandler>();
		myEndElementHandlers = new Hashtable<String, XmlHandler>();

		myStartElement = aTag;
	}


	/**
	 *  Adds an Element Handler for the specified start tag
	 *  
	 *  @param anElement   Specifies the start tag
	 *  @param aHandler	   The XML Handler that must handle all data after and including the start tag.
	 */
	public void addStartElementHandler( String anElement, XmlHandler aHandler )
	{
		Trace.println(Trace.UTIL, "addStartElementHandler( " + aHandler.toString() + " )", true);

    	myStartElementHandlers.put( anElement, aHandler );
	}
	
	/**
	 * Removes an element handler for a specified start tag
	 * 
	 * @param anElement Sppecifies the start tag
	 */
	public void removeStartElementHandler( String anElement )
	{
		Trace.println(Trace.UTIL, "removeStartElementHandler( " + anElement + " )", true);

    	myStartElementHandlers.remove( anElement );
	}
	
	/**
	 *  Adds an Element Handler for the specified end tag
	 *  
	 *  @param anElement   Specifies the end tag
	 *  @param aHandler	   The XML Handler that must handle all data after the end tag.
	 *                     In general that will be the parent Handler, but could in theory be a different Handler.
	 */
	public void addEndElementHandler( String anElement, XmlHandler aHandler )
	{
		Trace.println(Trace.UTIL, "addEndElementHandler( " + aHandler.toString() + " )", true);

    	myEndElementHandlers.put( anElement, aHandler );
	}
	
	/**
	 * Removes an Element Handler for the specified end tag
	 * 
	 * @param anElement Specifies the end tag
	 */
	public void removeEndElementHandler( String anElement )
	{
		Trace.println(Trace.UTIL, "removeEndElementHandler( " + anElement + " )", true);

    	myEndElementHandlers.remove( anElement );
	}
	
    // SAX calls this method when it encounters an element
	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	final public void startElement(String aNamespaceURI, String aLocalName,
			String aQualifiedName, Attributes anAtt) throws SAXException
	{
		Trace.println(Trace.UTIL, "startElement( " + aQualifiedName + " )", true);

		try
		{
			this.handleGoToChildElement(aQualifiedName);

			XmlHandler aHandler = this;
			for (Enumeration<String> keys = myStartElementHandlers.keys(); keys.hasMoreElements();)
			{
				String key = keys.nextElement();
				if (aQualifiedName.equalsIgnoreCase(key))
				{
					aHandler = myStartElementHandlers.get(key);
				}
			}
	
			myXmlReader.setContentHandler(aHandler);
			aHandler.setDocumentLocator(this.getLocator());
			aHandler.handleStartElement(aQualifiedName);
	
			if (anAtt.getLength() != 0)
			{
				aHandler.processElementAttributes(aQualifiedName, anAtt);
			}
		}
		catch (TTIException e)
		{
			throw new SAXException( e );
		}
	}

    // SAX calls this method to pass in character data
    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
     */
    final public void characters(char ch[], int start, int length)
            throws SAXException
    {
		Trace.println(Trace.UTIL);

    	String aValue = new String(ch, start, length);
    	try
		{
			this.handleCharacters(aValue);
		}
		catch (TTIException e)
		{
			throw new SAXException( e );
		}
    }
    
    // SAX call this method when it encounters an end tag
    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    final public void endElement(String aNamespaceURI,
                           String aLocalName,
                           String aQualifiedName)
            throws SAXException
    {
		Trace.println(Trace.UTIL, "endElement( " + aQualifiedName + " )", true);
    	
    	try
		{
			this.handleEndElement(aQualifiedName);

	    	if (!myEndElementHandlers.isEmpty())
		    {
			    for (Enumeration<String> keys = myEndElementHandlers.keys(); keys.hasMoreElements();)
			    {
			    	String key = keys.nextElement();
			    	if (aQualifiedName.equalsIgnoreCase(key))
			    	{
			            myXmlReader.setContentHandler(myEndElementHandlers.get(key));
			            myEndElementHandlers.get(key).handleReturnFromChildElement(aQualifiedName, this);
			    	}
			    }
		    }
		}
		catch (TTIException e)
		{
			throw new SAXException( e );
		}
    }
    
	/**
	 * @return the XmlReader
	 */
	public XMLReader getXmlReader()
	{
		return myXmlReader;
	}
	
	/**
	 * @return the start tag fr this handler
	 */
	public String getStartElement()
	{
		Trace.println(Trace.GETTER);
		return myStartElement;
	}
	
	/**
	 * @return the value read by this handler
	 */
	public String getValue()
	{
		Trace.println(Trace.GETTER);
		return myValue;
	}

	/**
	 * Sets the string value for this handler
	 * 
	 * @param aValue
	 */
	protected void setValue( String aValue )
	{
		Trace.println(Trace.SETTER, "setValue( " + aValue + " )", true);
		myValue = aValue;
	}
	
	/**
	 * Appends the string value for this handler to the current value
	 * 
	 * @param aValue
	 */
	protected void appendValue( String aValue )
	{
		Trace.println(Trace.UTIL, "appendValue( " + aValue + " )", true);
		myValue += aValue;
	}

	/**
	 * Resets the value read by this handler to the empty string
	 */
	public void reset( )
	{
		Trace.println(Trace.UTIL);
		myValue = "";
	}
	
    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#setDocumentLocator(org.xml.sax.Locator)
     */
    public void setDocumentLocator(Locator aLocator)
    {
        myLocator = aLocator;
    }
    
    /**
     * @return the locator of the XML document
     */
    public Locator getLocator()
    {
    	return myLocator;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return this.myStartElement + (this.myValue == "" ? "" : "=" + this.myValue);		
	}
}
