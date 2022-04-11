package com.visiongc.framework.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.*;

public class UsuarioGrupoPK
    implements Serializable
{

    public UsuarioGrupoPK(Long usuarioId, Long grupoId, Long organizacionId)
    {
        this.usuarioId = usuarioId;
        this.grupoId = grupoId;
        this.organizacionId = organizacionId;
    }

    public UsuarioGrupoPK()
    {
    }

    public Long getUsuarioId()
    {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId)
    {
        this.usuarioId = usuarioId;
    }

    public Long getGrupoId()
    {
        return grupoId;
    }

    public void setGrupoId(Long grupoId)
    {
        this.grupoId = grupoId;
    }

    public Long getOrganizacionId()
    {
        return organizacionId;
    }

    public void setOrganizacionId(Long organizacionId)
    {
        this.organizacionId = organizacionId;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("usuarioId", getUsuarioId()).append("grupoId", getGrupoId()).append("organizacionId", getOrganizacionId()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof UsuarioGrupoPK))
        {
            return false;
        } else
        {
            UsuarioGrupoPK castOther = (UsuarioGrupoPK)other;
            return (new EqualsBuilder()).append(getUsuarioId(), castOther.getUsuarioId()).append(getGrupoId(), castOther.getGrupoId()).append(getOrganizacionId(), castOther.getOrganizacionId()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getUsuarioId()).append(getGrupoId()).append(getOrganizacionId()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private Long usuarioId;
    private Long grupoId;
    private Long organizacionId;
}