package com.visiongc.framework.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.*;

public class Tabla
    implements Serializable
{

    public Tabla(String nombre, List columnas)
    {
        this.columnas = new ArrayList();
        this.nombre = nombre;
        this.columnas = columnas;
    }

    public Tabla()
    {
        columnas = new ArrayList();
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public List getColumnas()
    {
        return columnas;
    }

    public void setColumnas(List columnas)
    {
        this.columnas = columnas;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("nombre", getNombre()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof Tabla))
        {
            return false;
        } else
        {
            Tabla castOther = (Tabla)other;
            return (new EqualsBuilder()).append(getNombre(), castOther.getNombre()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getNombre()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private String nombre;
    private List columnas;
}