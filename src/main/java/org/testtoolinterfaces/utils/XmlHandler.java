/**
 * 
 */
package org.testtoolinterfaces.utils;

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
	/**
     * Called when a start element must be handled. 
     * @note processElementAttributes() and handleStartElement of child element is already called.
     * 
	 * @param aQualifiedName	The element name
	 * @throws TTIException
	 */
	public abstract void handleStartElement( String aQualifiedName ) throws TTIException;
	
    /**
     * Called to process all the attributes of an element
     * 
     * @param aQualifiedName	The element name
     * @param anAtt				A list of attributes
     * @throws TTIException
     */
    public abstract void processElementAttributes( String aQualifiedName, Attributes anAtt ) throws TTIException;

    /**
     * Called just before a child element is handled
     * 
     * @param aQualifiedName	the name of the child element
     * @throws TTIException
     */
    public abstract void handleGoToChildElement( String aQualifiedName ) throws TTIException;

    /**
     * Called to handle the characters, i.e the value of a simple element
     * 
     * @param aValue			The value of a simple element
     * @throws TTIException
     */
    public abstract void handleCharacters( String aValue ) throws TTIException;

    /**
     * Called to handle the end element
     * 
     * @param aQualifiedName	The name of the element
     * @throws TTIException
     */
    public abstract void handleEndElement( String aQualifiedName ) throws TTIException;

    /**
     * Called right after a child element is handled
     * 
     * @param aQualifiedName	the name of the child element
     * @param aChildXmlHandler	the child XmlHandler
     * @throws TTIException
     */
    public abstract void handleReturnFromChildElement( String aQualifiedName, XmlHandler aChildXmlHandler ) throws TTIException;

	private String myStartElement = "";
//	private String myValue = "";
	private boolean isStartElementHandled = false;

	private Hashtable<String, XmlHandler> myChildElementHandlers;
    private XmlHandler myParentElementHandler = null;
	private XMLReader myXmlReader;
	
	private Locator myLocator;

	/**
	 * @param anXmlReader The XML Reader
	 * @param aTag The XML element used to indicate this XML part
	 */
	public XmlHandler( XMLReader anXmlReader, String aTag )
	{
		Trace.println(Trace.CONSTRUCTOR);

    	myChildElementHandlers = new Hashtable<String, XmlHandler>();
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

    	myChildElementHandlers = new Hashtable<String, XmlHandler>();

		myStartElement = aTag;
	}


	/**
	 *  Adds an Element Handler
	 *  
	 *  @param aHandler	   The XML Handler that must handle all data after and including the start tag.
	 */
	public void addElementHandler( XmlHandler aHandler )
	{
		Trace.println(Trace.UTIL, "addElementHandler( " + aHandler.toString() + " )", true);

    	myChildElementHandlers.put( aHandler.getStartElement().toLowerCase(), aHandler );
    	aHandler.setParentElementHandler(this);
	}
	
	@Deprecated
	/**
	 *  Adds an Element Handler for the specified start tag
	 *  
	 *  @param anElement   Specifies the start tag
	 *  @param aHandler	   The XML Handler that must handle all data after and including the start tag.
	 */
	public void addElementHandler( String anElement, XmlHandler aHandler )
	{
		Trace.println(Trace.UTIL, "addElementHandler( " + aHandler.toString() + " )", true);

    	myChildElementHandlers.put( anElement.toLowerCase(), aHandler );
    	aHandler.setParentElementHandler(this);
	}
	
	/**
	 * Removes an element handler for a specified start tag
	 * 
	 * @param anElement Specifies the start tag
	 */
	public void removeElementHandler( String anElement )
	{
		Trace.println(Trace.UTIL, "removeStartElementHandler( " + anElement + " )", true);

    	myChildElementHandlers.remove( anElement.toLowerCase() );
	}
	
	/**
	 *  Sets the Element Handler when the end-tag is reached
	 *  
	 *  @param aHandler	   The XML Handler that must handle all data after the end tag.
	 *                     In general that will be the parent Handler, but could in theory be a different Handler.
	 */
	public void setParentElementHandler( XmlHandler aHandler )
	{
		Trace.println(Trace.UTIL, "setParentElementHandler( " + aHandler.toString() + " )", true);
		myParentElementHandler = aHandler;
	}
	
    // SAX calls this method when it encounters an element. This can be the "own" element or a child element
	@Override
	final public void startElement( String aNamespaceURI,
	                                String aLocalName,
	                                String aQualifiedName,
	                                Attributes anAtt) throws SAXException
	{
		Trace.println(Trace.UTIL, "startElement( " + aQualifiedName + " )", true);

		try
		{
			if ( this.myStartElement.equalsIgnoreCase(aQualifiedName)
				 && ! isStartElementHandled )
			{
				this.handleStartElement( aQualifiedName );
				if (anAtt.getLength() != 0)
				{
					this.processElementAttributes(aQualifiedName, anAtt);
				}
				
				isStartElementHandled = true;
			}
			else
			{
				this.handleGoToChildElement(aQualifiedName);

				if ( myChildElementHandlers.containsKey(aQualifiedName.toLowerCase()) )
				{
					XmlHandler childHandler = myChildElementHandlers.get(aQualifiedName.toLowerCase());
					myXmlReader.setContentHandler(childHandler);
					childHandler.setDocumentLocator(this.getLocator());
					
					childHandler.startElement(aNamespaceURI, aLocalName, aQualifiedName, anAtt);
				}
				else
				{
					throw new SAXException( "No (child) XmlHandler defined for " + aQualifiedName );
				}
			}
		}
		catch (TTIException e)
		{
			throw new SAXException( e );
		}
	}

    // SAX calls this method to handle the character data
	@Override
    final public void characters(char ch[], int start, int length)
            throws SAXException
    {
		Trace.println(Trace.UTIL);

		if ( isStartElementHandled )
		{
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
    }
    
    // SAX call this method when it encounters an end tag
	@Override
    final public void endElement( String aNamespaceURI,
                                  String aLocalName,
                                  String aQualifiedName )
            throws SAXException
    {
		Trace.println(Trace.UTIL, "endElement( " + aQualifiedName + " )", true);
    	
		isStartElementHandled = false;
    	try
		{
			this.handleEndElement(aQualifiedName);

			if( myParentElementHandler != null )
			{
				myXmlReader.setContentHandler( myParentElementHandler );
				myParentElementHandler.handleReturnFromChildElement(aQualifiedName, this);
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
	
//	/**
//	 * @return the value read by this handler
//	 */
//	public String getValue()
//	{
//		Trace.println(Trace.GETTER);
//		return myValue;
//	}
//
//	/**
//	 * Sets the string value for this handler
//	 * 
//	 * @param aValue
//	 */
//	protected void setValue( String aValue )
//	{
//		Trace.println(Trace.SETTER, "setValue( " + aValue + " )", true);
//		myValue = aValue;
//	}
//	
//	/**
//	 * Appends the string value for this handler to the current value
//	 * 
//	 * @param aValue
//	 */
//	protected void appendValue( String aValue )
//	{
//		Trace.println(Trace.UTIL, "appendValue( " + aValue + " )", true);
//		myValue += aValue;
//	}

	/**
	 * Resets the value read by this handler to the empty string
	 */
	public void reset( )
	{
		Trace.println(Trace.UTIL);
		// Nop
	}

	@Override
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

    @Override
	public String toString()
	{
		return this.myStartElement;
	}
}
