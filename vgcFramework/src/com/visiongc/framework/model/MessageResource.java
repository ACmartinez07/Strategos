package com.visiongc.framework.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MessageResource
    implements Serializable, Comparable
{

    public MessageResource(String clave, String valor)
    {
        this.clave = clave;
        this.valor = valor;
    }

    public MessageResource()
    {
    }

    public String getClave()
    {
        return clave;
    }

    public void setClave(String clave)
    {
        this.clave = clave;
    }

    public String getValor()
    {
        return valor;
    }

    public void setValor(String valor)
    {
        this.valor = valor;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("clave", getClave()).toString();
    }

    public int compareTo(Object o)
    {
        MessageResource mr = (MessageResource)o;
        return getClave().compareTo(mr.getClave());
    }

    static final long serialVersionUID = 0L;
    private String clave;
    private String valor;
}