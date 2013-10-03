package org.testtoolinterfaces.utils;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

@SuppressWarnings("deprecation")
public class Mark {
	
	public static final Marker HIGH = MarkerFactory.getMarker(Trace.HIGH.toString());
	public static final Marker EXEC = MarkerFactory.getMarker(Trace.EXEC.toString());
	public static final Marker EXEC_PLUS = MarkerFactory.getMarker(Trace.EXEC_PLUS.toString());
	public static final Marker EXEC_UTIL = MarkerFactory.getMarker(Trace.EXEC_UTIL.toString());
	public static final Marker SUITE = MarkerFactory.getMarker(Trace.SUITE.toString());
	public static final Marker CONSTRUCTOR = MarkerFactory.getMarker(Trace.CONSTRUCTOR.toString());
	public static final Marker UTIL = MarkerFactory.getMarker(Trace.UTIL.toString());
	public static final Marker SETTER = MarkerFactory.getMarker(Trace.SETTER.toString());
	public static final Marker GETTER = MarkerFactory.getMarker(Trace.GETTER.toString());
	public static final Marker ALL = MarkerFactory.getMarker(Trace.ALL.toString());
}
