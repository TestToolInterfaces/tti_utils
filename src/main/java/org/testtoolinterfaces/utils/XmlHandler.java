/**
 * 
 */
package org.testtoolinterfaces.utils;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOG = LoggerFactory.getLogger(XmlHandler.class);

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
		LOG.trace(Mark.CONSTRUCTOR, aTag);

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
		LOG.trace(Mark.CONSTRUCTOR, aTag);

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
		LOG.trace(Mark.UTIL, "{}", aHandler);

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
		LOG.trace(Mark.UTIL, "{}, {}", anElement, aHandler);

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
//		Trace.println(Trace.UTIL, "removeStartElementHandler( " + anElement + " )", true);
		LOG.trace(Mark.UTIL, "{}", anElement);

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
//		Trace.println(Trace.UTIL, "setParentElementHandler( " + aHandler.toString() + " )", true);
		LOG.trace(Mark.UTIL, "{}", aHandler);
		myParentElementHandler = aHandler;
	}
	
    // SAX calls this method when it encounters an element. This can be the "own" element or a child element
	@Override
	final public void startElement( String aNamespaceURI,
	                                String aLocalName,
	                                String aQualifiedName,
	                                Attributes anAtt) throws SAXException
	{
//		Trace.println(Trace.UTIL, "startElement( " + aQualifiedName + " )", true);
		LOG.trace(Mark.UTIL, "{}, {}, {}, {}", aNamespaceURI, aLocalName, aQualifiedName, anAtt);

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
//		Trace.println(Trace.UTIL);
		LOG.trace(Mark.UTIL, "{}, {}, {}", ch, start, length);

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
//		Trace.println(Trace.UTIL, "endElement( " + aQualifiedName + " )", true);
		LOG.trace(Mark.UTIL, "{}, {}, {}", aNamespaceURI, aLocalName, aQualifiedName);
    	
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
			System.out.println(e.getMessage());
			throw new SAXException( e );
		}
    }
    
	/**
	 * @return the XmlReader
	 */
	public XMLReader getXmlReader()
	{
		LOG.trace(Mark.GETTER, "");
		return myXmlReader;
	}
	
	/**
	 * @return the start tag fr this handler
	 */
	public String getStartElement()
	{
//		Trace.println(Trace.GETTER);
		LOG.trace(Mark.GETTER, "");
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
//		Trace.println(Trace.UTIL);
		LOG.trace(Mark.UTIL, "");
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
    
	public static XMLReader getNewXmlReader() throws TTIException
    {
//		Trace.println(Trace.UTIL, "getNewXmlReader()", true );
		LOG.trace(Mark.UTIL, "");

		// create a parser
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(false);
		SAXParser saxParser;

		try
		{
			saxParser = spf.newSAXParser();
			return saxParser.getXMLReader();
		}
		catch (ParserConfigurationException pce)
		{
	       	throw new TTIException( pce.getLocalizedMessage(), pce );
		}
		catch (SAXException saxe)
		{
	       	throw new TTIException( saxe.getLocalizedMessage(), saxe );
		}
    }

	public void parse( XMLReader xmlReader, File anXmlFile ) throws TTIException
    {
//		Trace.println(Trace.UTIL, "parse()", true );
		LOG.trace(Mark.UTIL, "");

        // assign the handler to the parser
        xmlReader.setContentHandler(this);

        // parse the document
        try
        {
			xmlReader.parse( anXmlFile.getAbsolutePath() );
		}
        catch (IOException ioe)
		{
        	throw new TTIException( ioe.getLocalizedMessage(), ioe );
		}
        catch (SAXException saxe)
        {
        	throw new TTIException( saxe.getLocalizedMessage(), saxe );
		}
    }
}
