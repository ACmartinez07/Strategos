package com.visiongc.framework.model;

import java.io.Serializable;
import java.util.*;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Grupo
    implements Serializable
{

    public Grupo(Long grupoId, String grupo, Date creado, Date modificado, Long creadoId, Long modificadoId, Set usuarioGrupos, 
            Set permisos)
    {
        this.grupoId = grupoId;
        this.grupo = grupo;
        this.creado = creado;
        this.modificado = modificado;
        this.creadoId = creadoId;
        this.modificadoId = modificadoId;
        this.usuarioGrupos = usuarioGrupos;
        this.permisos = permisos;
    }

    public Grupo()
    {
        permisos = new HashSet();
        usuarioGrupos = new HashSet();
    }

    public Grupo(Long grupoId, String grupo, Set usuarioGrupos, Set permisos)
    {
        this.grupoId = grupoId;
        this.grupo = grupo;
        this.usuarioGrupos = usuarioGrupos;
        this.permisos = permisos;
    }

    public Long getGrupoId()
    {
        return grupoId;
    }

    public void setGrupoId(Long grupoId)
    {
        this.grupoId = grupoId;
    }

    public String getGrupo()
    {
        return grupo;
    }

    public void setGrupo(String grupo)
    {
        this.grupo = grupo;
    }

    public Date getCreado()
    {
        return creado;
    }

    public void setCreado(Date creado)
    {
        this.creado = creado;
    }

    public Date getModificado()
    {
        return modificado;
    }

    public void setModificado(Date modificado)
    {
        this.modificado = modificado;
    }

    public Long getCreadoId()
    {
        return creadoId;
    }

    public void setCreadoId(Long creadoId)
    {
        this.creadoId = creadoId;
    }

    public Long getModificadoId()
    {
        return modificadoId;
    }

    public void setModificadoId(Long modificadoId)
    {
        this.modificadoId = modificadoId;
    }

    public Set getUsuarioGrupos()
    {
        return usuarioGrupos;
    }

    public void setUsuarioGrupos(Set usuarioGrupos)
    {
        this.usuarioGrupos = usuarioGrupos;
    }

    public Set getPermisos()
    {
        return permisos;
    }

    public void setPermisos(Set permisos)
    {
        this.permisos = permisos;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("grupoId", getGrupoId()).toString();
    }

    static final long serialVersionUID = 0L;
    private Long grupoId;
    private String grupo;
    private Date creado;
    private Date modificado;
    private Long creadoId;
    private Long modificadoId;
    private Set usuarioGrupos;
    private Set permisos;
}