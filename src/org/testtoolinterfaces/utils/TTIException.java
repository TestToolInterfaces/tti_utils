package org.testtoolinterfaces.utils;

/**
 * 
 * @author Arjan
 */
public class TTIException extends Exception
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1328189529445640127L;
	private String myId;
	private int mySequenceNr;

	/**
	 * @param aMessage
	 */
	public TTIException(String aMessage)
	{
		this( aMessage, "Unknown", 0 );
	}

	/**
	 * @param aMessage
	 * @param anId
	 */
	public TTIException( String aMessage, String anId )
	{
		this( aMessage, anId, 0 );
	}

	/**
	 * @param aMessage
	 * @param anId
	 * @param aSequenceNr
	 */
	public TTIException( String aMessage, String anId, int aSequenceNr )
	{
		super( aMessage );
		myId = anId;
		mySequenceNr = aSequenceNr;
	}

	/**
	 * @param aMessage
	 * @param anException
	 */
	public TTIException(String aMessage, Exception anException)
	{
		this( aMessage, "Unknown", 0, anException );
	}

	/**
	 * @param aMessage
	 * @param anId
	 * @param anException
	 */
	public TTIException( String aMessage, String anId, Exception anException )
	{
		this( aMessage, anId, 0, anException );
	}

	/**
	 * @param aMessage
	 * @param anId
	 * @param aSequenceNr
	 * @param anException
	 */
	public TTIException( String aMessage, String anId, int aSequenceNr, Exception anException )
	{
		super( aMessage, anException );
		myId = anId;
		mySequenceNr = aSequenceNr;
	}

	/**
	 * @return the myId
	 */
	public String getId()
	{
		return myId;
	}

	/**
	 * @return the mySequence
	 */
	public int getSequence()
	{
		return mySequenceNr;
	}
}