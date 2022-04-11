package com.visiongc.framework.auditoria.model;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.*;

public class ObjetoAuditable
    implements Serializable
{

    public ObjetoAuditable()
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

    public String getNombreCampoId()
    {
        String campoId = null;
        if(nombreCampoId != null)
            campoId = (new StringBuilder(String.valueOf(nombreCampoId.substring(0, 1).toUpperCase()))).append(nombreCampoId.substring(1)).toString();
        return campoId;
    }

    public void setNombreCampoId(String nombreCampoId)
    {
        this.nombreCampoId = nombreCampoId;
    }

    public String getNombreCampoNombre()
    {
        String campoNombre = null;
        if(nombreCampoNombre != null)
            campoNombre = (new StringBuilder(String.valueOf(nombreCampoNombre.substring(0, 1).toUpperCase()))).append(nombreCampoNombre.substring(1)).toString();
        return campoNombre;
    }

    public void setNombreCampoNombre(String nombreCampoNombre)
    {
        this.nombreCampoNombre = nombreCampoNombre;
    }

    public String getNombreClase()
    {
        return nombreClase;
    }

    public void setNombreClase(String nombreClase)
    {
        this.nombreClase = nombreClase;
    }

    public String getNombreCortoClase()
    {
        String nombre = "";
        if(nombreClase != null)
            nombre = nombreClase.substring(nombreClase.lastIndexOf(".") + 1);
        return nombre;
    }

    public String getNombreClaseFormateado()
    {
        return nombreClaseFormateado;
    }

    public void setNombreClaseFormateado(String nombreClaseFormateado)
    {
        this.nombreClaseFormateado = nombreClaseFormateado;
    }

    public Boolean getAuditoriaActiva()
    {
        return auditoriaActiva;
    }

    public void setAuditoriaActiva(Boolean auditoriaActiva)
    {
        this.auditoriaActiva = auditoriaActiva;
    }

    public Set getAtributosAuditables()
    {
        return atributosAuditables;
    }

    public void setAtributosAuditables(Set atributosAuditables)
    {
        this.atributosAuditables = atributosAuditables;
    }

    public Boolean getHasValue()
    {
        return hasValue;
    }

    public void setHasValue(Boolean hasValue)
    {
        this.hasValue = hasValue;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("objetoId", getObjetoId()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof ObjetoAuditable))
        {
            return false;
        } else
        {
            ObjetoAuditable castOther = (ObjetoAuditable)other;
            return (new EqualsBuilder()).append(getObjetoId(), castOther.getObjetoId()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getObjetoId()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private Long objetoId;
    private String nombreClase;
    private String nombreCampoId;
    private String nombreCampoNombre;
    private String nombreClaseFormateado;
    private Boolean auditoriaActiva;
    private Set atributosAuditables;
    private Boolean hasValue;
}