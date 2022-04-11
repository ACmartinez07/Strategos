package com.visiongc.commons.util.lang;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ChainedRuntimeException extends RuntimeException
{

    public ChainedRuntimeException(String msg)
    {
        super(msg);
        cause = null;
    }

    public ChainedRuntimeException(String msg, Throwable cause)
    {
        super(msg);
        this.cause = null;
        this.cause = cause;
    }

    public Throwable getCause()
    {
        return cause;
    }

    public String toString()
    {
        if(cause == null)
            return (new StringBuilder("(Sin Causa) ")).append(getMessage()).toString();
        else
            return (new StringBuilder("(")).append(cause.getClass().getName()).append(") ").append(cause.getMessage()).toString();
    }

    public void printStackTrace()
    {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream s)
    {
        super.printStackTrace(s);
        if(cause != null)
        {
            s.print("Causado por: ");
            cause.printStackTrace(s);
        }
    }

    public void printStackTrace(PrintWriter s)
    {
        super.printStackTrace(s);
        if(cause != null)
        {
            s.print("Causado por: ");
            cause.printStackTrace(s);
        }
    }

    static final long serialVersionUID = 0L;
    private Throwable cause;
}