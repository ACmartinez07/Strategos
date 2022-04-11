package com.visiongc.framework.auditoria.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.*;

public class AuditoriaAtributoPK
    implements Serializable
{

    public AuditoriaAtributoPK(Date fecha, String instanciaId, String nombreAtributo)
    {
        this.fecha = fecha;
        this.instanciaId = instanciaId;
        this.nombreAtributo = nombreAtributo;
    }

    public AuditoriaAtributoPK()
    {
    }

    public Date getFecha()
    {
        return fecha;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    public String getInstanciaId()
    {
        return instanciaId;
    }

    public void setInstanciaId(String instanciaId)
    {
        this.instanciaId = instanciaId;
    }

    public String getNombreAtributo()
    {
        return nombreAtributo;
    }

    public void setNombreAtributo(String nombreAtributo)
    {
        this.nombreAtributo = nombreAtributo;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("fecha", getFecha()).append("instanciaId", getInstanciaId()).append("nombreAtributo", getNombreAtributo()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof AuditoriaAtributoPK))
        {
            return false;
        } else
        {
            AuditoriaAtributoPK castOther = (AuditoriaAtributoPK)other;
            return (new EqualsBuilder()).append(getFecha(), castOther.getFecha()).append(getInstanciaId(), castOther.getInstanciaId()).append(getNombreAtributo(), castOther.getNombreAtributo()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getFecha()).append(getInstanciaId()).append(getNombreAtributo()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private Date fecha;
    private String instanciaId;
    private String nombreAtributo;
}