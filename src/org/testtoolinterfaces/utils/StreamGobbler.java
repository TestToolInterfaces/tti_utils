package org.testtoolinterfaces.utils;

import java.io.*;

import org.testtoolinterfaces.utils.Trace;


/**
 * Catches the output and error of external commands
 * 
 * @see http://www.javaworld.com/javaworld/jw-12-2000/jw-1229-traps.html
 * @author Michael C. Daconta, JavaWorld.com, 12/29/00
 * @author Arjan Kranenburg
 */
public class StreamGobbler extends Thread
{
	public static long MAX_WAIT = 2;
	
    private InputStream myIs;
    private String myType;
    private OutputStream myOs;
    
    /**
     * @param myIs		The Input Stream
     * @param myType	the type of input stream
     */
    public StreamGobbler(InputStream anIs, String aType)
    {
        myIs = anIs;
        myType = aType;
    }
    
    /**
     * @param myIs		The Input Stream
     * @param myType	The type of input stream
     * @param redirect	The Output Stream to redirect the input
     */
    public StreamGobbler(InputStream is, String type, OutputStream redirect)
    {
        myIs = is;
        myType = type;
        myOs = redirect;
    }
   
   /** 
    * Reads lines from the Input Stream and writes it to stdout and the
    * Output Stream.
    * 
    * The print to stdout is prefixed with the type and a '>' character
    * 
    */
    public void run()
    {
		Trace.println(Trace.UTIL);
        try
        {
            PrintWriter pw = null;
            if (myOs != null)
            {
                pw = new PrintWriter(myOs);
            }
                
            InputStreamReader isr = new InputStreamReader(myIs);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null)
            {
                if (pw != null)
                {
                    pw.println(line);
            		Trace.println(Trace.ALL, "Output written to file");
                }
                Trace.println(Trace.EXEC_PLUS, myType + ">" + line);    
            }
            if (pw != null)
            {
                pw.flush();
        		Trace.println(Trace.ALL, "Output flushed");
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();  
        }
    }
}