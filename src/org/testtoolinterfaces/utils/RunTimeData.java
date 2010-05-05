package org.testtoolinterfaces.utils;

//import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;

public class RunTimeData extends Hashtable<String, RunTimeVariable>
{
	private static final long	serialVersionUID	= -896399275664941954L;

	private RunTimeData myParentScope;
	
	public RunTimeData( RunTimeData aParentScope )
	{
		myParentScope = aParentScope;
	}
	
	public RunTimeData()
	{
		myParentScope = null;
	}

	public RunTimeVariable get( String aName )
	{
		RunTimeVariable var = super.get( aName );
		if ( var == null )
		{
			if ( myParentScope != null )
			{
				return myParentScope.get(aName);
			}
		}
		
		return var;
	}

	/**
	 * @return the type
	 */
	public Class<?> getType( String aName )
	{
		RunTimeVariable var = super.get( aName );
		if ( var == null )
		{
			if ( myParentScope != null )
			{
				var = myParentScope.get(aName);
			}
		}

		if ( var != null )
		{
			return var.getType();
		}
		return null;
	}

	/**
	 * @return the value
	 */
	public Object getValue( String aName )
	{
		RunTimeVariable var = super.get( aName );
		if ( var == null )
		{
			if ( myParentScope != null )
			{
				var = myParentScope.get(aName);
			}
		}

		if ( var != null )
		{
			return var.getValue();
		}
		return null;
	}

//	/**
//	 * @return the value as a File object
//	 *         null if it doesn't exist or is not a File
//	 */
//	public File getValueAsFile( String aName )
//	{
//		File varFile = null;
//		if ( this.getType( aName ).equals(File.class) )
//		{
//			varFile = (File) this.getValue(aName);
//		}
//		return varFile;
//	}
//
//	/**
//	 * @return the value as a String object
//	 *         null if it doesn't exist or is not a String
//	 */
//	public String getValueAsString( String aName )
//	{
//		String varString = null;
//		if ( this.getType( aName ).equals(String.class) )
//		{
//			varString = (String) this.getValue(aName);
//		}
//		return varString;
//	}

	/**
	 * @return the value as a <Type> object
	 *         null if it doesn't exist or is not a <Type>
	 */
	@SuppressWarnings("unchecked")
	public <Type> Type getValueAs(Class<Type> aType, String aName)
	{
		Type varOfType = null;
		if ( this.getType( aName ).equals(aType.getClass()) )
		{
			varOfType = (Type) this.getValue(aName);
		}
		return varOfType;
	}

	public void add( RunTimeVariable aVariable )
	{
		this.put(aVariable.getName(), aVariable);
	}
	
	public void print()
	{
	    for (Enumeration<String> keys = this.keys(); keys.hasMoreElements();)
	    {
	    	String key = keys.nextElement();
	    	System.out.println( "(" + this.get(key).getType().getCanonicalName() + ")" + key );
	    }
	}
}
