package com.visiongc.commons.persistence.lob;

import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;

public class BlobImpl
    implements Blob
{

    public BlobImpl(byte bytes[])
    {
        needsReset = false;
        stream = new ByteArrayInputStream(bytes);
        length = bytes.length;
    }

    public BlobImpl(InputStream stream, int length)
    {
        needsReset = false;
        this.stream = stream;
        this.length = length;
    }

    public long length()
        throws SQLException
    {
        return (long)length;
    }

    public void truncate(long pos)
        throws SQLException
    {
        excep();
    }

    public byte[] getBytes(long pos, int len)
        throws SQLException
    {
        excep();
        return null;
    }

    public int setBytes(long pos, byte bytes[])
        throws SQLException
    {
        excep();
        return 0;
    }

    public int setBytes(long pos, byte bytes[], int i, int j)
        throws SQLException
    {
        excep();
        return 0;
    }

    public long position(byte bytes[], long pos)
        throws SQLException
    {
        excep();
        return 0L;
    }

    public InputStream getBinaryStream()
        throws SQLException
    {
        try
        {
            if(needsReset)
                stream.reset();
        }
        catch(IOException ioe)
        {
            throw new SQLException("could not reset reader");
        }
        needsReset = true;
        return stream;
    }

    public OutputStream setBinaryStream(long pos)
        throws SQLException
    {
        excep();
        return null;
    }

    public long position(Blob blob, long pos)
        throws SQLException
    {
        excep();
        return 0L;
    }

    private static void excep()
    {
        throw new UnsupportedOperationException("Blob may not be manipulated from creating session");
    }

    public void free()
        throws SQLException
    {
    }

    public InputStream getBinaryStream(long arg0, long arg1)
        throws SQLException
    {
        return null;
    }

    private InputStream stream;
    private int length;
    private boolean needsReset;
}