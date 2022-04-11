

package com.visiongc.framework.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.*;

public class LockPK
    implements Serializable
{

    public LockPK(Long objetoId, String tipo)
    {
        this.objetoId = objetoId;
        this.tipo = tipo;
    }

    public LockPK()
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

    public String getTipo()
    {
        return tipo;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("objetoId", getObjetoId()).append("tipo", getTipo()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof LockPK))
        {
            return false;
        } else
        {
            LockPK castOther = (LockPK)other;
            return (new EqualsBuilder()).append(getObjetoId(), castOther.getObjetoId()).append(getTipo(), castOther.getTipo()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getObjetoId()).append(getTipo()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private Long objetoId;
    private String tipo;
}