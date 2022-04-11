package com.visiongc.framework.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.*;

// Referenced classes of package com.visiongc.framework.model:
//            PermisoGrupo

public class PermisoUsuario
    implements Serializable
{

    public PermisoUsuario(String permisoId, Long organizacionId, Long usuarioId)
    {
        this.permisoId = permisoId;
        this.organizacionId = organizacionId;
        this.usuarioId = usuarioId;
    }

    public PermisoUsuario()
    {
    }

    public Long getOrganizacionId()
    {
        return organizacionId;
    }

    public void setOrganizacionId(Long organizacionId)
    {
        this.organizacionId = organizacionId;
    }

    public String getPermisoId()
    {
        return permisoId;
    }

    public void setPermisoId(String permisoId)
    {
        this.permisoId = permisoId;
    }

    public Long getUsuarioId()
    {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId)
    {
        this.usuarioId = usuarioId;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("permisoId", getPermisoId()).append("organizacionId", getOrganizacionId()).append("usuarioId", getUsuarioId()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof PermisoGrupo))
        {
            return false;
        } else
        {
            PermisoUsuario castOther = (PermisoUsuario)other;
            return (new EqualsBuilder()).append(getPermisoId(), castOther.getPermisoId()).append(getOrganizacionId(), castOther.getOrganizacionId()).append(getUsuarioId(), castOther.getUsuarioId()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getPermisoId()).append(getOrganizacionId()).append(getUsuarioId()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private String permisoId;
    private Long organizacionId;
    private Long usuarioId;
}