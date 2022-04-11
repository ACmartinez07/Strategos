package com.visiongc.framework.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.*;

public class LockReadPK
    implements Serializable
{

    public LockReadPK(Long objetoId, String instancia)
    {
        this.objetoId = objetoId;
        this.instancia = instancia;
    }

    public LockReadPK()
    {
    }

    public Long getObjetoId()
    {
        return objetoId;
    }

    public void setObjetoId(Long objetoId)
    {
        this.objetoId = objetoId;
    }

    public String getInstancia()
    {
        return instancia;
    }

    public void setInstancia(String instancia)
    {
        this.instancia = instancia;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("objetoId", getObjetoId()).append("instancia", getInstancia()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof LockReadPK))
        {
            return false;
        } else
        {
            LockReadPK castOther = (LockReadPK)other;
            return (new EqualsBuilder()).append(getObjetoId(), castOther.getObjetoId()).append(getInstancia(), castOther.getInstancia()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getObjetoId()).append(getInstancia()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private Long objetoId;
    private String instancia;
}