package com.visiongc.framework.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.*;

public class PermisoGrupoPK
    implements Serializable
{

    public PermisoGrupoPK(Long permisoId, Long grupoId)
    {
        this.permisoId = permisoId;
        this.grupoId = grupoId;
    }

    public PermisoGrupoPK()
    {
    }

    public Long getPermisoId()
    {
        return permisoId;
    }

    public void setPermisoId(Long permisoId)
    {
        this.permisoId = permisoId;
    }

    public Long getGrupoId()
    {
        return grupoId;
    }

    public void setGrupoId(Long grupoId)
    {
        this.grupoId = grupoId;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("permisoId", getPermisoId()).append("grupoId", getGrupoId()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof PermisoGrupoPK))
        {
            return false;
        } else
        {
            PermisoGrupoPK castOther = (PermisoGrupoPK)other;
            return (new EqualsBuilder()).append(getPermisoId(), castOther.getPermisoId()).append(getGrupoId(), castOther.getGrupoId()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getPermisoId()).append(getGrupoId()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private Long permisoId;
    private Long grupoId;
}