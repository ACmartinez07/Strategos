package com.visiongc.framework.auditoria.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.*;

public class ObjetoAuditableAtributoPK
    implements Serializable
{

    public ObjetoAuditableAtributoPK(Long objetoId, String nombre)
    {
        this.objetoId = objetoId;
        this.nombre = nombre;
    }

    public ObjetoAuditableAtributoPK()
    {
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public Long getObjetoId()
    {
        return objetoId;
    }

    public void setObjetoId(Long objetoId)
    {
        this.objetoId = objetoId;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("objetoId", getObjetoId()).append("nombre", getNombre()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof ObjetoAuditableAtributoPK))
        {
            return false;
        } else
        {
            ObjetoAuditableAtributoPK castOther = (ObjetoAuditableAtributoPK)other;
            return (new EqualsBuilder()).append(getObjetoId(), castOther.getObjetoId()).append(getNombre(), castOther.getNombre()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getObjetoId()).append(getNombre()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private Long objetoId;
    private String nombre;
}