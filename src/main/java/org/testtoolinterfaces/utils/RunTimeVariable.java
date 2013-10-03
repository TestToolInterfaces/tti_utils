package org.testtoolinterfaces.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunTimeVariable
{
    private static final Logger LOG = LoggerFactory.getLogger(RunTimeVariable.class);

    private String myName;
	private Class<?> myType;
	private Object myValue;
	
	public RunTimeVariable( String aName, Class<?> aType, Object aValue )
	{
		myName = aName;
		myType = aType;
		myValue = aValue;
	}

	public RunTimeVariable( String aName, Object aValue )
	{
		this( aName, aValue.getClass(), aValue );
	}

	public RunTimeVariable( String aName, Class<?> aType )
	{
		this( aName, aType, null );
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return myName;
	}

	/**
	 * @return the type
	 */
	public Class<?> getType()
	{
		return myType;
	}

	/**
	 * @return the value
	 */
	public Object getValue()
	{
		return myValue;
	}

	public void setValue( Object aValue )
	{
		LOG.trace(Mark.SETTER, "{}", aValue);
//		if ( myType == aValue.getClass() )
//	    {
//			myValue = aValue;
//	    }
		myValue = aValue;
		myType = aValue.getClass();
	}
}
