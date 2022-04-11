package com.visiongc.framework.auditoria.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.*;

public class AuditoriaEventoPK
    implements Serializable
{

    public AuditoriaEventoPK(Date fecha, String instanciaId, Byte tipoEvento)
    {
        this.fecha = fecha;
        this.instanciaId = instanciaId;
        this.tipoEvento = tipoEvento;
    }

    public AuditoriaEventoPK()
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

    public Byte getTipoEvento()
    {
        return tipoEvento;
    }

    public void setTipoEvento(Byte tipoEvento)
    {
        this.tipoEvento = tipoEvento;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("fecha", getFecha()).append("instanciaId", getInstanciaId()).append("tipoEvento", getTipoEvento()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof AuditoriaEventoPK))
        {
            return false;
        } else
        {
            AuditoriaEventoPK castOther = (AuditoriaEventoPK)other;
            return (new EqualsBuilder()).append(getFecha(), castOther.getFecha()).append(getInstanciaId(), castOther.getInstanciaId()).append(getTipoEvento(), castOther.getTipoEvento()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getFecha()).append(getInstanciaId()).append(getTipoEvento()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private Date fecha;
    private String instanciaId;
    private Byte tipoEvento;
}