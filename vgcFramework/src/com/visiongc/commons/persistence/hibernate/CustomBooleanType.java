package com.visiongc.commons.persistence.hibernate;

import java.io.Serializable;
import java.sql.*;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.type.NullableType;
import org.hibernate.usertype.UserType;

public class CustomBooleanType
    implements UserType
{

    public CustomBooleanType()
    {
    }

    public int[] sqlTypes()
    {
        return (new int[] {
            4
        });
    }

    public Class returnedClass()
    {
        return Boolean.class;
    }

    public boolean equals(Object x, Object y)
        throws HibernateException
    {
        return x == y || x != null && y != null && x.equals(y);
    }

    public Object nullSafeGet(ResultSet inResultSet, String names[], Object o)
        throws HibernateException, SQLException
    {
        Integer val = (Integer)Hibernate.INTEGER.nullSafeGet(inResultSet, names[0]);
        return parseIntToBoolean(val);
    }

    public void nullSafeSet(PreparedStatement inPreparedStatement, Object o, int i)
        throws HibernateException, SQLException
    {
        int val = BooleanToInt((Boolean)o);
        inPreparedStatement.setInt(i, val);
    }

    public Object deepCopy(Object o)
        throws HibernateException
    {
        if(o == null)
            return null;
        else
            return new Boolean(((Boolean)o).booleanValue());
    }

    public boolean isMutable()
    {
        return false;
    }

    private Boolean parseIntToBoolean(Integer in)
    {
        if(in != null && !in.equals(new Integer(0)))
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }

    public int BooleanToInt(Boolean bool)
    {
        int ret;
        if(bool != null)
        {
            if(bool.booleanValue())
                ret = 1;
            else
                ret = 0;
        } else
        {
            ret = 0;
        }
        return ret;
    }

    public Serializable disassemble(Object value)
        throws HibernateException
    {
        return String.valueOf(value);
    }

    public Object assemble(Serializable cached, Object owner)
        throws HibernateException
    {
        return Boolean.valueOf((String)cached);
    }

    public Object replace(Object original, Object target, Object owner)
        throws HibernateException
    {
        return original;
    }

    public int hashCode(Object x)
        throws HibernateException
    {
        return 0;
    }
}