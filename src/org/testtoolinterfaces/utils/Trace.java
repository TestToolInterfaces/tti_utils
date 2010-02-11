package org.testtoolinterfaces.utils;

import java.util.ArrayList;

/**
 * Class that can be used to print Trace statements. Actual printing
 * is only done when Trace requirements is met (i.e. enabled, Trace class,
 * depth, LEVEL).
 * 
 * @author Arjan Kranenburg
 */
public class Trace
{
	/**
	 * Enumeration to specify the level (amount) of trace to be printed.
	 *  
	 * @author Arjan Kranenburg
	 */
	public enum LEVEL
	{
		/** Prints only the most important trace statements */ 		HIGH,
		/** Prints the main execution path */ 						EXEC,
		/** Prints the execution path */ 							EXEC_PLUS,
		/** Prints the execution path including called utilities */ EXEC_UTIL,
		/** Prints the main execution path and loading the suite */ SUITE,
		/** Prints all traces upto constructors */ 					CONSTRUCTOR,
		/** Prints all traces upto utilities */ 					UTIL,
		/** Prints all traces upto setters */ 						SETTER,
		/** Prints all traces upto getters */ 						GETTER,
		/** Prints all traces */ 									ALL
	};

	
	public final static LEVEL HIGH        = LEVEL.HIGH;
	public final static LEVEL EXEC        = LEVEL.EXEC;
	public final static LEVEL EXEC_PLUS   = LEVEL.EXEC_PLUS;
	public final static LEVEL EXEC_UTIL   = LEVEL.EXEC_UTIL;
	public final static LEVEL SUITE       = LEVEL.SUITE;
	public final static LEVEL CONSTRUCTOR = LEVEL.CONSTRUCTOR;
	public final static LEVEL UTIL        = LEVEL.UTIL;
	public final static LEVEL SETTER      = LEVEL.SETTER;
	public final static LEVEL GETTER      = LEVEL.GETTER;
	public final static LEVEL ALL         = LEVEL.ALL;

	private static Trace instance = new Trace();

	private boolean myEnabled = false;
	private ArrayList<String> myBaseClasses = new ArrayList<String>();
	private String myTraceClass = "";
	private LEVEL myTraceLevel = LEVEL.EXEC_PLUS;
	private int myDepth = 3;
	
	/**
	 * 
	 */
	private Trace()
	{
		// To defeat instantiation by external objects
	}

	/**
	 * Will provide an instance of this class
	 * 
	 * @return the one instance of the TraceConfiguration class
	 */
	public static Trace getInstance()
	{
		return instance;
	}

	/**
	 * Add a baseclass that will be removed from the class string, to keep the trace readable
	 * 
	 * @param aBaseClass the BaseClass to add
	 */
	public void addBaseClass(String aBaseClass)
	{
		myBaseClasses.add(aBaseClass);
	}

	/**
	 * Sets the depth to keep tracing after the trace class is reached
	 * 
	 * @param aDepth the Depth to set
	 */
	public void setDepth(int aDepth)
	{
		myDepth = aDepth;
	}

	/**
	 * Sets the enable flag.
	 * 	true:  Trace is printed as soon as the trace class is reached
	 *  false: Trace is not printed
	 *  
	 * @param anEnabled the Enable flag to set
	 */
	public void setEnabled(boolean anEnabled)
	{
		myEnabled = anEnabled;
	}

	/**
	 * The Trace class that triggers printing traces if the enable flag is true
	 * 
	 * @param aTraceClass the TraceClass to set
	 */
	public void setTraceClass(String aTraceClass)
	{
		myTraceClass = aTraceClass;
	}

	/**
	 * Sets the level (i.e. the amount) of tracing to be printed.
	 * When set to HIGH, only the most important trace statements are printed
	 * When set to ALL, all trace statements are printed
	 * 
	 * Of course printing will only start when other requirements are met
	 * (i.e. enabled, Trace class, depth).
	 * 
	 * @param aTraceLevel the TraceLevel to set
	 */
	public void setTraceLevel(LEVEL aTraceLevel)
	{
		myTraceLevel = aTraceLevel;
	}

	/**
	 * Prints the class and this method without any arguments.
	 * 
	 * Printing is only done when Trace requirements is met (i.e. enabled,
	 * Trace class, depth, LEVEL)
	 * 
	 * @param aLevel specifies at what level this trace should be printed
	 */
	public static void println(LEVEL aLevel)
	{
		getInstance()._println(aLevel, true);
	}

	/**
	 * Prints optionally the class and method without any arguments.
	 * 
	 * Printing is only done when Trace requirements is met (i.e. enabled,
	 * Trace class, depth, LEVEL)
	 * 
	 * @param aLevel 	Specifies at what level this trace should be printed
	 * @param aMethod 	The method to print.
	 * 			This could also be used to print a message other than the method.
	 * 			Then set the aPrintClass flag to false.
	 * @param aPrintClass Flag to indicate if the class must be printed.
	 */
	public static void print(LEVEL aLevel, String aMethod, boolean aPrintClass)
	{
		getInstance()._print(aLevel, aMethod, aPrintClass);
	}

	/**
	 * Prints the trhowable (exception).
	 * 
	 * Printing is only done when Trace requirements is met (i.e. enabled,
	 * Trace class, depth, LEVEL)
	 * 
	 * @param aLevel 	Specifies at what level this trace should be printed
	 * @param anE		The throwable to print
	 */
	public static void print(LEVEL aLevel, Throwable anE)
	{
		getInstance()._print(aLevel, anE);
	}

	/**
	 * Prints optionally the class and method without any arguments, terminated
	 * with a newline.
	 * 
	 * Printing is only done when Trace requirements is met (i.e. enabled,
	 * Trace class, depth, LEVEL)
	 * 
	 * @param aLevel 	Specifies at what level this trace should be printed
	 * @param aMethod 	The method to print.
	 * 			This could also be used to print a message other than the method.
	 * 			Then set the aPrintClass flag to false.
	 * @param aPrintClass Flag to indicate if the class must be printed.
	 */
	public static void println(LEVEL aLevel, String aMethod, boolean aPrintClass)
	{
		print(aLevel, aMethod + '\n', aPrintClass);
	}

	/**
	 * Prints the method without any arguments.
	 * 
	 * Printing is only done when Trace requirements is met (i.e. enabled,
	 * Trace class, depth, LEVEL)
	 * 
	 * @param aLevel 	Specifies at what level this trace should be printed
	 * @param aMethod 	The method to print.
	 * 			This could also be used to print a message other than the method.
	 */
	public static void print(LEVEL aLevel, String aMethod)
	{
		print(aLevel, aMethod, false);
	}

	/**
	 * Prints the method without any arguments, terminated with a newline.
	 * 
	 * Printing is only done when Trace requirements is met (i.e. enabled,
	 * Trace class, depth, LEVEL)
	 * 
	 * @param aLevel 	Specifies at what level this trace should be printed
	 * @param aMethod 	The method to print.
	 * 			This could also be used to print a message other than the method.
	 */
	public static void println(LEVEL aLevel, String aMethod)
	{
		println(aLevel, aMethod, false);
	}

	/**
	 * Prints the message, without any prefix or class.
	 * 
	 * Note:
	 * No newline is printed at the end either. The user itself is responsible for that.
	 * 
	 * Printing is only done when Trace requirements is met (i.e. enabled,
	 * Trace class, depth, LEVEL)
	 * 
	 * @param aLevel 	Specifies at what level this trace should be printed
	 * @param aMessage 	The message to print.
	 */
	public static void append(LEVEL aLevel, String aMessage)
	{
		getInstance()._append(aLevel, aMessage);
	}

	private void _print(LEVEL aLevel, String aMethod, boolean aPrintClass)
	{
		if( myEnabled && aLevel.compareTo(myTraceLevel) <= 0)
		{
			ArrayList<StackTraceElement> stack = getStackTrace();
			if (stack.size() > 0)
			{
				String className = getClassName(stack);
				int depth = getDepth(stack, aMethod, className);

//if (stack.size() > 0) System.err.println("ClassName0 is " + stack.get(0).getClassName() );
//if (stack.size() > 1) System.err.println("ClassName1 is " + stack.get(1).getClassName() );
//if (stack.size() > 2) System.err.println("ClassName2 is " + stack.get(2).getClassName() );
				if ( depth <= myDepth )
				{
					if (aPrintClass)
					{
						aMethod = className + "." + aMethod;
					}
					String prefix = getPrefix( depth );
					System.out.print(prefix + aMethod);
				}
			}
		}
	}

	private void _append(LEVEL aLevel, String aMethod)
	{
		if( myEnabled && aLevel.compareTo(myTraceLevel) <= 0)
		{
			ArrayList<StackTraceElement> stack = getStackTrace();
			if (stack.size() > 0)
			{
				String className = getClassName(stack);
				int depth = getDepth(stack, aMethod, className);

				if ( depth <= myDepth )
				{
					System.out.print(aMethod);
				}
			}
		}
	}

	private void _println(LEVEL aLevel, boolean aPrintClass)
	{
		if( myEnabled && aLevel.compareTo(this.myTraceLevel) <= 0)
		{
			ArrayList<StackTraceElement> stack = getStackTrace();
			if (stack.size() > 0)
			{
				String method = stack.get(0).getMethodName();
				_print(aLevel, method + '\n', aPrintClass);
			}
		}
	}

	/**
	 * 
	 */
	private void _print(LEVEL aLevel, Throwable anE)
	{
		if( myEnabled && aLevel.compareTo(this.myTraceLevel) <= 0)
		{
			ArrayList<StackTraceElement> stack = getStackTrace();
			if (stack.size() > 0)
			{
				String method = stack.get(0).getMethodName();
				String className = getClassName(stack);
				int depth = getDepth(stack, method, className);
				if ( depth <= myDepth )
				{
					anE.printStackTrace(System.out);
				}
			}
		}
	}
	
	/**
	 * Returns a list of classes from the stacktrace.
	 * The newest class is returned first.
	 * 
	 * Only the classes starting with a class in the myBaseClasses are returned
	 * Also, this Trace class is filtered out as well.
	 */
	private ArrayList<StackTraceElement> getStackTrace()
	{
		ArrayList<StackTraceElement> aStackTrace = new ArrayList<StackTraceElement>();
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		String thisTraceClass = Trace.class.getName(); // To filter out
		for (int i = 0; i<stack.length; i++)
		{
			String elementClassName = stack[i].getClassName();
			for (int j = 0; j < myBaseClasses.size(); j++)
			{
				if ( elementClassName.startsWith(myBaseClasses.get(j)) 
					 && ! elementClassName.equals(thisTraceClass) )
				{
					aStackTrace.add(stack[i]);
				}
			}
		}
		return aStackTrace;
	}
	
	private int getDepth(ArrayList<StackTraceElement> aStack, String aMethod, String aClass)
	{
		boolean foundClass = false;
		int depth = 99;

//System.err.print( "Depth of " + aMethod );
		for (int i = aStack.size()-1; i>=0; i--)
		{
			String stackLine = aStack.get(i).getClassName();
			String shortElClssName = getStrippedClassName(stackLine);

			if ( foundClass )
			{
				depth++;
			}

			if ( stackLine.equals(myTraceClass) )
			{
				foundClass = true;
				depth = 0;
			}

			if ( foundClass 
				 && shortElClssName.equals(aClass) 
				 && aStack.get(i).getMethodName().equals(aMethod) )
			{
//System.err.println( " is (2) " + depth );
				return depth;
			}
		}
//System.err.println( " is " + depth );
		return depth;
	}

	private String getClassName(ArrayList<StackTraceElement> aStack)
	{
		String className = aStack.get(0).getClassName();
		String[] classPath = className.split("\\.");

		return classPath[ classPath.length-1 ];
	}
	
	private String getStrippedClassName( String aClassName )
	{
		String shortClassName = aClassName;
		for (int j = 0; j < myBaseClasses.size(); j++)
		{
			if ( aClassName.startsWith(myBaseClasses.get(j)) )
			{
				shortClassName = aClassName.replaceFirst(myBaseClasses.get(j), "");
				shortClassName = shortClassName.replaceFirst("\\.", "");
				
				// As soon as we have a match, we return the shortened class name
				return shortClassName;
			}
		}
		return shortClassName; // If no match, we return the stackLine
	}

	private static String getPrefix( int aDepth )
	{
		int i=0;
		String prefix = "";
		while ( i++<aDepth )
		{
			prefix += "+";
		}
		
		return prefix + " ";
	}
	
	public void dumpSettings()
	{
		System.out.println("Enabled:    " + myEnabled);
		System.out.println("Depth:      " + myDepth);
		System.out.println("TraceClass: " + myTraceClass);
		System.out.println("TraceLevel: " + myTraceLevel);
		for (int j = 0; j < myBaseClasses.size(); j++)
		{
			System.out.println("BaseClass:  " + myBaseClasses.get(j));
		}
	}
}
