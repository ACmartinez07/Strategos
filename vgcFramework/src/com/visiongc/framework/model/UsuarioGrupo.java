package com.visiongc.framework.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.*;

// Referenced classes of package com.visiongc.framework.model:
//            UsuarioGrupoPK, Grupo, Organizacion, Usuario

public class UsuarioGrupo
    implements Serializable
{

    public UsuarioGrupo(UsuarioGrupoPK pk, Date modificado, Long modificadoId, Grupo grupo, Organizacion organizacion, Usuario usuario, Set permisosGrupo)
    {
        this.pk = pk;
        this.modificado = modificado;
        this.modificadoId = modificadoId;
        this.grupo = grupo;
        this.organizacion = organizacion;
        this.usuario = usuario;
        this.permisosGrupo = permisosGrupo;
    }

    public UsuarioGrupo()
    {
    }

    public UsuarioGrupo(UsuarioGrupoPK pk)
    {
        this.pk = pk;
    }

    public UsuarioGrupoPK getPk()
    {
        return pk;
    }

    public void setPk(UsuarioGrupoPK pk)
    {
        this.pk = pk;
    }

    public Date getModificado()
    {
        return modificado;
    }

    public void setModificado(Date modificado)
    {
        this.modificado = modificado;
    }

    public Long getModificadoId()
    {
        return modificadoId;
    }

    public void setModificadoId(Long modificadoId)
    {
        this.modificadoId = modificadoId;
    }

    public Grupo getGrupo()
    {
        return grupo;
    }

    public void setGrupo(Grupo grupo)
    {
        this.grupo = grupo;
    }

    public Organizacion getOrganizacion()
    {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion)
    {
        this.organizacion = organizacion;
    }

    public Usuario getUsuario()
    {
        return usuario;
    }

    public void setUsuario(Usuario usuario)
    {
        this.usuario = usuario;
    }

    public Set getPermisosGrupo()
    {
        return permisosGrupo;
    }

    public void setPermisosGrupo(Set permisosGrupo)
    {
        this.permisosGrupo = permisosGrupo;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("pk", getPk()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof UsuarioGrupo))
        {
            return false;
        } else
        {
            UsuarioGrupo castOther = (UsuarioGrupo)other;
            return (new EqualsBuilder()).append(getPk(), castOther.getPk()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getPk()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private UsuarioGrupoPK pk;
    private Date modificado;
    private Long modificadoId;
    private Grupo grupo;
    private Organizacion organizacion;
    private Usuario usuario;
    private Set permisosGrupo;
}