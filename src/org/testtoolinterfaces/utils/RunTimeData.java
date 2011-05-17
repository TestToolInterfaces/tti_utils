package org.testtoolinterfaces.utils;

import java.io.File;
import java.util.Date;
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

	/**
	 * @return the value as a String object
	 *         null if it doesn't exist or is not a String
	 */
	public String getValueAsString( String aName )
	{
		return getValueAs( String.class, aName );
	}

	/**
	 * @return the value as a File object
	 *         null if it doesn't exist or is not a File
	 */
	public File getValueAsFile( String aName )
	{
		return getValueAs( File.class, aName );
	}

	/**
	 * @return the value as a boolean object
	 *         false if it doesn't exist or is not a Boolean
	 */
	public boolean getValueAsBoolean( String aName )
	{
		boolean varOfType = false;
		Object value = this.getValue(aName);
		if ( value != null && this.getType( aName ).equals( Boolean.class ) )
		{
			varOfType = (Boolean) value;
		}
		return varOfType;
	}

	/**
	 * @return the value as a Date object
	 *         null if it doesn't exist or is not a String
	 */
	public Date getValueAsDate( String aName )
	{
		return getValueAs( Date.class, aName );
	}

	/**
	 * @return the value as a <Type> object
	 *         null if it doesn't exist or is not a <Type>
	 *         In case of Boolean, false is returned in stead of null. Be carefull to use this for booleans
	 */
	@SuppressWarnings("unchecked")
	public <Type> Type getValueAs(Class<Type> aType, String aName)
	{
		Type varOfType = null;
		if ( aType == Boolean.class )
		{
			varOfType = (Type) Boolean.FALSE;
		} 
		else if ( aType == Integer.class )
		{
			varOfType = (Type) new Integer( 0 );
		}

		Object value = this.getValue(aName);
		if ( value != null && this.getType( aName ).equals( aType ) )
		{
			varOfType = (Type) value;
		}
		return varOfType;
	}

	/**
	 * Adds the variable to the RunTimeData.
	 * 
	 * Overwrites the variable if a variable with the same name already exists.
	 * Returns the 'old' variable, if any.
	 * 
	 * @param aVariable
	 */
	public RunTimeVariable add( RunTimeVariable aVariable )
	{
		return this.put(aVariable.getName(), aVariable);
	}
	
	public void print()
	{
	    for (Enumeration<String> keys = this.keys(); keys.hasMoreElements();)
	    {
	    	String key = keys.nextElement();
	    	RunTimeVariable rtVar = this.get(key);
	    	if ( rtVar == null )
	    	{
	    		// This is only possible when rtData.put( key, null ) was used in stead of rtData.add( aVariable )
	    		throw new NullPointerException( "null-object added to RuntimeData" );
	    	}

	    	System.out.print( key + " -> ("
					+ rtVar.getType().getCanonicalName() + ") " );
	    	Object value = rtVar.getValue();
	    	if ( value != null )
	    	{
	    		System.out.println( value.toString() );
	    	}
	    	else
	    	{
	    		System.out.println( "null" );
	    	}
	    }
	}

	/**
	 * Substitutes this string with variables (Strings or file-names) found in this runTimeData.
	 * Variables in the string are indicated by {}, e.g. {user_home}\.pluginsettings,
	 * 
	 * A { that does not have a closing } does not mark the beginning of a variable and is treated as a normal '{'
	 * {} cannot be nested, but variables can consist of other variables. These are substituted as well
	 * \{ is substituted with '{'
	 * \} is substituted with '}'
	 * \\ is substituted with a single '\'
	 * 
	 * @param anOriginalString
	 * @return a String where the variables are substituted
	 */

	public String substituteVars( String anOriginalString )
	{
	    Trace.println(Trace.UTIL, "substituteVars( " + anOriginalString + " )", true);
		StringBuffer returnStrbuf;

		int firstEscape = anOriginalString.indexOf('\\');
		int firstOpeningCurlyBracket = anOriginalString.indexOf('{');

		if ( firstEscape < 0 && firstOpeningCurlyBracket < 0 )
		{
			return anOriginalString;
		}
		
		if ( firstEscape < 0 )
		{
			firstEscape = anOriginalString.length()+1;
		}
		if ( firstOpeningCurlyBracket < 0 )
		{
			firstOpeningCurlyBracket = anOriginalString.length()+1;
		}

		if ( firstEscape < firstOpeningCurlyBracket )
		{
			// We have an escaped character
			returnStrbuf = new StringBuffer( anOriginalString.substring(0, firstEscape) );

			char escapedChar = anOriginalString.toCharArray()[firstEscape+1];
			if ( escapedChar == '\\' || escapedChar == '{' || escapedChar == '}' )
			{
				returnStrbuf.append(escapedChar);
			}
			else
			{
				returnStrbuf.append('\\').append(escapedChar);
			}

			String restString = anOriginalString.substring(firstEscape+2);
			returnStrbuf.append( this.substituteVars(restString) );
		}
		else
		{
			// We have a possible variable
			returnStrbuf = new StringBuffer( anOriginalString.substring(0, firstOpeningCurlyBracket) );

			String remainder = anOriginalString.substring(firstOpeningCurlyBracket+1);
			int firstClosingCurlyBracket = remainder.indexOf('}');
			
			if ( firstClosingCurlyBracket > 0 )
			{
				String varName = remainder.substring(0, firstClosingCurlyBracket);
				String varValue = "";
				Class<?> varType = this.getType(varName);
				if ( varType != null )
				{
					if ( varType.equals(String.class) )
					{
						varValue = this.getValueAs(String.class, varName);
					}
					else if ( varType.equals(File.class) )
					{
						varValue = this.getValueAs(File.class, varName).getPath();
					}

					returnStrbuf.append( varValue );

					String restString = remainder.substring(firstClosingCurlyBracket+1);
					returnStrbuf.append( this.substituteVars(restString) );
				}
				else
				{
					returnStrbuf.append( '{' ).append( this.substituteVars(remainder) );
				}
			}
			else
			{
				returnStrbuf.append( '{' ).append( this.substituteVars(remainder) );
			}
		}
		
		return returnStrbuf.toString();
	}
	
	//TODO encode - The opposite of substituteVars needed to not have multiple substituteVars do wrong things.
	//              Also to 'store' variables encoded
//	public String encode( String aString )
//	{
//	
//		return null;
//	}
}
