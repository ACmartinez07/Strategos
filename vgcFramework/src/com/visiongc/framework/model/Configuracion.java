package com.visiongc.framework.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Configuracion
    implements Serializable, Comparable
{

    public Configuracion()
    {
    }

    public String getParametro()
    {
        return parametro;
    }

    public void setParametro(String parametro)
    {
        this.parametro = parametro;
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
        return (new ToStringBuilder(this)).append("parametro", getParametro()).append("valor", getValor()).toString();
    }

    public int compareTo(Object o)
    {
        Configuracion configuracion = (Configuracion)o;
        return getParametro().compareTo(configuracion.getParametro());
    }

    static final long serialVersionUID = 0L;
    private String parametro;
    private String valor;
}