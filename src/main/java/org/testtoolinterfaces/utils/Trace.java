package org.testtoolinterfaces.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;


/**
 * Class that can be used to print Trace statements. Actual printing
 * is only done when Trace requirements is met (i.e. enabled, Trace class,
 * depth, LEVEL).
 * 
 * @author Arjan Kranenburg
 */
@Deprecated // Use org.slf4j.Logger instead
public class Trace
{
    private static final Logger LOG = LoggerFactory.getLogger(Trace.class);

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


	
//	private boolean myEnabled = false;
//	private ArrayList<String> myBaseClasses = new ArrayList<String>();
//	private String myTraceClass = "";
//	private LEVEL myTraceLevel = LEVEL.EXEC_PLUS;
//	private int myDepth = 3;

//	private final static String NEWLINE = System.getProperty( "line.separator" );

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
	 * @return the one instance of the Trace class
	 * 
	 * @deprecated handled by logger
	 */
	@Deprecated
	public static Trace getInstance()
	{
		return instance;
	}

	/**
	 * @deprecated Does nothing, functionality is handled by logger
	 */
	@Deprecated
	public void addBaseClass(String aBaseClass)	{
		// Nop, handled by logger
	}

	/**
	 * @deprecated Does nothing, functionality is handled by logger
	 */
	@Deprecated
	public void setDepth(int aDepth) {
		// Nop, handled by logger
	}

	/**
	 * @deprecated Does nothing, functionality is handled by logger
	 */
	@Deprecated
	public void setEnabled(boolean anEnabled) {
		// Nop, handled by logger
	}

	/**
	 * @deprecated Does nothing, functionality is handled by logger
	 */
	@Deprecated
	public void setTraceClass(String aTraceClass) {
		// Nop, handled by logger
	}

	/**
	 * @deprecated Does nothing, functionality is handled by logger
	 */
	@Deprecated
	public void setTraceLevel(LEVEL aTraceLevel) {
		// Nop, handled by logger
	}

	/**
	 * Prints the class and this method without any arguments.
	 * Actual printing depends on logger.
	 * 
	 * @param aLevel specifies at what level this trace should be printed
	 * 
	 * @deprecated Use logger
	 */
	@Deprecated
	public static void println(LEVEL aLevel)
	{
		LOG.trace(MarkerFactory.getMarker( aLevel.toString() ), "");
	}

	/**
	 * Prints optionally the class and method without any arguments.
	 * Actual printing depends on logger.
	 * 
	 * @param aLevel 	Specifies at what level this trace should be printed
	 * @param aMethod 	The method to print.
	 * 			This could also be used to print a message other than the method.
	 * 			Then set the aPrintClass flag to false.
	 * @param aPrintClass ignored
	 * 
	 * @deprecated Use logger
	 */
	@Deprecated
	public static void print(LEVEL aLevel, String aMethod, boolean aPrintClass)
	{
		LOG.trace(MarkerFactory.getMarker( aLevel.toString() ), aMethod);
	}

	/**
	 * Prints the trhowable (exception).
	 * Actual printing depends on logger.
	 * 
	 * @param aLevel 	Specifies at what level this trace should be printed
	 * @param anE		The throwable to print
	 * 
	 * @deprecated Use logger
	 */
	@Deprecated
	public static void print(LEVEL aLevel, Throwable anE)
	{
		LOG.trace(MarkerFactory.getMarker( aLevel.toString() ), "", anE);
	}

	/**
	 * Prints optionally the class and method without any arguments.
	 * Actual printing depends on logger.
	 * 
	 * @param aLevel 	Specifies at what level this trace should be printed
	 * @param aMethod 	The method to print.
	 * 			This could also be used to print a message other than the method.
	 * 			Then set the aPrintClass flag to false.
	 * @param aPrintClass ignored
	 * 
	 * @deprecated Use logger
	 */
	@Deprecated
	public static void println(LEVEL aLevel, String aMethod, boolean aPrintClass)
	{
		LOG.trace(MarkerFactory.getMarker( aLevel.toString() ), aMethod);
	}

	/**
	 * Prints the method without any arguments.
	 * Actual printing depends on logger.
	 * 
	 * @param aLevel 	Specifies at what level this trace should be printed
	 * @param aMethod 	The method to print.
	 * 			This could also be used to print a message other than the method.
	 * 
	 * @deprecated Use logger
	 */
	@Deprecated
	public static void print(LEVEL aLevel, String aMethod)
	{
		LOG.trace(MarkerFactory.getMarker( aLevel.toString() ), aMethod);
	}

	/**
	 * Prints the method without any arguments, terminated with a newline.
	 * Actual printing depends on logger.
	 * 
	 * @param aLevel 	Specifies at what level this trace should be printed
	 * @param aMethod 	The method to print.
	 * 			This could also be used to print a message other than the method.
	 * 
	 * @deprecated Use logger
	 */
	@Deprecated
	public static void println(LEVEL aLevel, String aMethod)
	{
		LOG.trace(MarkerFactory.getMarker( aLevel.toString() ), aMethod);
	}

	/**
	 * Same as print(LEVEL aLevel, String aMethod)
	 * 
	 * @param aLevel 	Specifies at what level this trace should be printed
	 * @param aMessage 	The message to print.
	 * 
	 * @deprecated Use logger
	 */
	@Deprecated
	public static void append(LEVEL aLevel, String aMessage)
	{
		LOG.trace(MarkerFactory.getMarker( aLevel.toString() ), aMessage);
	}
	
	@Deprecated
	public void dumpSettings()	{
		// NOP: only kept to satisfy API
	}
}
